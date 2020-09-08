package in.droidparkz.droidband;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class Pairingwindow extends AppCompatActivity {

    WebView mWebView;
    private final int SPLASH_DISPLAY_LENGTH = 8000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairingwindow);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mWebView = (WebView) findViewById(R.id.pairingwindow_webview);
        mWebView.loadUrl("file:///android_asset/pairing.gif");
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.setScrollbarFadingEnabled(false);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

        mWebView.setWebViewClient(new WebViewClient());

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                Intent intent = new Intent(Pairingwindow.this,Pairingmessage.class);
                Pairingwindow.this.startActivity(intent);
                Pairingwindow.this.finish();

            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
