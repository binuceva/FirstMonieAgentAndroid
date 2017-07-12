package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.OkHttpClient;
import security.EncryptTransactionPin;
import security.SecurityLayer;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ForceChangePin extends AppCompatActivity implements View.OnClickListener{
    SweetAlertDialog pDialog;
    EditText et,et2,oldpin;
    Button btnok;
    SessionManagement session;
    String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_force_change_pin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        oldpin = (EditText) findViewById(R.id.oldpin);
        et = (EditText) findViewById(R.id.pin);
        et2 = (EditText) findViewById(R.id.cpin);
        btnok = (Button) findViewById(R.id.button2);
        btnok.setOnClickListener(this);
        setSupportActionBar(toolbar);
        session = new SessionManagement(getApplicationContext());
        pDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);

        if(!(getIntent() == null)){
          value = getIntent().getExtras().getString("pinna");
        }


    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.button2) {
            if (Utility.checkInternetConnection(getApplicationContext())) {
             final   String npin = et.getText().toString();
                String confnpin = et2.getText().toString();
                String oldpinn = oldpin.getText().toString();
                if (Utility.isNotNull(oldpinn)) {
                    if (Utility.isNotNull(npin)) {
                        if(confnpin.equals(npin)) {
                            if(npin.length() == 5 && oldpinn.length() == 5){
                     //   pDialog.show();
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

                            encrypted1 = EncryptTransactionPin.encrypt(key, oldpinn, 'F');
                            encrypted2 = EncryptTransactionPin.encrypt(key, npin, 'F');

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                                String usid = Utility.gettUtilUserId(getApplicationContext());
                                String agentid = Utility.gettUtilAgentId(getApplicationContext());
                                String mobnoo = Utility.gettUtilMobno(getApplicationContext());

String params = "1/"+usid+"/"+agentid+"/0000/"+encrypted1+"/"+encrypted2;




                                String lgparams = "1" + "/"+usid+"/" + value  + "/0000/";
                              invokeLoginSec(lgparams,params);

                               // invokeCheckRef(params);
                      /* ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                            Log.v("Chg Pin URL","1/"+usid+"/"+agentid+"/"+"0000/"+encrypted1+"/"+encrypted2);
                        Call<ChangePinModel> call = apiService.getChngPin("1",usid,agentid,"0000",encrypted1,encrypted2);
                        call.enqueue(new Callback<ChangePinModel>() {
                            @Override
                            public void onResponse(Call<ChangePinModel>call, Response<ChangePinModel> response) {

                                if(!(response.body() == null)) {
                                    String responsemessage = response.body().getMessage();
                                    String respcode = response.body().getRespCode();
                                    Log.v("Response Message", responsemessage);

                                    if (Utility.isNotNull(respcode) && Utility.isNotNull(respcode)) {


                                        if (respcode.equals("00")) {
                                             session.setReg();
                                            finish();
                                            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                                        //    pDialog.show();
                                             //  invokeCheckRef(npin);
                                           *//* String finpin = "12345";
                                            String key = "97206B46CE46376894703ECE161F31F2";
                                            String encrypted = null;
                                            try {

                                                encrypted = EncryptTransactionPin.encrypt(key, finpin, 'F');
                                                System.out.println("Encrypt Pin "+EncryptTransactionPin.encrypt(key, finpin, 'F'));
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            String usid = Utility.gettUtilUserId(getApplicationContext());
                                            String params = "1" + "/"+usid+"/" + encrypted  + "/0000/";
                                            invokeLoginSec(params); *//*


                                       }else{
                                            Toast.makeText(
                                                    getApplicationContext(),
                                                    "" + responsemessage,
                                                    Toast.LENGTH_LONG).show();
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
                                ClearFields();
                                pDialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<ChangePinModel> call, Throwable t) {
                                // Log error here since request failed
                                Log.v("throwable error",t.toString());
                                ClearFields();

                                Toast.makeText(
                                        getApplicationContext(),
                                        "There was an error on your request",
                                        Toast.LENGTH_LONG).show();


ClearFields();
                                pDialog.dismiss();
                            }
                        });*/
                        }
                        else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Please ensure the Confirm New Pin and New Pin values are 5 digit length",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Please ensure the Confirm New Pin and New Pin values are the same",
                                    Toast.LENGTH_LONG).show();
                        }
                    }  else {
                        Toast.makeText(
                                getApplicationContext(),
                                "Please enter a valid value for New pin",
                                Toast.LENGTH_LONG).show();
                    }
                }  else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Please enter a valid value for Old pin",
                            Toast.LENGTH_LONG).show();
                }
            }

        }
    }




    public void ClearFields(){

        oldpin.setText("");
        et.setText("");
        et2.setText("");
    }


    private void invokeCheckRef(final String params) {
        final ProgressDialog pro = new ProgressDialog(this);
        pro.setMessage("Loading...");
        pro.setTitle("");
        pro.setCancelable(false);
        pro.show();
        final AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(35000);

        String endpoint= "login/changepin.action";

        String url = "";
        String appid = session.getString(SecurityLayer.KEY_APP_ID);
        Log.v("appid", appid);
        try {
            url = ApplicationConstants.NET_URL+SecurityLayer.genURLCBC(params,endpoint,getApplicationContext());
            //Log.d("cbcurl",url);
            Log.v("RefURL",url);
            SecurityLayer.Log("refurl", url);
            SecurityLayer.Log("params", params);
        } catch (Exception e) {
Log.e("encryptionerror",e.toString());
        }

        try {
            MySSLSocketFactory.SecureURL(client, getApplicationContext());
        } catch (KeyStoreException e) {
            SecurityLayer.Log(e.toString());
            SecurityLayer.Log(e.toString());
        } catch (IOException e) {
            SecurityLayer.Log(e.toString());
        } catch (NoSuchAlgorithmException e) {
            SecurityLayer.Log(e.toString());
        } catch (CertificateException e) {
            SecurityLayer.Log(e.toString());
        } catch (UnrecoverableKeyException e) {
            SecurityLayer.Log(e.toString());
        } catch (KeyManagementException e) {
            SecurityLayer.Log(e.toString());
        }

        client.post(url, new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                // Hide Progress Dialog
                pro.dismiss();
                try {
                    // JSON Object
                    Log.v("response..:", response);
                    JSONObject obj = new JSONObject(response);
                    //obj = Utility.onresp(obj,getActivity());
                    obj = SecurityLayer.decryptTransaction(obj, getApplicationContext());
                    SecurityLayer.Log("decrypted_response", obj.toString());

                    String respcode = obj.optString("responseCode");
                    String responsemessage = obj.optString("message");


                  //  JSONObject datas = obj.optJSONObject("data");
                    //session.setString(SecurityLayer.KEY_APP_ID,appid);

                        if (Utility.isNotNull(respcode) && Utility.isNotNull(responsemessage)) {
                            Log.v("Response Message", responsemessage);

                            if (respcode.equals("00")) {
                                session.setReg();
                                finish();
                                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                            }
                            else {

                                Toast.makeText(
                                        getApplicationContext(),
                                        responsemessage,
                                        Toast.LENGTH_LONG).show();


                            }

                        }
                        else {

                            Toast.makeText(
                                    getApplicationContext(),
                                    "There was an error on your request",
                                    Toast.LENGTH_LONG).show();


                        }

                } catch (JSONException e) {
                    SecurityLayer.Log("encryptionJSONException", e.toString());
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), getApplicationContext().getText(R.string.conn_error), Toast.LENGTH_LONG).show();
                    // SecurityLayer.Log(e.toString());

                } catch (Exception e) {
                    SecurityLayer.Log("encryptionJSONException", e.toString());
                    // SecurityLayer.Log(e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {

                // Hide Progress Dialog
                pro.dismiss();
                SecurityLayer.Log("error:", error.toString());
                // When Http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "We are unable to process your request at the moment. Please try again later", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "We are unable to process your request at the moment. Please try again later", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), getApplicationContext().getText(R.string.conn_error), Toast.LENGTH_LONG).show();

                }
            }
        });
    }



    private void invokeFchangepin(final String params) {
        final ProgressDialog pro = new ProgressDialog(this);
        pro.setMessage("Loading...");
        pro.setTitle("");
        pro.setCancelable(false);
        pro.show();
        final AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(35000);

        String sessid = UUID.randomUUID().toString();


        String session_id = UUID.randomUUID().toString();
        String endpoint= "login/login.action/";

        String url = "";
        try {
            url = SecurityLayer.beforeLogin(params,getApplicationContext(),endpoint);
            //Log.d("cbcurl",url);
            SecurityLayer.Log("params", params);
            SecurityLayer.Log("refurl", url);
        } catch (Exception e) {
//Log.e("encryptionerror",e.toString());
        }

        try {
            MySSLSocketFactory.SecureURL(client, getApplicationContext());
        } catch (KeyStoreException e) {
            SecurityLayer.Log(e.toString());
            SecurityLayer.Log(e.toString());
        } catch (IOException e) {
            SecurityLayer.Log(e.toString());
        } catch (NoSuchAlgorithmException e) {
            SecurityLayer.Log(e.toString());
        } catch (CertificateException e) {
            SecurityLayer.Log(e.toString());
        } catch (UnrecoverableKeyException e) {
            SecurityLayer.Log(e.toString());
        } catch (KeyManagementException e) {
            SecurityLayer.Log(e.toString());
        }

        client.post(url, new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                // Hide Progress Dialog
                pro.dismiss();
                try {
                    // JSON Object
                    Log.v("response..:", response);
                    JSONObject obj = new JSONObject(response);
                    //obj = Utility.onresp(obj,getActivity());
                    obj = SecurityLayer.decryptFirstTimeLogin(obj, getApplicationContext());
                    SecurityLayer.Log("decrypted_response", obj.toString());

                    String respcode = obj.optString("responseCode");
                    String responsemessage = obj.optString("message");


                    JSONObject datas = obj.optJSONObject("data");
                    //session.setString(SecurityLayer.KEY_APP_ID,appid);
                    if (respcode.equals("00")) {
                        if (Utility.isNotNull(respcode) && Utility.isNotNull(responsemessage)) {
                            Log.v("Response Message", responsemessage);

                            if (respcode.equals("00")) {
                                if (!(datas == null)) {
                                    String agentid = datas.optString("agent");
                                    String userid = datas.optString("userId");
                                    String username = datas.optString("userName");
                                    String email = datas.optString("email");
                                    String lastl = datas.optString("lastLoggedIn");
                                    String mobno = datas.optString("mobileNo");
                                    String accno = datas.optString("acountNumber");
                                    session.SetAgentID(agentid);
                                    session.SetUserID(userid);
                                    session.putCustName(username);
                                    session.putEmail(email);
                                    session.putLastl(lastl);
                                    session.putMobNo(mobno);
                                    session.putAccountno(accno);
                                    boolean checknewast = session.checkAst();
                                    if (checknewast == false) {
                                        Toast.makeText(getApplicationContext(), "Activation has been completed successfully.You have successfully logged in", Toast.LENGTH_LONG).show();
                                        finish();
                                        startActivity(new Intent(getApplicationContext(), AdActivity.class));
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Activation has been completed successfully.You have successfully logged in", Toast.LENGTH_LONG).show();
                                        finish();
                                        startActivity(new Intent(getApplicationContext(), FMobActivity.class));
                                    }
                                }
                            }
                            else {

                                Toast.makeText(
                                        getApplicationContext(),
                                        responsemessage,
                                        Toast.LENGTH_LONG).show();


                            }

                        }
                        else {

                            Toast.makeText(
                                    getApplicationContext(),
                                    "There was an error on your request",
                                    Toast.LENGTH_LONG).show();


                        }
                    }

                    // Else display error message
                    else {
                        Toast.makeText(
                                getApplicationContext(),
                                "There was an error on your request",
                                Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    SecurityLayer.Log("encryptionJSONException", e.toString());
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), getApplicationContext().getText(R.string.conn_error), Toast.LENGTH_LONG).show();
                    // SecurityLayer.Log(e.toString());

                } catch (Exception e) {
                    SecurityLayer.Log("encryptionJSONException", e.toString());
                    // SecurityLayer.Log(e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {

                // Hide Progress Dialog
                pro.dismiss();
                SecurityLayer.Log("error:", error.toString());
                // When Http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "We are unable to process your request at the moment. Please try again later", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "We are unable to process your request at the moment. Please try again later", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), getApplicationContext().getText(R.string.conn_error), Toast.LENGTH_LONG).show();

                }
            }
        });
    }



    private void invokeAds() {
        final ProgressDialog pro = new ProgressDialog(this);
        pro.setMessage("Loading...");
        pro.setTitle("");
        pro.setCancelable(false);
        pro.show();
        final AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(35000);

        String endpoint= "adverts/ads.action";


        String usid = Utility.gettUtilUserId(getApplicationContext());
        String agentid = Utility.gettUtilAgentId(getApplicationContext());
        String params = "1/"+usid+"/"+agentid+"/0000";
        String url = "";
        try {
            url = SecurityLayer.genURLCBC(params,endpoint,getApplicationContext());
            //Log.d("cbcurl",url);
            Log.v("RefURL",url);
            SecurityLayer.Log("refurl", url);
            SecurityLayer.Log("params", params);
        } catch (Exception e) {
            Log.e("encryptionerror",e.toString());
        }

        try {
            MySSLSocketFactory.SecureURL(client, getApplicationContext());
        } catch (KeyStoreException e) {
            SecurityLayer.Log(e.toString());
            SecurityLayer.Log(e.toString());
        } catch (IOException e) {
            SecurityLayer.Log(e.toString());
        } catch (NoSuchAlgorithmException e) {
            SecurityLayer.Log(e.toString());
        } catch (CertificateException e) {
            SecurityLayer.Log(e.toString());
        } catch (UnrecoverableKeyException e) {
            SecurityLayer.Log(e.toString());
        } catch (KeyManagementException e) {
            SecurityLayer.Log(e.toString());
        }

        client.post(url, new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                // Hide Progress Dialog
                pro.dismiss();
                try {
                    // JSON Object
                    Log.v("response..:", response);
                    JSONObject obj = new JSONObject(response);
                    //obj = Utility.onresp(obj,getActivity());
                    obj = SecurityLayer.decryptTransaction(obj, getApplicationContext());
                    SecurityLayer.Log("decrypted_response", obj.toString());

                    String respcode = obj.optString("responseCode");
                    String responsemessage = obj.optString("message");


                    JSONObject datas = obj.optJSONObject("data");
                    //session.setString(SecurityLayer.KEY_APP_ID,appid);
                    if (respcode.equals("00")) {
                        if (Utility.isNotNull(respcode) && Utility.isNotNull(responsemessage)) {
                            Log.v("Response Message", responsemessage);

                            if (respcode.equals("00")) {
                                session.setReg();
                                finish();
                                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                            }
                            else {

                                Toast.makeText(
                                        getApplicationContext(),
                                        responsemessage,
                                        Toast.LENGTH_LONG).show();


                            }

                        }
                        else {

                            Toast.makeText(
                                    getApplicationContext(),
                                    "There was an error on your request",
                                    Toast.LENGTH_LONG).show();


                        }
                    }

                    // Else display error message
                    else {
                        Toast.makeText(
                                getApplicationContext(),
                                "There was an error on your request",
                                Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    SecurityLayer.Log("encryptionJSONException", e.toString());
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), getApplicationContext().getText(R.string.conn_error), Toast.LENGTH_LONG).show();
                    // SecurityLayer.Log(e.toString());

                } catch (Exception e) {
                    SecurityLayer.Log("encryptionJSONException", e.toString());
                    // SecurityLayer.Log(e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {

                // Hide Progress Dialog
                pro.dismiss();
                SecurityLayer.Log("error:", error.toString());
                // When Http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "We are unable to process your request at the moment. Please try again later", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "We are unable to process your request at the moment. Please try again later", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), getApplicationContext().getText(R.string.conn_error), Toast.LENGTH_LONG).show();

                }
            }
        });
    }





    private void invokeLoginSec(final String params,final String chgpinparams) {
        final ProgressDialog pro = new ProgressDialog(this);
        pro.setMessage("Loading...");
        pro.setTitle("");
        pro.setCancelable(false);
        pro.show();
        final AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(35000);

        String endpoint= "login/login.action/";

        String url = "";
        try {
            url = SecurityLayer.generalLogin(params,"23322",getApplicationContext(),endpoint);
            //Log.d("cbcurl",url);
            Log.v("RefURL",url);
            SecurityLayer.Log("refurl", url);
            SecurityLayer.Log("params", params);
        } catch (Exception e) {
            Log.e("encryptionerror",e.toString());
        }

        try {
            MySSLSocketFactory.SecureURL(client, getApplicationContext());
        } catch (KeyStoreException e) {
            SecurityLayer.Log(e.toString());
            SecurityLayer.Log(e.toString());
        } catch (IOException e) {
            SecurityLayer.Log(e.toString());
        } catch (NoSuchAlgorithmException e) {
            SecurityLayer.Log(e.toString());
        } catch (CertificateException e) {
            SecurityLayer.Log(e.toString());
        } catch (UnrecoverableKeyException e) {
            SecurityLayer.Log(e.toString());
        } catch (KeyManagementException e) {
            SecurityLayer.Log(e.toString());
        }

        client.post(url, new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                // Hide Progress Dialog
                pro.dismiss();
                try {
                    // JSON Object
                    Log.v("response..:", response);
                    JSONObject obj = new JSONObject(response);
                    //obj = Utility.onresp(obj,getActivity());
                    obj = SecurityLayer.decryptGeneralLogin(obj, getApplicationContext());
                    SecurityLayer.Log("decrypted_response", obj.toString());

                    String respcode = obj.optString("responseCode");
                    String responsemessage = obj.optString("message");


                    JSONObject datas = obj.optJSONObject("data");
                    //session.setString(SecurityLayer.KEY_APP_ID,appid);

                        if (Utility.isNotNull(respcode) && Utility.isNotNull(responsemessage)) {
                            Log.v("Response Message", responsemessage);

                            if (respcode.equals("00")) {
                                if (!(datas == null)) {
                                   invokeCheckRef(chgpinparams);
                                 //   invokeAds();
                                   /* String agentid = datas.optString("agent");
                                    String userid = datas.optString("userId");
                                    String username = datas.optString("userName");
                                    String email = datas.optString("email");
                                    String lastl = datas.optString("lastLoggedIn");
                                    String mobno = datas.optString("mobileNo");
                                    String accno = datas.optString("acountNumber");
                                    session.SetAgentID(agentid);
                                    session.SetUserID(userid);
                                    session.putCustName(username);
                                    session.putEmail(email);
                                    session.putLastl(lastl);
                                    session.putMobNo(mobno);
                                    session.putAccountno(accno);
                                    boolean checknewast = session.checkAst();
                                    if (checknewast == false) {
                                        Toast.makeText(getApplicationContext(), "Activation has been completed successfully.You have successfully logged in", Toast.LENGTH_LONG).show();
                                        finish();
                                        startActivity(new Intent(getApplicationContext(), AdActivity.class));
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Activation has been completed successfully.You have successfully logged in", Toast.LENGTH_LONG).show();
                                        finish();
                                        startActivity(new Intent(getApplicationContext(), FMobActivity.class));
                                    }*/
                                }
                            }
                            else {

                                Toast.makeText(
                                        getApplicationContext(),
                                        responsemessage,
                                        Toast.LENGTH_LONG).show();


                            }

                        }
                        else {

                            Toast.makeText(
                                    getApplicationContext(),
                                    "There was an error on your request",
                                    Toast.LENGTH_LONG).show();


                        }

                } catch (JSONException e) {
                    SecurityLayer.Log("encryptionJSONException", e.toString());
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), getApplicationContext().getText(R.string.conn_error), Toast.LENGTH_LONG).show();
                    // SecurityLayer.Log(e.toString());

                } catch (Exception e) {
                    SecurityLayer.Log("encryptionJSONException", e.toString());
                    // SecurityLayer.Log(e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {

                // Hide Progress Dialog
                pro.dismiss();
                SecurityLayer.Log("error:", error.toString());
                // When Http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "We are unable to process your request at the moment. Please try again later", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "We are unable to process your request at the moment. Please try again later", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), getApplicationContext().getText(R.string.conn_error), Toast.LENGTH_LONG).show();

                }
            }
        });
    }


}
