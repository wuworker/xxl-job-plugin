package com.wxl.xxljob.finder

import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiLiteralValue
import com.intellij.psi.PsiMethod
import com.wxl.xxljob.model.XxlJobModel
import com.wxl.xxljob.utils.Constants

/**
 * Create by wuxingle on 2022/2/14
 * 查找xxl任务
 * 通过
 */
class DefaultXxlJobFinder : XxlJobFinder {

    /**
     * 获取xxl job
     */
    override fun findXxlJob(project: Project, module: Module): List<XxlJobModel> {
        val jobs = arrayListOf<XxlJobModel>()

        findXxlJobClass(project, module).forEach { jobs.add(XxlJobModel(it.name!!, it)) }

        findXxlJobMethod(project, module).forEach {
            var jobName = it.name
            val attributeValue = it.getAnnotation(Constants.XLL_JOB_METHOD_ANNOTATION_NAME)
                ?.findAttributeValue("value")
            if (attributeValue is PsiLiteralValue) {
                jobName = attributeValue.value?.toString() ?: jobName
            }
            jobs.add(XxlJobModel(jobName, it))
        }

        return jobs
    }

    /**
     * 获取xxlJob class
     */
    private fun findXxlJobClass(project: Project, module: Module): List<PsiClass> {
        return PsiElementFinders.findInheritorsClass(project, module, Constants.XLL_JOB_ABSTRACT_CLASS_NAME)
    }

    /**
     * 获取xxlJob method
     */
    private fun findXxlJobMethod(project: Project, module: Module): List<PsiMethod> {
        return PsiElementFinders.findHasAnnotation(project, module, Constants.XLL_JOB_METHOD_ANNOTATION_NAME)
    }

}
