package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapter.InboxList;
import adapter.InboxListAdapter;


public class InboxActivity extends ActionBarActivity  {
    Button signup;
    Spinner sp1;
    ArrayList<String> phoneContactList = new ArrayList<String>();
    List<InboxList> planetsList = new ArrayList<InboxList>();
    private TextView emptyView;
    InboxListAdapter aAdpt;
    ListView lv;
    Button ok;
    ProgressDialog prgDialog,prgDialog2;
    TextView acno;
    EditText accno,mobno,fnam;
    SessionManagement session;
    private Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inbox);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Inbox");
        setSupportActionBar(mToolbar);
        lv = (ListView)findViewById(R.id.lv);
        emptyView = (TextView) findViewById(R.id.empty_view);
        prgDialog2 = new ProgressDialog(this);
        prgDialog2.setMessage("Loading Inbox....");
        // Set Cancelable as False
        session = new SessionManagement(this);
        prgDialog2.setCancelable(false);
        planetsList.clear();
        //  checkInternetConnection2();
        //Setinbox();
    }


    public void StartChartAct(int i){
    }

    @Override
    public void onResume(){
        super.onResume();
    }

/*public void Setinbox(){
    String inbx = "Welcome to our new Agent App";
    String msgd = "23rd June 2016 10:00";
    planetsList.add(new InboxList(inbx, msgd));
    planetsList.add(new InboxList("Failed Cash Deposit transaction of 12,120 Naira", "22nd June 2016 11:00"));
    aAdpt = new InboxListAdapter(planetsList, this);



    lv.setAdapter(aAdpt);

    CheckAdaptEmpty();
}*/
/*

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

        invokeWS(newurl);
    }
    public void invokeWS( String tplate){
        // Show Progress Dialog
        prgDialog2.show();

        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(35000);
        HashMap<String, String> nurl = session.getNetURL();
        String newurl = nurl.get(SessionManagement.NETWORK_URL);
        // String url = newurl+"/natmobileapi/rest/customer/ministat?acctno="+acc+"";

      //  String url =newurl+"/natmobileapi/rest/customer/getInbox?mobno="+tplate+"";
        String url = ApplicationConstants.NET_URL+ApplicationConstants.AND_ENPOINT+"getInbox/"+tplate;

        Log.d("NewUrl",url);
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
                prgDialog2.hide();
                try {
                    // JSON Object
                    Log.v("response..:", response);
                    JSONObject obj = new JSONObject(response);
                    if (obj.optString("message").equals("SUCCESS")) {
                        JSONArray js = obj.getJSONArray("inbox");
                        Log.v("JSON Aray", js.toString());
                        if (js.length() > 0) {


                            JSONObject json_data = null;
                            for (int i = 0; i < js.length(); i++) {
                                json_data = js.getJSONObject(i);


                                String msgd = json_data.optString("msgdate");
                                String inbx = json_data.optString("inbox");

                                planetsList.add(new InboxList(inbx, msgd));

                                aAdpt = new InboxListAdapter(planetsList, getActivity());


                            }
                            lv.setAdapter(aAdpt);

                        } else {
                            Toast.makeText(getActivity(), " Your Inbox is Empty", Toast.LENGTH_LONG).show();

                        }
                    }
                    // Else display error message
                    else {
                        SetDialog(obj.optString("message"), "Inbox");
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getActivity(), " The device has not successfully connected to server. Please check your internet settings", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                CheckAdaptEmpty();
            }

            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {

                // Hide Progress Dialog
                prgDialog2.hide();
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
        CheckAdaptEmpty();
    }

    public void SetDialog(String msg,String title){
        new MaterialDialog.Builder(getActivity())
                .title(title)
                .content(msg)

                .negativeText("Close")
                .show();
    }
*/

    public void CheckAdaptEmpty(){
        if(planetsList.size() >0) {
           // lv.setAdapter(aAdpt);
            emptyView.setVisibility(View.GONE);
        }else{
            emptyView.setVisibility(View.VISIBLE);
        }
    }
}
