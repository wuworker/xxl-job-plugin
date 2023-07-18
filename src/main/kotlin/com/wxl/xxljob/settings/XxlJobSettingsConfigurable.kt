package com.wxl.xxljob.settings

import com.intellij.openapi.options.BoundSearchableConfigurable
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.layout.panel
import com.wxl.xxljob.utils.Constants
import javax.swing.DefaultComboBoxModel

/**
 * Create by wuxingle on 2022/7/2
 * 设置界面配置
 */
class XxlJobSettingsConfigurable(private val project: Project) : BoundSearchableConfigurable(
    "XxlJob Settings", "XxlJob Settings", ID
) {

    private val settings
        get() = XxlJobSettings.getInstance(project)

    override fun createPanel(): DialogPanel {

        return panel {
            titledRow("查找xxlJob类") {
                row("查找方式：") {
                    comboBox(
                        DefaultComboBoxModel(arrayOf("按父类查找", "按注解查找")),
                        {
                            if (settings.classFindWay == Constants.FIND_BY_PARENT_CLASS) {
                                "按父类查找"
                            } else {
                                "按注解查找"
                            }
                        }, {
                            settings.classFindWay = if (it == "按父类查找") {
                                Constants.FIND_BY_PARENT_CLASS
                            } else {
                                Constants.FIND_BY_ANNOTATION
                            }
                        }
                    )
                }
                row("全类名或注解名：") {
                    textField({ settings.classFullName }, { settings.classFullName = it })
                }
            }
            titledRow("查找xxlJob方法") {
                row("方法注解：") {
                    textField({ settings.methodFullName }, { settings.methodFullName = it })
                }
            }
        }
    }

    companion object {
        val ID = XxlJobSettingsConfigurable::class.java.name
    }

}