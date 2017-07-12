package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adapter.InboxListAdapter;
import model.GetCommPerfData;
import rest.ApiClient;
import rest.ApiInterface;
import rest.ApiSecurityClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import security.SecurityLayer;


public class Inbox extends Fragment {
    Button signup;
    Spinner sp1;
    ArrayList<String> phoneContactList = new ArrayList<String>();
    List<GetCommPerfData> planetsList = new ArrayList<GetCommPerfData>();
   // private TextView emptyView;
    InboxListAdapter aAdpt;
    ListView lv;
    Button ok;
    ProgressDialog prgDialog,prgDialog2;
    TextView acno;
    EditText accno,mobno,fnam;
    SessionManagement session;
    List<GetCommPerfData> temp  = new ArrayList<GetCommPerfData>();
    public Inbox() {
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
        View rootView = inflater.inflate(R.layout.inbox, container, false);
        lv = (ListView) rootView.findViewById(R.id.lv);
     //   emptyView = (TextView) rootView.findViewById(R.id.empty_view);
        prgDialog2 = new ProgressDialog(getActivity());
        prgDialog2.setMessage("Loading Inbox....");
        // Set Cancelable as False
        session = new SessionManagement(getActivity());
        prgDialog2.setCancelable(false);
        planetsList.clear();
      //  checkInternetConnection2();
        SetMinist();
     /*   lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Bundle b  = new Bundle();
                String txnref = planetsList.get(position).getrefNumber();

                b.putString("txnref",txnref);
                Log.v("Txn Ref",txnref);
               String title = "Txn Status";
                Fragment  fragment = new TxnStatus();

                fragment.setArguments(b);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //  String tag = Integer.toString(title);
                fragmentTransaction.replace(R.id.container_body, fragment,title);
                fragmentTransaction.addToBackStack(title);
                ((FMobActivity) getActivity())
                        .setActionBarTitle(title);
                fragmentTransaction.commit();

            }
        });*/
        return rootView;
    }

    public void StartChartAct(int i){
    }

    @Override
    public void onResume(){
        super.onResume();
    }

/*
public void Setinbox(){
    String inbx = "Welcome to our new Agent App";
    String msgd = "23rd June 2016 10:00";
    planetsList.add(new InboxList(inbx, msgd));
    planetsList.add(new InboxList("Failed Cash Deposit transaction of 12,120 Naira", "22nd June 2016 11:00"));
    aAdpt = new InboxListAdapter(planetsList, getActivity());



    lv.setAdapter(aAdpt);

    CheckAdaptEmpty();
}


*/

    public void SetDialog(String msg,String title){
        new MaterialDialog.Builder(getActivity())
                .title(title)
                .content(msg)

                .negativeText("Close")
                .show();
    }

    public void SetMinist(){


        prgDialog2.show();
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
        int day = now.get(Calendar.DAY_OF_MONTH);


        String frmdymonth = Integer.toString(day);
        if (day < 10) {
            frmdymonth = "0" + frmdymonth;
        }
        String frmyear = Integer.toString(year);
        frmyear = frmyear.substring(2, 4);
        SimpleDateFormat format1 = new SimpleDateFormat("" +
                "MMMM dd yyyy");
        String fdate = frmdymonth + "-" + (month) + "-" + frmyear;
        Calendar noww = Calendar.getInstance();
        int yearr = now.get(Calendar.YEAR);
        int monthh = now.get(Calendar.MONTH); // Note: zero based!

        noww.set(yearr,monthh,01);
        String formattedstartdate = "01" + "-" + (month) + "-" + frmyear;

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String usid = Utility.gettUtilUserId(getActivity());
        String agentid = Utility.gettUtilAgentId(getActivity());
        String mobnoo = Utility.gettUtilMobno(getActivity());
        String params = "1/"+usid+"/"+agentid+"/0000/TXNRPT/"+formattedstartdate+"/"+fdate;
        Inbox(params);
        /*Call<GetPerf> call = apiService.getPerfData("1",usid,agentid,"0000","TXNRPT","09-09-16",fdate);
        call.enqueue(new Callback<GetPerf>() {
            @Override
            public void onResponse(Call<GetPerf>call, Response<GetPerf> response) {

                GetPerfData data = response.body().getResults();
//                                    Log.v("Respnse getResults",datas.toString());
                temp = data.getResults();
                if(!(temp == null)) {
                    for(int i =0; i<temp.size(); i++){
                        String status = temp.get(i).getStatus();
                        String amon = temp.get(i).getAmount();
                        Double dbagcmsn = Double.parseDouble(amon);
                        if(((status.equals("SUCCESS"))) && (dbagcmsn > 0)){
                            Log.v("Amount tt",amon);
                            planetsList.add(temp.get(i));

                        }
                    }

if(temp.size() == 0){
    Toast.makeText(
                getActivity(),
                "There are no inbox records to display",
                Toast.LENGTH_LONG).show();

}
                    if(!(getActivity() == null)) {
                        aAdpt = new InboxListAdapter(planetsList, getActivity());


                        lv.setAdapter(aAdpt);
                    }
                }else{
                    Toast.makeText(
                            getActivity(),
                            "There are no records to display",
                            Toast.LENGTH_LONG).show();
                }
                if(!(getActivity() == null)) {
                    prgDialog2.dismiss();
                }
            }

            @Override
            public void onFailure(Call<GetPerf>call, Throwable t) {
                // Log error here since request failed
                Log.v("Throwable error",t.toString());
                Toast.makeText(
                        getActivity(),
                        "There was an error processing your request",
                        Toast.LENGTH_LONG).show();
                prgDialog2.dismiss();
            }
        });*/

    }


