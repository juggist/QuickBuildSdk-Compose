package com.juggist.sdk.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.juggist.sdk.viewmodel.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * 基础fragment基类
 * 适用于viewModel
 */
abstract class BaseVMFragment<VM:BaseViewModel> : Fragment(){
    protected lateinit var viewModel : VM
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(getVmClazz(this))
        return ComposeView(requireContext()).apply {
            setContent { BaseChildView() }
        }
    }

    private fun <VM> getVmClazz(obj: Any): VM {
        return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
    }

    @Composable
    abstract fun BaseChildView()
}