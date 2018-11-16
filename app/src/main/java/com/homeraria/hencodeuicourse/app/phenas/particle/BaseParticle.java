package com.homeraria.hencodeuicourse.app.phenas.particle;

import android.graphics.Canvas;

/**
 * @author sean
 * @describe 基础粒子接口
 * @email sean.zhou@oppo.com
 * @date on 2018/11/12 11:08
 */
public interface BaseParticle {
    void drawItemRandomly(Canvas canvas);

    void drawItemGathering(Canvas canvas, float gatheringX, float gatheringY);
}
