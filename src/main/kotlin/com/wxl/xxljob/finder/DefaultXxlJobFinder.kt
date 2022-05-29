package com.wxl.xxljob.finder

import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.psi.*
import com.intellij.psi.impl.java.stubs.index.JavaAnnotationIndex
import com.intellij.psi.search.searches.ClassInheritorsSearch
import com.jetbrains.rd.util.getLogger
import com.wxl.xxljob.model.XxlJobModel

/**
 * Create by wuxingle on 2022/2/14
 * 查找xxl任务
 * 通过
 */
class DefaultXxlJobFinder : XxlJobFinder {

    // todo
    companion object {

        private val xxlJobAbstractClass = Qualified("IJobHandler", "com.xxl.job.core.handler.IJobHandler")

        private val xxlJobMethodAnnotation = Qualified("XxlJob", "com.xxl.job.core.handler.annotation.XxlJob")

        private val log = getLogger<DefaultXxlJobFinder>()
    }

    /**
     * 获取xxl job
     */
    override fun findXxlJob(project: Project, module: Module): List<XxlJobModel> {
        val jobs = arrayListOf<XxlJobModel>()

        findXxlJobClass(project, module).forEach { jobs.add(XxlJobModel(it.name!!, it)) }

        findXxlJobMethod(project, module).forEach {
            var jobName = it.name
            val attributeValue = it.getAnnotation(xxlJobMethodAnnotation.fullName)
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
        val findClass = JavaPsiFacade.getInstance(project).findClass(
            xxlJobAbstractClass.fullName, module.moduleWithLibrariesScope
        ) ?: return emptyList()

        return ClassInheritorsSearch.search(findClass, module.moduleScope, true).toList()
    }

    /**
     * 获取xxlJob method
     */
    private fun findXxlJobMethod(project: Project, module: Module): List<PsiMethod> {
        val psiMethods = arrayListOf<PsiMethod>()

        val psiAnnotations = JavaAnnotationIndex.getInstance().get(
            xxlJobMethodAnnotation.name,
            project, module.moduleScope
        )
        for (psiAnnotation in psiAnnotations) {
            val psiElement = (psiAnnotation.parent as PsiModifierList).parent
            if ((psiElement is PsiMethod)) {
                psiMethods.add(psiElement)
            }
        }

        return psiMethods
    }
}