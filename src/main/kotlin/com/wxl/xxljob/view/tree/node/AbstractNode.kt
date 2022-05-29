package com.wxl.xxljob.view.tree.node

import com.intellij.ui.SimpleTextAttributes
import javax.swing.Icon
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.MutableTreeNode

/**
 * Create by wuxingle on 2022/2/26
 * 树节点
 */
abstract class AbstractNode<T>(
    source: T
) : DefaultMutableTreeNode(source) {

    var source: T
        get() = userObject as T
        set(value) {
            userObject = value
        }

    /**
     * 获取显示的字符串
     */
    abstract fun getFragment(): String

    open fun getTextAttributes(): SimpleTextAttributes {
        return SimpleTextAttributes.REGULAR_ATTRIBUTES
    }

    /**
     * 获取图标
     */
    open fun getIcon(selected: Boolean): Icon? {
        return null
    }

    override fun add(newChild: MutableTreeNode) {
        if (newChild is AbstractNode<*>) {
            super.add(newChild)
        }
    }
}
