package firstmob.firstbank.com.firstagent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity implements View.OnClickListener {
Spinner sp5,sp3,sp4;
    TextView desc;
    ImageView img;
    Button next;
    String url,txtdesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sp5 = (Spinner)findViewById(R.id.spin5);
        sp3 = (Spinner)findViewById(R.id.spin3);
        sp4 = (Spinner)findViewById(R.id.spin4);
        img = (ImageView) findViewById(R.id.img);
        next = (Button) findViewById(R.id.button1);
        next.setOnClickListener(this);
        desc = (TextView) findViewById(R.id.title);


        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
                getApplicationContext(), R.array.time, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp3.setAdapter(adapter3);



        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(
                getApplicationContext(), R.array.theatres, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp4.setAdapter(adapter4);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            url = extras.getString("url");
            txtdesc = extras.getString("desc");

            Picasso.with(getApplicationContext())
                    .load(url)
                    .into(img);
            desc.setText(txtdesc);
        }
    }




    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button1){
            Intent ii = new Intent(getApplicationContext(), ConfirmMovie.class);

            startActivity(ii);
        }
    }
}
