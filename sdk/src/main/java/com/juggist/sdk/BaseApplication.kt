package com.juggist.sdk

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import android.webkit.WebView
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.unit.Subunits

abstract class BaseApplication constructor(private val applicationId: String) : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        Log.i("juggist","applicationId = $applicationId")
        val processName = (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
            .runningAppProcesses.find { it.pid == android.os.Process.myPid() }?.processName
        /**
         * 9.0及以后版本多进程导致加载webview失败
         * java.lang.RuntimeException:Using WebView from more than one process at once with the same data directory is not
         **/
        if (processName != applicationId) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                WebView.setDataDirectorySuffix(applicationId)
            }

        }else{
            initAutoSize()
        }

    }

    /**
     * 初始化ui自适应
     * 可以在 pt、in、mm 这三个冷门单位中，选择一个作为副单位，副单位是用于规避修改 DisplayMetrics#density 所造成的对于其他使用 dp 布局的系统控件或三方库控件的不良影响，使用副单位后可直接填写设计图上的像素尺寸，不需要再将像素转化为 dp
     */
    private fun initAutoSize(){
        AutoSizeConfig.getInstance().unitsManager
            .setSupportDP(false)
            .setSupportSP(false).supportSubunits = Subunits.MM;
    }

}