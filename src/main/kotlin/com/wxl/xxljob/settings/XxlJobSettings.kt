package com.wxl.xxljob.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import com.wxl.xxljob.utils.Constants

/**
 * Create by wuxingle on 2022/7/2
 * 设置数据
 */
@State(name = "XxlJobSettings", storages = [Storage("xxlJobSetting.xml")])
class XxlJobSettings : PersistentStateComponent<XxlJobSettings> {

    /**
     * 查找类
     */
    var classFindWay: String = Constants.FIND_BY_PARENT_CLASS

    var classFullName: String = Constants.XLL_JOB_ABSTRACT_CLASS_NAME

    /**
     * 查找方法
     */
    var methodFullName: String = Constants.XLL_JOB_METHOD_ANNOTATION_NAME

    override fun getState(): XxlJobSettings = this

    override fun loadState(state: XxlJobSettings) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {

        fun getInstance(): XxlJobSettings {
            return ApplicationManager.getApplication().getService(XxlJobSettings::class.java)
        }
    }
}
