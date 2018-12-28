package com.homeraria.hencodeuicourse.app;

import android.animation.Animator;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;

import com.homeraria.hencodeuicourse.app.fragment.ContentFragment;
import com.homeraria.hencodeuicourse.app.fragment.ContentNextFragment;
import com.homeraria.hencodeuicourse.app.widget.ActivityRevealListener;
import com.homeraria.hencodeuicourse.app.widget.ScreenShotInterface;


/**
 * Phenas项目呼吸效果演示
 */
public class a13RevealActivity extends FragmentActivity implements ActivityRevealListener {
    private ContentFragment mContentFragment;
    private ContentNextFragment mContentNextFragment;
    private boolean isAnimating = false, isNext = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base);

        mContentFragment = ContentFragment.newInstance(R.mipmap.facebook_icon);
        mContentNextFragment = ContentNextFragment.newInstance(R.mipmap.test_bg);
        getSupportFragmentManager().beginTransaction().add(R.id.container, mContentFragment).commit();
    }


    @Override
    public void onFragmentSwitch(View sourceView, ScreenShotInterface screenShot) {
        if (isAnimating) return;

        View view = findViewById(R.id.container);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(view, (sourceView.getLeft() + sourceView.getRight()) / 2,
                (sourceView.getTop() + sourceView.getBottom()) / 2, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(1000);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isAnimating = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        /*
          需要一个placeHolder控件用于暂存新一个fragment的截屏！
          在replace操作中会先把container中的fragment销毁，然后container短暂消失，如果不把新Fragment截屏显示出来会中间空白一下；
         */
        findViewById(R.id.place_holder).setBackground(new BitmapDrawable(getResources(), screenShot.getBitmap()));
        animator.start();

        if (isNext) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mContentFragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mContentNextFragment).commit();
        }
        isNext = !isNext;
    }
}
