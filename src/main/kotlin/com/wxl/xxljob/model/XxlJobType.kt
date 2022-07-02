package com.wxl.xxljob.model

import com.intellij.icons.AllIcons
import javax.swing.Icon

/**
 * Create by wuxingle on 2022/6/27
 * xxl job 任务类型
 */
enum class XxlJobType(
    val typeName: String,
    val icon: Icon
) {

    CLASS("class", AllIcons.Nodes.Class),

    METHOD("method", AllIcons.Nodes.Method)

}