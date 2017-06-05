package firstmob.firstbank.com.firstagent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class EnterAcCode extends ActionBarActivity implements View.OnClickListener {
    SessionManagement session;
    private Toolbar mToolbar;
    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enteracccode);
        ok = (Button) findViewById(R.id.sign_up);
        ok.setOnClickListener(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Enter Access Code");


    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.sign_up){
            Intent i = new Intent(EnterAcCode.this, MobileLogin.class);
            startActivity(i);
        }

    }
}
