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
 * File: TypeTextView.java
 * Description:
 *
 * ---------------------------- Revision History: ------------------------
 * <author>             <date>          <version>           <desc>
 * sean.zhou@oppo.com   2019/1/14       1.0                 create this module
 * -----------------------------------------------------------------------
 */
package com.homeraria.hencodeuicourse.app.widget;

import android.content.Context;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.homeraria.hencodeuicourse.app.R;

import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * 模拟打字机输出文字效果
 */
public class TypeTextView extends AppCompatTextView {
    private Context mContext = null;
    private MediaPlayer mMediaPlayer = null;
    private String mShowTextString = null;
    private Timer mTypeTimer = null;
    private OnTypeViewListener mOnTypeViewListener = null;
    private static final int TYPE_TIME_DELAY = 50;
    private int mTypeTimeDelay = TYPE_TIME_DELAY; // 打字间隔
    private int mExistLength = 0;      //保存TextView当前文字数量

    public TypeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initTypeTextView(context);
    }

    public TypeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTypeTextView(context);
    }

    public TypeTextView(Context context) {
        super(context);
        initTypeTextView(context);
    }

    public void setOnTypeViewListener(OnTypeViewListener onTypeViewListener) {
        mOnTypeViewListener = onTypeViewListener;
    }

    public void start(final String textString) {
        start(textString, TYPE_TIME_DELAY);
    }

    public void start(final String textString, final int typeTimeDelay) {
        if (TextUtils.isEmpty(textString) || typeTimeDelay < 0) {
            return;
        }
        post(() -> {
            mShowTextString = textString;
            mTypeTimeDelay = typeTimeDelay;

            startTypeTimer("");
            if (null != mOnTypeViewListener) {
                mOnTypeViewListener.onTypeStart();
            }
        });
    }

    public void stop() {
        stopTypeTimer();
        stopAudio();
    }

    private void initTypeTextView(Context context) {
        mContext = context;
    }

    private void startTypeTimer(String content) {
        if(mExistLength == mShowTextString.length()){
            return;
        }
        mExistLength ++;
        setText(content);
        stopTypeTimer();
        mTypeTimer = new Timer();
        mTypeTimer.schedule(new TypeTimerTask(), mTypeTimeDelay);
    }

    private void stopTypeTimer() {
        if (null != mTypeTimer) {
            mTypeTimer.cancel();
            mTypeTimer = null;
        }
    }

    private void startAudioPlayer() {
        stopAudio();
        playAudio(R.raw.type_in);
    }

    private void playAudio(int audioResId) {
        try {
            stopAudio();
            mMediaPlayer = MediaPlayer.create(mContext, audioResId);
            mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopAudio() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    //=====================================================================================================================
    class TypeTimerTask extends TimerTask {
        @Override
        public void run() {
            post(() -> {
                /*
                原有设置方式
                判断所有字段是否都已经加全，否则不持续执行setText()，因为这会导致父控件不断onLayout()
                 */
                if (getText().toString().length() < mShowTextString.length()) {
                    String temp = mShowTextString.substring(0, getText().toString().length() + 1);

//                    setText(temp);
//                      startAudioPlayer();
                    startTypeTimer(temp);


                } else {
                    stopTypeTimer();
                    if (null != mOnTypeViewListener) {
                        mOnTypeViewListener.onTypeOver();
                    }
                }
            });
        }
    }

    public interface OnTypeViewListener {
        public void onTypeStart();

        public void onTypeOver();
    }
}
