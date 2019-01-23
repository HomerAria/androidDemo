/*
 * Copyright (c) 2018 Guangdong oppo Mobile Communication(Shanghai)
 * Corp.,Ltd. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above
 *       copyright notice, this list of conditions and the following
 *       disclaimer in the documentation and/or other materials provided
 *       with the distribution.
 *     * Neither the name of The Linux Foundation nor the names of its
 *       contributors may be used to endorse or promote products derived
 *       from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * File: TypeTextLayout.java
 * Description:
 *
 * ---------------------------- Revision History: ------------------------
 * <author>             <date>          <version>           <desc>
 * sean.zhou@oppo.com   2019/1/14      1.0                 create this module
 * -----------------------------------------------------------------------
 */
package com.homeraria.hencodeuicourse.app.farmland;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.homeraria.hencodeuicourse.app.widget.LineLayout;
import com.homeraria.hencodeuicourse.app.widget.LinePopView;
import com.homeraria.hencodeuicourse.app.widget.RectLayout;
import com.homeraria.hencodeuicourse.app.widget.RectPopView;
import com.homeraria.hencodeuicourse.app.widget.TypeTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于测试新的识别结果文字展示动画
 */
public class TypeTextLayout extends RelativeLayout {
    private static final String TYPE_CONTENT = "Georgia a serif font designed to be read on screen, not on paper. As such, it's wide, legible, and attractive. Note that I said \"attractive\", not gorgeous—it's not a font that takes your breath away, but it is one that's easy to stare at for hours. You can use it as a body font, but I wouldn't (I vastly prefer sans-serif fonts for that); instead, I'd feel free to use it as a header font.";
    private TypeTextView mTypeTextView, mTypeTextViewDes;
    private Button mButton;
    private RectPopView mRectView;
    private List<RectF> mData = new ArrayList<>();

    public TypeTextLayout(Context context) {
        this(context, null);
    }

    public TypeTextLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TypeTextLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mData.add(new RectF(100, 100, 300, 300));
        mData.add(new RectF(300, 300, 900, 1300));
        initViews();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

//        mTypeTextView = findViewById(R.id.target);
//        mTypeTextView.setOnTypeViewListener(new TypeTextView.OnTypeViewListener() {
//            @Override
//            public void onTypeStart() {
//                Snackbar.make(TypeTextLayout.this, "Start typing", Snackbar.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onTypeOver() {
////                Snackbar.make(TypeTextLayout.this, "End typing", Snackbar.LENGTH_LONG).show();
//                mTypeTextViewDes.start(TYPE_CONTENT);
//
//            }
//        });
//
//        mTypeTextViewDes = findViewById(R.id.target_des);
//        mTypeTextViewDes.setOnTypeViewListener(new TypeTextView.OnTypeViewListener() {
//            @Override
//            public void onTypeStart() {
////                Snackbar.make(TypeTextLayout.this, "Start typing", Snackbar.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onTypeOver() {
//                Snackbar.make(TypeTextLayout.this, "End typing", Snackbar.LENGTH_LONG).show();
//            }
//        });
//
//        mRectView = findViewById(R.id.pop_rect);
//
//        RectLayout rectLayout = findViewById(R.id.rect_layout);
//
//
//        mButton = findViewById(R.id.start_button);
//        mButton.setOnClickListener(v -> {
////            mRectView.startAnim();
////            mTypeTextView.start("Courier New");
//            rectLayout.show();
//        });
    }

    private void initViews() {
        this.post(() -> {
            /*
            后期通过adapter获取
             */
//            removeAllViews();
            for (int i = 0; i < mData.size(); i++) {
                RectF rectF = mData.get(i);
                LineLayout.addToParent(getContext(), this, rectF);

//                RectLayout rectLayout = new RectLayout(getContext());
//                LineLayout view = new LineLayout(getContext());
//                float width = rectF.right - rectF.left;
//                float height = rectF.bottom - rectF.top;
//                float scale = LinePopView.UNDERLINE_LENGTH / width;
//
//                RelativeLayout.LayoutParams params = new LayoutParams((int) (1.5f * LinePopView.UNDERLINE_LENGTH), (int) (scale * height / 2f));
////                params.leftMargin = (int) rectF.left;
////                params.topMargin = (int) rectF.top;
//                addView(view, params);
//                addView(view);
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) return;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Log.v("sean1", "onLayout, i=" + i + ", class=" + child.getId() + ", change=" + changed);

            if (child instanceof RectLayout) {
//                LayoutParams params = (LayoutParams) child.getLayoutParams();
                RectF rect = mData.get(i);


//                l = (int) rect.left;
//                t = (int) rect.top;
                float x = (rect.right - rect.left) / child.getMeasuredWidth();
                float y = (rect.bottom - rect.top) / child.getMeasuredHeight();
                float scale = Math.max(x, y);
//                child.setScaleX(scale);
//                child.setScaleY(scale);

//                ((RectLayout) child).scaleLayout(scale);
                int right = (int) (rect.left + child.getMeasuredWidth());
                int bottom = (int) (rect.top + child.getMeasuredHeight());
                child.layout((int) rect.left, (int) rect.top, right, bottom);

                child.setScaleX(scale);
                child.setScaleY(scale);
                child.setPivotX(0f);
                child.setPivotY(0f);
            } else if (child instanceof LineLayout) {
                RectF rect = mData.get(i);
                int right = (int) (rect.left + child.getMeasuredWidth());
                int bottom = (int) (rect.top + child.getMeasuredHeight());
                child.layout((int) rect.left, (int) rect.top, right, bottom);

                float scale = (rect.right - rect.left) / child.getMeasuredWidth();
                child.setScaleX(scale);
                child.setScaleY(scale);
                child.setPivotX(0f);
                child.setPivotY(0f);
            }
        }
    }

    //==================================================================================
//    public static class CustomLayoutParams extends MarginLayoutParams {
//        public static final int POSITION_MIDDLE = 0; // 中间
//        public static final int POSITION_LEFT = 1; // 左上方
//        public static final int POSITION_RIGHT = 2; // 右上方
//        public static final int POSITION_BOTTOM = 3; // 左下角
//        public static final int POSITION_RIGHTANDBOTTOM = 4; // 右下角
//
//        public int position = POSITION_LEFT;  // 默认我们的位置就是左上角
//
//        public CustomLayoutParams(Context c, AttributeSet attrs) {
//            super(c, attrs);
//            TypedArray a = c.obtainStyledAttributes(attrs,R.styleable.CustomLayout );
//            //获取设置在子控件上的位置属性
//            position = a.getInt(R.styleable.CustomLayout_layout_position ,position );
//
//            a.recycle();
//        }
//
//        public CustomLayoutParams( int width, int height) {
//            super(width, height);
//        }
//
//        public CustomLayoutParams(ViewGroup.LayoutParams source) {
//            super(source);
//        }
//
//    }

}
