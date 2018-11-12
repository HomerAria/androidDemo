package com.homeraria.hencodeuicourse.app.phenas.particle;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author sean
 * @describe 通过View实现粒子组的管理
 * @email sean.zhou@oppo.com
 * @date on 2018/11/12 11:33
 */
public class ParticleView extends View {



    public ParticleView(Context context) {
        super(context);
        init(context, null);
    }

    public ParticleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ParticleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){

    }
}
