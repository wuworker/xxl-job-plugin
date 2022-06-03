package com.wxl.xxljob.action

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.wxl.xxljob.toolwindow.XxlJobToolWindowFactory

/**
 * Create by wuxingle on 2022/6/3
 * 导航到选中文件
 */
class NavigateAction : AnAction() {

    init {
        templatePresentation.text = "Open File"
        templatePresentation.icon = AllIcons.General.Locate
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project
        if (project != null) {
            XxlJobToolWindowFactory.getToolWindow(project)?.navigateSelected()
        }
    }

}