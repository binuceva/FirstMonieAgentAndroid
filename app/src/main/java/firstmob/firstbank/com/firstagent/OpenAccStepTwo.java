package firstmob.firstbank.com.firstagent;

import android.app.Activity;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog;
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


public class OpenAccStepTwo extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {


    Button sigin;
    TextView gendisp;
    SessionManagement session;
    EditText idno,mobno,fnam,lnam,yob;
    List<String> planetsList = new ArrayList<String>();
    List<String> prodid = new ArrayList<String>();
    ArrayAdapter<String> mArrayAdapter;
    Spinner sp1,sp2,sp5,sp3,sp4;
    Button btn4;
    static Hashtable<String, String> data1;
    String paramdata = "";
    ProgressDialog prgDialog,prgDialog2,prgDialog7;
    TextView tnc;
    List<String> mobopname  = new ArrayList<String>();
    List<String> mobopid  = new ArrayList<String>();
    DatePickerDialog datePickerDialog;
    TextView tvdate;
    public static final String DATEPICKER_TAG = "datepicker";

    public OpenAccStepTwo() {
        // Required empty public constructor
    }
  /*  private static Fragment newInstance(Context context) {
        LayoutOne f = new LayoutOne();

        return f;
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.opennatstep2, null);
        sigin = (Button) root.findViewById(R.id.button1);
        sigin.setOnClickListener(this);


        session = new SessionManagement(getActivity());

        sp2 = (Spinner)root.findViewById(R.id.spin2);
        sp3 = (Spinner)root.findViewById(R.id.spin3);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
               getActivity(), R.array.mar_status, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(adapter2);


        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
                getActivity(), R.array.states, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp3.setAdapter(adapter3);




        return root;
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
        if (view.getId() == R.id.button1) {

            Fragment  fragment = new OpenAccStepThree();


            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //  String tag = Integer.toString(title);
            fragmentTransaction.replace(R.id.container_body, fragment,"Step Three");
            fragmentTransaction.addToBackStack("Step Three");
            ((FMobActivity)getActivity())
                    .setActionBarTitle("Step Three");
            fragmentTransaction.commit();
        }




        if(view.getId()==  R.id.button4){
            datePickerDialog.show(getFragmentManager(), DATEPICKER_TAG);
        }
        if(view.getId() == R.id.tdispedit){

          /*  Fragment fragment =  new NatWebProd();;
String title = "Bank Info";
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //  String tag = Integer.toString(title);
            fragmentTransaction.replace(R.id.container_body, fragment, title);
            fragmentTransaction.addToBackStack(title);
            fragmentTransaction.commit();
            Activity activity123 = getActivity();
            if(activity123 instanceof MainActivity) {
                ((MainActivity)getActivity())
                        .setActionBarTitle(title);
            }
            if(activity123 instanceof SignInActivity) {
                ((SignInActivity) getActivity())
                        .setActionBarTitle(title);
            }*/
        }

        if(view.getId() == R.id.textView3){

            Fragment fragment =  new NatWebProd();;
            String title = "Bank Info";
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //  String tag = Integer.toString(title);
            fragmentTransaction.replace(R.id.container_body, fragment, title);
            fragmentTransaction.addToBackStack(title);
            fragmentTransaction.commit();
            Activity activity123 = getActivity();
            if(activity123 instanceof MainActivity) {
                ((MainActivity)getActivity())
                        .setActionBarTitle(title);
            }

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
     final   String acty = "01261";
      //  final String prodn = sp1.getSelectedItem().toString();

  String mobnumm = mobno.getText().toString().trim();
      final  String idn = idno.getText().toString().trim();
      final  String fname = fnam.getText().toString().trim();
      final  String lname = lnam.getText().toString().trim();
      final  String yb  = yob.getText().toString().trim();
        final String nt = sp2.getSelectedItem().toString();
   final String    mobnum = setMobFormat(mobnumm);
        Log.v("Mobile Number Formatted",mobnum);
        if( Utility.isNotNull(mobnum)){
            if( Utility.isNotNull(idn)){
                if( Utility.isNotNull(fname)){

                    if( Utility.isNotNull(lname)){
                        if( Utility.isNotNull(yb)){
                            if(Utility.checknum(mobnum) == true) {
                                if(Utility.checknum(yb) == true) {
                                    if(yb.length() == 4) {
                                        if(!(mobnum.equals("N"))) {

                                                if( Utility.isValidWord(fname)){
                                                    if( Utility.isValidWord(lname)){
                                                        if( Utility.isValidWord(idn)){
                                            new MaterialDialog.Builder(getActivity())
                                                    .title("Open Account")
                                                    .content("Are you sure you want to Open an Account with these particulars? \n First Name: "+fname+"  \n  Last Name "+ lname+" \n Mobile Number  "+mobnum+" \n Mobile Operator "+nt+" \n ID Number: "+idn+" \n Year Of Birth  "+yb+"  ")
                                                    .positiveText("YES")
                                                    .negativeText("NO")

                                                    .callback(new MaterialDialog.ButtonCallback() {
                                                        @Override
                                                        public void onPositive(MaterialDialog dialog) {
                                                            invokeWS(acty, mobnum, idn, fname, lname, yb);
                                                        }

                                                        @Override
                                                        public void onNegative(MaterialDialog dialog) {

                                                        }
                                                    })
                                                    .show();

                                                        }else{
                                                            Toast.makeText(getActivity(), "Please enter a valid Id Number/Passport", Toast.LENGTH_LONG).show();

                                                        }
                                                    }else{
                                                        Toast.makeText(getActivity(), "Please enter a valid Last Name", Toast.LENGTH_LONG).show();

                                                    }
                                                }else{
                                                    Toast.makeText(getActivity(), "Please enter a valid First Name", Toast.LENGTH_LONG).show();

                                                }


                                    }else{
                                        Toast.makeText(getActivity(), "Please enter a valid mobile number", Toast.LENGTH_LONG).show();

                                    }
                                }else{
                                    Toast.makeText(getActivity(), "The Year Of Birth field should only contain numeric characters. Please fill in appropiately", Toast.LENGTH_LONG).show();
                                }}else{
                                Toast.makeText(getActivity(), "The Mobile Number field should only contain numeric characters. Please fill in appropiately", Toast.LENGTH_LONG).show();
                            }}else{
                            Toast.makeText(getActivity(), "The Year Of Birth field is empty. Please fill in appropiately", Toast.LENGTH_LONG).show();

                        }}else{
                        Toast.makeText(getActivity(), "The Last Name  field is empty. Please fill in appropiately", Toast.LENGTH_LONG).show();

                    }}else{
                    Toast.makeText(getActivity(), "The First Name field is empty. Please fill in appropiately", Toast.LENGTH_LONG).show();

                }}else{
                Toast.makeText(getActivity(), "The ID Number field is empty. Please fill in appropiately", Toast.LENGTH_LONG).show();
                }}else{
                Toast.makeText(getActivity(), "The ID No/Passport  field is empty. Please fill in appropiately", Toast.LENGTH_LONG).show();


            }}else{
            Toast.makeText(getActivity(), "The Mobile Number field is empty. Please fill in appropiately", Toast.LENGTH_LONG).show();

        }
    }
    public void invokeWS( String acctype,String msisdn,String id,String fname,String lname,String yearob){
        // Show Progress Dialog
        prgDialog.show();

        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(35000);
        HashMap<String, String> nurl = session.getNetURL();
        String newurl = nurl.get(SessionManagement.NETWORK_URL);
        client.setURLEncodingEnabled(true);

        // String url = newurl+"/natmobileapi/rest/customer/ministat?acctno="+acc+"";
        HashMap<String, String> stuse = session.getDisp();
        String username = stuse.get(SessionManagement.KEY_DISP);
        String url =   ApplicationConstants.NET_URL+ApplicationConstants.AND_ENPOINT+"agencyopenAccount/1/01261/"+ msisdn+"/1/"+id+"/"+fname+"/"+lname+"/"+yearob+"/ANDROID/"+username;

       Log.d("Open Acc URL",url);
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


                            String rpcode = obj.optString("responsecode");
                            String rsmesaage = obj.optString("responsemessage");
                            String fname = obj.optString("fullname");
                            String mno = obj.optString("mobilenumber");
                            Log.v("Response Code", rsmesaage);
                            if (rpcode.equals("00")) {

                            } else {


                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    SetDialog(" The device has not successfully connected to server. Please check your internet settings","Check Settings");
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
                    SetDialog(" The device has not successfully connected to server. Please check your internet settings","Check Settings");
                }
            }
        });
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

        String tplate = "718754578";
        invokeWS(tplate);
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

        String url =newurl+"/natmobileapi/rest/customer/getproducts";
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
                        planetsList.clear();
                        JSONArray js = obj.getJSONArray("proddetails");
                        Log.v("JSON Aray", js.toString());
                        if (js.length() > 0) {


                            JSONObject json_data = null;
                            for (int i = 0; i < js.length(); i++) {
                                json_data = js.getJSONObject(i);
                                //String accid = json_data.getString("benacid");


                                String desc = json_data.optString("desc");
                                String prd = json_data.optString("prodid");

                                planetsList.add(desc);
                                prodid.add(prd);
                                mArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, planetsList);
                                mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            }


                            sp1.setAdapter(mArrayAdapter);

                        }
                    }
                    // Else display error message
                    else {
                        SetDialog(" The device has not successfully connected to server. Please check your internet settings", "Check Settings");

                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    SetDialog(" The device has not successfully connected to server. Please check your internet settings", "Check Settings");
                    e.printStackTrace();

                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                    SetDialog(" The device has not successfully connected to server. Please check your internet settings", "Check Settings");
                }
            }
        });
    }
    public void ClearOpenAcc(){
    //   sp1.setSelection(0);

        mobno.setText(" ");
        idno.setText(" ");
        fnam.setText(" ");
        lnam.setText(" ");
     //   yob.setText(" ");
    }


    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        String finalmon = null;

        int nwm = month + 1;
        finalmon = Integer.toString(nwm);
        if (nwm < 10) {
            finalmon = "0" + nwm;
        }

        String tdate = day+"/"+finalmon + "/" +year ;


        tvdate.setText(tdate);
    }




    public void SetDialog(String msg,String title){
        new MaterialDialog.Builder(getActivity())
                .title(title)
                .content(msg)

                .negativeText("Close")
                .show();
    }

    public String setMobFormat(String mobno){
        String vb = mobno.substring(Math.max(0, mobno.length() - 9));
        Log.v("Logged Number is", vb);
        if(vb.length() == 9 && (vb.substring(0, Math.min(mobno.length(), 1)).equals("7"))){
            return "254"+vb;
        }else{
            return  "N";
        }
    }


}