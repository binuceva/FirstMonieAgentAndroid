package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.security.KeyStore;
import java.util.HashMap;


public class AddUser extends Fragment implements View.OnClickListener {
    ImageView imageView1;
    Spinner sp2,sp3;
    Button btn;
    ProgressDialog prgDialog;
    SessionManagement session;
    EditText edfname,edlname,edempno,edusid,edema,edmobno;
    String glbstorid;

    public AddUser() {
        // Required empty public constructor
    }
  /*  private static Fragment newInstance(Context context) {
        LayoutOne f = new LayoutOne();

        return f;
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.adduser, null);
        sp2 = (Spinner) root.findViewById(R.id.spin1);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            glbstorid = bundle.getString("storeid");

        }
        btn = (Button) root.findViewById(R.id.button1);

        btn.setOnClickListener(this);
        edfname = (EditText) root.findViewById(R.id.fname);
        edlname = (EditText) root.findViewById(R.id.lname);
        edempno = (EditText) root.findViewById(R.id.empno);
        edusid = (EditText) root.findViewById(R.id.user_id);
        edema = (EditText) root.findViewById(R.id.email);
        edmobno = (EditText) root.findViewById(R.id.mobno);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.ugroup, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(adapter);



        session = new SessionManagement(getActivity());
        prgDialog = new ProgressDialog(getActivity());
        prgDialog.setMessage("Adding Store....");
        return root;
    }



    public void StartChartAct(int i){


    }
    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }


    private boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            //	new SendTask().execute();
        validUser();
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

    public void validUser(){
String txtmobno = edmobno.getText().toString();
        String txtfname = edfname.getText().toString();
 String txtlname = edlname.getText().toString();
        String txtema = edema.getText().toString();
        String txtusid = edusid.getText().toString();
        String txtempno = edempno.getText().toString();
        String usst = sp2.getSelectedItem().toString();
        if(Utility.isNotNull(txtmobno)){
            if(Utility.isNotNull(txtfname)){
                if(Utility.isNotNull(txtlname)){
                    if(Utility.isNotNull(txtema)){
                        if(Utility.isNotNull(txtusid)){
                            if(Utility.isNotNull(txtempno)){
                                invokeWS(  txtfname,txtlname,txtema,txtusid,txtempno,txtmobno,"BIGBA0000000001",glbstorid,"MA");
                            }
                        }
                    }
                }
            }
        }
    }

    public void invokeWS( String fname,String lname,String email,String userid,String empno,String mobno, String merchid,String storid,String ustype){
        // Show Progress Dialog
        prgDialog.show();

        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(35000);
        HashMap<String, String> nurl = session.getNetURL();
        String newurl = nurl.get(SessionManagement.NETWORK_URL);
        // String url = newurl+"/natmobileapi/rest/customer/ministat?acctno="+acc+"";
        HashMap<String, String> nombo = session.getMobNo();
        String newu = nombo.get(SessionManagement.KEY_MOBILE);
        String numb = newu.substring(Math.max(0, newu.length() - 9));
      //  http://localhost:8080/natmobileapi/rest/merchant/adduser/Makid/Ian/Chep/Usid/0909/0713333/ian@ema.com/pwd/EncryptPwd/Merchid/ST01/MA
        String url = ApplicationConstants.NET_URL+ApplicationConstants.MERCH_ENPOINT+"adduser/Makid/"+URLEncoder.encode(fname)+"/"+URLEncoder.encode(lname)+"/"+URLEncoder.encode(userid)+"/"+URLEncoder.encode(empno)+"/"+URLEncoder.encode(mobno)+"/"+ URLEncoder.encode(email)+"/Pwd/EncryptPwd/"+URLEncoder.encode(merchid)+"/"+URLEncoder.encode(storid)+"/"+URLEncoder.encode(ustype);

        Log.v(" Add User URL", url);
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
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


                    if (obj.optString("message").equals("SUCCESS")) {
                        Toast.makeText(getActivity(), "You have successfully added this user", Toast.LENGTH_LONG).show();
                        getActivity().finish();
                        Intent i = new Intent(getActivity(), MainActivity.class);

                        // Staring Login Activity
                        startActivity(i);
                    } else {
                        Toast.makeText(getActivity(), obj.optString("message"), Toast.LENGTH_LONG).show();
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

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button1){
            checkInternetConnection();
        }
    }
}
