package com.wxl.xxljob.toolwindow

import com.intellij.ide.CommonActionsManager
import com.intellij.openapi.Disposable
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionPlaces
import com.intellij.openapi.actionSystem.ActionToolbar
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.application.ModalityState
import com.intellij.openapi.application.ReadAction
import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.DumbService
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.openapi.wm.ToolWindow
import com.wxl.xxljob.action.RefreshAction
import com.wxl.xxljob.finder.DefaultXxlJobFinder
import com.wxl.xxljob.finder.XxlJobFinder
import com.wxl.xxljob.model.XxlJobModel
import com.wxl.xxljob.view.tree.node.*
import java.util.concurrent.*

/**
 * Create by wuxingle on 2022/2/19
 * xxl job tool window
 */
class XxlJobToolWindow(
    private val project: Project,
    private val toolWindow: ToolWindow
) : SimpleToolWindowPanel(true, false), Disposable {

    private val executorTaskBounded: ExecutorService

    private val xxlJobFind: XxlJobFinder

    private val xxlJobListPanel: XxlJobListPanel

    init {
        executorTaskBounded = ThreadPoolExecutor(
            0, 5,
            1L, TimeUnit.MINUTES,
            LinkedBlockingQueue(),
            Executors.defaultThreadFactory(),
            ThreadPoolExecutor.DiscardOldestPolicy()
        )

        xxlJobFind = DefaultXxlJobFinder()

        xxlJobListPanel = XxlJobListPanel(project)

        toolbar = initToolBar(xxlJobListPanel).component

        setContent(xxlJobListPanel)

        DumbService.getInstance(project).smartInvokeLater {
            ReadAction.nonBlocking(Callable { getXxlJobs() })
                .inSmartMode(project)
                .finishOnUiThread(ModalityState.defaultModalityState()) { renderXxlJobTree(it) }
                .submit(executorTaskBounded)
        }
    }

    /**
     * 刷新树
     */
    fun refresh() {
        ReadAction.nonBlocking(Callable { getXxlJobs() })
            .inSmartMode(project)
            .finishOnUiThread(ModalityState.defaultModalityState()) {
                renderXxlJobTree(it)
            }
            .submit(executorTaskBounded)
    }

    /**
     * 导航到当前选中的节点文件
     */
    fun navigateSelected() {
        val node = xxlJobListPanel.getChooseNode()
        if (node != null) {
            xxlJobListPanel.navigateToNode(node)
        }
    }

    /**
     * 初始化toolbar
     */
    private fun initToolBar(xxlJobListPanel: XxlJobListPanel): ActionToolbar {
        val group = DefaultActionGroup()

        group.add(RefreshAction())
        group.add(ActionManager.getInstance().getAction("Tool.GotoRequestService"))

        group.addSeparator()

        group.add(CommonActionsManager.getInstance().createExpandAllAction(xxlJobListPanel, this))
        group.add(CommonActionsManager.getInstance().createCollapseAllAction(xxlJobListPanel, this))

        val toolbar = ActionManager.getInstance().createActionToolbar(
            ActionPlaces.TOOLBAR,
            group,
            true
        )
        toolbar.setTargetComponent(this)

        return toolbar
    }

    /**
     * 按模块找到xxl job
     */
    private fun getXxlJobs(): Map<String, List<XxlJobModel>> {
        val jobMap = mutableMapOf<String, List<XxlJobModel>>()
        val modules = ModuleManager.getInstance(project).modules

        for (module in modules) {
            val jobs = xxlJobFind.findXxlJob(project, module)
            jobMap[module.name] = jobs
        }

        return jobMap
    }

    /**
     * 渲染列表树
     */
    private fun renderXxlJobTree(jobMap: Map<String, List<XxlJobModel>>) {
        ProgressManager.getInstance().run(object : Task.Backgroundable(project, "Reload xxlJob", false) {
            override fun run(indicator: ProgressIndicator) {
                indicator.isIndeterminate = false

                val xxlJobs = arrayListOf<XxlJobModel>()

                val producer: () -> RootNode? = p@{
                    if (indicator.isCanceled) {
                        return@p null
                    }
                    val root = RootNode(RootData("Find empty"))
                    indicator.text = "Initialize"

                    jobMap.entries.mapNotNull {
                        val moduleName = it.key
                        val jobs = it.value
                        if (jobs.isEmpty()) {
                            return@mapNotNull null
                        }
                        xxlJobs.addAll(jobs)

                        val moduleNode = ModuleNode(ModuleData(moduleName))
                        val nodes = getChildrenNode(jobs)
                        nodes.forEach(moduleNode::add)
                        moduleNode
                    }
                        .sortedBy { it.source.name }
                        .forEach(root::add)

                    indicator.text = "Waiting to re-render"
                    root
                }

                val consumer: (RootNode?) -> Unit = {
                    if (it != null) {
                        if (xxlJobs.isNotEmpty()) {
                            it.source.name = "Find ${xxlJobs.size} jobs"
                        }

                        xxlJobListPanel.renderAll(it, true)
                    }
                }

                ReadAction.nonBlocking(producer)
                    .finishOnUiThread(ModalityState.defaultModalityState(), consumer)
                    .submit(executorTaskBounded)
            }
        })
    }

    private fun getChildrenNode(jobs: List<XxlJobModel>): List<AbstractNode<*>> {
        if (jobs.isEmpty()) {
            return emptyList()
        }
        val children = arrayListOf<AbstractNode<*>>()
        for (job in jobs) {
            children.add(XxlJobNode(job))
        }

        children.sortBy { it.getFragment() }
        return children
    }

    override fun dispose() {
        executorTaskBounded.shutdown()
    }
}