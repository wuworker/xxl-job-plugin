package com.wxl.xxljob.view.tree.node

import com.intellij.icons.AllIcons
import com.wxl.xxljob.model.XxlJobModel
import javax.swing.Icon

/**
 * Create by wuxingle on 2022/2/26
 * 类节点
 */
class ClassNode(
    data: ClassData
) : DefaultNode<ClassData>(data) {

    override fun getIcon(selected: Boolean): Icon {
        return AllIcons.Nodes.Class
    }

    override fun getFragment(): String {
        return source.xxlJob.jobName
    }
}

data class ClassData(
    val xxlJob: XxlJobModel
)
