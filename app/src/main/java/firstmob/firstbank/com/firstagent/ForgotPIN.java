package firstmob.firstbank.com.firstagent;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.KeyStore;

import cn.pedant.SweetAlert.SweetAlertDialog;
import model.ChangePinModel;
import okhttp3.OkHttpClient;
import rest.ApiClient;
import rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import security.EncryptTransactionPin;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ForgotPIN extends AppCompatActivity implements View.OnClickListener{
    SweetAlertDialog pDialog;
    EditText et,et2;
    Button btnok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        et = (EditText) findViewById(R.id.pin);
        et2 = (EditText) findViewById(R.id.cpin);
        btnok = (Button) findViewById(R.id.button2);
        btnok.setOnClickListener(this);
        setSupportActionBar(toolbar);

        pDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.button2) {
            if (Utility.checkInternetConnection(getApplicationContext())) {
              String oldpin = et.getText().toString();
                String newpin = et2.getText().toString();
                if (Utility.isNotNull(oldpin)) {
                    if (Utility.isNotNull(newpin)) {
                        pDialog.show();
                        OkHttpClient client = new OkHttpClient();
                        try {
                            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                            trustStore.load(null, null);
                            MySSLSocketFactory  sf = new MySSLSocketFactory(trustStore);
                            sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                            client.sslSocketFactory();
                        }
                        catch (Exception e) {
                        }
                        String key = "97206B46CE46376894703ECE161F31F2";

                        String encrypted1 = null;
                        String encrypted2 = null;
                        try {

                            encrypted1 = EncryptTransactionPin.encrypt(key, oldpin, 'F');
                            encrypted2 = EncryptTransactionPin.encrypt(key, newpin, 'F');

                        }
                            catch (Exception e) {
                            e.printStackTrace();
                        }

                        ApiInterface apiService =
                                ApiClient.getClient().create(ApiInterface.class);
                        String usid = Utility.gettUtilUserId(getApplicationContext());
                        String agentid = Utility.gettUtilAgentId(getApplicationContext());
                        String mobnoo = Utility.gettUtilMobno(getApplicationContext());
                        Call<ChangePinModel> call = apiService.getChngPin("1",usid,agentid,"0000",encrypted1,encrypted2);
                        call.enqueue(new Callback<ChangePinModel>() {
                            @Override
                            public void onResponse(Call<ChangePinModel>call, Response<ChangePinModel> response) {

                                if(!(response.body() == null)) {
                                    String responsemessage = response.body().getMessage();
                                    String respcode = response.body().getRespCode();
                                    Log.v("Response Message", responsemessage);

                                    if (Utility.isNotNull(respcode) && Utility.isNotNull(respcode)) {
                                        Toast.makeText(
                                                getApplicationContext(),
                                                "" + responsemessage,
                                                Toast.LENGTH_LONG).show();

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
                            public void onFailure(Call<ChangePinModel> call, Throwable t) {
                                // Log error here since request failed
                                Log.v("throwable error",t.toString());


                                Toast.makeText(
                                        getApplicationContext(),
                                        "There was an error on your request",
                                        Toast.LENGTH_LONG).show();



                                pDialog.dismiss();
                            }
                        });
                    }  else {
                        Toast.makeText(
                                getApplicationContext(),
                                "Please enter a valid value for Confirm New pin",
                                Toast.LENGTH_LONG).show();
                    }
                }  else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Please enter a valid value for New pin",
                            Toast.LENGTH_LONG).show();
                }
                    }

            }
        }

    public void forgotpin(){
        pDialog.show();
        OkHttpClient client = new OkHttpClient();
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            MySSLSocketFactory  sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            client.sslSocketFactory();
        }
        catch (Exception e) {
        }

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

       Call<ChangePinModel> call = apiService.getChngPin("1","CEVA","PAND0000000001","0000","","9B7D106A26E2884F");
        call.enqueue(new Callback<ChangePinModel>() {
            @Override
            public void onResponse(Call<ChangePinModel>call, Response<ChangePinModel> response) {

                if(!(response.body() == null)) {
                    String responsemessage = response.body().getMessage();
                    String respcode = response.body().getRespCode();
                    Log.v("Response Message", responsemessage);

                    if (Utility.isNotNull(respcode) && Utility.isNotNull(respcode)) {
                        Toast.makeText(
                                getApplicationContext(),
                                "" + responsemessage,
                                Toast.LENGTH_LONG).show();

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
            public void onFailure(Call<ChangePinModel> call, Throwable t) {
                // Log error here since request failed
                Log.v("throwable error",t.toString());


                Toast.makeText(
                        getApplicationContext(),
                        "There was an error on your request",
                        Toast.LENGTH_LONG).show();



                pDialog.dismiss();
            }
        });
    }
}
