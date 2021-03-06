package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import okhttp3.OkHttpClient;
import rest.ApiInterface;
import rest.ApiSecurityClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import security.SecurityLayer;

public class AdActivity extends AppCompatActivity implements View.OnClickListener {

   // List<GetAgentIdData> plan = new ArrayList<GetAgentIdData>();
    String agid;

    ImageView iv;
   // SweetAlertDialog pDialog;
   public static final String SHAREDPREFFILE = "temp";
    public static final String USERIDPREF = "uid";
    public static final String TOKENPREF = "tkn";
    ProgressBar prgbar;
    Button btncontinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        btncontinue = (Button) findViewById(R.id.btncontinue);
        setSupportActionBar(toolbar);
//mClient  = new MobileServiceClient("https://cevabank.azurewebsites.net");
     /*   try {
            mClient = new MobileServiceClient("https://auth21.azurewebsites.net", getApplicationContext());


            //Log.d(Constant.LOG_TAG, "tuko poa: ");
        } catch (MalformedURLException m) {
            m.printStackTrace();
            //Log.d(Constant.LOG_TAG, "maneno: " + m.toString());
        }*/
        Button btnc = (Button) findViewById(R.id.btncontinue);

        prgbar = (ProgressBar) findViewById(R.id.prgbar);
        prgbar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.nbkyellow), android.graphics.PorterDuff.Mode.MULTIPLY);

      /*  pDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);*/

        iv = (ImageView) findViewById(R.id.adidimg);
        //  iv.setImageBitmap(null);

        btnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "You have successfully logged in", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(getApplicationContext(),FMobActivity.class));
            }
        });   OkHttpClient client = new OkHttpClient();
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            MySSLSocketFactory  sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            client.sslSocketFactory();
        }
        catch (Exception e) {
        }

if(Utility.checkInternetConnection(getApplicationContext())){
  //  getAgentIDs();
  fetchAds();
}

    }
    private void fetchAds() {


        String endpoint= "adverts/ads.action";


        String usid = Utility.gettUtilUserId(getApplicationContext());
        String agentid = Utility.gettUtilAgentId(getApplicationContext());
        String mobilenumber = Utility.gettUtilMobno(getApplicationContext());
        String params = "1/"+usid+"/"+agentid+"/"+mobilenumber;
        String urlparams = "";
        try {
            urlparams = SecurityLayer.genURLCBC(params,endpoint,getApplicationContext());
            //Log.d("cbcurl",url);
            Log.v("RefURL",urlparams);
            SecurityLayer.Log("refurl", urlparams);
            SecurityLayer.Log("params", params);
        } catch (Exception e) {
            Log.e("encryptionerror",e.toString());
        }





        ApiInterface apiService =
                ApiSecurityClient.getClient().create(ApiInterface.class);


        Call<String> call = apiService.setGenericRequestRaw(urlparams);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    // JSON Object

                    Log.v("response..:", response.body());
                    JSONObject obj = new JSONObject(response.body());
                    //obj = Utility.onresp(obj,getActivity());
                    obj = SecurityLayer.decryptTransaction(obj, getApplicationContext());
                    SecurityLayer.Log("decrypted_response", obj.toString());

                    String respcode = obj.optString("responseCode");
                    String responsemessage = obj.optString("message");


                    JSONArray plan = obj.optJSONArray("data");
                    //session.setString(SecurityLayer.KEY_APP_ID,appid);





                    Log.v("Response Message", responsemessage);
                    if (respcode.equals("00")) {
                        if (Utility.isNotNull(respcode) && Utility.isNotNull(responsemessage)) {
                            Log.v("Response Message", responsemessage);

                            if (respcode.equals("00")) {
                                JSONObject json_data = null;

                                prgbar.setVisibility(View.GONE);
                                Picasso.with(getApplicationContext()).load(R.drawable.adfbn).into(iv);
                                btncontinue.setVisibility(View.VISIBLE);

//                                    Log.v("Respnse getResults",datas.toString());
                               /* if (!(plan == null)) {

                                        for (int sw = 0; sw < plan.length(); sw++) {

                                                json_data = plan.getJSONObject(sw);
                                            if(!(json_data == null)) {
                                                String imgloc = json_data.optString("imgLoc");
                                                if (imgloc.equals("HOME")) {
                                                    agid = json_data.optString("id");
                                                    new DownloadImg().execute("");
                                                } else {
                                                    Picasso.with(getApplicationContext()).load(R.drawable.adfbn).into(iv);
                                                    prgbar.setVisibility(View.GONE);

                                                }
                                            }

                                        }

                                } else {
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "There was an error processing your request",
                                            Toast.LENGTH_LONG).show();
                                    Picasso.with(getApplicationContext()).load(R.drawable.adfbn).into(iv);

                                    prgbar.setVisibility(View.GONE);
                                }*/
                            } else {

                                Toast.makeText(
                                        getApplicationContext(),
                                        responsemessage,
                                        Toast.LENGTH_LONG).show();


                            }

                        } else {

                            Toast.makeText(
                                    getApplicationContext(),
                                    "There was an error on your request",
                                    Toast.LENGTH_LONG).show();


                        }
                    }
                   /* if (respcode.equals("00")) {
                        if (Utility.isNotNull(respcode) && Utility.isNotNull(responsemessage)) {
                            Log.v("Response Message", responsemessage);

                            if (respcode.equals("00")) {

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

                    }*/
                } catch (JSONException e) {
                    Utility.errornexttoken();
                    SecurityLayer.Log("encryptionJSONException", e.toString());
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), getApplicationContext().getText(R.string.conn_error), Toast.LENGTH_LONG).show();
                    // SecurityLayer.Log(e.toString());

                } catch (Exception e) {
                    Utility.errornexttoken();
                    SecurityLayer.Log("encryptionJSONException", e.toString());
                    // SecurityLayer.Log(e.toString());
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Log error here since request failed
                Log.v("Throwable error",t.toString());
                Utility.errornexttoken();
                Toast.makeText(

                        getApplicationContext(),
                        "There was a error processing your request",
                        Toast.LENGTH_LONG).show();
                //   pDialog.dismiss();
                prgbar.setVisibility(View.GONE);

            }
        });


    }
