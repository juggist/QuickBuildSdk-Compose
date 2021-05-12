package com.juggist.uicore.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.juggist.uicore.R

class StatusInfo(
    val statusVisiable: Boolean = false,
    val imgRes: Int = R.mipmap.ic_empty_data,
    val tip: String = "暂无数据",
    val btnStr: String = "重试"

)

open class LoadingViewModel : NavViewModel() {
    var autoLoading by mutableStateOf(true)
    var statusInfo by mutableStateOf(StatusInfo())
    var loadingVisiable by mutableStateOf(false)
    var contentVisiable by mutableStateOf(true)

    fun showLoading() {
        loadingVisiable = true
    }

    fun hideLoading() {
        loadingVisiable = false
    }

    fun showContent() {
        contentVisiable = true
    }

    fun hideContent() {
        contentVisiable = false
    }

    fun updateStatusInfo(statusInfo: StatusInfo){
        this.statusInfo = statusInfo
    }
}