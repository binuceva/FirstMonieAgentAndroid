package firstmob.firstbank.com.firstagent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

public class NewsWebvActiv extends ActionBarActivity {
    ImageView imageView1;
    private Toolbar mToolbar;
    private WebView wv1;
    String url;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ringwebvact);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        wv1=(WebView) findViewById(R.id.webView);
       // wv1.setWebViewClient(new MyBrowser());
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            url = bundle.getString("url");

        }


        wv1.getSettings().setLoadsImagesAutomatically(true);

        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);






        final Activity activity = this;
        wv1.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
                activity.setProgress(progress * 1000);
            }
        });
        wv1.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity,  description, Toast.LENGTH_SHORT).show();
            }
        });

        wv1.loadUrl(url);
	}


    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    public void StartChartAct(int i){


    }
    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }

    @Override
    public void onBackPressed()
    {
        finish();
        Intent i = new Intent(NewsWebvActiv.this, News.class);

        // Staring Login Activity

        startActivity(i);

    }

}
