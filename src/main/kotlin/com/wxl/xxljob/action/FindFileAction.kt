package com.wxl.xxljob.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.module.ModuleManager
import com.jetbrains.rd.util.getLogger
import com.jetbrains.rd.util.info
import com.wxl.xxljob.finder.DefaultXxlJobFinder


/**
 * Create by wuxingle on 2022/2/12
 *
 */
class FindFileAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project!!
        val xxlFinder = DefaultXxlJobFinder()
        val logger = getLogger<FindFileAction>()
        for (module in ModuleManager.getInstance(project).modules) {
            logger.info { "module: $module" }
            xxlFinder.findXxlJob(project, module).forEach {
                logger.info {
                    it.toString()
                }
            }
        }

    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = e.project != null
    }
}