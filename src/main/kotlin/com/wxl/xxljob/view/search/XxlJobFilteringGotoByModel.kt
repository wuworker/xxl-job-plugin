package com.wxl.xxljob.view.search

import com.intellij.ide.util.gotoByName.CustomMatcherModel
import com.intellij.ide.util.gotoByName.FilteringGotoByModel
import com.intellij.navigation.ChooseByNameContributor
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.project.Project
import com.wxl.xxljob.model.XxlJobClass
import com.wxl.xxljob.model.XxlJobMethod

/**
 * Create by wuxingle on 2022/6/27
 *
 */
class XxlJobFilteringGotoByModel(
    project: Project,
    contributors: Array<ChooseByNameContributor>
) : FilteringGotoByModel<XxlJobType>(project, contributors), CustomMatcherModel {

    override fun filterValueFor(item: NavigationItem?): XxlJobType? {
        if (item is XxlJobItem) {
            if (item.xxlJobModel is XxlJobClass) {
                return XxlJobType.CLASS
            }
            if (item.xxlJobModel is XxlJobMethod) {
                return XxlJobType.METHOD
            }
        }
        return null
    }

    override fun getPromptText(): String = "Entry job name"

    override fun getNotInMessage(): String = "No matches found"

    override fun getNotFoundMessage(): String = "No matches found"

    override fun getCheckBoxName(): String? = null

    override fun loadInitialCheckBoxState(): Boolean = false

    override fun saveInitialCheckBoxState(state: Boolean) {
    }

    override fun getSeparators(): Array<String> = arrayOf("/", "?")

    override fun getFullName(element: Any): String? = getElementName(element)

    override fun willOpenEditor(): Boolean = true

    override fun matches(popupItem: String, userPattern: String): Boolean = true
}