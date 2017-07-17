package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.OkHttpClient;
import rest.ApiClient;
import rest.ApiInterface;
import security.EncryptTransactionPin;
import security.SecurityLayer;


public class ChangePin extends Fragment implements View.OnClickListener {
    SweetAlertDialog pDialog;
    EditText et,et2,oldpin;
    Button btnok;
    SessionManagement session;
    public ChangePin() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.change_pin, container, false);
        session = new SessionManagement(getActivity());
        oldpin = (EditText) rootView.findViewById(R.id.oldpin);
        et = (EditText) rootView.findViewById(R.id.pin);
        et2 = (EditText) rootView.findViewById(R.id.cpin);
        btnok = (Button) rootView.findViewById(R.id.button2);
        btnok.setOnClickListener(this);


        pDialog = new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        return rootView;
    }



    public void StartChartAct(int i){


    }
    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }
    @Override
    public void onClick(View v) {




    if (v.getId() == R.id.button2) {
        if (Utility.checkInternetConnection(getActivity())) {
            String npin = et.getText().toString();
            String confnpin = et2.getText().toString();
            String oldpinn = oldpin.getText().toString();
            if (Utility.isNotNull(oldpinn)) {
                if (Utility.isNotNull(npin)) {
                    if (confnpin.equals(npin)) {
                        if (!oldpinn.equals(npin)) {
                        if(npin.length() == 5 && oldpinn.length() == 5){
                    //    pDialog.show();
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

                        String encrypted1 = null;
                        String encrypted2 = null;
                        try {

                            encrypted1 = EncryptTransactionPin.encrypt(key, oldpinn, 'F');
                            encrypted2 = EncryptTransactionPin.encrypt(key, npin, 'F');

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        ApiInterface apiService =
                                ApiClient.getClient().create(ApiInterface.class);
                        String usid = Utility.gettUtilUserId(getActivity());
                        String agentid = Utility.gettUtilAgentId(getActivity());
                        String mobnoo = Utility.gettUtilMobno(getActivity());
                        Log.v("Chg Pin URL", "1/" + usid + "/" + agentid + "/" + "0000/" + encrypted1 + "/" + encrypted2);
                       String params = "1/"+ usid+"/"+agentid+"/"+mobnoo+"/"+ encrypted1+"/"+encrypted2;
                       invokeCheckRef(params);
                       /* Call<ChangePinModel> call = apiService.getChngPin("1", usid, agentid, "0000", encrypted1, encrypted2);
                        call.enqueue(new Callback<ChangePinModel>() {
                            @Override
                            public void onResponse(Call<ChangePinModel> call, Response<ChangePinModel> response) {

                                if (!(response.body() == null)) {
                                    String responsemessage = response.body().getMessage();
                                    String respcode = response.body().getRespCode();
                                    Log.v("Response Message", responsemessage);

                                    if (Utility.isNotNull(respcode) && Utility.isNotNull(respcode)) {


                                        if (respcode.equals("00")) {

                                            getActivity().finish();
                                            startActivity(new Intent(getActivity(), SignInActivity.class));
                                            Toast.makeText(
                                                    getActivity(),
                                                    "Pin change successful.Proceed to sign in with your new pin" ,
                                                    Toast.LENGTH_LONG).show();
                                        }else{
                                            Toast.makeText(
                                                    getActivity(),
                                                    "" + responsemessage,
                                                    Toast.LENGTH_LONG).show();
                                        }


                                    } else {

                                        Toast.makeText(
                                                getActivity(),
                                                "There was an error on your request",
                                                Toast.LENGTH_LONG).show();


                                    }
                                } else {

                                    Toast.makeText(
                                            getActivity(),
                                            "There was an error on your request",
                                            Toast.LENGTH_LONG).show();


                                }
                                pDialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<ChangePinModel> call, Throwable t) {
                                // Log error here since request failed
                                Log.v("throwable error", t.toString());


                                Toast.makeText(
                                        getActivity(),
                                        "There was an error on your request",
                                        Toast.LENGTH_LONG).show();


                                pDialog.dismiss();
                            }
                        });*/
                        }
                        else {
                            Toast.makeText(
                                    getActivity(),
                                    "Please ensure the Confirm New Pin and New Pin values are 5 digit length",
                                    Toast.LENGTH_LONG).show();
                        }
                        }
                        else {
                            Toast.makeText(
                                    getActivity(),
                                    "Please ensure Old Pin and New Pin values are not the same",
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(
                                getActivity(),
                                "Please ensure the Confirm New Pin and New Pin values are  the same",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(
                            getActivity(),
                            "Please enter a valid value for New pin",
                            Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(
                        getActivity(),
                        "Please enter a valid value for Old pin",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
    }

    private void invokeCheckRef(final String params) {
        final ProgressDialog pro = new ProgressDialog(getActivity());
        pro.setMessage("Loading...");
        pro.setTitle("");
        pro.setCancelable(false);
        pro.show();
        final AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(35000);

        String endpoint= "login/changepin.action";

        String url = "";
        try {
            url = ApplicationConstants.NET_URL+ SecurityLayer.genURLCBC(params,endpoint,getActivity());
            //Log.d("cbcurl",url);
            Log.v("RefURL",url);
            SecurityLayer.Log("refurl", url);
            SecurityLayer.Log("params", params);
        } catch (Exception e) {
            Log.e("encryptionerror",e.toString());
        }

        try {
            MySSLSocketFactory.SecureURL(client, getActivity());
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
                    obj = SecurityLayer.decryptTransaction(obj, getActivity());
                    SecurityLayer.Log("decrypted_response", obj.toString());

                    String respcode = obj.optString("responseCode");
                    String responsemessage = obj.optString("message");


                    //  JSONObject datas = obj.optJSONObject("data");
                    //session.setString(SecurityLayer.KEY_APP_ID,appid);

                    if (Utility.isNotNull(respcode) && Utility.isNotNull(responsemessage)) {
                        Log.v("Response Message", responsemessage);

                        if (respcode.equals("00")) {

                            getActivity().finish();
                            startActivity(new Intent(getActivity(), SignInActivity.class));
                            Toast.makeText(
                                    getActivity(),
                                    "Pin change successful.Proceed to sign in with your new pin" ,
                                    Toast.LENGTH_LONG).show();
                        }
                        else {

                            Toast.makeText(
                                    getActivity(),
                                    responsemessage,
                                    Toast.LENGTH_LONG).show();


                        }

                    }
                    else {

                        Toast.makeText(
                                getActivity(),
                                "There was an error on your request",
                                Toast.LENGTH_LONG).show();


                    }

                } catch (JSONException e) {
                    SecurityLayer.Log("encryptionJSONException", e.toString());
                    // TODO Auto-generated catch block
                    Toast.makeText(getActivity(), getActivity().getText(R.string.conn_error), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getActivity(), "We are unable to process your request at the moment. Please try again later", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getActivity(), "We are unable to process your request at the moment. Please try again later", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getActivity(), getActivity().getText(R.string.conn_error), Toast.LENGTH_LONG).show();

                }
            }
        });
    }


}
