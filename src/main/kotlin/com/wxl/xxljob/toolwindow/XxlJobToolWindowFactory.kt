package com.wxl.xxljob.toolwindow

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory

/**
 * Create by wuxingle on 2022/2/19
 * xxl job tool window factory
 */
class XxlJobToolWindowFactory : ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val windowPanel = XxlJobToolWindowPanel(project, toolWindow)
        val contentFactory = ContentFactory.SERVICE.getInstance()
        val content = contentFactory.createContent(windowPanel, "", false)
        toolWindow.contentManager.addContent(content)
    }
}