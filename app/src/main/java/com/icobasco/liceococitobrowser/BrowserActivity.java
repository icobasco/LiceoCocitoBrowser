package com.icobasco.liceococitobrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BrowserActivity extends AppCompatActivity {

    private static final String TAG = "BrowserActivity";
    private WebView wvBrowser = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        wvBrowser = findViewById(R.id.wvBrowser);
        wvBrowser.setWebViewClient(new WebViewClient());
        WebSettings settings = wvBrowser.getSettings();
        settings.setJavaScriptEnabled(true);

        Intent i = getIntent();
        String url = i.getStringExtra(MainActivity.EXTRA_MESSAGE_OPEN_BROWSER);
        if (url != null) {
            wvBrowser.loadUrl(url);
        }
        else {
            Log.d(TAG, "Warning, url not found");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wvBrowser.canGoBack()) {
            wvBrowser.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
