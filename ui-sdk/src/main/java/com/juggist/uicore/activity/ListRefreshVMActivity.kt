package com.juggist.uicore.activity

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.juggist.uicore.viewmodel.ListRefreshViewModel

/**
 * 下拉&上拉刷新-列表 activity
 */
abstract class ListRefreshVMActivity<VM : ListRefreshViewModel,DataType>(
    private val autoLoading: Boolean = true
    /**是否启动自动加载**/
    ,
    private val contentVisiable: Boolean = true
    /**是否显示内容区域**/
    ,
    private val autoType: AUTO_TYPE = AUTO_TYPE.REFRESH
    /**自动加载类型**/
) : RefreshVMActivity<VM>(
    autoLoading = autoLoading,
    autoType = autoType,
    contentVisiable = contentVisiable
) {

    //数据集合
    var listData by mutableStateOf(emptyList<DataType>())
    @Composable
    override fun RefreshChildView() {
        LazyColumn() {
            itemsIndexed(listData, key = { index,item -> "${item.hashCode()}:$index" }) { index,item ->
                ItemView(item)
            }
        }
    }

    fun addData(addData:List<DataType>){
        listData = listData.toMutableList().apply {
            addAll(addData)
        }
    }

    fun refreshData(newData:List<DataType>){
        listData = newData
    }

    @Composable
    abstract fun ItemView(item: DataType)
}