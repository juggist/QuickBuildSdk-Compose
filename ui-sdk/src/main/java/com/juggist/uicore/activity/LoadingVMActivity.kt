package com.juggist.uicore.activity

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.glide.rememberGlidePainter
import com.juggist.uicore.R
import com.juggist.uicore.activity.ui.theme.White
import com.juggist.uicore.viewmodel.LoadingViewModel
import com.juggist.uicore.viewmodel.StatusInfo

/**
 * 加载loading&显示status的activity
 */
abstract class LoadingVMActivity<VM : LoadingViewModel>(
    private val autoLoading: Boolean = true,
    private val contentVisiable: Boolean = true
) : NavBarVMActivity<VM>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.apply {
            if (this@LoadingVMActivity.autoLoading){
                showLoading()
                startLoading(contentVisiable = this@LoadingVMActivity.contentVisiable)
            }
            else
                hideLoading()
            if(this@LoadingVMActivity.contentVisiable)
                showContent()
            else
                hideContent()
        }
    }

    @Composable
    override fun NavBarChildView() {
        Box(
            modifier = Modifier
                .background(color = White)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (viewModel.contentVisiable.observeAsState(initial = true).value)
                LoadingChildView()
            if (viewModel.loadingVisiable.observeAsState(initial = false).value)
                LoadingView()
            val statusInfo = viewModel.statusInfo.observeAsState(initial = StatusInfo()).value
            if (statusInfo.statusVisiable)
                StatusView(statusInfo)
        }
    }


    @Composable
    private fun StatusView(statusInfo: StatusInfo) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = rememberGlidePainter(request = statusInfo.imgRes),
                contentDescription = "占位图",
                modifier = Modifier.size(180.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(statusInfo.tip, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(10.dp))
            if(statusInfo.btnVisiable)
                Button(
                    onClick = { startLoading() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Red)
                ) {
                    Text(statusInfo.btnStr, fontSize = 18.sp, color = White)
                }
        }
    }

    @Composable
    private fun LoadingView() {
        Image(
            painter = rememberGlidePainter(R.drawable.gf_loading),
            contentDescription = "loading加载",
        )
    }

    /**
     * 显示/隐藏 loading视图&content视图&状态占位
     */
    fun finishLoading(statusInfo: StatusInfo = StatusInfo()) {
        viewModel.hideLoading()
        updateContentAndStatusView(!statusInfo.statusVisiable,statusInfo)
    }

    fun startLoading(contentVisiable: Boolean = false) {
        viewModel.showLoading()
        updateContentAndStatusView(contentVisiable,StatusInfo())
        startLoadingAction()
    }

    /**
     * contentVisiable:内容区域展示/隐藏
     * statusInfo:状态占位信息
     */
    fun updateContentAndStatusView(contentVisiable: Boolean = false, statusInfo: StatusInfo = StatusInfo()){
        if (contentVisiable)
            viewModel.showContent()
        else
            viewModel.hideContent()
        viewModel.updateStatusInfo(statusInfo = statusInfo)
    }


    /**
     * Loading的子view
     * 派生类自己实现视图
     */
    @Composable
    abstract fun LoadingChildView()

    abstract fun startLoadingAction()

}