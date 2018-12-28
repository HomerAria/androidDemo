package com.homeraria.hencodeuicourse.app.camera;

import androidx.annotation.NonNull;

/**
 * @author sean
 * @describe 得到权限申请的回调
 * @email sean.zhou@oppo.com
 * @date on 2018/12/11 15:43
 */
public interface PermissionListener {
    void onGetPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
}
