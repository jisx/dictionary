package com.zykj.dictionary

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.TextView

import android.view.WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
import android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
import android.view.WindowManager.LayoutParams.FLAG_SPLIT_TOUCH

object WindowUtils {

    /**
     * 显示弹出框
     *
     * @param context
     */
    fun showPopupWindow(context: Context) {
        // 获取应用的Context
        val mContext = context.applicationContext
        // 获取WindowManager
        val mWindowManager = mContext
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val mView = setUpView(context)
        val params = WindowManager.LayoutParams()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0+
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        }
        // WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        // 设置flag
        val flags = FLAG_ALT_FOCUSABLE_IM or FLAG_NOT_TOUCH_MODAL
        // | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 如果设置了WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE，弹出的View收不到Back键的事件
        params.flags = flags
        // 不设置这个弹出框的透明遮罩显示为黑色
        params.format = PixelFormat.TRANSLUCENT
        // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
        // 设置 FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按
        // 不设置这个flag的话，home页的划屏会有问题
        params.width = 200
        params.height = 200
        params.gravity = Gravity.CENTER
        mWindowManager.addView(mView, params)
    }

    private fun setUpView(context: Context): View {
        val textView = TextView(context)
        textView.text = "test"
        textView.setBackgroundColor(Color.GREEN)
        textView.layoutParams = WindowManager.LayoutParams(200, 200)
        textView.setOnClickListener {
            val intent = Intent()
            intent.action = RedPacketService.ACTION_SERVICE_CHANGE_INFO
            intent.type = ("text/plain")
            context.sendBroadcast(intent)
        }
        return textView
    }
}
