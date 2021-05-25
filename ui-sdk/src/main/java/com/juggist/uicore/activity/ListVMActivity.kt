package com.juggist.uicore.activity

import android.os.Bundle
import com.juggist.uicore.viewmodel.ListViewModel
import com.juggist.uicore.viewmodel.StatusInfo

/**
 * 简单的展示列表数据
 * 不做刷新/加载动作
 */
abstract class ListVMActivity<VM:ListViewModel,DataType> : ListRefreshVMActivity<VM,DataType>(autoLoading = false) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.apply {
            updateRefreshEnable(false)
            updateLoadMoreEnable(false)
        }
        refreshData(getData().apply {
            if(this.isEmpty()){
                updateContentAndStatusView(statusInfo = createStatusInfo())
            }
        })

    }

    final override fun startLoadMoreAction() {
        //空实现
    }

    final override fun startLoadingAction() {
        //空实现
    }

    final override fun startRefreshAction() {
        //空实现
    }


    /**
     * 自定义占位图信息
     * 可以被派生类重写
     */
    open fun createStatusInfo():StatusInfo{
        return StatusInfo(statusVisiable = true, btnVisiable = false)
    }

    //获取数据源
    abstract fun getData():List<DataType>

}