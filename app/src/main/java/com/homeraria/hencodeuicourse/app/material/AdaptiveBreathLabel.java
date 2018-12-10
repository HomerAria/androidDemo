package com.homeraria.hencodeuicourse.app.material;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homeraria.hencodeuicourse.app.R;

import io.codetail.widget.RevealFrameLayout;

/**
 * @author sean
 * @describe 标签的位置可以根据圆点在屏幕中的位置，可以自适应位于左侧或者右侧
 * @email sean.zhou@oppo.com
 * @date on 2018/12/4 16:51
 */
public class AdaptiveBreathLabel extends RevealFrameLayout {
    private final static int BREATH_PERIOD = 500;
    private int mPositionX, mPositionY;
    private RevealFrameLayout mRootView;
    private ViewGroup mParentView;
    private Point marginPoint;
    private boolean isInitial = true;

    public AdaptiveBreathLabel add2Parent (RelativeLayout parentView, int x, int y){
        Log.v(AdaptiveBreathLabel.class.getSimpleName(), "width:" + parentView.getWidth());
        this.mParentView = parentView;
        mParentView.addView(this, getPositionParam(x, y));
        return this;
    }

    public AdaptiveBreathLabel moveTo(int x, int y){
        if(mParentView == null) return this;

        try {
            mParentView.addView(this, getPositionParam(x, y));
        } catch (IllegalStateException e){
            mParentView.removeView(this);
            mParentView.addView(this, getPositionParam(x, y));
        }
        return this;
    }

    /**
     * 设置的应当是其中呼吸点的位置
     */
    private RelativeLayout.LayoutParams getPositionParam(int x, int y){
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = x;
        params.topMargin = y;
        marginPoint = new Point(x, y);
        return params;
    }

    public AdaptiveBreathLabel setLabel(String label){
        ((TextView)mRootView.findViewById(R.id.target_text)).setText(label);
        return this;
    }

    public String getLabel(){
        return (String) ((TextView)mRootView.findViewById(R.id.target_text)).getText();
    }

    public AdaptiveBreathLabel(Context context) {
        this(context, null);
    }

    public AdaptiveBreathLabel(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdaptiveBreathLabel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mRootView = (RevealFrameLayout) inflate(context, R.layout.breath_label_adaptive, this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    /**
     * 需要获得本View和父View的位置与尺寸信息需要在这个回调里拿，
     * 只有在这时，这些信息才被确定
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if(!isInitial) return;

        Log.v(AdaptiveBreathLabel.class.getSimpleName(), "child left:" + this.getLeft() + ", parent:" + ((View)this.getParent()).getWidth());
//        if(marginPoint != null) {
//            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(w, h);
//            params.leftMargin = marginPoint.x;
//            params.topMargin = marginPoint.y;
//            this.setLayoutParams(params);
//        }
        //动态分配内部控件位置

        new Handler().postDelayed(()->{
            View currentView = mRootView.findViewById(R.id.target);
            View nextView = mRootView.findViewById(R.id.target_text);
            nextView.setVisibility(VISIBLE);

            try {
                Animator revealAnimator = ViewAnimationUtils.
                        createCircularReveal(nextView, currentView.getLeft(),
                                (nextView.getTop() + nextView.getBottom()) / 2, 0, nextView.getWidth());
                revealAnimator.setDuration(1000);
                revealAnimator.setInterpolator(new FastOutLinearInInterpolator());
                revealAnimator.start();
            } catch (IllegalStateException e) {
                /*
                Cannot start this animator on a detached view!
                因为有延迟执行，可能已经返回上个界面，造成view已经detach
                 */
                Log.e(BreathLabelLayout.class.getSimpleName(), "Error: " + e.getMessage());
            }
            isInitial = false;

        }, BREATH_PERIOD);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onDragEvent(DragEvent event) {
        return super.onDragEvent(event);
    }
}
