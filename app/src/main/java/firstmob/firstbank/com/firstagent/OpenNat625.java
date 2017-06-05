package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyStore;
import java.util.HashMap;

public class OpenNat625 extends ActionBarActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    Button sigin;
    SessionManagement session;
    EditText idno,mobno,fnam,lnam,yob;
    Spinner sp1;
    ProgressDialog prgDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.opennat625);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
       sigin = (Button) findViewById(R.id.button1);
        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Opening Account....");
        // Set Cancelable as False

        prgDialog.setCancelable(false);
        session = new SessionManagement(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Open Account ");



        idno = (EditText) findViewById(R.id.user_id);
        mobno = (EditText) findViewById(R.id.user_id45);
        fnam = (EditText) findViewById(R.id.user_id2);
       lnam = (EditText) findViewById(R.id.user_id29);
        //yob = (EditText) findViewById(R.id.user_id7);
        sigin.setOnClickListener(this);

        sp1 = (Spinner)findViewById(R.id.spin2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                OpenNat625.this, R.array.netop, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter);
	}

	@Override
	public void onBackPressed() {
		this.finish();
		startActivity(new Intent(getApplicationContext(), MainActivity.class));
	}

    @Override
    public void onClick(View view) {
        if(view.getId()==  R.id.button1){
            checkInternetConnection();
       }
    }

    private boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            //	new SendTask().execute();
            registerUser();
            //	RegTest();
            return true;
        } else {

            Toast.makeText(
                    getApplicationContext(),
                    "No Internet Connection. Please check your internet setttings",
                    Toast.LENGTH_LONG).show();
            return false;
        }
    }
    public void registerUser(){
String acty = Integer.toString(sp1.getSelectedItemPosition());
        String mobnum = mobno.getText().toString().trim();
        String idn = idno.getText().toString().trim();
        String fname = fnam.getText().toString().trim();
        String lname = lnam.getText().toString().trim();
        String yb  = yob.getText().toString().trim();

        if( Utility.isNotNull(mobnum)){
            if( Utility.isNotNull(idn)){
                if( Utility.isNotNull(fname)){
                    if( Utility.isNotNull(lname)){
                        if( Utility.isNotNull(yb)){
                if(Utility.checknum(mobnum) == true) {
                    if(Utility.checknum(yb) == true) {
                        invokeWS(acty, mobnum, idn, fname, lname, yb);
                    }else{
                        Toast.makeText(getApplicationContext(), "The Year Of Birth field should only contain numeric characters. Please fill in appropiately", Toast.LENGTH_LONG).show();
}}else{
                    Toast.makeText(getApplicationContext(), "The Mobile Number field should only contain numeric characters. Please fill in appropiately", Toast.LENGTH_LONG).show();
                }}else{
                            Toast.makeText(getApplicationContext(), "The Year Of Birth field is empty. Please fill in appropiately", Toast.LENGTH_LONG).show();

                        }}else{
                        Toast.makeText(getApplicationContext(), "The Last Name  field is empty. Please fill in appropiately", Toast.LENGTH_LONG).show();

                    }}else{
                    Toast.makeText(getApplicationContext(), "The First Name field is empty. Please fill in appropiately", Toast.LENGTH_LONG).show();

                }}else{
                Toast.makeText(getApplicationContext(), "The ID Number field is empty. Please fill in appropiately", Toast.LENGTH_LONG).show();

            }}else{
            Toast.makeText(getApplicationContext(), "The Mobile Number field is empty. Please fill in appropiately", Toast.LENGTH_LONG).show();

        }
    }
    public void invokeWS( String acctype,String msisdn,String id,String fname,String lname,String yearob){
        // Show Progress Dialog
        prgDialog.show();

        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        HashMap<String, String> nurl = session.getNetURL();
        String newurl = nurl.get(SessionManagement.NETWORK_URL);
        // String url = newurl+"/natmobileapi/rest/customer/ministat?acctno="+acc+"";

        String url =newurl+"/natmobileapi/rest/customer/openAccount?serviceID=1&accountType="+acctype+"&msisdn="+msisdn+"&channelID=1&documentNumber="+id+"&firstName="+fname+"&lastName="+lname+"&yearOb="+yearob+"&accessPointID=ANDROID";
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            MySSLSocketFactory  sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            client.setSSLSocketFactory(sf);
        }
        catch (Exception e) {
        }
        client.post(url,new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                // Hide Progress Dialog
                prgDialog.hide();
                try {
                    // JSON Object
                    Log.v("response..:", response);
                    JSONObject obj = new JSONObject(response);





                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), " The device has not successfully connected to server. Please check your internet settings", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {

                // Hide Progress Dialog
                prgDialog.hide();
                // When Http response code is '404'
                if(statusCode == 404){
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(getApplicationContext(), " The device has not successfully connected to server. Please check your internet settings", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
