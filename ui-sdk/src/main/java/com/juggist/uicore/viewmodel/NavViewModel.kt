package com.juggist.uicore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.juggist.sdk.viewmodel.BaseViewModel

open class NavViewModel : BaseViewModel() {
    /**导航栏显示/隐藏状态**/
    private val _navVisiable = MutableLiveData(true)
    val navVisiable: LiveData<Boolean>
        get() = _navVisiable

    fun hideNav() {
        _navVisiable.postValue(false)
    }

    fun showNav() {
        _navVisiable.postValue(true)
    }
}