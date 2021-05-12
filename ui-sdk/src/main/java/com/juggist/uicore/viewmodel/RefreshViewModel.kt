package com.juggist.uicore.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

open class RefreshViewModel : LoadingViewModel(){
    var refreshEnable by mutableStateOf(true)
    var loadMoreEnable by mutableStateOf(true)

    fun updateRefreshEnable(refreshEnable:Boolean){
        this.refreshEnable = refreshEnable
    }
    fun updateLoadMoreEnable(loadMoreEnable:Boolean){
        this.loadMoreEnable = loadMoreEnable
    }
}