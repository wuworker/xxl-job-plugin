package com.wxl.xxljob.action

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.jetbrains.rd.util.getLogger
import com.jetbrains.rd.util.info

/**
 * Create by wuxingle on 2022/2/19
 * 刷新job列表
 */
class RefreshAction : AnAction() {

    init {
        templatePresentation.text = "Refresh"
        templatePresentation.icon = AllIcons.Actions.Refresh
    }

    override fun actionPerformed(e: AnActionEvent) {
        getLogger<RefreshAction>().info { "refresh" }
    }

}