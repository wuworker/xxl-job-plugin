package com.wxl.xxljob.view.tree

import com.intellij.ide.TreeExpander
import com.intellij.ui.border.CustomLineBorder
import com.intellij.ui.components.JBScrollPane
import com.intellij.util.ui.JBUI
import com.wxl.xxljob.view.tree.node.AbstractNode
import com.wxl.xxljob.view.tree.node.DefaultNode
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JTree
import javax.swing.ScrollPaneConstants
import javax.swing.SwingUtilities
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

        tree.addTreeSelectionListener {
            if (!tree.isEnabled) {
                return@addTreeSelectionListener
            }
            val component = tree.lastSelectedPathComponent
            if (component !is DefaultNode<*>) {
                return@addTreeSelectionListener
            }
            onChooseNode(component)
        }

        tree.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (!tree.isEnabled) {
                    return
                }
                val node = getNode(e) ?: return

                if (SwingUtilities.isLeftMouseButton(e) && e.clickCount == 2) {
                    onDoubleClickNode(node)
                }
            }

            private fun getNode(e: MouseEvent): DefaultNode<*>? {
                val path = tree.getPathForLocation(e.x, e.y)
                tree.selectionPath = path
                return getChooseNode(path)
            }
        })
    }

    protected fun getTreeModel() = tree.model as DefaultTreeModel

    fun render(root: AbstractNode<*>) {
        getTreeModel().setRoot(root)
    }

    /**
     * 获取当前选择的节点
     */
    fun getChooseNode(path: TreePath? = null): DefaultNode<*>? {
        val component = if (path == null) {
            tree.lastSelectedPathComponent
        } else {
            path.lastPathComponent
        }
        if (component !is DefaultNode<*>) {
            return null
        }
        return component
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

    /**
     * 节点选中触发
     */
    abstract fun onChooseNode(node: DefaultNode<*>)

    /**
     * 节点双击触发
     */
    abstract fun onDoubleClickNode(node: DefaultNode<*>)
}

