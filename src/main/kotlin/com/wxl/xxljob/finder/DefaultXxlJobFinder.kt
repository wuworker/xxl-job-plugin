package com.wxl.xxljob.finder

import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiLiteralValue
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiModifierListOwner
import com.wxl.xxljob.model.XxlJobClass
import com.wxl.xxljob.model.XxlJobMethod
import com.wxl.xxljob.model.XxlJobModel
import com.wxl.xxljob.settings.XxlJobSettings
import com.wxl.xxljob.utils.Constants

/**
 * Create by wuxingle on 2022/2/14
 * 查找xxl任务
 * 通过
 */
class DefaultXxlJobFinder : XxlJobFinder {

    private val settings = XxlJobSettings.getInstance()

    /**
     * 获取xxl job
     */
    override fun findXxlJob(project: Project, module: Module): List<XxlJobModel> {
        val jobs = arrayListOf<XxlJobModel>()

        // 查找类
        val classFullName = settings.classFullName
        if (classFullName.isNotBlank()) {
            // 按父类查找
            if (Constants.FIND_BY_PARENT_CLASS == settings.classFindWay) {
                PsiElementFinders.findInheritorsClass(project, module, classFullName)
                    .forEach {
                        jobs.add(XxlJobClass(it.name!!, it))
                    }
            }
            // 按注解查找
            else if (Constants.FIND_BY_ANNOTATION == settings.classFindWay) {
                PsiElementFinders.findHasAnnotation<PsiClass>(project, module, classFullName)
                    .forEach {
                        val jobName = findAnnotationValue(it, classFullName) ?: it.name
                        jobs.add(XxlJobClass(jobName!!, it))
                    }
            }
        }

        // 查找方法
        val methodAnnotation = settings.methodFullName
        if (methodAnnotation.isNotBlank()) {
            PsiElementFinders.findHasAnnotation<PsiMethod>(project, module, methodAnnotation).forEach {
                val jobName = findAnnotationValue(it, methodAnnotation) ?: it.name
                jobs.add(XxlJobMethod(jobName, it))
            }
        }

        return jobs
    }

    private fun findAnnotationValue(psiElement: PsiModifierListOwner, annotationName: String): String? {
        val attributeValue = psiElement.getAnnotation(annotationName)
            ?.findAttributeValue("value")
        if (attributeValue is PsiLiteralValue) {
            return attributeValue.value?.toString()
        }
        return null
    }
}
