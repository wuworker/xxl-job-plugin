package com.wxl.xxljob.view.tree.node

/**
 * Create by wuxingle on 2022/2/26
 * 默认节点
 */
open class DefaultNode<T>(
    source: T
) : AbstractNode<T>(source) {

    override fun getFragment() = source.toString()

}
