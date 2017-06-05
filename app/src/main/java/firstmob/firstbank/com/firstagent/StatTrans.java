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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import adapter.MinListAdapter;
import adapter.MinstList;


public class StatTrans extends Fragment  {
    Button signup;

    List<String> acc  = new ArrayList<String>();
    static Hashtable<String, String> data1;
    public static ArrayList<String> instidacc = new ArrayList<String>();
    String paramdata = "";
    ArrayAdapter<String> mArrayAdapter;
    ArrayList<String> phoneContactList = new ArrayList<String>();
    List<MinstList> planetsList = new ArrayList<MinstList>();
    private TextView emptyView;
    MinListAdapter aAdpt;
    ListView lv;
    Button ok;
    String selacc;
    ProgressDialog prgDialog2,prgDialog;
    TextView acno;
    EditText accno,mobno,fnam;
    SessionManagement session;
    TextView descn;
    String serv,pfrom,pto,serv2;
    public StatTrans() {
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
        View rootView = inflater.inflate(R.layout.stattrans, container, false);
        descn = (TextView) rootView.findViewById(R.id.bname);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String type = bundle.getString("serv");
            serv2 = type;
            pfrom = bundle.getString("pfrom");
            pto = bundle.getString("pto");
            descn.setText(type+" transactions between "+pfrom+" to "+pto);
            if (type.equals("Airtime TopUp Self")) {
                type = "ARTISL";
            }
            if (type.equals("Airtime TopUp Other")) {
                type = "ARTIOT";
            }
            if (type.equals("Mobile Money")) {
                type = "MPESAB2C";
            }

            if (type.equals("Funds Transfer")) {
                type = "FTWIBK";
            }
            if (type.equals("School Fees")) {
                type = "SCHFEE";
            }
            if (type.equals("Billers")) {
                type = "CELBILL";
            }
            if (type.equals("Zuku")) {
                type = "CELLZUK";
            }
            if (type.equals("Airtel Money")) {
                type = "AIRTELC2B";
            }
            serv = type;

        }
        lv = (ListView) rootView.findViewById(R.id.lv);

        prgDialog2 = new ProgressDialog(getActivity());
        prgDialog2.setMessage("Loading Transactions....");
        // Set Cancelable as False
        session = new SessionManagement(getActivity());
        HashMap<String, String> nurl = session.getSelAcc();
        selacc = nurl.get(SessionManagement.SEL_ACCOUNT);
        prgDialog2.setCancelable(false);
        emptyView = (TextView) rootView.findViewById(R.id.empty_view);
        checkConnStats();


        prgDialog = new ProgressDialog(getActivity());
        prgDialog.setMessage("Loading Your Accounts....");

       return rootView;
    }

    public void StartChartAct(int i){
    }

    @Override
    public void onResume(){
        super.onResume();
    }



    private boolean checkConnStats() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            //	new SendTask().execute();
            HashMap<String, String> nurl = session.getMobNo();
            String newurl = nurl.get(SessionManagement.KEY_MOBILE);
            invokeFX(newurl);
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

    public void invokeFX( String mno){
        // Show Progress Dialog



        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(35000);
        HashMap<String, String> nurl = session.getNetURL();
        String newurl = nurl.get(SessionManagement.NETWORK_URL);
        // String url = newurl+"/natmobileapi/rest/customer/ministat?acctno="+acc+"";

        //String url =newurl+"/natmobileapi/rest/customer/servstats?mobno="+mno+"&pfrom="+pfrom+"&pto="+pto+"&serv="+serv+"";
        String url = ApplicationConstants.NET_URL+ApplicationConstants.AND_ENPOINT+"servstats/"+mno+"/"+pfrom+"/"+pto+"/"+serv;

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

                try {
                    // JSON Object
                    Log.v("response..:", response);
                    JSONObject obj = new JSONObject(response);
                    if (obj.optString("message").equals("SUCCESS")) {
                        JSONArray js = obj.optJSONArray("servstats");
                        Log.v("JSON Aray", js.toString());
                        if (js.length() > 0) {


                            JSONObject json_data = null;
                            for (int i = 0; i < js.length(); i++) {
                                json_data = js.getJSONObject(i);
                                //String accid = json_data.getString("benacid");


                                String amo = json_data.optString("amount");
                                String timest = json_data.optString("timest");


                                planetsList.add(new MinstList(serv2, timest, amo));
                                aAdpt = new MinListAdapter(planetsList, getActivity());
                            }
                            lv.setAdapter(aAdpt);

                        }


                    }
                    // Else display error message
                    else {
                        Toast.makeText(getActivity(), " The device has not successfully connected to server. Please check your internet settings", Toast.LENGTH_LONG).show();

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


}
