package com.wxl.xxljob.view.tree

import com.intellij.ui.ColoredTreeCellRenderer
import com.wxl.xxljob.view.tree.node.AbstractNode
import javax.swing.JTree

/**
 * Create by wuxingle on 2022/2/26
 * 树节点渲染
 */
class CustomTreeCellRenderer : ColoredTreeCellRenderer() {

    override fun customizeCellRenderer(
        tree: JTree,
        target: Any?,
        selected: Boolean,
        expanded: Boolean,
        leaf: Boolean,
        row: Int,
        hasFocus: Boolean
    ) {
        if (target is AbstractNode<*>) {
            icon = target.getIcon(selected)
            append(target.getFragment(), target.getTextAttributes())
        }
    }
}


