package lins.com.myspace.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import lins.com.myspace.R;

public class WebActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.wv_mine)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        unbinder=ButterKnife.bind(this);
        // toolBar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("关于");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //设置webview的属性缓存模式离线
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //设置加载全部后的监听
        webView.setWebViewClient(viewclient);
        //设置当加载时的监听
        webView.setWebChromeClient(chromeClient);
        //设置路径
        webView.loadUrl("http://lib.csdn.net/article/android/56264");

    }

    private WebViewClient viewclient = new WebViewClient() {
        //在点击请求的是连接时才会调用，重写此方法返回true表名点击网页里面的连接还是在当前的webview里跳转，不挑到浏览器那边
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            webView.loadUrl(url);
            return true;
        }
    };

    //进度条的使用，根据进度
    private WebChromeClient chromeClient = new WebChromeClient() {
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress);
            if (progressBar.getProgress() == 100) {
                progressBar.setVisibility(View.GONE);
            }
        };
    };


    // toolbar上返回箭头的处理
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
