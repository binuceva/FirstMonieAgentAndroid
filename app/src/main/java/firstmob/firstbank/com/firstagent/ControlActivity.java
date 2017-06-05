package firstmob.firstbank.com.firstagent;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;

/**
 * Created by deeru on 15-07-2015.
 */
public class ControlActivity extends ActionBarActivity {
    private static final String TAG=ControlActivity.class.getName();

    /**
     * Gets reference to global Application
     * @return must always be type of ControlApplication! See AndroidManifest.xml
     */
    public ControlApplication getApp()
    {
        return (ControlApplication )this.getApplication();
    }

    @Override
    public void onUserInteraction()
    {
        super.onUserInteraction();
        getApp().touch();
        Log.d(TAG, "User interaction to " + this.toString());
    }
}
