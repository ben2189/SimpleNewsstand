package vn.me.simplenewsstand.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.me.simplenewsstand.R;
import vn.me.simplenewsstand.utils.Constants;

/**
 * Created by taq on 24/10/2016.
 */

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.wvWeb)
    WebView wvWeb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        String url = getIntent().getStringExtra(Constants.WEB_URL);

        wvWeb.getSettings().setJavaScriptEnabled(true);
        wvWeb.setWebViewClient(new WebViewClient());
        wvWeb.loadUrl(url);
    }
}