    private void Inbox(String params) {

        String endpoint= "report/genrpt.action";


        String usid = Utility.gettUtilUserId(getActivity());
        String agentid = Utility.gettUtilAgentId(getActivity());




        String urlparams = "";
        try {
            urlparams = SecurityLayer.genURLCBC(params,endpoint,getActivity());
            //Log.d("cbcurl",url);
            Log.v("RefURL",urlparams);
            SecurityLayer.Log("refurl", urlparams);
            SecurityLayer.Log("params", params);
        } catch (Exception e) {
            Log.e("encryptionerror",e.toString());
        }





        ApiInterface apiService =
                ApiSecurityClient.getClient().create(ApiInterface.class);


        Call<String> call = apiService.setGenericRequestRaw(urlparams);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    // JSON Object


                    SecurityLayer.Log("Cable TV Resp", response.body());
                    Log.v("response..:", response.body());
                    JSONObject obj = new JSONObject(response.body());
                    //obj = Utility.onresp(obj,getActivity());
                    obj = SecurityLayer.decryptTransaction(obj, getActivity());
                    SecurityLayer.Log("decrypted_response", obj.toString());




                    JSONObject comdatas = obj.optJSONObject("data");
                    JSONArray comperf = comdatas.optJSONArray("transaction");
                    //session.setString(SecurityLayer.KEY_APP_ID,appid);

                    if(!(response.body() == null)) {
                        String respcode = obj.optString("responseCode");
                        String responsemessage = obj.optString("message");

                        Log.v("Response Message", responsemessage);

                        if (Utility.isNotNull(respcode) && Utility.isNotNull(respcode)) {
                            if (!(Utility.checkUserLocked(respcode))) {
                                Log.v("Response Message", responsemessage);

                                if (respcode.equals("00")){
                                    Log.v("JSON Aray", comperf.toString());
                                    if(comperf.length() > 0){


                                        JSONObject json_data = null;
                                        for (int i = 0; i < comperf.length(); i++) {
                                            json_data = comperf.getJSONObject(i);
                                            //String accid = json_data.getString("benacid");


                                            String txnCode = json_data.optString("txnCode");
                                            double agentCmsn = json_data.optDouble("agentCmsn");
                                            String txndateTime = json_data.optString("txndateTime");
                                            String amount = json_data.optString("amount");
                                            String status = json_data.optString("status");
                                            String toAcNum = json_data.optString("toAcNum");
                                            String refNumber = json_data.optString("refNumber");
double dbam = Double.parseDouble(amount);
                                            if( (dbam > 0)) {
                                                planetsList.add(new GetCommPerfData(txnCode, txndateTime, agentCmsn, status, amount, toAcNum, refNumber));

                                            }


                                        }
                                        if(!(getActivity() == null)) {
                                            aAdpt = new InboxListAdapter(planetsList, getActivity());


                                            lv.setAdapter(aAdpt);
                                        }


                                    }

                                }else{
                                    Toast.makeText(
                                            getActivity(),
                                            "" + responsemessage,
                                            Toast.LENGTH_LONG).show();
                                }
                            } else {
                                getActivity().finish();
                                startActivity(new Intent(getActivity(), SignInActivity.class));
                                Toast.makeText(
                                        getActivity(),
                                        "You have been locked out of the app.Please call customer care for further details",
                                        Toast.LENGTH_LONG).show();

                            }
                        } else {

                            Toast.makeText(
                                    getActivity(),
                                    "There was an error on your request",
                                    Toast.LENGTH_LONG).show();


                        }
                    } else {

                        Toast.makeText(
                                getActivity(),
                                "There was an error on your request",
                                Toast.LENGTH_LONG).show();


                    }
                    // prgDialog2.dismiss();




                } catch (JSONException e) {
                    SecurityLayer.Log("encryptionJSONException", e.toString());
                    // TODO Auto-generated catch block
                    Toast.makeText(getActivity(), getActivity().getText(R.string.conn_error), Toast.LENGTH_LONG).show();
                    // SecurityLayer.Log(e.toString());

                } catch (Exception e) {
                    SecurityLayer.Log("encryptionJSONException", e.toString());
                    // SecurityLayer.Log(e.toString());
                }
                if(!(getActivity() == null)) {
                    prgDialog2.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Log error here since request failed
                // Log error here since request failed
                Log.v("throwable error",t.toString());


                Toast.makeText(
                        getActivity(),
                        "There was an error on your request",
                        Toast.LENGTH_LONG).show();


                if(!(getActivity() == null)) {
                    prgDialog2.dismiss();
                }
            }
        });

    }

}
