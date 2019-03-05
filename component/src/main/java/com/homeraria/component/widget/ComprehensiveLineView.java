package com.homeraria.component.widget;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.homeraria.component.R;
import com.homeraria.component.enums.TargetAnimProcess;

import static com.homeraria.component.widget.LineLayout.LINE_RATIO;

/**
 * 集成loading球和LineLayout综合动画
 */
public class ComprehensiveLineView extends FrameLayout {
    private RectF mRectF = new RectF(300, 300, 800, 600);
    private int centerX, centerY;
    private int screenHeight, screenWidth;

    private ProgressBarCircularIndeterminate mProgressBar;
    private LineLayout mLine;

    private TargetAnimProcess mProcess = TargetAnimProcess.Idle;

    public ComprehensiveLineView(Context context) {
        super(context);
        init();
    }

    public ComprehensiveLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ComprehensiveLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_comprehensive_line, this, true);
        mProgressBar = findViewById(R.id.target11);

        mLine = new LineLayout(getContext());

        //当前LineLayout的尺寸中，长宽比是不变的，仅仅会根据rect中宽度和设定宽度的比例做同比例的缩放
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) (LINE_RATIO * LinePopView.UNDERLINE_LENGTH / (LINE_RATIO - 1)),
                (int) (LINE_RATIO * LinePopView.UNDERLINE_LENGTH / (2 * (LINE_RATIO - 1))));
        addView(mLine, params);
        mLine.setVisibility(INVISIBLE);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        mProgressBar.start1stAnimation();
        mProcess = TargetAnimProcess.InRough;



    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        /*
        相对于本控件FrameLayout的左上角坐标系
         */
        centerX = w / 2;
        centerY = h / 2;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (!changed) {
            return;
        }

        if (mLine == null) {
            return;
        }

        /*
        将lineLayout的引出线起点设置为progressBar中心位置
         */
        LayoutParams params = (LayoutParams) mLine.getLayoutParams();
        params.leftMargin = mProgressBar.getWidth() / 2;
//        mLine.setLayoutParams(params);    //不需要set也可以起效

        /*
        对LineLayout执行scale缩放
        缩放中心应该是左下角，重合于progressBar的中心点
         */
        if (mRectF == null) {
            return;
        }
        float measuredHeight = params.height;
        float measuredWidth = params.width;
        float scale = (mRectF.right - mRectF.left) / measuredWidth;
        mLine.setScaleX(scale);
        mLine.setScaleY(scale);
        mLine.setPivotX(0f);
        mLine.setPivotY(measuredHeight);

        /*
        默认loading球处于整个空间左侧中间
        这里将loading球圆形移动至整个控件的圆点位置（左上角）（以及LineLayout同步移动）
         */
        LayoutParams params1 = (LayoutParams) mProgressBar.getLayoutParams();
//        params1.topMargin = -centerX;

    }

    /**
     * 将本控件定位到需要的位置，LineLayout需要根据rect执行相应的缩放，而PopupProgress不需要，始终位于中央
     */
    public void scale(int screenWidth, int screenHeight, RectF position) {
        this.mRectF = position;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;

        requestLayout();
    }

    public void go2ndLevel() {
        if (mProcess == TargetAnimProcess.InRough) {
            /*
            开始显示折线，同时隐去progressBar
             */
            mProcess = TargetAnimProcess.InExact;
            mProgressBar.doClick();
            mProgressBar.setEndListener((centerX, centerY) -> {
                /*
                模拟rough分类结束，收到精确分类的回调
                 */
                mLine.setVisibility(VISIBLE);
                mLine.show();

            });
        } else if (mProcess == TargetAnimProcess.InExact) {
            /*
            重新开始
             */

        }
    }
}
