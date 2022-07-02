package com.wxl.xxljob.model

import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement

/**
 * Create by wuxingle on 2022/2/19
 * 任务描述
 */
data class XxlJobModel(
    val jobName: String,
    val psiElement: PsiElement
) {

    val jobType: XxlJobType = if (psiElement is PsiClass) {
        XxlJobType.CLASS
    } else {
        XxlJobType.METHOD
    }


    val icon = jobType.icon

}