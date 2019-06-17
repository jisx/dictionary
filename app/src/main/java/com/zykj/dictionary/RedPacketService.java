package com.zykj.dictionary;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import static androidx.core.accessibilityservice.AccessibilityServiceInfoCompat.FLAG_REPORT_VIEW_IDS;
import static androidx.core.accessibilityservice.AccessibilityServiceInfoCompat.FLAG_REQUEST_TOUCH_EXPLORATION_MODE;

/**
 * 抢红包Service,继承AccessibilityService
 */
public class RedPacketService extends AccessibilityService {

    public static final String ACTION_SERVICE_CHANGE_INFO = "ACTION_SERVICE_CHANGE_INFO";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
//        AccessibilityNodeInfo rootInActiveWindow = getRootInActiveWindow();
//        rootInActiveWindow.
//        System.out.println("onAccessibilityEvent:" + eventType);
        switch (eventType) {
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_START:
                System.out.println("TYPE_GESTURE_DETECTION_START");
                break;
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_END:
                System.out.println("TYPE_GESTURE_DETECTION_END");
                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START:
                System.out.println("TYPE_TOUCH_EXPLORATION_GESTURE_START");
                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:
                System.out.println("TYPE_TOUCH_EXPLORATION_GESTURE_END");
                break;
            case AccessibilityEvent.TYPE_VIEW_CLICKED://1
                System.out.println("TYPE_VIEW_CLICKED");
                break;
            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
                System.out.println("TYPE_VIEW_LONG_CLICKED");
                break;
            case AccessibilityEvent.TYPE_TOUCH_INTERACTION_START:
                System.out.println("TYPE_TOUCH_INTERACTION_START");
                break;
            case AccessibilityEvent.TYPE_TOUCH_INTERACTION_END:
                System.out.println("TYPE_TOUCH_INTERACTION_END");
                break;
            case AccessibilityEvent.TYPE_ANNOUNCEMENT:
                System.out.println("TYPE_ANNOUNCEMENT");
                break;
            case AccessibilityEvent.TYPE_ASSIST_READING_CONTEXT:
                System.out.println("TYPE_ASSIST_READING_CONTEXT");
                break;
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                System.out.println("TYPE_NOTIFICATION_STATE_CHANGED");
                break;
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED:
                System.out.println("TYPE_VIEW_ACCESSIBILITY_FOCUSED");
                break;
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED:
                System.out.println("TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED");
                break;
            case AccessibilityEvent.TYPE_VIEW_CONTEXT_CLICKED:
                System.out.println("TYPE_VIEW_CONTEXT_CLICKED");
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                System.out.println("TYPE_VIEW_FOCUSED");
                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER://128
                System.out.println("TYPE_VIEW_HOVER_ENTER");
                for (CharSequence charSequence : event.getText()) {
                    Toast.makeText(this,charSequence,Toast.LENGTH_SHORT).show();
                }
                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_EXIT://256
                System.out.println("TYPE_VIEW_HOVER_EXIT");
//                for (CharSequence charSequence : event.getText()) {
//                    Toast.makeText(this,charSequence,Toast.LENGTH_SHORT).show();
//                }
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                System.out.println("TYPE_VIEW_TEXT_CHANGED");
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                System.out.println("TYPE_VIEW_TEXT_SELECTION_CHANGED");
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY:
                System.out.println("TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY");
                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                System.out.println("TYPE_VIEW_SCROLLED");
                break;
            case AccessibilityEvent.TYPE_VIEW_SELECTED:
                System.out.println("TYPE_VIEW_SELECTED");
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED://2048
                System.out.println("TYPE_WINDOW_CONTENT_CHANGED");
                break;
            case AccessibilityEvent.CONTENT_CHANGE_TYPE_PANE_DISAPPEARED://32
                System.out.println("CONTENT_CHANGE_TYPE_PANE_DISAPPEARED");
                break;
        }

    }

    @Override
    protected boolean onGesture(int gestureId) {
        System.out.println("onGesture:" + gestureId);
        return super.onGesture(gestureId);
    }

    /**
     * 服务连接
     */
    @Override
    protected void onServiceConnected() {
        Toast.makeText(this, "翻译词典开启", Toast.LENGTH_SHORT).show();
        openBroadCast();
        super.onServiceConnected();
    }

    /**
     * 必须重写的方法：系统要中断此service返回的响应时会调用。在整个生命周期会被调用多次。
     */
    @Override
    public void onInterrupt() {
        Toast.makeText(this, "我快被终结了啊-----", Toast.LENGTH_SHORT).show();
    }

    /**
     * 服务断开
     */
    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, "翻译词典服务已被关闭", Toast.LENGTH_SHORT).show();
        closeBroadCast();
        return super.onUnbind(intent);
    }

    private void closeBroadCast() {
        unregisterReceiver(serverInfoReceiver);
    }

    boolean flag = true;

    public void openBroadCast() {
        registerReceiver(serverInfoReceiver, IntentFilter.create(ACTION_SERVICE_CHANGE_INFO, "text/plain"));
    }

    BroadcastReceiver serverInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            flag = !flag;
            AccessibilityServiceInfo serviceInfo = getServiceInfo();
            if (flag) {
                serviceInfo.flags = FLAG_REQUEST_TOUCH_EXPLORATION_MODE | FLAG_REPORT_VIEW_IDS;
            }else{
                serviceInfo.flags =  FLAG_REPORT_VIEW_IDS;
            }
            setServiceInfo(serviceInfo);
        }
    };

}
