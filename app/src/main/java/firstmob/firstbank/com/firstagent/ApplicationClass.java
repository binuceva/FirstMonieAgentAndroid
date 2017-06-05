package firstmob.firstbank.com.firstagent;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.google.firebase.analytics.FirebaseAnalytics;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by brian on 05/10/2016.
 */
public class ApplicationClass  extends MultiDexApplication {
    private static final String TAG = InjectedApplication.class.getSimpleName();
    private FirebaseAnalytics mFirebaseAnalytics;
   // private ObjectGraph mObjectGraph;
    @Override
    public void onCreate() {
        super.onCreate();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Montserrat-Light.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        MultiDex.install(this);
      //  initObjectGraph(new FingerprintModule(this));
    }

   /* @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
        MultiDex.install(this);
    }*/

    /**
     * Initialize the Dagger module. Passing null or mock modules can be used for testing.
     *
     * @param module for Dagger
     */

}