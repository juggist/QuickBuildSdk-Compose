package com.juggist.sdk.activity

import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * 点击屏幕空白区域，软键盘消失
 */
open class KeyboardHideActivity:FontSizeActivity() {
    /**
     * 点击屏幕空白区域 收起软键盘
     *
     * @param ev
     * @return
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (isShouldHideKeyboard(v, ev)) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(
                    v!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    // Return whether touch the view.
    private fun isShouldHideKeyboard(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()
            return !(event.x > left && event.x < right && event.y > top && event.y < bottom)
        }
        return false
    }
}