package firstmob.firstbank.com.firstagent;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;


public class ChangeAcNameActivity extends ActionBarActivity  {

    private Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changeacname);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Profile");
        setSupportActionBar(mToolbar);


    }


    public void StartChartAct(int i){
    }

    @Override
    public void onResume(){
        super.onResume();
    }

}
