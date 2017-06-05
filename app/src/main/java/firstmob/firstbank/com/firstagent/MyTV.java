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
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyStore;
import java.util.HashMap;


public class MyTV extends Fragment implements View.OnClickListener {
ImageView imageView1;
    EditText amon, edacc,pno,plastn,pmobno,pidno;
    Button btnsub;
    SessionManagement session;
    ProgressDialog prgDialog;

    String depositid;
    public MyTV() {
        // Required empty public constructor
    }
  /*  private static Fragment newInstance(Context context) {
        LayoutOne f = new LayoutOne();

        return f;
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.mytv, null);
        session = new SessionManagement(getActivity());
        amon = (EditText) root.findViewById(R.id.phone);
        edacc = (EditText) root.findViewById(R.id.acc);
        prgDialog = new ProgressDialog(getActivity());
        prgDialog.setMessage("Loading Request....");
        pno = (EditText) root.findViewById(R.id.scodepnam);
        plastn = (EditText) root.findViewById(R.id.lnam);
        pmobno = (EditText) root.findViewById(R.id.mobnn);
        pidno = (EditText) root.findViewById(R.id.edidno);
        btnsub = (Button) root.findViewById(R.id.button2);
      btnsub.setOnClickListener(this);
        return root;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button2) {

         //   checkInternetConnection2();

        }
    }

    private boolean checkInternetConnection2() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            //	new SendTask().execute();
            registerUser2();
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
    public void registerUser2(){
        HashMap<String, String> nurl = session.getMobNo();
        String newurl = nurl.get(SessionManagement.KEY_MOBILE);
        String numbers = newurl.substring(Math.max(0, newurl.length() - 9));

        HashMap<String, String> instit = session.getInst();
        String finalinst  = instit.get(SessionManagement.KEY_INST);


        final   String accon = edacc.getText().toString().trim();
        invokeGetAcc(numbers, accon);
    }
    public void invokeGetAcc( String tplate, String accontnumber){
        // Show Progress Dialog
        prgDialog.show();

        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(35000);
        HashMap<String, String> nurl = session.getNetURL();
        String newurl = nurl.get(SessionManagement.NETWORK_URL);

        HashMap<String, String> stmerch = session.getMerchid();
        String merchid = stmerch.get(SessionManagement.KEY_MERCHID);
        // String url = newurl+"/natmobileapi/rest/customer/ministat?acctno="+acc+"";

        //  String url =newurl+"/natmobileapi/rest/customer/balinquiry?mobno="+tplate+"&acctype="+finalinst;
        HashMap<String, String> stuse = session.getDisp();
        String username = stuse.get(SessionManagement.KEY_DISP);
        String url = ApplicationConstants.NET_URL+ApplicationConstants.AND_ENPOINT+"cashdepositverify/"+merchid+"/"+tplate+"/1/"+accontnumber+"/"+username;

        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            MySSLSocketFactory  sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            client.setSSLSocketFactory(sf);
        }
        catch (Exception e) {
        }
        client.post(url, new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                // Hide Progress Dialog
                prgDialog.hide();
                try {
                    // JSON Object
                    Log.v("response..:", response);
                    JSONObject obj = new JSONObject(response);
                    String mess = obj.optString("responsemessage");
                    depositid = obj.optString("referenceid");
                    String responsecode = obj.optString("responsecode");
                    if(responsecode.equals("00")) {
                        checkInternetConnectionPost();
                    }
                    else{
                        SetDialog(mess,"School Fees");
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getActivity(), " The device has not successfully connected to server. Please check your internet settings", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {

                // Hide Progress Dialog
                prgDialog.hide();
                // When Http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(getActivity(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getActivity(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getActivity(), " The device has not successfully connected to server. Please check your internet settings", Toast.LENGTH_LONG).show();
                }
            }
        });
    }







    private boolean checkInternetConnectionPost() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            //	new SendTask().execute();
           DepoPost();
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
    public void DepoPost(){
        HashMap<String, String> nurl = session.getMobNo();
        String newurl = nurl.get(SessionManagement.KEY_MOBILE);
        String numbers = newurl.substring(Math.max(0, newurl.length() - 9));

        HashMap<String, String> instit = session.getInst();
        String finalinst  = instit.get(SessionManagement.KEY_INST);
        final   String firstno = pno.getText().toString().trim();
        final   String lastn = plastn.getText().toString().trim();
        final   String pmobnoo = pmobno.getText().toString().trim();
        final   String idd = pidno.getText().toString().trim();

        final   String accon = amon.getText().toString().trim();
        invokeGetDepoPost(numbers,accon,firstno,lastn,pmobnoo,idd);
    }
    public void invokeGetDepoPost( String tplate, String accontnumber,String firstno,String lastn,String pmobnoo,String idd){
        // Show Progress Dialog
        prgDialog.show();

        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(35000);
        HashMap<String, String> nurl = session.getNetURL();
        String newurl = nurl.get(SessionManagement.NETWORK_URL);

        HashMap<String, String> stmerch = session.getMerchid();
        String merchid = stmerch.get(SessionManagement.KEY_MERCHID);
        // String url = newurl+"/natmobileapi/rest/customer/ministat?acctno="+acc+"";

        //  String url =newurl+"/natmobileapi/rest/customer/balinquiry?mobno="+tplate+"&acctype="+finalinst;
        HashMap<String, String> stuse = session.getDisp();
        String username = stuse.get(SessionManagement.KEY_DISP);
        String url = ApplicationConstants.NET_URL+ApplicationConstants.AND_ENPOINT+"cashdepositpost/"+merchid+"/"+tplate+"/1/"+accontnumber+"/"+depositid+"/"+username+"/"+firstno+"/"+lastn+"/"+pmobnoo+"/"+idd;

        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            MySSLSocketFactory  sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            client.setSSLSocketFactory(sf);
        }
        catch (Exception e) {
        }
        client.post(url, new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                // Hide Progress Dialog
                prgDialog.hide();
                try {
                    // JSON Object
                    Log.v("response..:", response);
                    JSONObject obj = new JSONObject(response);
                    String mess = obj.optString("responsemessage");
                    SetDialog(mess,"Cash Deposit");

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getActivity(), " The device has not successfully connected to server. Please check your internet settings", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {

                // Hide Progress Dialog
                prgDialog.hide();
                // When Http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(getActivity(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getActivity(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getActivity(), " The device has not successfully connected to server. Please check your internet settings", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void SetDialog(String msg,String title){
        new MaterialDialog.Builder(getActivity())
                .title(title)
                .content(msg)

                .negativeText("Close")
                .show();
    }
}
