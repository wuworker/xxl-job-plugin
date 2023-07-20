package com.wxl.xxljob.action

import com.intellij.icons.AllIcons
import com.intellij.ide.projectView.ProjectView
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.psi.util.PsiUtilCore
import com.wxl.xxljob.toolwindow.XxlJobToolWindowFactory

/**
 * Create by wuxingle on 2023/07/19
 * 定位到打开文件
 */
class LocateFileAction : AnAction() {

    init {
        templatePresentation.text = "Select Opened File"
        templatePresentation.icon = AllIcons.General.Locate
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project
        if (project != null) {
            val toolWindow = XxlJobToolWindowFactory.getToolWindow(project)?:return
            val node = toolWindow.getSelectedNode() ?: return
            val psiElement = node.getXxlJobPsiElement()
            val virtualFile = PsiUtilCore.getVirtualFile(psiElement)
            ProjectView.getInstance(project).select(psiElement, virtualFile, true)

            toolWindow.navigateSelected()
        }
    }
}
