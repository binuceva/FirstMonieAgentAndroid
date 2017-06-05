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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapter.Dashboard;
import adapter.DashboardAdapter;


public class EditBen extends Fragment implements View.OnClickListener {
    Spinner sp2,sp3;

    GridView gridView;
    List<Dashboard> planetsList = new ArrayList<Dashboard>();
    DashboardAdapter aAdpt;
    ProgressDialog prgDialog3;
    CheckBox chk1,chk2,chk3;
    Button ok,edben;
    List<String> accbank  = new ArrayList<String>();
    List<String> accbankno  = new ArrayList<String>();
    List<String> accbranch  = new ArrayList<String>();
    ProgressDialog prgDialog,prgDialog7,prgDialog8;
    ArrayAdapter<String> mArrayAdapter,mAdapt2,mBankAdapt,mBranchAdapt;
    TextView bena,bencon;
    SessionManagement session;
    String bna,bmo,bid,btype,bac,bank,bran;
    EditText accno,fnam,edothacc;
    LinearLayout l1,l2,l3;
    AutoCompleteTextView textView=null;


    public EditBen() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.editben, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
           bna = bundle.getString("benname");
            bmo = bundle.getString("benmb");
            bid = bundle.getString("beid");
            btype = bundle.getString("btype");
            Log.v("Ben Type",btype);
            bac = bundle.getString("bacn");
            bank = bundle.getString("bank");
            bran = bundle.getString("bran");
        }
        accno = (EditText) rootView.findViewById(R.id.phone9);


        edothacc = (EditText) rootView.findViewById(R.id.edothacc);
        fnam = (EditText) rootView.findViewById(R.id.phone8);
        l1 = (LinearLayout) rootView.findViewById(R.id.gh);
        l2 = (LinearLayout) rootView.findViewById(R.id.rlwithin);
        l3 = (LinearLayout) rootView.findViewById(R.id.rlphone);
        ok = (Button) rootView.findViewById(R.id.button4);
        sp2 = (Spinner) rootView.findViewById(R.id.spin2);
        sp3 = (Spinner) rootView.findViewById(R.id.spin3);
        bena = (TextView) rootView.findViewById(R.id.phonec);
        bencon = (TextView) rootView.findViewById(R.id.phone);
        session = new SessionManagement(getActivity());
        prgDialog3 = new ProgressDialog(getActivity());
        prgDialog3.setMessage("Deleting Beneficiary....");
        prgDialog = new ProgressDialog(getActivity());
        prgDialog.setMessage("Modifying Beneficiary....");

        // Set Cancelable as False

        prgDialog.setCancelable(false);

        prgDialog7 = new ProgressDialog(getActivity());
        prgDialog7.setMessage("Loading Banks....");
        // Set Cancelable as False

        prgDialog7.setCancelable(false);

        prgDialog8 = new ProgressDialog(getActivity());
        prgDialog8.setMessage("Loading Branches....");
        // Set Cancelable as False

        prgDialog8.setCancelable(false);
        edben = (Button) rootView.findViewById(R.id.button2);
        edben.setOnClickListener(this);
        textView = (AutoCompleteTextView) rootView.findViewById(R.id.toNumber);
        ok.setOnClickListener(this);
        /*ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                getActivity(), R.array.dombank, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(adapter2);*/

     /*   ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
                getActivity(), R.array.branch, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp3.setAdapter(adapter3);*/
/*bena.setText(bna);
        bencon.setText(bmo);*/
        SetD();
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String ij = accbankno.get(i);
                chkConnLoadBranches(ij);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button4) {
            new MaterialDialog.Builder(getActivity())
                    .title("Delete Beneficiary")
                    .content("Are you sure you want to delete this Beneficiary? \n ")
                    .positiveText("YES")
                    .negativeText("NO")

                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog)
                        {
                            GenPinConn();
                        }

                        @Override
                        public void onNegative(MaterialDialog dialog) {

                        }
                    })
                    .show();

        }
        if(view.getId()==  R.id.button2){
            checkInternetConnection();
        }
    }

    public boolean GenPinConn() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            //	new SendTask().execute();

