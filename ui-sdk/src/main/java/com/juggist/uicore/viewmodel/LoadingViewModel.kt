package com.juggist.uicore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.juggist.uicore.R

class StatusInfo(
    val statusVisiable: Boolean = false,
    val imgRes: Int = R.mipmap.ic_empty_data,
    val tip: String = "暂无数据",
    val btnStr: String = "重试",
    val btnVisiable: Boolean = true
)

open class LoadingViewModel : NavViewModel() {
    private val _autoLoading = MutableLiveData(true)
    val autoLoading: LiveData<Boolean>
        get() = _autoLoading
    private val _statusInfo = MutableLiveData(StatusInfo())
    val statusInfo: LiveData<StatusInfo>
        get() = _statusInfo
    private val _loadingVisiable = MutableLiveData(false)
    val loadingVisiable: LiveData<Boolean>
        get() = _loadingVisiable
    private val _contentVisiable = MutableLiveData(true)
    val contentVisiable: LiveData<Boolean>
        get() = _contentVisiable

    fun showLoading() {
        _loadingVisiable.postValue(true)
    }

    fun hideLoading() {
        _loadingVisiable.postValue(false)
    }

    fun showContent() {
        _contentVisiable.postValue(true)
    }

    fun hideContent() {
        _contentVisiable.postValue(false)
    }

    fun updateStatusInfo(statusInfo: StatusInfo) {
        _statusInfo.postValue(statusInfo)
    }
}