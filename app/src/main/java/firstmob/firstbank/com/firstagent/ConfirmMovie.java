package firstmob.firstbank.com.firstagent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class ConfirmMovie extends AppCompatActivity implements View.OnClickListener {
Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        next = (Button) findViewById(R.id.button1);
        next.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button1){
            finish();
            Intent ii = new Intent(getApplicationContext(), LifeStyle.class);

            startActivity(ii);
        }
    }
}
