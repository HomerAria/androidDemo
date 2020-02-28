package com.homeraria.hencodeuicourse.app;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * 在不做多余设置情况下，使用Activity则不会显示顶部ActionBar，而用AppCompatActivity则会显示ActionBar；
 * 默认ActionBar只会显示ActivityName，需要只用AppCompatActivity默认的一些函数，添加功能；
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public ListView mContentListView;
    private Toolbar mToolBar;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        //如果需要侧拉抽屉直通到顶部，需要把状态栏也变成透明状态
        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
    }

    private void initViews() {
        mContext = this.getBaseContext();
        mContentListView = findViewById(R.id.content_list_view);

        ArrayList<String> titleList = new ArrayList<>();
        titleList.add("1-1 绘制基础");
        titleList.add("1-2 paint详解");
        titleList.add("1-3 文字绘制");
        titleList.add("1-4 paint的绘制辅助（裁切+几何变换）");
        titleList.add("1-5 3D变化");
        titleList.add("1-6 属性动画");
        titleList.add("1-7 属性动画进阶");
        titleList.add("2-1~2-3 布局");
        titleList.add("3-1 echarts使用");
        titleList.add("3-2 hencoder仿写");
        titleList.add("3-3 贝塞尔曲线");
        titleList.add("4-1 Phenas动画效果");
        titleList.add("4-2 Material设计");
        titleList.add("4-3 按钮点击");
        titleList.add("4-4 Lottie动画");
        titleList.add("4-4 跨进程通信");

        ArrayAdapter adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, titleList);
        mContentListView.setAdapter(adapter);
        mContentListView.setOnItemClickListener(this);

        mToolBar = findViewById(R.id.tool_bar);
        if(mToolBar != null){
            setSupportActionBar(mToolBar);
        }

        if (getSupportActionBar() == null) return;
//        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().hide();
    }

    /**
     * 使用带有额外按钮的appBar
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * appBar上的按钮被点击后的回调，用于增加点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                Snackbar.make(mContentListView, "location...", Snackbar.LENGTH_SHORT).show();
                return true;
            case R.id.action_favorite:
                Snackbar.make(mContentListView, "favorite", Snackbar.LENGTH_SHORT).show();
                return true;
            case R.id.action_settings:
                Snackbar.make(mContentListView, "setting!", Snackbar.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(mContext, "点击位置：" + i, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();

        switch (i) {
            case 0:
                intent.setClass(MainActivity.this, a1Activity.class);
                break;
            case 1:
                intent.setClass(MainActivity.this, a2Activity.class);
                break;
            case 2:
                break;
            case 3:
                intent.setClass(MainActivity.this, a4Activity.class);
                break;
            case 4:
                intent.setClass(MainActivity.this, a5Activity.class);
                break;
            case 5:
                intent.setClass(MainActivity.this, a6Activity.class);
                break;
            case 6:
                intent.setClass(MainActivity.this, a7Activity.class);
                break;
            case 7:
                intent.setClass(MainActivity.this, a8Activity.class);
                break;
            case 8:
                intent.setClass(MainActivity.this, a9Activity.class);
                break;
            case 9:
                intent.setClass(MainActivity.this, a10Activity.class);
                break;
            case 10:
                intent.setClass(MainActivity.this, a11Activity.class);
                break;
            case 11:    //4.phenas相关
                intent.setClass(MainActivity.this, a12Activity.class);
                break;
            case 12:
                intent.setClass(MainActivity.this, a13Activity.class);
                break;
            case 13:
                intent.setClass(MainActivity.this, a14Activity.class);
                break;
            case 14:
                intent.setClass(MainActivity.this, a15Activity.class);
                break;
            case 15:   //IPC跨进程demo
                intent.setClass(MainActivity.this, a16ipcActivity.class);
                break;
        }

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(mContext, "未设置跳转目的地", Toast.LENGTH_SHORT).show();
        }
    }
}
