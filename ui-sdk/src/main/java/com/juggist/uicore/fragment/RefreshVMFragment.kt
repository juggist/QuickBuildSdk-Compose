package com.juggist.uicore.fragment

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.lifecycleScope
import com.juggist.uicore.activity.AutoType
import com.juggist.uicore.viewmodel.RefreshViewModel
import com.juggist.uicore.viewmodel.StatusInfo
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 下拉&上拉刷新fragment
 */
abstract class RefreshVMFragment<VM : RefreshViewModel>(
    private val autoLoading: Boolean = true
    /**是否启动自动加载**/
    ,
    private val contentVisiable: Boolean = true
    /**是否显示内容区域**/
    ,
    private val autoType: AutoType = AutoType.REFRESH
    /**自动加载类型**/
) : LoadingVMFragment<VM>(
    autoLoading = autoLoading && autoType == AutoType.LOADING
    /**自动加载&loading模式**/
    , contentVisiable = contentVisiable
) {


    @Composable
    override fun LoadingChildView(show: Boolean) {
        //第一次启动activity，防止某些动作重复
        val firstLaunchActivity = remember { mutableStateOf(true) }
        if(!show)
            return
        val refreshResult = viewModel.refreshResult.observeAsState().value!!
        val refreshEnable = viewModel.refreshEnable.observeAsState().value!!
        val loadMoreEnable = viewModel.loadMoreEnable.observeAsState().value!!
        AndroidView(factory = {
            SmartRefreshLayout(it).apply {
                setHeaderHeight(100f)
                setEnableRefresh(refreshEnable)
                setRefreshHeader(ClassicsHeader(it))
                setFooterHeight(100f)
                setEnableLoadMore(loadMoreEnable)
                setRefreshFooter(ClassicsFooter(it))
                setRefreshContent(
                    //添加内容视图
                    ComposeView(it).apply {
                        setContent {
                            RefreshChildView()
                        }
                    }
                )
                setOnRefreshListener {
                    startRefreshAction()
                }
                setOnLoadMoreListener {
                    startLoadMoreAction()
                }
                if (autoLoading && autoType == AutoType.REFRESH && firstLaunchActivity.value) {
                    lifecycleScope.launch{
                        delay(500)
                        autoRefresh()
                    }
                }
            }
        }, modifier = Modifier.fillMaxSize(), update = {
            if(firstLaunchActivity.value)
                firstLaunchActivity.value = false
            if(refreshResult.refreshType){
                it.finishRefresh(0, refreshResult.success, refreshResult.noMoreData)
            }else{
                it.finishLoadMore(0, refreshResult.success, refreshResult.noMoreData)
            }
        })
    }

    /**
     * 完成刷新
     * success:请求结果
     * noMoreData:是否有更多数据
     * statusInfo:状态占位信息
     */
    fun finishRefresh(success: Boolean, noMoreData: Boolean, statusInfo: StatusInfo = StatusInfo()) {
        viewModel.updateRefreshResult(RefreshViewModel.RefreshResult(refreshType = true, success = success,noMoreData = noMoreData))
        updateContentAndStatusView(contentVisiable = !statusInfo.statusVisiable,statusInfo = statusInfo)
    }

    /**
     * 完成加载更多
     * success:请求结果
     * noMoreData:是否有更多数据
     * statusInfo:状态占位信息
     */
    fun finishLoadMore(success: Boolean, noMoreData: Boolean,statusInfo: StatusInfo = StatusInfo()) {
        viewModel.updateRefreshResult(RefreshViewModel.RefreshResult(refreshType = false, success = success,noMoreData = noMoreData))
        updateContentAndStatusView(contentVisiable = !statusInfo.statusVisiable,statusInfo = statusInfo)
    }

    @Composable
    abstract fun RefreshChildView()

    abstract fun startRefreshAction()

    abstract fun startLoadMoreAction()
}