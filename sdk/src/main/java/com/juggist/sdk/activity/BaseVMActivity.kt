package com.juggist.sdk.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.juggist.sdk.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.reflect.ParameterizedType
import kotlin.coroutines.CoroutineContext

/**
 * 基础activity基类
 * 适用于viewModel
 */
abstract class BaseVMActivity<VM : BaseViewModel> : KeyboardHideActivity() {
    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(getVmClazz(this))
        setContent { BaseChildView() }
    }

    private fun <VM> getVmClazz(obj: Any): VM {
        return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
    }

    @Composable
    abstract fun BaseChildView()

}