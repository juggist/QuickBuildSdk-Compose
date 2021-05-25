package com.juggist.quicksdk

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.juggist.quicksdk.ui.theme.QuickSDKTheme
import com.juggist.uicore.activity.LoadingVMActivity
import com.juggist.uicore.activity.RefreshVMActivity
import com.juggist.uicore.viewmodel.RefreshViewModel
import com.juggist.uicore.viewmodel.StatusInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivityBar
//    :LoadingVMActivity<MainViewModel>(){
//    @Composable
//    override fun LoadingChildView() {
//
//        Column() {
//            Text("LoadingChildView")
//            Button(onClick = {
//                finishLoading(statusInfo = StatusInfo(statusVisiable = true))
//            }) {
//                Text("点击")
//            }
//        }
//    }
//
//    override fun startLoadingAction() {
//            lifecycleScope.launch {
//                delay(2000)
//                finishLoading(statusInfo = StatusInfo(statusVisiable = false))
//            }
//    }

    : RefreshVMActivity<MainViewModel>(autoLoading = true){
    override fun startLoadingAction() {
        lifecycleScope.launch {
            delay(3000)
            finishLoading()
//            viewModel.updateRefreshResult(RefreshViewModel.RefreshResult(refreshType = true, success = true,noMoreData = false))
        }
    }

    @Composable
    override fun RefreshChildView() {
        Text("RefreshChildView")
    }

    override fun startRefreshAction() {
        lifecycleScope.launch {
            delay(3000)
            finishRefresh( success = true,noMoreData = false, statusInfo = StatusInfo(statusVisiable = true))
        }
    }

    override fun startLoadMoreAction() {
        lifecycleScope.launch {
            delay(3000)
            finishLoadMore(success  = true, noMoreData = true)
        }
    }

//    : NavBarVMActivity<MainViewModel>(){
//    @Composable
//    override fun NavBarChildView() {
//        var model by remember {
//            mutableStateOf(true)
//        }
//        var unStable by remember {
//            mutableStateOf(object : StableListener{
//                override var name: String = "juggist"
//                    set(value) {
//                        field = value
//                    }
//
//            })
//        }
//
//        Column() {
//            Button(onClick = {
////                model = StableBean(name = "helln", age = 11)
//                if(model)
//                    hideNav()
//                else
//                    showNav()
//                model = !model
//            }) {
//
//                Text("切换")
//            }
////            UnStableView(unStable)
////            StableView(stable = model)
//        }
//    }

//    : ListVMActivity<MainViewModel,String>(){
//    @Composable
//    override fun ItemView(item: String) {
//        Text(item)
//    }
//
//    override fun getData(): List<String> {
//     return listOf()
//    }
//
//    override fun createStatusInfo(): StatusInfo {
//        return StatusInfo(statusVisiable = true, btnVisiable = false,tip = "你请求的数据为空")
//    }
//   : ListRefreshVMActivity<MainViewModel,String>(autoLoading = false,autoType = AUTO_TYPE.REFRESH,contentVisiable = true) {
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
        Greeting("Juggist")
    }
}

@Composable
fun StableView(stable: StableBean){
    Log.w("juggist","update")
    Text("${stable.name} ; ${stable.age}")
}
@Composable
fun UnStableView(stableListener: StableListener){
    Text("name = ${stableListener.name}")
}
class StableBean(val name: String = "juggist", val age: Int = 1)
@Stable
interface StableListener{
    var name : String
}