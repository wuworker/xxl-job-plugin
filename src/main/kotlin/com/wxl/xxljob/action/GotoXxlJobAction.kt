package com.wxl.xxljob.action

import com.intellij.featureStatistics.FeatureUsageTracker
import com.intellij.icons.AllIcons
import com.intellij.ide.actions.GotoActionBase
import com.intellij.ide.util.gotoByName.ChooseByNameFilter
import com.intellij.ide.util.gotoByName.ChooseByNameItemProvider
import com.intellij.ide.util.gotoByName.ChooseByNamePopup
import com.intellij.ide.util.gotoByName.DefaultChooseByNameItemProvider
import com.intellij.navigation.ChooseByNameContributor
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.project.Project
import com.wxl.xxljob.model.XxlJobType
import com.wxl.xxljob.view.search.GotoXxlJobConfiguration
import com.wxl.xxljob.view.search.GotoXxlJobContributor
import com.wxl.xxljob.view.search.XxlJobFilteringGotoByModel
import com.wxl.xxljob.view.search.XxlJobItem
import org.jetbrains.annotations.Contract
import javax.swing.Icon

/**
 * Create by wuxingle on 2022/6/27
 * 跳转到文件
 */
class GotoXxlJobAction : GotoActionBase() {

    init {
        templatePresentation.text = "Goto XxlJob"
        templatePresentation.description = "Goto xxl job definition"
        templatePresentation.icon = AllIcons.Actions.Search
    }

    override fun gotoActionPerformed(e: AnActionEvent) {
        val project = e.project ?: return

        // 显示featureId对应的Tips
        FeatureUsageTracker.getInstance().triggerFeatureUsed("navigation.popup.service");

        val contributors: Array<ChooseByNameContributor> = arrayOf(GotoXxlJobContributor(e.getData(LangDataKeys.MODULE)))

        val gotoByModel = XxlJobFilteringGotoByModel(project, contributors)

        val callback = object : GotoActionCallback<XxlJobType>() {

            @Contract("_ -> new")
            override fun createFilter(popup: ChooseByNamePopup): ChooseByNameFilter<XxlJobType>? {
                return GotoXxlJobFilter(popup, gotoByModel, project)
            }

            override fun elementChosen(popup: ChooseByNamePopup?, element: Any?) {
                if (element is XxlJobItem) {
                    if (element.canNavigate()) {
                        element.navigate(true)
                    }
                }
            }
        }

        val provider: ChooseByNameItemProvider = DefaultChooseByNameItemProvider(getPsiContext(e))
        showNavigationPopup(
            e, gotoByModel, callback,
            "Job Name matching pattern",
            true, true,
            provider
        )
    }
}

private class GotoXxlJobFilter(
    popup: ChooseByNamePopup,
    model: XxlJobFilteringGotoByModel,
    project: Project
) : ChooseByNameFilter<XxlJobType>(popup, model, GotoXxlJobConfiguration.getInstance(project), project) {


    override fun textForFilterValue(value: XxlJobType): String {
        return value.name
    }

    override fun iconForFilterValue(value: XxlJobType): Icon {
        return value.icon
    }

    override fun getAllFilterValues(): MutableCollection<XxlJobType> {
        return XxlJobType.values().toMutableList()
    }

}
