package com.homeraria.hencodeuicourse.app.view.echarts;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.homeraria.hencodeuicourse.app.R;
import com.homeraria.hencodeuicourse.app.view.echarts.utils.EchartsDataBean;

public class PaiRelativeLayout extends RelativeLayout implements View.OnClickListener {
//    private Context mContext;
    private WebView mWebView;
    private ProgressDialog dialog;

    public PaiRelativeLayout(Context context) {
        super(context);
//        mContext = context;
    }

    public PaiRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PaiRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PaiRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 控件初始化
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        Button mButton = findViewById(R.id.control_button);
        mWebView = findViewById(R.id.webview);
//        dialog = new ProgressDialog(mContext);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setMessage("玩儿命加载中...");

        webViewInitial(mWebView);

        mButton.setOnClickListener(this);
    }

    /**
     * 点击后加载html页面
     *
     * @param v mButton
     */
    @Override
    public void onClick(View v) {
        mWebView.loadUrl("javascript:createChart('pie'," + EchartsDataBean.getInstance().getEchartsPieJson() + ");");
    }

    /**
     * webView初始化
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void webViewInitial(WebView webView) {
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/myechart.html");
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

//                dialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //最好在这里调用js代码 以免网页未加载完成
//                if (dialog.isShowing()) {
//                    dialog.dismiss();
//                }
            }
        });
    }
}
