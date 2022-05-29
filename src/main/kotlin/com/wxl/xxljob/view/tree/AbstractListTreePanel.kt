package com.wxl.xxljob.view.tree

import com.intellij.ide.TreeExpander
import com.intellij.ui.border.CustomLineBorder
import com.intellij.ui.components.JBScrollPane
import com.intellij.util.ui.JBUI
import com.wxl.xxljob.view.tree.node.AbstractNode
import javax.swing.JTree
import javax.swing.ScrollPaneConstants
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.TreePath

/**
 * Create by wuxingle on 2022/2/19
 *
 */
abstract class AbstractListTreePanel(
    protected val tree: JTree
) : JBScrollPane(), TreeExpander {

    init {
        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED)
        border = CustomLineBorder(JBUI.insetsTop(1))

        tree.cellRenderer = CustomTreeCellRenderer()
        tree.isRootVisible = true
        tree.showsRootHandles = false
        setViewportView(tree)
    }

    protected fun getTreeModel() = tree.model as DefaultTreeModel

    fun render(root: AbstractNode<*>) {
        getTreeModel().setRoot(root)
    }

    /**
     * 展开tree视图
     *
     * @param parent treePath
     * @param expand 是否展开
     */
    private fun expandAll(parent: TreePath, expand: Boolean) {
        val node = parent.lastPathComponent as DefaultMutableTreeNode
        if (node.childCount >= 0) {
            for (child in node.children()) {
                val path = parent.pathByAddingChild(child)
                expandAll(path, expand)
            }
        }

        if (expand) {
            tree.expandPath(parent)
        } else {
            if (node.isRoot) {
                return
            }
            tree.collapsePath(parent)
        }
    }

    override fun expandAll() = expandAll(TreePath(tree.model.root), true)

    override fun canExpand() = tree.rowCount > 0

    override fun collapseAll() = expandAll(TreePath(tree.model.root), false)

    override fun canCollapse() = tree.rowCount > 0
}

