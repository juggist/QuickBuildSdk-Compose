package com.juggist.quicksdk

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.juggist.quicksdk.ui.theme.QuickSDKTheme
import com.juggist.uicore.activity.AUTO_TYPE
import com.juggist.uicore.activity.ListRefreshVMActivity
import com.juggist.uicore.activity.ListVMActivity
import com.juggist.uicore.activity.RefreshVMActivity
import com.juggist.uicore.fragment.LoadingVMFragment
import com.juggist.uicore.viewmodel.StatusInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivityBar : ListVMActivity<MainViewModel,String>(){
    @Composable
    override fun ItemView(item: String) {
        Text(item)
    }

    override fun getData(): List<String> {
     return listOf()
    }

    override fun createStatusInfo(): StatusInfo {
        return StatusInfo(statusVisiable = true, btnVisiable = false,tip = "你请求的数据为空")
    }
//    ListRefreshVMActivity<MainViewModel,String>(autoLoading = false,autoType = AUTO_TYPE.REFRESH,contentVisiable = true) {
//
//    override fun startLoadingAction() {
//        lifecycleScope.launch{
//            delay(3000)
//            finishLoading(statusInfo = StatusInfo(statusVisiable = true,tip = "网络异常",btnStr = "再试试？"))
//        }
//    }
//
//    override fun startRefreshAction() {
//        lifecycleScope.launch{
//            delay(3000)
//            finishRefresh(true,noMoreData = false, statusInfo = StatusInfo(statusVisiable = false,tip = "获取数据失败",btnStr = "重试"))
//            refreshData(mutableListOf("1","2","3"))
//        }
//    }
//
//    override fun startLoadMoreAction() {
//        lifecycleScope.launch{
//            delay(3000)
//            finishLoadMore(true,noMoreData = false, statusInfo = StatusInfo(statusVisiable = false,tip = "获取数据失败",btnStr = "重试"))
//            addData(mutableListOf("5","6","7"))
//        }
//    }
//
//    @Composable
//    override fun ItemView(item: String) {
//        Text(item)
//    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    QuickSDKTheme {
        Greeting("Android")
    }
}

class TestLoadingFragment : LoadingVMFragment<TestLoadingViewModel>() {
    @Composable
    override fun LoadingChildView() {
        Text("我是loading fragment的child")
    }

}