if(Utility.isNotNull(bid)) {
    invokeNewAl(bid);
}else{
    Toast.makeText(
            getActivity(),
            "Please select a valid beneficiary",
            Toast.LENGTH_LONG).show();
}
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

    public void invokeNewAl(final String tplate){
        // Show Progress Dialog
        prgDialog3.show();

        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(35000);
        HashMap<String, String> nurl = session.getNetURL();
        String newurl = nurl.get(SessionManagement.NETWORK_URL);
        // String url = newurl+"/natmobileapi/rest/customer/ministat?acctno="+acc+"";

       // String url =newurl+"/natmobileapi/rest/customer/delben?benid="+tplate+"";
        String url = ApplicationConstants.NET_URL+ApplicationConstants.AND_ENPOINT+"delben/"+tplate;

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
                prgDialog3.hide();
                try {
                    // JSON Object
                    Log.v("response..:", response);
                    JSONObject obj = new JSONObject(response);
                    if(obj.optString("message").equals("SUCCESS")){


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
                prgDialog3.hide();
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

public void SetD(){
    if(btype.equals("Within Bank")){
        fnam.setText(bna);
        accno.setText(bac);
        l2.setVisibility(View.VISIBLE);
        l1.setVisibility(View.GONE);
        l3.setVisibility(View.GONE);
    }
    else  if(btype.equals("Mobile Money")){
        fnam.setText(bna);
    textView.setText(bmo);
        l3.setVisibility(View.VISIBLE);
        l1.setVisibility(View.GONE);
        l2.setVisibility(View.GONE);
    }
    else  if(btype.equals("Airtime Topup")){
        fnam.setText(bna);
        textView.setText(bmo);
        l3.setVisibility(View.VISIBLE);
        l1.setVisibility(View.GONE);
        l2.setVisibility(View.GONE);
    }
    else  if(btype.equals("Other Bank")){
        chkConnLoadBanks();

        fnam.setText(bna);
        edothacc.setText(bac);
        l1.setVisibility(View.VISIBLE);
        l3.setVisibility(View.GONE);
        l2.setVisibility(View.GONE);
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
    public void registerUser(){

        String mobnum = setMobFormat(textView.getText().toString());
        Log.v("Mobile Number Got",mobnum);
        String aacn = accno.getText().toString();
        String fname = fnam.getText().toString();
        fname = fname.replace(" ","_");


        String ban = "N";

        String branch = "N";

        String edoth = edothacc.getText().toString();

        String fbank = "N";
        String fbranch = "N";
        String fmob = "N";
        String fwtbacc = "N";
        String fmbmon = "N";
        String fotbacc = "N";
        if(btype.equals("Within Bank")){
            btype = "WTB";
            fwtbacc = aacn;

        }
        else if(btype.equals("Mobile Money")){
            btype = "MBM";
            fmbmon = mobnum;
        }
        else if(btype.equals("Other Bank")){
            btype = "OTB";

            ban = sp2.getSelectedItem().toString();
            ban = ban.replace(" ", "_");
            branch = sp3.getSelectedItem().toString();
            branch = branch.replace(" ", "_");
            fbank = ban;
            fbranch = branch;
            fwtbacc = edoth;

        }
        HashMap<String, String> nurl = session.getMobNo();
        String newurl = nurl.get(SessionManagement.KEY_MOBILE);
        String numbers = newurl.substring(Math.max(0, newurl.length() - 9));
        boolean bchked = false;
        boolean bchno = true;
        boolean bchklen = true;
        if(mobnum.equals("N") && (btype.equals("MBM"))) {
            bchno = false;
        }
        if( Utility.isNotNull(aacn) && (btype.equals("WTB"))) {
            bchked = true;
        }
        if (Utility.isNotNull(mobnum) && (btype.equals("MBM"))) {
            bchked = true;
        }
        if( Utility.isNotNull(edoth) && (btype.equals("OTB"))) {
            bchked = true;
        }

        if( Utility.isNotNull(mobnum) && (btype.equals("ATM"))) {
            bchked = true;
        }

        if( (!(aacn.length() == 14)) && (btype.equals("WTB"))) {
            bchklen = false;
        }


        if( Utility.isNotNull(fname)){
            if( Utility.isValidWordHyphen(fname)){


                boolean bfinchk = ( (bchked == true) );
                Log.v("Boolean Check ",Boolean.toString(bchked));
                if( bchked) {
                    if( bchno) {
                        if( bchklen) {
        invokeWS(bid, fmbmon, fwtbacc, fname,btype,fbank,fbranch,numbers);
                }
                        else{
                            Toast.makeText(getActivity(), "The Account Number field length should  be  14 characters. Please fill in appropiately", Toast.LENGTH_LONG).show();

                        }}

                    else {
                        Toast.makeText(getActivity(), "Please enter a valid mobile number", Toast.LENGTH_LONG).show();


                    }}
                    else {

                    String actxt = "";
                    if(btype.equals("MBM") || btype.equals("ATM") ){
                        actxt = "Mobile Number";
                    }else{
                        actxt = "Account Number";
                    }
                    Toast.makeText(getActivity(), "The "+actxt+" field is empty. Please fill in appropiately", Toast.LENGTH_LONG).show();

                }

            }else{
                Toast.makeText(getActivity(), "Please enter a valid name", Toast.LENGTH_LONG).show();

            }  }else{

            Toast.makeText(getActivity(), "The Name field is empty. Please fill in appropiately", Toast.LENGTH_LONG).show();

        }
    }
    public void invokeWS(String bidd, String msisdn,String acn,String fname,String btype,String bank,String brnch,String numb){
        // Show Progress Dialog
        prgDialog.show();

        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(35000);
        HashMap<String, String> nurl = session.getNetURL();
        String newurl = nurl.get(SessionManagement.NETWORK_URL);
        // String url = newurl+"/natmobileapi/rest/customer/ministat?acctno="+acc+"";

        //String url =newurl+"/natmobileapi/rest/customer/editben?benid="+bidd+"&bennam="+fname+"&benacno="+acn+"&benmb="+msisdn+"&bentype="+btype+"&bank="+bank+"&branch="+brnch;
        String url = ApplicationConstants.NET_URL+ApplicationConstants.AND_ENPOINT+"editben/"+bidd+"/"+fname+"/"+acn+"/"+msisdn+"/"+btype+"/"+bank+"/"+brnch;

        Log.v(" Add Ben URL",url);
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


                    if(obj.optString("message").equals("SUCCESS")){

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
            return vb;
        }else{
            return  "N";
        }
    }


    private boolean chkConnLoadBanks() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            //	new SendTask().execute();
            LoadBanks();
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
    public void LoadBanks(){

        invokeBanks();
    }
    public void invokeBanks( ){
        // Show Progress Dialog
        prgDialog7.show();

        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(35000);
        HashMap<String, String> nurl = session.getNetURL();
        String newurl = nurl.get(SessionManagement.NETWORK_URL);
        // String url = newurl+"/natmobileapi/rest/customer/ministat?acctno="+acc+"";

        String url =newurl+"/natmobileapi/rest/customer/loadbanks";
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
                prgDialog7.hide();
                accbank.clear();
                accbankno.clear();
                try {
                    // JSON Object
                    Log.v("response..:", response);
                    JSONObject obj = new JSONObject(response);
                    if (obj.optString("message").equals("SUCCESS")) {

                        JSONArray js = obj.getJSONArray("bankdet");
                        Log.v("JSON Aray", js.toString());
                        if (js.length() > 0) {


                            JSONObject json_data = null;
                            for (int i = 0; i < js.length(); i++) {
                                json_data = js.getJSONObject(i);
                                String accid = json_data.optString("bankname");
                                String bno = json_data.optString("bankcode");
                                if (accid != null || !(accid.equals(" "))) {

                                    accbank.add(accid);
                                    accbankno.add(bno);
                                    mBankAdapt = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, accbank);
                                    mBankAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                }
                            }


                            sp2.setAdapter(mBankAdapt);


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

                // Hide Progress Dialog
                prgDialog7.hide();
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


    private boolean chkConnLoadBranches(String bcode) {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            //	new SendTask().execute();
            // ();
            LoadBranche(bcode);
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
    public void LoadBranche(String bcode){

        invokeBran(bcode);
    }
    public void invokeBran( String bcode){
        // Show Progress Dialog
        prgDialog8.show();

        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(35000);
        HashMap<String, String> nurl = session.getNetURL();
        String newurl = nurl.get(SessionManagement.NETWORK_URL);
        // String url = newurl+"/natmobileapi/rest/customer/ministat?acctno="+acc+"";

        String url =newurl+"/natmobileapi/rest/customer/loadbrancheseft?bcode="+bcode;
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
                prgDialog8.hide();
                accbranch.clear();
                try {
                    // JSON Object
                    Log.v("response..:", response);
                    JSONObject obj = new JSONObject(response);
                    if (obj.optString("message").equals("SUCCESS")) {

                        JSONArray js = obj.getJSONArray("branchdet");
                        Log.v("JSON Aray", js.toString());
                        if (js.length() > 0) {


                            JSONObject json_data = null;
                            for (int i = 0; i < js.length(); i++) {
                                json_data = js.getJSONObject(i);
                                String accid = json_data.optString("branchname");
                                if (accid != null || !(accid.equals(" "))) {

                                    accbranch.add(accid);
                                    mBranchAdapt = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, accbranch);
                                    mBranchAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                }
                            }


                            sp3.setAdapter(mBranchAdapt);


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

                // Hide Progress Dialog
                prgDialog8.hide();
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
