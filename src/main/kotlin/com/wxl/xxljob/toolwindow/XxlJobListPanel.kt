package com.wxl.xxljob.toolwindow

import com.intellij.openapi.project.Project
import com.intellij.pom.Navigatable
import com.intellij.ui.treeStructure.SimpleTree
import com.wxl.xxljob.view.tree.AbstractListTreePanel
import com.wxl.xxljob.view.tree.node.AbstractNode
import com.wxl.xxljob.view.tree.node.DefaultNode
import com.wxl.xxljob.view.tree.node.XxlJobNode

/**
 * Create by wuxingle on 2022/2/19
 * xxl job 任务列表
 */
class XxlJobListPanel(
    private val project: Project
) : AbstractListTreePanel(SimpleTree()) {

    override fun onChooseNode(node: DefaultNode<*>) {
    }

    /**
     * 节点双击导航到文件
     */
    override fun onDoubleClickNode(node: DefaultNode<*>) {
        navigateToNode(node)
    }

    /**
     * 导航到当前选中的文件
     */
    fun navigateToNode(node: DefaultNode<*>) {
        if (node is XxlJobNode) {
            val psi = node.getXxlJobPsiElement()
            if (psi is Navigatable) {
                if (psi.canNavigate()) {
                    psi.navigate(true)
                }
            }
        }
    }

    /**
     * 渲染
     */
    fun renderAll(root: AbstractNode<*>, expand: Boolean?) {
        super.render(root)
        if (expand != null && expand) {
            expandAll()
        } else {
            collapseAll()
        }
    }
}
