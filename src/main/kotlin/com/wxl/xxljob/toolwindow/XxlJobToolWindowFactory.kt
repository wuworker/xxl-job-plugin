package com.wxl.xxljob.toolwindow

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.ui.content.ContentFactory
import com.wxl.xxljob.utils.Constants

/**
 * Create by wuxingle on 2022/2/19
 * xxl job tool window factory
 */
class XxlJobToolWindowFactory : ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val windowPanel = XxlJobToolWindow(project, toolWindow)
        val contentFactory = ContentFactory.SERVICE.getInstance()
        val content = contentFactory.createContent(windowPanel, "", false)
        toolWindow.contentManager.addContent(content)
    }

    companion object {

        fun getToolWindow(project: Project, show: Boolean? = null): XxlJobToolWindow? {
            val toolWindow = ToolWindowManager.getInstance(project).getToolWindow(Constants.TOOL_WINDOWS_ID)
            if (show != null && show) {
                showToolWindow(project)
            }
            if (toolWindow != null) {
                for (component in toolWindow.component.components) {
                    if (component is XxlJobToolWindow) {
                        return component
                    }
                }
            }
            return null
        }

        fun showToolWindow(project: Project, runnable: Runnable? = null) {
            val toolWindow = ToolWindowManager.getInstance(project).getToolWindow(Constants.TOOL_WINDOWS_ID)
            toolWindow?.show(runnable)
        }

        fun hideToolWindow(project: Project, runnable: Runnable? = null) {
            val toolWindow = ToolWindowManager.getInstance(project).getToolWindow(Constants.TOOL_WINDOWS_ID)
            toolWindow?.hide(runnable)
        }
    }
}