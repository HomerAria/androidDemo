/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.homeraria.hencodeuicourse.app.camera;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;

import java.util.List;

/**
 * Graphic instance for rendering face position, orientation, and landmarks within an associated
 * graphic overlay view.
 */
class FaceGraphic extends GraphicOverlay.Graphic {
    private static final float FACE_POSITION_RADIUS = 30.0f;
    private static final float ID_TEXT_SIZE = 45.0f;
    private static final float ID_Y_OFFSET = 10.0f;
    private static final float ID_X_OFFSET = 60.0f;
    private static final float BOX_STROKE_WIDTH = 5.0f;
    private static final float TEXT_MARGIN = 10f;

    private static final int COLOR_CHOICES[] = {
            Color.parseColor("#B71C1C"),
            Color.parseColor("#880E4F"),
            Color.parseColor("#4A148C"),
            Color.parseColor("#311B92"),
            Color.parseColor("#1A237E"),
            Color.parseColor("#0D47A1"),
            Color.parseColor("#01579B"),
            Color.parseColor("#004D40"),
            Color.parseColor("#827717"),
            Color.parseColor("#E65100")
    };
    private static int mCurrentColorIndex = 0;

    private Paint mFacePositionPaint;
    private Paint mIdPaint, mIdBGPaint, mIdBoxPaint, mLandMarkPaint;
    private Paint mBoxPaint;

    private volatile Face mFace;
    private int mFaceId;
    private float mFaceHappiness;

    FaceGraphic(GraphicOverlay overlay) {
        super(overlay);

        mCurrentColorIndex = (mCurrentColorIndex + 1) % COLOR_CHOICES.length;
        final int selectedColor = COLOR_CHOICES[mCurrentColorIndex];

        mFacePositionPaint = new Paint();
        mFacePositionPaint.setColor(selectedColor);
        mFacePositionPaint.setAntiAlias(true);

        mIdPaint = new Paint();
        mIdPaint.setColor(Color.BLACK);
        mIdPaint.setTextSize(ID_TEXT_SIZE);
        mIdPaint.setAntiAlias(true);


        mIdBGPaint = new Paint();
        mIdBGPaint.setAntiAlias(true);
        mIdBGPaint.setDither(true);
        mIdBGPaint.setStyle(Paint.Style.FILL);
        mIdBGPaint.setColor(Color.WHITE);
        mIdBGPaint.setAlpha(200);

        mIdBoxPaint = new Paint();
        mIdBoxPaint.setAntiAlias(true);
        mIdBoxPaint.setDither(true);
        mIdBoxPaint.setStyle(Paint.Style.STROKE);
        mIdBoxPaint.setColor(selectedColor);
        mIdBoxPaint.setStrokeWidth(1f);

        mBoxPaint = new Paint();
        mBoxPaint.setColor(selectedColor);
        mBoxPaint.setStyle(Paint.Style.STROKE);
        mBoxPaint.setStrokeWidth(BOX_STROKE_WIDTH);

        mLandMarkPaint = new Paint();
        mLandMarkPaint.setStyle(Paint.Style.FILL);
        mLandMarkPaint.setColor(selectedColor);
    }

    void setId(int id) {
        mFaceId = id;
    }

    int getId(){
        return mFaceId;
    }


    /**
     * Updates the face instance from the detection of the most recent frame.  Invalidates the
     * relevant portions of the overlay to trigger a redraw.
     */
    void updateFace(Face face) {
        mFace = face;
        postInvalidate();
    }

    /**
     * Draws the face annotations for position on the supplied canvas.
     */
    @Override
    public void draw(Canvas canvas) {
        Face face = mFace;
        if (face == null) {
            return;
        }

        // Draws a circle at the position of the detected face, with the face's track id below.
        float x = translateX(face.getPosition().x + face.getWidth() / 2);
        float y = translateY(face.getPosition().y + face.getHeight() / 2);
        canvas.drawCircle(x, y, FACE_POSITION_RADIUS, mFacePositionPaint);

        String label = "标签: " + mFaceId + ", " + face.getLandmarks().size();
        Rect bounds = new Rect();
        mIdPaint.getTextBounds(label, 0, label.length(), bounds);
//        Paint.FontMetricsInt fontMetricsInt = mIdPaint.getFontMetricsInt();

        float textLeft = x + bounds.left + ID_X_OFFSET - TEXT_MARGIN*2;
        float textRight = x + bounds.right + ID_X_OFFSET + TEXT_MARGIN*2;
        float textTop = y + bounds.top + ID_Y_OFFSET - TEXT_MARGIN;
        float textBottom = y + bounds.bottom + ID_Y_OFFSET + TEXT_MARGIN;
        canvas.drawRoundRect(textLeft, textTop, textRight, textBottom, 5, 5, mIdBGPaint);
        canvas.drawRoundRect(textLeft, textTop, textRight, textBottom, 5, 5, mIdBoxPaint);
        canvas.drawText(label, x + ID_X_OFFSET, y + ID_Y_OFFSET, mIdPaint);

//        canvas.drawText("happiness: " + String.format("%.2f", face.getIsSmilingProbability()), x - ID_X_OFFSET, y - ID_Y_OFFSET, mIdPaint);
//        canvas.drawText("right eye: " + String.format("%.2f", face.getIsRightEyeOpenProbability()), x + ID_X_OFFSET * 2, y + ID_Y_OFFSET * 2, mIdPaint);
//        canvas.drawText("left eye: " + String.format("%.2f", face.getIsLeftEyeOpenProbability()), x - ID_X_OFFSET*2, y - ID_Y_OFFSET*2, mIdPaint);
//
//        // Draws a bounding box around the face.
        float xOffset = scaleX(face.getWidth() / 2.0f);
        float yOffset = scaleY(face.getHeight() / 2.0f);
        float left = x - xOffset;
        float top = y - yOffset;
        float right = x + xOffset;
        float bottom = y + yOffset;
        canvas.drawRect(left, top, right, bottom, mBoxPaint);

        List<Landmark> landmarks = face.getLandmarks();
        for(int i=0; i<landmarks.size();i++){
            canvas.drawCircle(x+ landmarks.get(i).getPosition().x, y + landmarks.get(i).getPosition().y, 5, mLandMarkPaint);
        }

    }
}
