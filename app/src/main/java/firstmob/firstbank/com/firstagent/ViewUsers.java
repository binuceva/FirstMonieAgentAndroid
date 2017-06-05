package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.content.Context;
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

import com.afollestad.materialdialogs.MaterialDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.software.shell.fab.ActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapter.NewBenList;
import adapter.adapter.UsersAdapt;
import adapter.adapter.UsersList;


public class ViewUsers extends Fragment implements View.OnClickListener{



    ArrayList<String> phoneContactList = new ArrayList<String>();
    List<NewBenList> planetsList = new ArrayList<NewBenList>();
    List<UsersList> userlist = new ArrayList<UsersList>();
    UsersAdapt aAdpt;
    ListView lv;
    ScrollView scv;
    private TextView emptyView;
    ProgressDialog prgDialog,prgDialog2;
    TextView acno;
String glbstorid;
    SessionManagement session;
    LinearLayout l1,l2,l3;
    public ViewUsers() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.viewusers, container, false);

        lv = (ListView) rootView.findViewById(R.id.lv);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            glbstorid = bundle.getString("storeid");

        }

        session = new SessionManagement(getActivity());
        emptyView = (TextView) rootView.findViewById(R.id.empty_view);


        prgDialog = new ProgressDialog(getActivity());
        prgDialog.setMessage("Adding Beneficiary....");
        // Set Cancelable as False

        prgDialog.setCancelable(false);

        prgDialog2 = new ProgressDialog(getActivity());
        prgDialog2.setMessage("Loading Users....");
        // Set Cancelable as False

        planetsList.clear();
       checkInternetConnection2();
//setStore();


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
           /*     Bundle b  = new Bundle();
                String benname = planetsList.get(position).getBenName();
                String benmb = planetsList.get(position).getBenmob();
                String bennid = planetsList.get(position).getBenid();
                String bentype = planetsList.get(position).getBenType();
                String bacn = planetsList.get(position).getAcc();
                String bank = planetsList.get(position).getBank();
                String bran = planetsList.get(position).getBranch();
                b.putString("benname",benname);
                b.putString("benmb",benmb);
                b.putString("beid",bennid);SUCCES
                b.putString("btype",bentype);
                b.putString("bacn",bacn);
                b.putString("bank",bank);
                b.putString("bran",bran);
                Fragment  fragment = new EditBen();

                fragment.setArguments(b);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //  String tag = Integer.toString(title);
                fragmentTransaction.replace(R.id.container_body, fragment,"Edit Beneficiary");
                fragmentTransaction.addToBackStack("Edit Beneficiary");
                ((MainActivity)getActivity())
                        .setActionBarTitle("Edit Beneficiary");
                fragmentTransaction.commit();*/

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
   userlist.add(new UsersList("Mike Wachira", "Supervisor","BIF-8623(Store ID)"));
    userlist.add(new UsersList("Ian Test","Supervisor" ,"BIF-8601(Store ID)"));
    userlist.add(new UsersList("Ian Test", "Supervisor", "BIF-8601(Store ID)"));
    aAdpt = new UsersAdapt(userlist, getActivity());

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

        String url = ApplicationConstants.NET_URL+ApplicationConstants.MERCH_ENPOINT+"loadusers/"+ URLEncoder.encode(glbstorid);
        Log.v("Load Users URL",url);
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
                    Log.v("response..:", response);
                    JSONObject obj = new JSONObject(response);
                    if(obj.optString("message").equals("SUCCESS")){
                        userlist.clear();
                        JSONArray js = obj.getJSONArray("userdet");
                        Log.v("JSON Aray", js.toString());
                        if(js.length() > 0){


                            JSONObject json_data = null;
                            for (int i = 0; i < js.length(); i++) {
                                json_data = js.getJSONObject(i);
                                //String accid = json_data.getString("benacid");


                                String userid = json_data.optString("userid");

                                String usname = json_data.optString("username");
                                String user_status = json_data.optString("user_status");
                                String usertype = json_data.optString("usertype");

                                userlist.add(new UsersList(usname, usertype,glbstorid));

                                aAdpt = new UsersAdapt(userlist, getActivity());


                            }

                            if(userlist.size() >0) {
                                lv.setAdapter(aAdpt);
                                emptyView.setVisibility(View.GONE);
                            }else{
                              /*  SimpleDialogFragment.createBuilder(getActivity(), getFragmentManager())
                                        .setTitle("Mini Statement").setMessage
                                        ("Dear customer,please note you do not have any transactions to display")
                                        .setNegativeButtonText("Close")
                                        .show();*/

                                new MaterialDialog.Builder(getActivity())
                                        .title("Store Users")
                                        .content("Dear customer,please note you do not have any users assigned to this store")
                                        .negativeText("Close")
                                        .callback(new MaterialDialog.ButtonCallback() {
                                            @Override
                                            public void onPositive(MaterialDialog dialog) {
                                            }
                                            @Override
                                            public void onNegative(MaterialDialog dialog) {

                                            }
                                        }).show();
                                emptyView.setVisibility(View.VISIBLE);
                            }

                        }
                    }
                    // Else display error message
                    else{
                        new MaterialDialog.Builder(getActivity())
                                .title("Store Users")
                                .content("Dear customer,please note you do not have any users assigned to this store")
                                .negativeText("Close")
                                .callback(new MaterialDialog.ButtonCallback() {
                                    @Override
                                    public void onPositive(MaterialDialog dialog) {
                                    }
                                    @Override
                                    public void onNegative(MaterialDialog dialog) {

                                    }
                                }).show();
                        emptyView.setVisibility(View.VISIBLE);
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
    Bundle b  = new Bundle();
    b.putString("storeid",glbstorid);
    Fragment  fragment = new AddUser();
    fragment.setArguments(b);
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    //  String tag = Integer.toString(title);
    fragmentTransaction.replace(R.id.container_body, fragment,"Add User");
    fragmentTransaction.addToBackStack("Add User");
    fragmentTransaction.commit();
    ((MainActivity)getActivity())
            .setActionBarTitle("Add User");
}
    }
}
