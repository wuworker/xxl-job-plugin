package com.wxl.xxljob.finder

import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.wxl.xxljob.model.XxlJobModel

/**
 * Create by wuxingle on 2022/2/14
 * 查找xxl任务
 */
interface XxlJobFinder {

    /**
     * 获取xxl job
     */
    fun findXxlJob(project: Project, module: Module): List<XxlJobModel>

}

