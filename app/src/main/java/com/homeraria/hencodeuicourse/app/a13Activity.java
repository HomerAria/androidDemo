package com.homeraria.hencodeuicourse.app;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.homeraria.hencodeuicourse.app.camera.PermissionListener;
import com.homeraria.hencodeuicourse.app.fragment.ContentFragment;
import com.homeraria.hencodeuicourse.app.fragment.PageFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * Phenas项目呼吸效果演示
 */
public class a13Activity extends FragmentActivity {
    static final int SWITCH_ID = -11;

    //用于作为pageFragment的指示tab栏
    TabLayout mTabLayout;
    ViewPager mPager;
    ContentFragment mExtraFragment;
    ContentFragment.OnSwitchListener mSwitchListener;

    public void setmPermissionListener(PermissionListener mPermissionListener) {
        this.mPermissionListener = mPermissionListener;
    }

    PermissionListener mPermissionListener;

    //保存每个page的信息
    List<a13Activity.PageModel> mPageModelList = new ArrayList<>();

    {
        mPageModelList.add(new a13Activity.PageModel("循环展开", R.layout.material_circle_reveal));
        mPageModelList.add(new a13Activity.PageModel("展开返回", R.layout.material_circle_back_reveal));
        mPageModelList.add(new a13Activity.PageModel("标签展开拖放", R.layout.material_circle_label));
        mPageModelList.add(new a13Activity.PageModel("人脸追踪", R.layout.face_track_layout));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a9);

        mTabLayout = findViewById(R.id.tabLayout);
        mPager = findViewById(R.id.pager);

        mPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                a13Activity.PageModel pageModel = mPageModelList.get(position);

                return PageFragment.newInstance(pageModel.practiceLayoutRes);
            }

            @Override
            public int getCount() {
                return mPageModelList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mPageModelList.get(position).titleRes;
            }
        });

        mTabLayout.setupWithViewPager(mPager);

//        mExtraFragment = ContentFragment.newInstance(R.mipmap.test_bg, mSwitchListener);
//        mSwitchListener = (event, bitmap) -> {
//            //执行fragment切换动画
//            Log.v(a13Activity.class.getSimpleName(), "fragment切换：x=" + event.getX() + ", y="+event.getY());
//            getSupportFragmentManager().beginTransaction().replace(R.id.pager, mExtraFragment).commit();
//        };
    }

    private class PageModel {
        String titleRes;
        @LayoutRes
        int practiceLayoutRes;

        PageModel(String titleRes, int practiceLayoutRes) {
            this.titleRes = titleRes;
            this.practiceLayoutRes = practiceLayoutRes;
        }
    }

    /**
     * 因为在FaceTrackLayout中请求权限的是Activity，所以这里也是在Activity中得到接受请求的回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.v(PageFragment.class.getSimpleName(), permissions[0] + "");
        mPermissionListener.onGetPermissionsResult(requestCode, permissions, grantResults);
    }


}
