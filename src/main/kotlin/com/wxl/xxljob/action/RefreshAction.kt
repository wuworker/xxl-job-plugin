package com.wxl.xxljob.action

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import com.wxl.xxljob.toolwindow.XxlJobToolWindowFactory

/**
 * Create by wuxingle on 2022/2/19
 * 刷新job列表
 */
class RefreshAction : DumbAwareAction() {

    init {
        templatePresentation.text = "Refresh"
        templatePresentation.icon = AllIcons.Actions.Refresh
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project
        if (project != null) {
            XxlJobToolWindowFactory.getToolWindow(project)?.refresh()
        }
    }

}
