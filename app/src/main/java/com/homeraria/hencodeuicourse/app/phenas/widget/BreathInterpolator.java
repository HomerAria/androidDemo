package com.homeraria.hencodeuicourse.app.phenas.widget;

import android.animation.TimeInterpolator;

/**
 * @author sean
 * @describe 模拟呼吸效果插值器
 * @email sean.zhou@oppo.com
 * @date on 2018/11/9 9:51
 */
public class BreathInterpolator implements TimeInterpolator {
    @Override
    public float getInterpolation(float input) {
        float x = 6 * input;
        float k = 1.0f / 3;
        int t = 6;
        int n = 1;//控制函数周期，这里取此函数的第一个周期
        float PI = 3.1416f;
        float output = 0;

        if (x >= ((n - 1) * t) && x < ((n - (1 - k)) * t)) {
            output = (float) (0.5 * Math.sin((PI / (k * t)) * ((x - k * t / 2) - (n - 1) * t)) + 0.5);

        } else if (x >= (n - (1 - k)) * t && x < n * t) {
            output = (float) Math.pow((0.5 * Math.sin((PI / ((1 - k) * t)) * ((x - (3 - k) * t / 2) - (n - 1) * t)) + 0.5), 2);
        }
        return output;
    }
}
