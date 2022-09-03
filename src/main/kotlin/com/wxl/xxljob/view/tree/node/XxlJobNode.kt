package com.wxl.xxljob.view.tree.node

import com.intellij.psi.PsiElement
import com.intellij.ui.SimpleTextAttributes
import com.wxl.xxljob.model.XxlJobModel
import javax.swing.Icon

/**
 * Create by wuxingle on 2022/6/3
 * 定时任务节点
 */
class XxlJobNode(
    data: XxlJobModel
) : DefaultNode<XxlJobModel>(data) {

    /**
     * 字体中划线
     */
    val STRIKEOUT_ATTRIBUTES = SimpleTextAttributes(SimpleTextAttributes.STYLE_STRIKEOUT, null)

    override fun getIcon(selected: Boolean): Icon {
        return source.icon
    }

    override fun getFragment(): String {
        return source.toString()
    }

    fun getXxlJobPsiElement(): PsiElement {
        return source.psiElement
    }

    override fun getTextAttributes(): SimpleTextAttributes {
        return if (source.isDeprecated) STRIKEOUT_ATTRIBUTES else super.getTextAttributes()
    }
}

