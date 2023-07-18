package com.wxl.xxljob.finder

import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiModifierList
import com.intellij.psi.impl.java.stubs.index.JavaAnnotationIndex
import com.intellij.psi.search.searches.ClassInheritorsSearch

/**
 * Create by wuxingle on 2022/6/3
 * 提供查找pisElement通用方法
 */
object PsiElementFinders {

    /**
     * 找到有继承关系的psiClass
     * @param fullName 父类全类名
     */
    fun findInheritorsClass(project: Project, module: Module, fullName: String): List<PsiClass> {
        val findClass = JavaPsiFacade.getInstance(project).findClass(
            fullName, module.getModuleWithDependenciesAndLibrariesScope(false)
        ) ?: return emptyList()

        return ClassInheritorsSearch.search(findClass, module.moduleScope, true).toList()
    }

    /**
     * 找到含有注解的psiElement
     * @param fullName 注解全类名
     */
    inline fun <reified T : PsiElement> findHasAnnotation(
        project: Project,
        module: Module,
        fullName: String
    ): List<T> {
        val psiElements = arrayListOf<T>()

        val simpleName = fullName.split(".").last()

        val psiAnnotations = JavaAnnotationIndex.getInstance().get(
            simpleName, project, module.moduleScope
        )

        for (psiAnnotation in psiAnnotations) {
            if (!psiAnnotation.hasQualifiedName(fullName)) {
                continue
            }
            val psiElement = (psiAnnotation.parent as PsiModifierList).parent
            if (psiElement is T) {
                psiElements.add(psiElement)
            }
        }

        return psiElements
    }

}


