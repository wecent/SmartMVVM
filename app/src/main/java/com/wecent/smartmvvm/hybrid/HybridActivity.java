package com.wecent.smartmvvm.hybrid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.wecent.common.base.BaseActivity;
import com.wecent.common.constant.ConfigConstants;
import com.wecent.common.utils.StatusBarUtils;
import com.wecent.smartmvvm.BR;
import com.wecent.smartmvvm.R;
import com.wecent.smartmvvm.databinding.ActivityHybridBinding;

import androidx.databinding.DataBindingUtil;

/**
 * @desc: H5界面
 * @author: wecent
 * @date: 202/4/13
 */
public class HybridActivity extends BaseActivity<ActivityHybridBinding, HybridViewModel> {

    private AgentWeb mAgentWeb;

    public static void launch(Activity context, String url) {
        Intent intent = new Intent(context, HybridActivity.class);
        intent.putExtra(ConfigConstants.KEY_HYBRID_URL, url);
        context.startActivity(intent);
    }

    @Override
    public ActivityHybridBinding getBinding(LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.setContentView(this, R.layout.activity_hybrid);
    }

    @Override
    public HybridViewModel getViewModel() {
        return getActivityViewModelProvider(this).get(HybridViewModel.class);
    }

    @Override
    public int getViewModelId() {
        return BR.ViewModel;
    }

    @Override
    public void bindData() {
        StatusBarUtils.setStatusBarColor(this, getResources().getColor(R.color.config_color_white));
        StatusBarUtils.setStatusBarLightMode(this, true);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mBinding.llHybrid, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(getResources().getColor(R.color.config_color_primary))
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go(getUrl());
    }

    @Override
    public void bindEvent() {

    }

    /**
     * 获取WebViewUrl
     */
    public String getUrl() {
        return getIntent().getStringExtra(ConfigConstants.KEY_HYBRID_URL);
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
            Log.i("Info", "WebViewActivity onPageStarted");
        }
    };

    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {

        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            mBinding.tbHybrid.setTitleText(title);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void closePage() {
        // 基于需求 网页页面返回
        if (!mAgentWeb.back()) {
            finish();
        }
    }

    @Override
    public void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    public void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("Info", "onResult:" + requestCode + " onResult:" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }
}
