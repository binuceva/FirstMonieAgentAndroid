package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.widget.Toast.makeText;


public class Sign_In extends Fragment implements View.OnClickListener{
    Button signin;
    EditText us;
    EditText pin;
    boolean chkcard = false;
    SessionManagement session;
    ProgressDialog prgDialog;
    static Hashtable<String, String> data1;
    String paramdata = "";


    public Sign_In() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.sign_in, container, false);
        session = new SessionManagement(getActivity());
        signin = (Button) rootView.findViewById(R.id.sign_in);
        us = (EditText) rootView.findViewById(R.id.user_id);
       pin = (EditText)rootView.findViewById(R.id.pin);
        //pin = (EditText) rootView.findViewById(R.id.pin);
        signin.setOnClickListener(this);
        prgDialog = new ProgressDialog(getActivity());
        prgDialog.setMessage("Logging In....");

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
        if (v.getId() == R.id.sign_in) {
            checkInternetConnection();


        }
    }

    private boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            //	new SendTask().execute();
            registerUser();
            //	RegTest();
            return true;
        } else {

            Toast.makeText(
                    getActivity(),
                    "No Internet Connection. Please check your internet setttings",
                    Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public void registerUser() {


     String usid = us.getText().toString().trim();
         String pinn =pin.getText().toString().trim();
        final String strPssword = "AMH6GrLRGK2SBtNiziAdl+Z9HK+98qChhGuCaLZ795M";
        AES encrypter = null;
        if (Utility.isNotNull(pinn)) {
            try {
                encrypter = new AES(strPssword);


                pinn = encrypter.encrypt(pinn.getBytes("UTF-8")).trim();
                // mobilenum = new String(encrypted, "UTF-8");
                Log.d("Mobile Encrypted", pinn);
                String decrypted = encrypter.decrypt(pinn);
                Log.d("Decrypted String", decrypted);

                String imei = "";

                TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(getActivity().TELEPHONY_SERVICE);
                imei = telephonyManager.getDeviceId();


                WifiManager wifiManager = (WifiManager) getActivity().getSystemService(getActivity().WIFI_SERVICE);
                WifiInfo wInfo = wifiManager.getConnectionInfo();
                String mac = wInfo.getMacAddress();
                String ip = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
                if (Utility.isNotNull(ip) == false) {
                    ip = "N";
                }
                if (Utility.isNotNull(mac) == false) {
                    mac = "N";
                }

                boolean chknum = true;
                if (usid.length() == 16) {
                    chkcard = true;
                }
                if (chkcard == true) {
                    chknum = Utility.checknum(usid);
                }


                if (Utility.checknum(usid) && usid.length() < 16) {
                    usid = setMobFormat(usid);
                }

                if (Utility.isNotNull(usid)) {

                    if (Utility.isNotNull(pinn)) {
                        if (!(usid.equals("N"))) {
                            if (Utility.isNotNull(imei)) {
                                if (chknum) {
                                    final String usidd = usid;
                                   invokeWS(usidd, pinn, imei, mac, ip);
                                } else {
                                    Toast.makeText(getActivity(), "The Card Number provided is not valid ", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Please ensure there is a valid IMEI Number for this device ", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Please enter a valid mobile number ", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), "The Pin field is empty. Please fill in appropiately", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "The User Id/Mobile Number field is empty. Please fill in appropiately", Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(), "The Pin field is empty. Please fill in appropiately", Toast.LENGTH_LONG).show();
        }



    }




    public void invokeWS(  String usd,String pn,String imei,String mac,String ip){
        // Show Progress Dialog
        prgDialog.show();

        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(40000);
        HashMap<String, String> nurl = session.getNetURL();
        String newurl = nurl.get(SessionManagement.NETWORK_URL);
        // String url = newurl+"/natmobileapi/rest/customer/ministat?acctno="+acc+"";


        String build = Build.SERIAL;
        String cardno = "N";
        if(chkcard == true){
            cardno = usd;
            usd = "N";
        }else{
            cardno = "N";
        }
        String url =newurl+"/natmobileapi/rest/customer/userlogin?loginid="+usd+"&pin="+pn+"&imei="+imei+"&cardno="+cardno+"&cardexp=N&macadress="+mac+"&ipadress="+ip+"&serial="+build;
        Log.v("url",url);
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


                    JSONArray js = obj.getJSONArray("customer");
                    Log.v("JSON Aray", js.toString());
                    if(js.length() > 0) {
                        JSONObject json_data = null;
                        for (int i = 0; i < js.length(); i++) {
                            json_data = js.getJSONObject(i);
                            String rsmesaage = json_data.optString("responsemessage");
                            String fname = json_data.optString("fullname");
                            String mno = json_data.optString("mobilenumber");
                            String pstat = json_data.optString("pinstatus");
                            String imeic = json_data.optString("imeicount");
                            String txnflag = json_data.optString("txnpinflag");
                            String imeiflag = json_data.optString("imeiflag");
                            String custid = json_data.optString("customerid");
                            String inst = json_data.optString("institute");
                            String dispn = json_data.optString("dispname");


                            String ema = json_data.optString("email");
                            Log.v("Response Code", rsmesaage);
                            if (rsmesaage.equals("Success")) {
                                String insttyp = null;
                                if(inst.equals("INSTID1")){
                                    insttyp = "bfub";
                                }
                                if(inst.equals("INSTID2")){
                                    insttyp = "imal";
                                }
                                if (pstat.equals("1")) {


                                  //  Toast.makeText(getActivity(), "Imei Count is "+imeic, Toast.LENGTH_LONG).show();
                               session.logoutUser();
                             session.createLoginSession(fname);
                                    session.putMobNo(mno);
                                    session.putTxnFlag(txnflag);
                                    session.putInst(insttyp);
                                    Toast.makeText(getActivity(), "You have successfully logged in", Toast.LENGTH_LONG).show();
                                    getActivity().finish();
                                    startActivity(new Intent(getActivity(), MainActivity.class));





/*
if(imeiflag.equals("N")) {

        Bundle b = new Bundle();



        b.putString("cust", custid);
        b.putString("mobno", mno);
        b.putString("imeic", imeic);


        getActivity().finish();
        Intent ip = new Intent(getActivity(), RegDev_Confirm.class);
        ip.putExtras(b);
        startActivity(new Intent(ip));



}
                                    else if (imeiflag.equals("Y")){
    session.logoutUser();
                                         session.createLoginSession(fname);
                                         session.putMobNo(mno);
                                         session.putTxnFlag(txnflag);
                                         session.putInst(insttyp);
                                         session.putEmail(ema);
    if(!(dispn == null)) {
        dispn = dispn.replace("_"," ");
        session.putDisp(dispn);
    }

                                         Toast.makeText(getActivity(), "You have successfully logged in", Toast.LENGTH_LONG).show();
                                         getActivity().finish();
                                         startActivity(new Intent(getActivity(),MainActivity.class));
                                     }


*/




                                } else if (pstat.equals("2")) {
                                    Bundle b = new Bundle();


                                    b.putString("fname", fname);
                                    b.putString("mobno", mno);
                                    b.putString("txnpinflag", txnflag);
                                    b.putString("inst", insttyp);

                                    Fragment fragment = new OptForce();

                                    fragment.setArguments(b);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    //  String tag = Integer.toString(title);
                                    fragmentTransaction.replace(R.id.container_body, fragment, "Change New Pin");
                                    fragmentTransaction.addToBackStack("Change New Pin");
                                    ((SignInActivity) getActivity())
                                            .setActionBarTitle("Change New Pin");
                                    fragmentTransaction.commit();
                                }

                            }  else{
                                Toast.makeText(getActivity(), rsmesaage, Toast.LENGTH_LONG).show();

                            }
                        }
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getActivity(), " The device has not successfully connected to server. Please check your internet settings", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getActivity(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(getActivity(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(getActivity(), " The device has not successfully connected to server. Please check your internet settings", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public String setMobFormat(String mobno){
        String vb = mobno.substring(Math.max(0, mobno.length() - 9));
        Log.v("Logged Number is",vb);
        if(vb.length() == 9 && (vb.substring(0, Math.min(mobno.length(), 1)).equals("7"))){
            return "254"+vb;
        }else{
            return  "N";
        }
    }


    public void SendFT(String usd,String pn,String imei,String mac,String ip){
        try{
            System.out
                    .println("Starting the Requestor intiation................");
            prgDialog.show();

            HashMap<String, String> nurl = session.getNetURL();
            String newurl = nurl.get(SessionManagement.NETWORK_URL);
            // String url = newurl+"/natmobileapi/rest/customer/ministat?acctno="+acc+"";


            String build = Build.SERIAL;
            String cardno = "N";
            if(chkcard == true){
                cardno = usd;
                usd = "N";
            }else{
                cardno = "N";
            }
     paramdata = "loginid="+usd+"&pin="+pn+"&imei="+imei+"&cardno="+cardno+"&cardexp=N&macadress="+mac+"&ipadress="+ip+"&serial="+build+"&transactionalcode=MOBLOG";

            System.out.println("The Orginal XML [" + paramdata + "]");

            String pvtKeyFileName = "C:\\Users\\deeru\\Downloads\\pkisecurity\\pkisecurity\\src\\requestorkeys\\private.pem";
            String pubkey = "C:\\Users\\deeru\\Downloads\\pkisecurity\\pkisecurity\\src\\requestorkeys\\public.der";

            String pkiProvider = "BC";
            // String signatureAlg = "SHA1withRSA";

            String KEYGEN_ALG = "AES";
            int SYMETRIC_KEY_SIZE = 128;

            String SYMETRICKEY_ALG = "AES/ECB/PKCS5Padding";
            String CIPHER_ALG = "RSA/ECB/PKCS1Padding";
            String KEYFACTORY_ALG = "RSA";

            // bodyToEncrypt = paramdata;

            System.out.println(paramdata);

            // Step 1: Signing the Request
            // String dignsignValue1 = YPKIImplementation.signRequest(
            // pvtKeyFileName, paramdata.getBytes(), pkiProvider,
            // signatureAlg);
            // System.out.println("The Signature Value :  \n"+dignsignValue1);

            // Step 2: Generating the session key
            byte[] sessionKey = SecurityImplementation.generateSessionKey(
                    KEYGEN_ALG, pkiProvider, SYMETRIC_KEY_SIZE);
            System.out.println("The Symetric key Value :\n" + sessionKey);

            // Step 3: Encrypting the request with the session key
            String encRequest = SecurityImplementation
                    .encryptRequestWithSessionKey(sessionKey, SYMETRICKEY_ALG,
                            pkiProvider, paramdata.getBytes());
            // String encBodyData = "<Body>"+encRequest+"</Body>";

            System.out.println("Encrypted Request with session Key :\n["
                    + encRequest + "]");

            // Step 4: Encrpting the session key
            String encSessionKey = SecurityImplementation
                    .encryptSessionKey(sessionKey, pubkey, CIPHER_ALG,
                            KEYFACTORY_ALG, pkiProvider);
            System.out.println("Encrypted SessionKey: \n[" + encSessionKey
                    + "]");
            data1 = new Hashtable<String, String>();
            data1.put("param1", encRequest);
            data1.put("param2", encSessionKey);
            //makePostRequest(data1);
            new DownloadFileFromURL().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public   class DownloadFileFromURL extends AsyncTask<String, String, String> {
        StringBuffer result;
        int rpcode;
        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // dialog = ProgressDialog.show(getActivity(), "", "Retrieving Contacts...please be patient,it might take some time", true);
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {
            HttpClient httpClient = new DefaultHttpClient();
            // replace with your url
            HashMap<String, String> nurl = session.getNetURL();
            String newurl = nurl.get(SessionManagement.NETWORK_URL);
            String url = newurl + "/nbkselfreg/redirection.action";



            String line = "";
            try {


                HttpPost post = new HttpPost(url);

                // add header
                post.setHeader("User-Agent", "Mozilla/5.0");

                List<BasicNameValuePair> urlParameters = setPostParams(data1);

                post.setEntity(new UrlEncodedFormEntity(urlParameters));

                HttpResponse response = httpClient.execute(post);
                rpcode = response.getStatusLine().getStatusCode();
                System.out.println("Response Code : "
                        + rpcode);

                BufferedReader rd = new BufferedReader(new InputStreamReader(
                        response.getEntity().getContent()));

                result = new StringBuffer();

                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                if(rpcode == 200){

                }else{

                }

                System.out.println("Response from client is ::: \n" + result);

            } catch (IOException | IllegalStateException e) {
                e.printStackTrace();
            }
            return null;
        }



        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            if (rpcode == 200) {
                String response = result.toString();
                prgDialog.hide();
                try {
                    // JSON Object
                    Log.v("response..:", response);
                    JSONObject obj = new JSONObject(response);


                    JSONArray js = obj.getJSONArray("customer");
                    Log.v("JSON Aray", js.toString());
                    if(js.length() > 0) {
                        JSONObject json_data = null;
                        for (int i = 0; i < js.length(); i++) {
                            json_data = js.getJSONObject(i);
                            String rsmesaage = json_data.optString("responsemessage");
                            String fname = json_data.optString("fullname");
                            String mno = json_data.optString("mobilenumber");
                            String pstat = json_data.optString("pinstatus");
                            String imeic = json_data.optString("imeicount");
                            String txnflag = json_data.optString("txnpinflag");
                            String imeiflag = json_data.optString("imeiflag");
                            String custid = json_data.optString("customerid");
                            String inst = json_data.optString("institute");
                            String dispn = json_data.optString("dispname");


                            String ema = json_data.optString("email");
                            Log.v("Response Code", rsmesaage);
                            if (rsmesaage.equals("Success")) {
                                String insttyp = null;
                                if(inst.equals("INSTID1")){
                                    insttyp = "bfub";
                                }
                                if(inst.equals("INSTID2")){
                                    insttyp = "imal";
                                }
                                if (pstat.equals("1")) {


                                    //  Toast.makeText(getActivity(), "Imei Count is "+imeic, Toast.LENGTH_LONG).show();
                                 /*   session.logoutUser();
                                    session.createLoginSession(fname);
                                    session.putMobNo(mno);
                                    session.putTxnFlag(txnflag);
                                    session.putInst(insttyp);
                                    Toast.makeText(getActivity(), "You have successfully logged in", Toast.LENGTH_LONG).show();
                                    getActivity().finish();
                                    startActivity(new Intent(getActivity(), MainActivity.class));*/


if(imeiflag.equals("N")) {

        Bundle b = new Bundle();



        b.putString("cust", custid);
        b.putString("mobno", mno);
        b.putString("imeic", imeic);


        getActivity().finish();
        Intent ip = new Intent(getActivity(), RegDev_Confirm.class);
        ip.putExtras(b);
        startActivity(new Intent(ip));



}
                                    else if (imeiflag.equals("Y")){
    session.logoutUser();
                                         session.createLoginSession(fname);
                                         session.putMobNo(mno);
                                         session.putTxnFlag(txnflag);
                                         session.putInst(insttyp);
                                         session.putEmail(ema);
    if(!(dispn == null)) {
        dispn = dispn.replace("_"," ");
        session.putDisp(dispn);
    }

                                         Toast.makeText(getActivity(), "You have successfully logged in", Toast.LENGTH_LONG).show();
                                         getActivity().finish();
                                         startActivity(new Intent(getActivity(),MainActivity.class));
                                     }



                                } else if (pstat.equals("2")) {
                                    Bundle b = new Bundle();


                                    b.putString("fname", fname);
                                    b.putString("mobno", mno);
                                    b.putString("txnpinflag", txnflag);
                                    b.putString("inst", insttyp);

                                    Fragment fragment = new OptForce();

                                    fragment.setArguments(b);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    //  String tag = Integer.toString(title);
                                    fragmentTransaction.replace(R.id.container_body, fragment, "Change New Pin");
                                    fragmentTransaction.addToBackStack("Change New Pin");
                                    ((SignInActivity) getActivity())
                                            .setActionBarTitle("Change New Pin");
                                    fragmentTransaction.commit();
                                }

                            }  else{
                                Toast.makeText(getActivity(), rsmesaage, Toast.LENGTH_LONG).show();

                            }
                        }
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getActivity(), " The device has not successfully connected to server. Please check your internet settings", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
                catch(Exception e){
                    e.printStackTrace();
                }

            }else{
                prgDialog.hide();
                // When Http response code is '404'
                if(rpcode == 404){
                    Toast.makeText(getActivity(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(rpcode == 500){
                    Toast.makeText(getActivity(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(getActivity(), " The device has not successfully connected to server. Please check your internet settings", Toast.LENGTH_LONG).show();
                }
            }
        }

    }


    private static List<BasicNameValuePair> setPostParams(
            Map<String, String> data) {
        List<BasicNameValuePair> urlParameters = new ArrayList<BasicNameValuePair>();
        try {
            Set<Map.Entry<String, String>> entrySet = data.entrySet();

            for (Iterator<Map.Entry<String, String>> itr = entrySet.iterator(); itr
                    .hasNext();) {

                Map.Entry<String, String> entry = itr.next();

                System.out.println(entry.getKey());
                System.out.println(entry.getValue());

                urlParameters.add(new BasicNameValuePair(entry.getKey(), entry
                        .getValue()));

            }
        } catch (Exception e) {
        }

        return urlParameters;
    }
}
