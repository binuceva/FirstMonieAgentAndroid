package firstmob.firstbank.com.firstagent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Splash extends Activity {
    SessionManagement session;
	private static int SPLASH_TIME_OUT = 3000;
    ProgressBar prgDialog;
 // String NET_URL = "https://mbanking.nationalbank.co.ke:8443";
  String NET_URL = "http://196.32.226.78:8010";
    Typeface mTf;
    TextView gm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
        session = new SessionManagement(this);
        mTf = Typeface.createFromAsset(getAssets(), "RobotoSlab-Regular.ttf");
        gm = (TextView) findViewById(R.id.gm);

        session.putURL(NET_URL);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
                boolean checklg = false;
                checklg = session.checkLogin();
                Intent i = null;
                session.logoutUser();


                boolean  checktpref = session.checkReg();
                Log.v("Boolean checkpref", String.valueOf(checktpref));
                if(checktpref == false){
                    i = new Intent(Splash.this, ActivateAgent.class);
                }else{
                    i = new Intent(Splash.this, SignInActivity.class);
                }


                    startActivity(i);
                    finish();

              //  overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }


		}, SPLASH_TIME_OUT);

	}

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
