package com.homeraria.hencodeuicourse.app.widget;

import android.view.View;

/**
 * @author sean
 * @describe Activity可以实现这个接口，用于实现对其子Fragment的监听
 * @email sean.zhou@oppo.com
 * @date on 2018/11/30 14:29
 */
public interface ActivityRevealListener {
    /**
     * fragment需要发生切换时回调
     * @param sourceView  发起切换请求的v被点击控件
     * @param screenShot  当前Fragment的截屏
     */
    void onFragmentSwitch(View sourceView, ScreenShotInterface screenShot);
}
