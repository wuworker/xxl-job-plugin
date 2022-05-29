package com.wxl.xxljob.view.tree.node

import com.intellij.icons.AllIcons
import com.wxl.xxljob.model.XxlJobModel
import javax.swing.Icon

/**
 * Create by wuxingle on 2022/2/26
 * 方法节点
 */
class MethodNode(
    methodData: MethodData
) : DefaultNode<MethodData>(methodData) {

    override fun getIcon(selected: Boolean): Icon {
        return AllIcons.Nodes.Method
    }

    override fun getFragment(): String {
        return source.xxlJob.jobName
    }
}

data class MethodData(
    val xxlJob: XxlJobModel
)