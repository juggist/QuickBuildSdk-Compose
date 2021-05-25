package com.juggist.uicore.fragment

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.viewinterop.AndroidView
import com.juggist.uicore.activity.AutoType
import com.juggist.uicore.viewmodel.RefreshViewModel
import com.juggist.uicore.viewmodel.StatusInfo
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
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

    private var refreshLayout: SmartRefreshLayout? = null

    @Composable
    override fun LoadingChildView() {
        val refreshResult = viewModel.refreshResult.observeAsState(initial = RefreshViewModel.RefreshResult()).value
        val refreshEnable = viewModel.refreshEnable.observeAsState(initial = true).value
        val loadMoreEnable = viewModel.loadMoreEnable.observeAsState(initial = true).value
        AndroidView(factory = {
            SmartRefreshLayout(it).apply {
                refreshLayout = this
                if (autoLoading && autoType == AutoType.REFRESH) {
                    autoRefresh()
                }
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
            }
        }, modifier = Modifier.fillMaxSize(), update = {
            print("SmartRefreshLayout update")
        })
    }

    /**
     * 完成刷新
     * success:请求结果
     * noMoreData:是否有更多数据
     * statusInfo:状态占位信息
     */
    fun finishRefresh(
        success: Boolean,
        noMoreData: Boolean,
        statusInfo: StatusInfo = StatusInfo()
    ) {
        refreshLayout?.finishRefresh(0, success, noMoreData)
        updateContentAndStatusView(
            contentVisiable = !statusInfo.statusVisiable,
            statusInfo = statusInfo
        )
    }

    /**
     * 完成加载更多
     * success:请求结果
     * noMoreData:是否有更多数据
     * statusInfo:状态占位信息
     */
    fun finishLoadMore(
        success: Boolean,
        noMoreData: Boolean,
        statusInfo: StatusInfo = StatusInfo()
    ) {
        refreshLayout?.finishLoadMore(0, success, noMoreData)
        updateContentAndStatusView(
            contentVisiable = !statusInfo.statusVisiable,
            statusInfo = statusInfo
        )
    }

    @Composable
    abstract fun RefreshChildView()

    abstract fun startRefreshAction()

    abstract fun startLoadMoreAction()
}