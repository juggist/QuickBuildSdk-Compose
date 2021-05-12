package com.juggist.uicore.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.juggist.sdk.viewmodel.BaseViewModel

open class NavViewModel : BaseViewModel() {
    /**导航栏显示/隐藏状态**/
    var navVisiable by mutableStateOf(true)

    fun hideNav(){
        navVisiable = false
    }

    fun showNav(){
        navVisiable = true
    }
}