package com.juggist.uicore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

open class RefreshViewModel : LoadingViewModel() {
    var firstLaunch = true
    private val _refreshEnable = MutableLiveData(true)
    val refreshEnable: LiveData<Boolean>
        get() = _refreshEnable
    private val _loadMoreEnable = MutableLiveData(true)
    val loadMoreEnable: LiveData<Boolean>
        get() = _loadMoreEnable
    private val _refreshResult = MutableLiveData(RefreshResult())
    val refreshResult: LiveData<RefreshResult>
        get() = _refreshResult

    fun updateRefreshEnable(refreshEnable: Boolean) {
        _refreshEnable.postValue(refreshEnable)
    }

    fun updateLoadMoreEnable(loadMoreEnable: Boolean) {
        _loadMoreEnable.postValue(loadMoreEnable)
    }

    fun updateRefreshResult(refreshResult: RefreshResult) {
        _refreshResult.postValue(refreshResult)
    }

    class RefreshResult(
        val refreshType: Boolean = true,
        val success: Boolean = true,
        val noMoreData: Boolean = true,
    )
}
