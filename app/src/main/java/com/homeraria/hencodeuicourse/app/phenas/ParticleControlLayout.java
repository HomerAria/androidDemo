package com.homeraria.hencodeuicourse.app.phenas;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    private TextView mTextView;
    private ImageView mImageView;
    private AnimatorSet mAnimatorSet1, mAnimatorSet2;
    private ObjectAnimator mAnimator1, mAnimator2, mAnimation1Text, mAnimation2Text, mAnimation2Image;
    private boolean isFirst = true;
    private Animator.AnimatorListener mShowListener;

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
        mTextView = findViewById(R.id.desc);
        mImageView = findViewById(R.id.screen_shot);
//        mImageView.setVisibility(GONE);

        mShowListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //不能通过设置visibility来控制Image的出现与消失，会导致获取的view高度为0
                mImageView.setAlpha(1f);
                int height = mImageView.getMeasuredHeight();
                Log.v("sean", height + "");

                ObjectAnimator animator = ObjectAnimator.ofFloat(mImageView, "translationY", -height, 0);
                animator.setInterpolator(new DecelerateInterpolator());
                animator.setDuration(500);
                animator.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };

        mButton.setOnClickListener(v -> {
            if(mAnimatorSet1 == null){
                mAnimator1 = ObjectAnimator.ofFloat(mParticleView, "height", 1, 0.25f);
                mAnimator1.setInterpolator(new DecelerateInterpolator());
                mAnimator1.setDuration(1000);

                mAnimation1Text = ObjectAnimator.ofFloat(mTextView, "alpha", 0, 1);
                mAnimation1Text.setInterpolator(new DecelerateInterpolator());
                mAnimation1Text.setDuration(1000);

                mAnimatorSet1 = new AnimatorSet();
                mAnimatorSet1.playTogether(mAnimator1, mAnimation1Text);
                mAnimatorSet1.addListener(mShowListener);
            }
            if(mAnimatorSet2 == null){
                mAnimator2 = ObjectAnimator.ofFloat(mParticleView, "height", 0.25f, 1f);
                mAnimator2.setInterpolator(new DecelerateInterpolator());
                mAnimator2.setDuration(1000);

                mAnimation2Text = ObjectAnimator.ofFloat(mTextView, "alpha", 1f, 0f);
                mAnimation2Text.setInterpolator(new DecelerateInterpolator());
                mAnimation2Text.setDuration(1000);

                mAnimation2Image = ObjectAnimator.ofFloat(mImageView, "alpha", 1f, 0f);
                mAnimation2Image.setInterpolator(new DecelerateInterpolator());
                mAnimation2Image.setDuration(1000);

                mAnimatorSet2 = new AnimatorSet();
                mAnimatorSet2.playTogether(mAnimator2, mAnimation2Text, mAnimation2Image);
            }

            if(isFirst) {
                new Handler().postDelayed(() -> mParticleView.clearParticles(), 1000);
                mAnimatorSet1.start();
            }else{
                 mParticleView.showParticles();
                mAnimatorSet2.start();
            }
            isFirst = !isFirst;
        });
    }

}
