package com.juggist.uicore.fragment

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.google.accompanist.glide.rememberGlidePainter
import com.juggist.sdk.fragment.BaseVMFragment
import com.juggist.uicore.R
import com.juggist.uicore.viewmodel.LoadingViewModel

/**
 * 加载loading的fragment
 */
abstract class LoadingVMFragment<VM:LoadingViewModel>:BaseVMFragment<VM>() {
    @Composable
    override fun BaseChildView() {
//        Box(contentAlignment = Alignment.Center) {
//            if(viewModel.contentVisiable)
                LoadingChildView()
//            if(viewModel.loadingVisiable)
//                LoadingView()
//        }
    }

    @Composable
    private fun LoadingView(){
        Image(
            painter = rememberGlidePainter(R.drawable.gf_loading),
            contentDescription = "loading加载",
        )
    }

    /**
     * 显示/隐藏 loading视图&content视图
     */
    fun showContent(loadingVisiable:Boolean = false){
        viewModel.showContent()
        if(loadingVisiable)
            viewModel.showLoading()
        else
            viewModel.hideLoading()
    }
    fun showLoading(contentVisiable: Boolean = false){
        viewModel.showLoading()
        if(contentVisiable)
            viewModel.showContent()
        else
            viewModel.hideContent()
    }
    /**
     * Loading的子view
     * 派生类自己实现视图
     */
    @Composable
    abstract fun LoadingChildView()
}