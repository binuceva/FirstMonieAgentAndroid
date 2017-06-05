package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
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

import java.security.KeyStore;
import java.util.HashMap;


public class OptForce extends Fragment implements View.OnClickListener{
    Button signin;
    EditText oldp,newp,newconfp;
    SessionManagement session;
    ProgressDialog prgDialog;
String fulln,mobnum,txnfl,inst;

    public OptForce() {
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
        View rootView = inflater.inflate(R.layout.otpforce, container, false);
        session = new SessionManagement(getActivity());
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            fulln = bundle.getString("fname");
            mobnum = bundle.getString("mobno");
            txnfl = bundle.getString("txnpinflag");
            inst = bundle.getString("inst");

        }
        signin = (Button) rootView.findViewById(R.id.sign_up);
        oldp = (EditText) rootView.findViewById(R.id.phonec);
        newp = (EditText) rootView.findViewById(R.id.phone);
        newconfp = (EditText) rootView.findViewById(R.id.phonexa);
        signin.setOnClickListener(this);
        prgDialog = new ProgressDialog(getActivity());
        prgDialog.setMessage("Change Pin Request....");

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
        if (v.getId() == R.id.sign_up) {
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


        String oldpi = oldp.getText().toString().trim();
        String newpi =newp.getText().toString().trim();
        final   String confnewpi = newconfp.getText().toString().trim();


            if( Utility.isNotNull(oldpi)) {
                if (Utility.isNotNull(newpi)) {

                    if (Utility.isNotNull(confnewpi)) {
                        if(confnewpi.equals(newpi)) {
                            final String strPssword = "AeH6GrLRGK2SBtNiziAdl+Z9HK+98qChhGuCaLZ7O5M";
                            AES encrypter = null;
                            try {
                                encrypter = new AES(strPssword);


                                oldpi = encrypter.encrypt(oldpi.getBytes("UTF-8")).trim();
                                newpi = encrypter.encrypt(newpi.getBytes("UTF-8")).trim();
                                // mobilenum = new String(encrypted, "UTF-8");



                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            invokeWS(oldpi, newpi,mobnum);
                        } else {
                            Toast.makeText(getActivity(), "Please enter a confirmation pin similar to your new pin ", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "The Confirm New Pin field is empty.Please fill in appropiately", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "The New Pin field is empty. Please fill in appropiately", Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(getActivity(), "The Old Pin field is empty. Please fill in appropiately", Toast.LENGTH_LONG).show();
            }





    }




    public void invokeWS( final String oldp, final String newp,final String num){
        // Show Progress Dialog
        prgDialog.show();

        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(40000);
        HashMap<String, String> nurl = session.getNetURL();
        String newurl = nurl.get(SessionManagement.NETWORK_URL);
        // String url = newurl+"/natmobileapi/rest/customer/ministat?acctno="+acc+"";

       /* String url =newurl+"/natmobileapi/rest/customer/otforcepin?customerid=Wed&mobno="+num+"&oldpin="+oldp+"&newpin="+newp+"";
       */   String url = ApplicationConstants.NET_URL+ApplicationConstants.AND_ENPOINT+"otforcepin/Wed/"+num+"/"+oldp+"/"+newp;

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



                            String rsmesaage = obj.optString("responsemessage");
                    String rpcode = obj.optString("responsecode");

                            Log.v("Response Code", rsmesaage);
                            if (rpcode.equals("00")) {

                              /*  session.createLoginSession(fulln);
                                session.putMobNo(mobnum);
                                session.putTxnFlag(txnfl);
                                session.putInst(inst);*/

                            } else {
                                Toast.makeText(getActivity(), rsmesaage, Toast.LENGTH_LONG).show();



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
}
