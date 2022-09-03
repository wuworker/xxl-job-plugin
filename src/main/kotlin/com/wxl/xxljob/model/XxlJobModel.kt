package com.wxl.xxljob.model

import com.intellij.icons.AllIcons
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import com.intellij.psi.javadoc.PsiDocComment
import com.intellij.psi.javadoc.PsiDocToken
import javax.swing.Icon

/**
 * Create by wuxingle on 2022/2/19
 * 任务对象类
 */
abstract class XxlJobModel(
    val jobName: String,
    val psiElement: PsiElement,
) {

    /**
     * 是否过时
     */
    abstract val isDeprecated: Boolean

    /**
     * 图标
     */
    abstract val icon: Icon

    /**
     * 文档注释
     */
    abstract val comment: List<String>

    override fun toString(): String {
        return if (comment.isEmpty()) jobName else "${jobName}（${comment[0]}）"
    }
}

class XxlJobClass(
    jobName: String,
    psiElement: PsiClass
) : XxlJobModel(jobName, psiElement) {

    override val isDeprecated = psiElement.hasAnnotation("java.lang.Deprecated")

    override val icon = AllIcons.Nodes.Class

    override val comment = parseDocComment(psiElement.docComment)
}

class XxlJobMethod(
    jobName: String,
    psiElement: PsiMethod
) : XxlJobModel(jobName, psiElement) {

    override val isDeprecated = psiElement.hasAnnotation("java.lang.Deprecated")

    override val icon = AllIcons.Nodes.Method

    override val comment = parseDocComment(psiElement.docComment)
}

/**
 * 解析java文档注释
 */
private fun parseDocComment(docComment: PsiDocComment?): List<String> {
    if (docComment == null) {
        return emptyList()
    }
    val content = arrayListOf<String>()
    for (descriptionElement in docComment.descriptionElements) {
        if (descriptionElement is PsiDocToken) {
            content.add(descriptionElement.text)
        }
    }

    return content
}
