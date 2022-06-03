package com.wxl.xxljob.view.tree.node

import com.intellij.icons.AllIcons
import com.intellij.psi.PsiElement
import com.wxl.xxljob.model.XxlJobModel
import javax.swing.Icon

/**
 * Create by wuxingle on 2022/6/3
 * 定时任务节点
 */
class XxlJobNode(
    data: XxlJobModel
) : DefaultNode<XxlJobModel>(data) {


    override fun getIcon(selected: Boolean): Icon {
        return if (source.isClassJob) {
            AllIcons.Nodes.Class
        } else {
            AllIcons.Nodes.Method
        }
    }

    override fun getFragment(): String {
        return source.jobName
    }

    fun getXxlJobPsiElement(): PsiElement {
        return source.holder
    }
}

