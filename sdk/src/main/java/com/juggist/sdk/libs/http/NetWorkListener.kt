package com.juggist.sdk.libs.http

import com.cqteam.networklib.NetWorkManager

interface NetWorkListener <T>{

    /**
     * 需要显示做操作时重写该方法
     * 例如网络请求需要显示loading
     */
    fun onStart(){

    }

    /**
     * 请求成功
     * @param result
     */
    fun onSuccess(result:T)

    /**
     * 请求失败
     * @param msg 返回失败信息
     * -999是我自定义的错误,不用管直接看msg
     */
    fun onFail(errCode:Int,errMes:String){
        NetWorkManager.getConfig().toastProvider?.toast(errMes)
    }

    /**
     * 同 onStart();
     */
    fun onFinish(){

    }
}