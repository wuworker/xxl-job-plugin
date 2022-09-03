package com.wxl.xxljob.finder

/**
 * Create by wuxingle on 2022/9/3
 * 获取XxlJobFinder
 */
object XxlJobFinderFactory {

    private val FINDER = DefaultXxlJobFinder()

    fun getFind(): XxlJobFinder = FINDER

}