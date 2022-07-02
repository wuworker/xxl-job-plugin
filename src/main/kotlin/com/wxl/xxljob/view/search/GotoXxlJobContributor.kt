package com.wxl.xxljob.view.search

import com.intellij.navigation.ChooseByNameContributor
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.Project
import com.wxl.xxljob.finder.DefaultXxlJobFinder
import com.wxl.xxljob.finder.XxlJobFinder
import com.wxl.xxljob.model.XxlJobModel
import java.util.*

/**
 * Create by wuxingle on 2022/6/27
 * 用于获取所有xxl job 名称
 */
class GotoXxlJobContributor(
    private val module: Module?
) : ChooseByNameContributor {

    private val xxlJobFind: XxlJobFinder = DefaultXxlJobFinder()


    override fun getNames(project: Project, includeNonProjectItems: Boolean): Array<String> {
        return getXxlJobModels(project).map { it.jobName }.toTypedArray()
    }

    override fun getItemsByName(
        name: String?,
        pattern: String?,
        project: Project,
        includeNonProjectItems: Boolean
    ): Array<NavigationItem> {
        return getXxlJobModels(project)
            .filter { Objects.equals(it.jobName, name) }
            .map { XxlJobItem(it) }
            .toTypedArray()
    }

    private fun getXxlJobModels(project: Project): List<XxlJobModel> {
        return if (module != null) {
            xxlJobFind.findXxlJob(project, module)
        } else {
            val list = arrayListOf<XxlJobModel>()
            for (module in ModuleManager.getInstance(project).modules) {
                list.addAll(xxlJobFind.findXxlJob(project, module))
            }
            list
        }
    }

}