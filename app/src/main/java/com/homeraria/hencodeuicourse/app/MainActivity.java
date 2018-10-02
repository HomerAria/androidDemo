package com.homeraria.hencodeuicourse.app;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener
{
    public ListView mContentListView;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews()
    {
        mContext = this.getBaseContext();
        mContentListView = findViewById(R.id.content_list_view);

        ArrayList<String> titleList = new ArrayList<>();
        titleList.add("1-1 绘制基础");
        titleList.add("1-2 paint详解");
        titleList.add("1-3 文字绘制");
        titleList.add("1-4 paint的绘制辅助（裁切+几何变换）");
        titleList.add("1-5 绘制顺序");
        titleList.add("1-6 属性动画");
        titleList.add("1-7 属性动画进阶");
        titleList.add("2-1~2-3 布局");
        titleList.add("3-1 echarts使用");

        ArrayAdapter adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, titleList);
        mContentListView.setAdapter(adapter);
        mContentListView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        Toast.makeText(mContext, "点击位置：" + i, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();

        switch (i)
        {
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
        }

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e)
        {
            Toast.makeText(mContext, "未设置跳转目的地", Toast.LENGTH_SHORT).show();
        }
    }
}
