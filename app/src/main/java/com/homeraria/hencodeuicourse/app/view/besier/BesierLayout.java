package com.homeraria.hencodeuicourse.app.view.besier;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.homeraria.hencodeuicourse.app.R;

public class BesierLayout extends RelativeLayout {

    private BesierTri mBesierView;
    private RadioButton mButtonA, mButtonB;

    public BesierLayout(Context context) {
        this(context, null);
    }

    public BesierLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BesierLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 控件初始化
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        mBesierView = findViewById(R.id.target);
        mButtonA = findViewById(R.id.control_a);
        mButtonB = findViewById(R.id.control_b);
        mBesierView.setAChosen(true);

        mButtonA.setOnClickListener(v -> mBesierView.setAChosen(true));
        mButtonB.setOnClickListener(v -> mBesierView.setAChosen(false));
    }
}
