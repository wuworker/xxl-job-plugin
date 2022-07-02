package com.wxl.xxljob.view.search

import com.intellij.icons.AllIcons
import com.intellij.navigation.ItemPresentation
import com.intellij.navigation.NavigationItem
import com.intellij.pom.Navigatable
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiMethod
import com.wxl.xxljob.model.XxlJobModel
import javax.swing.Icon

/**
 * Create by wuxingle on 2022/6/27
 * 搜索结果条目
 */
class XxlJobItem(
    val xxlJobModel: XxlJobModel
) : NavigationItem {

    private val psiElement = xxlJobModel.psiElement

    override fun navigate(requestFocus: Boolean) {
        if (psiElement is Navigatable) {
            psiElement.navigate(requestFocus)
        }
    }

    override fun canNavigate(): Boolean {
        if (psiElement is Navigatable) {
            return psiElement.canNavigate()
        }
        return false
    }

    override fun canNavigateToSource(): Boolean {
        return true
    }

    override fun getName(): String {
        return xxlJobModel.jobName
    }

    override fun getPresentation(): ItemPresentation {
        return object : ItemPresentation {
            override fun getPresentableText(): String {
                return xxlJobModel.jobName
            }

            override fun getLocationString(): String {
                var location: String? = null
                if (psiElement is PsiMethod) {
                    val psiClass = psiElement.containingClass
                    location = "${psiClass?.name ?: ""}#${psiElement.name}"
                } else if (psiElement is PsiClass) {
                    location = psiElement.name
                }

                location = "${location ?: ""} in ${psiElement.resolveScope.displayName}"

                return location
            }

            override fun getIcon(unused: Boolean): Icon {
                return if (psiElement is PsiMethod) {
                    AllIcons.Nodes.Method
                } else {
                    AllIcons.Nodes.Class
                }
            }
        }
    }
}

