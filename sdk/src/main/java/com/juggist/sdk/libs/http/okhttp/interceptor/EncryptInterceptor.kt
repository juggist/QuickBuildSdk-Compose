package com.juggist.sdk.libs.http.okhttp.interceptor

import android.util.Log
import com.cqteam.networklib.NetWorkManager
import com.juggist.sdk.utils.NetWorkUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

class EncryptInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val builder = request.newBuilder()
        val url = request.url.toString()
        if (!NetWorkUtils.isNetworkAvailable(NetWorkManager.getContent())) {
            builder.cacheControl(CacheControl.FORCE_CACHE)
        }
        NetWorkManager.getConfig().paramsProvider?.let {
            it.headerParams()?.let { it ->
                val iterator = it.entries.iterator()
                while (iterator.hasNext()){
                    val data = iterator.next()
                    builder.addHeader(data.key,data.value)
                    Log.e("NetWorkHttp","header : ${data.key} = ${data.value}")
                }
            }
        }

        val response = chain.proceed(builder.url(url).build())
        if (!NetWorkUtils.isNetworkAvailable(NetWorkManager.getContent())) {
            val maxAge = 60 * 60
            response.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public, max-age=$maxAge")
                .addHeader("juggist","un-alive")
                .build()
        } else {
            val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
            response.newBuilder()
                .removeHeader("Pragma")
                .addHeader("juggist","alive")
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .build()
        }
        return response
    }
}