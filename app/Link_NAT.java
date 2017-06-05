package firstmob.firstbank.com.firstagent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Link_NAT extends ActionBarActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    Button link;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.link_nat);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        link = (Button) findViewById(R.id.activate);
        link.setOnClickListener(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Link To NatMobile");
	}

	@Override
	public void onBackPressed() {
		this.finish();
		startActivity(new Intent(getApplicationContext(), MainActivity.class));
	}

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.activate){
           finish();
            startActivity(new Intent(Link_NAT.this, ViewFlipper.class));
        }
    }
}
