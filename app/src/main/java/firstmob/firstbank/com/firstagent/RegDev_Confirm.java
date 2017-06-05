package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyStore;
import java.util.HashMap;

public class RegDev_Confirm extends ActionBarActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    String value,newrefd,ema,mon,cust,imeic;
    Button signup;
    EditText code;

    SessionManagement session;
    ProgressDialog prgDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regdev_confirm);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        code = (EditText) findViewById(R.id.phone);

        signup = (Button) findViewById(R.id.sign_up);
        signup.setOnClickListener(this);

        mon = getIntent().getExtras().getString("mobno");
        cust = getIntent().getExtras().getString("cust");
        imeic = getIntent().getExtras().getString("imeic");


        session = new SessionManagement(this);
        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Processing request....");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Register Device");
	}

	@Override
	public void onBackPressed() {

	}

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.sign_up){
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

    public void registerUser() {
        String fg = code.getText().toString();




            if (Utility.isNotNull(fg)) {


                invokeWS(value,fg);

            } else{
                Toast.makeText(getApplicationContext(), "The Code Field is empty. Please fill in appropiately", Toast.LENGTH_LONG).show();
            }






    }




    public void invokeWS(String refidd,final String fg){
        // Show Progress Dialog
        prgDialog.show();

        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(40000);
        HashMap<String, String> nurl = session.getNetURL();
        String newurl = nurl.get(SessionManagement.NETWORK_URL);
        // String url = newurl+"/natmobileapi/rest/customer/ministat?acctno="+acc+"";
        String imei = "";

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        imei = telephonyManager.getDeviceId();


        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        String mac = wInfo.getMacAddress();

        String build = Build.SERIAL;
      /*  String brand = Build.BRAND;
        String model = Build.MODEL;
        String dtype = brand + " "+model;
        dtype = dtype.replace(" ","_");*/
        String dtype = getDeviceName();
        dtype = dtype.replace(" ","_");
       /* String url =newurl+"/natmobileapi/rest/customer/regdeviceotp?msisdn="+mon+"&otp="+fg+"&custcode="+cust+"&imei="+imei+"&macadress="+mac+"&ipadress="+mac+"&serial="+build+"&imeicount="+imeic+"&dtype="+dtype;
       */ String url = ApplicationConstants.NET_URL+ApplicationConstants.AND_ENPOINT+"regdeviceotp/"+mon+"/"+fg+"/"+cust+"/"+imei+"/"+mac+"/"+mac+"/"+build+"/"+imeic+"/"+dtype;


     Log.d("Reg Dev Url",url);
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

                            String rpcode = obj.optString("responsecode");

                    String rpmess = obj.optString("responsemessage");
                            Log.v("Response Message", rpmess);

                    if (rpcode.equals("00")) {

                        Log.v("Imei count is",imeic);
if(imeic.equals("0")) {

   checkRgdConn();

} else if (Double.parseDouble(imeic)>0) {
    Bundle b = new Bundle();


    b.putString("cust", cust);
    b.putString("mobno", mon);
    b.putString("otp", fg);
    b.putString("imeic", imeic);


    Intent ip = new Intent(RegDev_Confirm.this, RegDevCard.class);

    ip.putExtras(b);
   finish();
    startActivity(new Intent(ip));
}

                            } else{
                                Toast.makeText(getApplicationContext(), rpmess, Toast.LENGTH_LONG).show();

                            }



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


    private boolean checkRgdConn() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            //	new SendTask().execute();
            RegDevice();
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

    public void RegDevice() {

        String fg = code.getText().toString();




                if (Utility.isNotNull(fg)) {


                    invokeRgd(fg);

                } else{
                    Toast.makeText(getApplicationContext(), "The Code Field is Empty. Please Fill In Appropiately", Toast.LENGTH_LONG).show();
                }






    }




    public void invokeRgd(String otp){
        // Show Progress Dialog
        prgDialog.show();

        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(40000);
        HashMap<String, String> nurl = session.getNetURL();
        String newurl = nurl.get(SessionManagement.NETWORK_URL);
        // String url = newurl+"/natmobileapi/rest/customer/ministat?acctno="+acc+"";
        String imei = "";

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        imei = telephonyManager.getDeviceId();


        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        String mac = wInfo.getMacAddress();

        String build = Build.SERIAL;
   String url = ApplicationConstants.NET_URL+ApplicationConstants.AND_ENPOINT+"regdevice/"+mon+"/"+otp+"/"+cust+"/"+imei+"/"+mac+"/"+mac+"/"+build+"/N/N/N/"+imeic;

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

                            String rpcode = obj.optString("responsecode");
                    String rpmess = obj.optString("responsemessage");

                            Log.v("Response Code", rpcode);
                            if (rpcode.equals("00")) {

                            } else{
                                Toast.makeText(getApplicationContext(), rpmess, Toast.LENGTH_LONG).show();

                            }



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

    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }


    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }
}