/*public void getAgentIDs(){
 //   pDialog.show();



    ApiInterface apiService =
            ApiClient.getClient().create(ApiInterface.class);
    String usid = Utility.gettUtilUserId(getApplicationContext());
    String agentid = Utility.gettUtilAgentId(getApplicationContext());
    String mobnoo = Utility.gettUtilMobno(getApplicationContext());
    Call<GetAgentId> call = apiService.GetAgId("1", usid, agentid, "0000");
    call.enqueue(new Callback<GetAgentId>() {
        @Override
        public void onResponse(Call<GetAgentId> call, Response<GetAgentId> response) {
            String responsemessage = response.body().getMessage();

            Log.v("Response Message", responsemessage);
            plan.clear();
            plan = response.body().getData();
//                                    Log.v("Respnse getResults",datas.toString());
            if (!(plan == null)) {
if(!(plan.get(0) == null)) {
    for(int sw = 0;sw < plan.size();sw++) {
        if (!(plan.get(sw) == null)) {
            String imgloc = plan.get(sw).getimgLoc();
            if (imgloc.equals("HOME")) {
                agid = plan.get(sw).getId();
                new DownloadImg().execute("");
            }
        }else{
            Picasso.with(getApplicationContext()).load(R.drawable.adfbn).into(iv);
            prgbar.setVisibility(View.GONE);

        }
    }
}else{
    Picasso.with(getApplicationContext()).load(R.drawable.adfbn).into(iv);
    prgbar.setVisibility(View.GONE);
}
            }
            else{
                Toast.makeText(
                        getApplicationContext(),
                        "There was an error processing your request",
                        Toast.LENGTH_LONG).show();
                prgbar.setVisibility(View.GONE);
            }
          //  pDialog.dismiss();

        }

        @Override
        public void onFailure(Call<GetAgentId> call, Throwable t) {
            // Log error here since request failed
            Log.v("Throwable error",t.toString());
            Toast.makeText(
                    getApplicationContext(),
                    "There was a error processing your request",
                    Toast.LENGTH_LONG).show();
         //   pDialog.dismiss();
           prgbar.setVisibility(View.GONE);
        }
    });

}*/

    class DownloadImg extends AsyncTask<String, String, String> {
        Bitmap bmp=null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // prgDialog.show();
        }

        // Download Music File from Internet
        @Override
        protected String doInBackground(String... f_url) {


            try{
             //   http://localhost:9399/agencyapi/app/adverts/pic.action/1/CEVA/PAND00000000019493818389/2
                String usid = Utility.gettUtilUserId(getApplicationContext());
                String agentid = Utility.gettUtilAgentId(getApplicationContext());
                String mobnoo = Utility.gettUtilMobno(getApplicationContext());
              //  http://localhost:9399/agencyapi/app/adverts/pic.action/1/CEVA/PAND00000000019493818389/2
                String url = ApplicationConstants.UNENC_URL+"adverts/pic.action/1/"+usid+"/"+agentid+"9493818389/"+agid;
                bmp = downloadBitmap(url);

                Log.v("Download Pic Url",url);
            }catch(Exception e){
                Log.e("ERROR While Downloadin", e.getLocalizedMessage());
                Toast.makeText(getApplicationContext(), "Error While Downloading File", Toast.LENGTH_LONG);
            }
            return "34";
        }


        private Bitmap downloadBitmap(String url) {
            HttpURLConnection urlConnection = null;
            try {
                Log.i("thumb", url);
                URL uri = new URL(url);
                urlConnection = (HttpURLConnection) uri.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setUseCaches(false);
                urlConnection.connect();

           /* int statusCode = urlConnection.getResponseCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }*/

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                }
            } catch (Exception e) {
                urlConnection.disconnect();
                Log.w("thumb dnwld", "Error downloading image from " + url);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }





        @Override
        protected void onPostExecute(String file_url) {
            //  prgDialog.dismiss();

            if(bmp != null)
            {
                iv.setImageBitmap(bmp);
            }
            else
            {
                iv.setImageBitmap(null);
            }
            prgbar.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.adidimg) {

        }

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
        String mobnoo = Utility.gettUtilMobno(getApplicationContext());
        String params = "1/"+usid+"/"+agentid+"/"+mobnoo;
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

}



