package com.wxl.xxljob.view.tree.node

import com.intellij.icons.AllIcons
import javax.swing.Icon

/**
 * Create by wuxingle on 2022/2/26
 * 模块node
 */
class ModuleNode(
    source: ModuleData
) : DefaultNode<ModuleData>(source) {

    override fun getIcon(selected: Boolean): Icon {
        return AllIcons.Nodes.Module
    }

    override fun getFragment(): String {
        return source.name
    }
}


data class ModuleData(
    var name: String
)