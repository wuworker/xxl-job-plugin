package com.wxl.xxljob.model

import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod

/**
 * Create by wuxingle on 2022/2/19
 * 任务描述
 */
data class XxlJobModel(
    val jobName: String,
    val holder: PsiElement
) {

    val isClassJob: Boolean
        get() = holder is PsiClass

    val isMethodJob: Boolean
        get() = holder is PsiMethod
}