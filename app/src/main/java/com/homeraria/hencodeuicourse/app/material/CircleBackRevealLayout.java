package com.homeraria.hencodeuicourse.app.material;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.homeraria.hencodeuicourse.app.R;
import com.homeraria.hencodeuicourse.app.a13RevealActivity;

import androidx.fragment.app.FragmentActivity;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;


/**
 * @author sean
 * @describe 可以实现水波展开的进入和返回，且其他控件
 * @email sean.zhou@oppo.com
 * @date on 2018/11/22 20:02
 */
public class CircleBackRevealLayout extends CircleRevealLayout {
    private Context mContext;
    private FloatingActionButton mStartButton;
    private View mContainView;
    private LinearLayout mRevealTitleView;
//    private Animator mPopAnimator, mReverseAnimator;
    private boolean isAnimating = false, isHidden = true;
    int cx;
    int cy;
    int radius;

    public CircleBackRevealLayout(Context context) {
        super(context);
        this.mContext = context;
    }

    public CircleBackRevealLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public CircleBackRevealLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        //重新定义点击事件
        for (int i = 0; i < mRevealLayout.getChildCount(); i++) {
            mRevealLayout.getChildAt(i).setOnTouchListener((view, event) -> {
                if (view.getId() != R.id.parent) {
                    mDetector.onTouchEvent(event);
                }
                return true;
            });
        }
        findViewById(R.id.snack_button).setOnClickListener(v -> Snackbar.make(findViewById(R.id.parent), "Snackbar", Snackbar.LENGTH_SHORT).show());
        findViewById(R.id.exit).setOnClickListener(v -> {
            View nextView = mRevealLayout.getChildAt(0);
//                nextView.bringToFront();
            mRevealLayout.bringChildToFront(nextView);

            final float finalRadius = (float) Math.hypot(nextView.getWidth() / 2f, nextView.getHeight() / 2f) + hypo(nextView, (v.getRight() + v.getLeft()) / 2, (v.getTop() + v.getBottom()) / 2);
            Animator revealAnimator = ViewAnimationUtils.
                    createCircularReveal(nextView, (v.getRight() + v.getLeft()) / 2, (v.getTop() + v.getBottom()) / 2, 0, finalRadius);
            revealAnimator.setDuration(1000);
            revealAnimator.setInterpolator(new FastOutLinearInInterpolator());
            revealAnimator.start();
        });

        mRevealTitleView = findViewById(R.id.reveal_items);
        mContainView = findViewById(R.id.reveal_layout);
        mStartButton = findViewById(R.id.next);
        mStartButton.setOnClickListener(v -> {
            showNextFragment();
        });
        findViewById(R.id.add).setOnClickListener(v -> {
            if(isAnimating) return;

            /*
              水波动画需要注意的几点：
              1.执行动画的view需要有width&height，GONE状态下均为0，以后fragment或activity切换中尤其注意；
              2.cx,cy,radius三个指标可以确定动画范围与出发点，出发点可以由点击位置决定；或者由点击来源view位置决定(view较小情况下)；
              3.createCircularReveal()目标view的父控件必须是RevealLayout子类；
             */
            mRevealTitleView.setVisibility(VISIBLE);   //原先Visibility千万不能设置为GONE，这样获得的view尺寸都是0，需要这是为INVISIBLE才可以

            //reveal动画需要的参数
            cx = (v.getLeft() + v.getRight()) / 2;     //水波展开中心位置
            cy = (v.getTop() + v.getBottom()) / 2;
            radius = Math.max(mRevealTitleView.getWidth(), mRevealTitleView.getHeight());    //水波最大半径

            Animator mPopAnimator = ViewAnimationUtils.createCircularReveal(mRevealTitleView, cx, cy, 0, radius);
            mPopAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            mPopAnimator.setDuration(1000);
            mPopAnimator.start();
            mPopAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    isHidden  = false;
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

        });

        findViewById(R.id.anchorView).setOnClickListener(v -> {
            if (isAnimating && !isHidden) return;

            /*
                通过配置startRadius和endRadius，实现水波收缩效果（与水波扩散反向）
             */
            Animator mReverseAnimator = ViewAnimationUtils.createCircularReveal(mRevealTitleView, cx, cy, radius, 0);
            mReverseAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            mReverseAnimator.setDuration(1000);
            mReverseAnimator.start();
            mReverseAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    isAnimating = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mRevealTitleView.setVisibility(INVISIBLE);
                    isAnimating = false;
                    isHidden = true;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    mRevealTitleView.setVisibility(INVISIBLE);
                    isAnimating = false;
                    isHidden = true;
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        });
    }


    private FragmentActivity getActivity() {
        Context context = mContext;
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (FragmentActivity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    private void showNextFragment() {
//        Fragment revealFragment = RevealFragment.newInstance(R.layout.base, (mStartButton.getLeft() + mStartButton.getRight()) / 2,
//                (mStartButton.getTop() + mStartButton.getBottom()) / 2, mContainView.getWidth(), mContainView.getHeight());
//        if (getActivity() != null)
//            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.reveal_layout, revealFragment).commit();
        Intent intent = new Intent(mContext, a13RevealActivity.class);
        mContext.startActivity(intent);
    }
}
