package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.security.KeyStore;

import cn.pedant.SweetAlert.SweetAlertDialog;
import model.DeviceActivation;
import okhttp3.OkHttpClient;
import rest.ApiClient;
import rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import security.EncryptTransactionPin;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivateSecrQn extends AppCompatActivity implements View.OnClickListener {
    Button btnnext;
    String agid,agpin,agphn;
    GoogleCloudMessaging gcmObj;
    //Context applicationContext;
    String regId = "";
    SessionManagement session;
    ProgressDialog prgDialog,prgDialog2;
    SweetAlertDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate_secr_qn);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        agid = getIntent().getStringExtra("agid");
        agphn = getIntent().getStringExtra("agphnno");
        agpin = getIntent().getStringExtra("agpin");
        session = new SessionManagement(getApplicationContext());

        prgDialog2 = new ProgressDialog(this);
        prgDialog2.setMessage("Loading....");
        prgDialog2.setCancelable(false);
       registerInBackground();
        btnnext = (Button) findViewById(R.id.button1);
        btnnext.setOnClickListener(this);

         pDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcmObj == null) {

                        gcmObj = GoogleCloudMessaging
                                .getInstance(getApplicationContext());
                    }
                    regId = gcmObj
                            .register(ApplicationConstants.GOOGLE_PROJ_ID);
                    msg = "Registration ID :" + regId;
                    Log.v("Reg Gotten ID",msg);

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                if (!TextUtils.isEmpty(regId)) {

                   /* Toast.makeText(
                            getApplicationContext(),
                            "Registered with GCM Server successfully.\n\n"
                                    + msg, Toast.LENGTH_SHORT).show();*/
                } else {
                    Toast.makeText(
                           getApplicationContext(),
                            "Reg ID Creation Failed.\n\nEither you haven't enabled Internet or GCM server is busy right now. Make sure you enabled Internet and try registering again after some time."
                                    + msg, Toast.LENGTH_LONG).show();
                }
            }
        }.execute(null, null, null);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button1) {


            String ip = Utility.getIP(getApplicationContext());
            String mac = Utility.getMacAddress(getApplicationContext());
            String serial = Utility.getSerial();
            String version = Utility.getDevVersion();
            String devtype = Utility.getDevModel();
            String imei = Utility.getDevImei(getApplicationContext());
         if (Utility.checkInternetConnection(getApplicationContext())){
                if (Utility.isNotNull(imei) && Utility.isNotNull(serial)) {
                    if(regId == null){
                        regId = "JKKS";
                    }
                   pDialog.show();
                        OkHttpClient client = new OkHttpClient();
                        try {
                            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                            trustStore.load(null, null);
                            MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
                            sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                            client.sslSocketFactory();
                        } catch (Exception e) {
                        }


                        String key = "97206B46CE46376894703ECE161F31F2";
                        String password = "43211";
                        String encrypted = "sdd";
                        try {
                            encrypted = EncryptTransactionPin.encrypt(key, password, 'F');
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        ApiInterface apiService =
                                ApiClient.getClient().create(ApiInterface.class);
                        // reg/devReg.action/{channel}/{userId}/{merchantId}/{mobileNumber}/{pin}/{secans1}/{secans2}/{secans3}/{macAddr}/{deviceIp}/{imeiNo}/{serialNo}/{version}/{deviceType}/{gcmId}
                        // /agencyapi/app/reg/devReg.action/1/CEVA/JANE00000000019493818389/67E13557CCC8F7DA/secans1/secans2/secans3/123.123.324234.123.123./123.123.123/3213213123123129493818389000/4.3.2/mobile/88932983298kldfjkdf93290e3kjdsfkjds90we

                        Log.v("Dev Reg", "1" + "/CEVA/" + agid + "/" + agphn + "/" + encrypted + "/" + "secans1/" + "secans2/" + "secans3/" + mac + "/" + ip + "/" + imei + "/" + serial + "/" + version + "/" + devtype + "/" + regId);
                        Call<DeviceActivation> call2 = apiService.getDevReg("1",  agid, agphn, encrypted, "secans1", "secans2", "secans3", mac, ip, imei, serial, version, devtype, regId);
                        call2.enqueue(new Callback<DeviceActivation>() {
                            @Override
                            public void onResponse(Call<DeviceActivation> call, Response<DeviceActivation> response) {
                                if (!(response.body() == null)) {
                                    String responsemessage = response.body().getMessage();
                                    String respcode = response.body().getRespCode();


                                    if (Utility.isNotNull(respcode) && Utility.isNotNull(responsemessage)) {
                                        Log.v("Response Message", responsemessage);
                                        Toast.makeText(
                                                getApplicationContext(),
                                                "" + responsemessage,
                                                Toast.LENGTH_LONG).show();
                                        if (respcode.equals("00")) {
                                            session.setReg();
                                            finish();
                                            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                                        }


                                    } else {

                                        Toast.makeText(
                                                getApplicationContext(),
                                                "There was an error on your request",
                                                Toast.LENGTH_LONG).show();


                                    }
                                } else {

                                    Toast.makeText(
                                            getApplicationContext(),
                                            "There was an error on your request",
                                            Toast.LENGTH_LONG).show();


                                }

                                     pDialog.dismiss();


                            }

                            @Override
                            public void onFailure(Call<DeviceActivation> call, Throwable t) {
                                // Log error here since request failed
                                Log.v("throwable error", t.toString());
                                session.setReg();
                                finish();
                                startActivity(new Intent(getApplicationContext(), SignInActivity.class));

                                Toast.makeText(
                                        getApplicationContext(),
                                        "There was an error on your request",
                                        Toast.LENGTH_LONG).show();


                                    pDialog.dismiss();
                            }

                        });
                    }
                } else {

                    Toast.makeText(
                            getApplicationContext(),
                            "Please ensure this device has an IMEI number",
                            Toast.LENGTH_LONG).show();



            }
        }
    }

}
