package com.wxl.xxljob.view.tree.node

/**
 * Create by wuxingle on 2022/2/26
 * 根节点
 */
class RootNode(
    source: RootData
) : DefaultNode<RootData>(source) {

    override fun getFragment(): String {
        return source.name
    }
}

data class RootData(
    var name: String
)
