package com.juggist.sdk.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable

/**
 * 基础activity基类
 */
abstract class BaseActivity : KeyboardHideActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { createBaseChildView() }
    }

    @Composable
    abstract fun createBaseChildView()
}