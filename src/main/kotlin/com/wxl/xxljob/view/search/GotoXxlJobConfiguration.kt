package com.wxl.xxljob.view.search

import com.intellij.ide.util.gotoByName.ChooseByNameFilterConfiguration
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.StoragePathMacros
import com.intellij.openapi.project.Project
import com.wxl.xxljob.model.XxlJobType

/**
 * Create by wuxingle on 2022/6/27
 * 按任务类型过滤配置
 */
@State(name = "GotoXxlJobConfiguration", storages = [Storage(StoragePathMacros.WORKSPACE_FILE)])
class GotoXxlJobConfiguration : ChooseByNameFilterConfiguration<XxlJobType>() {

    companion object {

        fun getInstance(project: Project): GotoXxlJobConfiguration {
            return project.getService(GotoXxlJobConfiguration::class.java)
        }
    }

    override fun nameForElement(type: XxlJobType): String {
        return type.name
    }
}