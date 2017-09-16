package th.tintuchangngay;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import th.libtintuc.R;
public class DailyNewsDetailsActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_news_details);
        Bundle mBundle = getIntent().getExtras();
        String URL = (String) mBundle.get("link");
        WebView mWebview = (WebView) findViewById(R.id.details_content_webview);
        mWebview.getSettings().setJavaScriptEnabled(true);
//        mWebview.getSettings().setBuiltInZoomControls(true);
//        mWebview.getSettings().setSupportZoom(true);
//        mWebview.getSettings().setLoadWithOverviewMode(true);
//        mWebview.getSettings().setUseWideViewPort(true);
//        mWebview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        String ua =  "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
//        mWebview.getSettings().setUserAgentString(ua);
        mWebview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        System.out.println(URL);
        mWebview.loadUrl(URL);

    }


}
