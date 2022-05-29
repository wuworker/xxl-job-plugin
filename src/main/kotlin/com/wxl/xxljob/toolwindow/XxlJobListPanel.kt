package com.wxl.xxljob.toolwindow

import com.intellij.openapi.project.Project
import com.intellij.ui.treeStructure.SimpleTree
import com.wxl.xxljob.view.tree.AbstractListTreePanel
import com.wxl.xxljob.view.tree.node.AbstractNode

/**
 * Create by wuxingle on 2022/2/19
 * xxl job 任务列表
 */
class XxlJobListPanel(
    private val project: Project
) : AbstractListTreePanel(SimpleTree()) {

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
