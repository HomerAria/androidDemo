package com.homeraria.hencodeuicourse.app.phenas;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.homeraria.hencodeuicourse.app.R;
import com.homeraria.hencodeuicourse.app.phenas.particle.CircleParticleView;


/**
 * @author sean
 * @describe 对CircleParticleView做操作
 * @email sean.zhou@oppo.com
 * @date on 2018/11/13 15:22
 */
public class ParticleControlLayout extends RelativeLayout {
    private CircleParticleView mParticleView;
    private Button mButton;
    private ObjectAnimator mAnimator1, mAnimator2;
    private boolean isFirst = true;

    public ParticleControlLayout(Context context) {
        super(context);
    }

    public ParticleControlLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParticleControlLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        mParticleView = findViewById(R.id.target);
        mButton = findViewById(R.id.control_button);

        mButton.setOnClickListener(v -> {
            if(mAnimator1 == null){
                mAnimator1 = ObjectAnimator.ofFloat(mParticleView, "height", 1, 0.25f);
                mAnimator1.setInterpolator(new DecelerateInterpolator());
                mAnimator1.setDuration(1000);
            }
            if(mAnimator2 == null){
                mAnimator2 = ObjectAnimator.ofFloat(mParticleView, "height", 0.25f, 1f);
                mAnimator2.setInterpolator(new DecelerateInterpolator());
                mAnimator2.setDuration(1000);
            }

            if(isFirst) {
                new Handler().postDelayed(() -> mParticleView.clearParticles(), 1000);
                mAnimator1.start();
            }else{
                 mParticleView.showParticles();
                mAnimator2.start();
            }
            isFirst = !isFirst;
        });
    }
}
