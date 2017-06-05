package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.software.shell.fab.ActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapter.NewBenList;
import adapter.adapter.StoreAdapt;
import adapter.adapter.StoresList;


public class ViewStores extends Fragment implements View.OnClickListener{



    ArrayList<String> phoneContactList = new ArrayList<String>();
    List<NewBenList> planetsList = new ArrayList<NewBenList>();
    List<StoresList> storelist = new ArrayList<StoresList>();
    StoreAdapt aAdpt;
    ListView lv;
    ScrollView scv;

    ProgressDialog prgDialog,prgDialog2;
    TextView acno;

    SessionManagement session;
    LinearLayout l1,l2,l3;
    public ViewStores() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.viewstores, container, false);

        lv = (ListView) rootView.findViewById(R.id.lv);

        session = new SessionManagement(getActivity());



        prgDialog = new ProgressDialog(getActivity());
        prgDialog.setMessage("Adding Beneficiary....");
        // Set Cancelable as False

        prgDialog.setCancelable(false);

        prgDialog2 = new ProgressDialog(getActivity());
        prgDialog2.setMessage("Loading Stores....");
        // Set Cancelable as False


       checkInternetConnection2();



        ActionButton actionButton = (ActionButton) rootView.findViewById(R.id.action_button);
        actionButton.show();
        actionButton.setButtonColor(getResources().getColor(R.color.nbkdarkbrown));
        boolean hasImage = actionButton.hasImage();

// To set an image (either bitmap, drawable or resource id):

        actionButton.setImageResource(R.mipmap.ic_add);
        actionButton.setOnClickListener(this);
       // fab.attachToListView(lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
               Bundle b  = new Bundle();
                String storid = storelist.get(position).getStoreid();

                b.putString("storeid",storid);

                Fragment  fragment = new ViewUsers();

                fragment.setArguments(b);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //  String tag = Integer.toString(title);
                fragmentTransaction.replace(R.id.container_body, fragment,storid);
                fragmentTransaction.addToBackStack(storid);
                ((MainActivity)getActivity())
                        .setActionBarTitle(storid);
                fragmentTransaction.commit();

            }
        });


        return rootView;
    }



    public void StartChartAct(int i){


    }
    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }

public void setStore(){
   storelist.add(new StoresList("Digitel Communications","BIF-86O1(Store Id)"));
    storelist.add(new StoresList("Wabera Street Store", "BIF-8623(Store ID)"));
    aAdpt = new StoreAdapt(storelist, getActivity());

lv.setAdapter(aAdpt);
}

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    private boolean checkInternetConnection2() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            //	new SendTask().execute();
invokeWS();
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

    public void invokeWS( ){
        // Show Progress Dialog
        prgDialog2.show();

        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(35000);
        HashMap<String, String> nurl = session.getNetURL();
        String newurl = nurl.get(SessionManagement.NETWORK_URL);
        // String url = newurl+"/natmobileapi/rest/customer/ministat?acctno="+acc+"";

        String url = ApplicationConstants.NET_URL+ApplicationConstants.MERCH_ENPOINT+"loadstores/BIGBA0000000001";
Log.v("Load Stores URL",url);

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
                prgDialog2.hide();
                try {
                    // JSON Object
                    storelist.clear();
                    Log.v("response..:", response);
                    JSONObject obj = new JSONObject(response);
                    if(obj.optString("message").equals("SUCCESS")){
                        JSONArray js = obj.getJSONArray("storedet");
                        Log.v("JSON Aray", js.toString());
                        if(js.length() > 0){


                            JSONObject json_data = null;
                            for (int i = 0; i < js.length(); i++) {
                                json_data = js.getJSONObject(i);
                                //String accid = json_data.getString("benacid");



                                String stid = json_data.optString("storeid");
                                String stname = json_data.optString("storename");



                                      //aAdpt = new NewBenAdapt(planetsList, getActivity());


                                storelist.add(new StoresList(stname, stid));
                                aAdpt = new StoreAdapt(storelist, getActivity());


                            }
                            lv.setAdapter(aAdpt);

                        }
                    }
                    // Else display error message
                    else{
                        Toast.makeText(getActivity(), " The device has not successfully connected to server. Please check your internet settings", Toast.LENGTH_LONG).show();

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
                prgDialog2.hide();
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
    @Override
    public void onClick(View v) {
if(v.getId() == R.id.action_button){
  /*  Fragment  fragment = new AddStore();
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    //  String tag = Integer.toString(title);
    fragmentTransaction.replace(R.id.container_body, fragment,"Add Store");
    fragmentTransaction.addToBackStack("Add Store");
    fragmentTransaction.commit();
    ((MainActivity)getActivity())
            .setActionBarTitle("Add Store");*/

    Intent i = new Intent(getActivity(), FormActivity.class);

    // Staring Login Activity
    startActivity(i);
}
    }







}
