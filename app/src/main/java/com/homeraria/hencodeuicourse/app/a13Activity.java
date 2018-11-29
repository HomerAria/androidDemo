package com.homeraria.hencodeuicourse.app;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.homeraria.hencodeuicourse.app.fragment.ContentFragment;
import com.homeraria.hencodeuicourse.app.fragment.PageFragment;

import java.util.ArrayList;
import java.util.List;

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

    //保存每个page的信息
    List<a13Activity.PageModel> mPageModelList = new ArrayList<>();

    {
        mPageModelList.add(new a13Activity.PageModel("循环展开", R.layout.material_circle_reveal));
        mPageModelList.add(new a13Activity.PageModel("展开返回", R.layout.material_circle_back_reveal));
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
}
