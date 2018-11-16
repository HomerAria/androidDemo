package com.homeraria.hencodeuicourse.app.phenas.particle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author sean
 * @describe 管理一系列particle
 * @email sean.zhou@oppo.com
 * @date on 2018/11/10 17:51
 */
public class CircleParticleSurfaceView extends SurfaceView implements Runnable, SurfaceHolder.Callback {
    private static final int FRAME_RATE = 16;   //[ms]
    private static final int MAX_NUM = 50;     //随机粒子数量
    private Canvas mCanvas;
    private boolean isRun;

    private final SurfaceHolder mHolder = getHolder();
    private Random mRandom = new Random();
    private List<BaseParticle> mCircles = new ArrayList<>();

    public CircleParticleSurfaceView(Context context) {
        super(context);
        initView();
    }

    public CircleParticleSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CircleParticleSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        setZOrderOnTop(true);//设置画布  背景透明

        mHolder.addCallback(this);
        mHolder.setFormat(PixelFormat.TRANSLUCENT);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();

        if (mCircles.size()==0) {
            for (int i = 0; i < MAX_NUM; i++) {
                mCircles.add(new CircleParticle(mRandom, measuredWidth, measuredHeight));
            }
        }
        isRun = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRun = false;
    }

    @Override
    public void run() {
        while (isRun) {
            try {
                mCanvas = mHolder.lockCanvas(null);
                if (mCanvas != null) {
                    synchronized (mHolder) {
                        // 清屏
                        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

//                        for (BaseParticle circle : mCircles) {
//                            circle.drawItemRandomly(mCanvas);
//                        }
                        for (int i = 0; i < mCircles.size(); i++) {
                            mCircles.get(i).drawItemRandomly(mCanvas);
                        }
                        //frame rate
                        Thread.sleep(FRAME_RATE);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (mCanvas != null) {
                    mHolder.unlockCanvasAndPost(mCanvas);
                }
            }
        }
    }
}
