package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

import adapter.NewMinListAdapter;
import model.MinistatData;
import rest.ApiClient;
import rest.ApiInterface;
import rest.ApiSecurityClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import security.SecurityLayer;


public class Minstat extends Fragment  {
    Button signup;
   // Spinner sp1;
    List<String> acc  = new ArrayList<String>();
    static Hashtable<String, String> data1;
    public static ArrayList<String> instidacc = new ArrayList<String>();
    String paramdata = "";
    ArrayAdapter<String> mArrayAdapter;
    ArrayList<String> phoneContactList = new ArrayList<String>();
    List<MinistatData> planetsList = new ArrayList<MinistatData>();
    private TextView emptyView;
   NewMinListAdapter aAdpt;
    ListView lv;
    Button ok;
    String selacc;
    ProgressDialog prgDialog2;
    TextView acno,txaco;
    EditText accno,mobno,fnam;
    SessionManagement session;
    public Minstat() {
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
        View rootView = inflater.inflate(R.layout.ministat2, container, false);
        lv = (ListView) rootView.findViewById(R.id.lv);
        txaco = (TextView) rootView.findViewById(R.id.bname);
     //   sp1 = (Spinner) rootView.findViewById(R.id.accno);
        prgDialog2 = new ProgressDialog(getActivity());
        prgDialog2.setMessage("Loading ....");
        // Set Cancelable as False
        session = new SessionManagement(getActivity());

        prgDialog2.setCancelable(false);
        emptyView = (TextView) rootView.findViewById(R.id.empty_view);

        String accnoo = Utility.getAcountno(getActivity());
        txaco.setText("Statement for Account Number -"+accnoo);

        SetMinist();

        return rootView;
    }

    public void StartChartAct(int i){
    }

    @Override
    public void onResume(){
        super.onResume();
    }

public void SetMinist(){


    prgDialog2.show();

    ApiInterface apiService =
            ApiClient.getClient().create(ApiInterface.class);
    String usid = Utility.gettUtilUserId(getActivity());
    String agentid = Utility.gettUtilAgentId(getActivity());
    String mobnoo = Utility.gettUtilMobno(getActivity());
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat format1 = new SimpleDateFormat("" +
            "yyyy-MM-dd");
    String formattednow = format1.format(cal.getTime());
//    Call<Ministat> call = apiService.getMiniStat("1",usid,agentid,"9493818389","2017-01-01",formattednow);
    String params = "1/"+usid+"/"+agentid+"/9493818389/2017-01-01/"+formattednow;
    MiniStmtt(params);
/*    call.enqueue(new Callback<Ministat>() {
        @Override
        public void onResponse(Call<Ministat>call, Response<Ministat> response) {

            planetsList = response.body().getResults();
//                                    Log.v("Respnse getResults",datas.toString());
            if(!(planetsList == null)) {
                Log.v("Tran Remark",planetsList.get(0).getTranRemark());

                aAdpt = new NewMinListAdapter(planetsList, getActivity());
                lv.setAdapter(aAdpt);
            }else{
                Toast.makeText(
                        getActivity(),
                        "There are no records to display",
                        Toast.LENGTH_LONG).show();
            }
            prgDialog2.dismiss();
        }

        @Override
        public void onFailure(Call<Ministat>call, Throwable t) {
            // Log error here since request failed
            Log.v("Throwable error",t.toString());
            Toast.makeText(
                    getActivity(),
                    "There was an error processing your request",
                    Toast.LENGTH_LONG).show();
            prgDialog2.dismiss();
        }
    });*/
   /* planetsList.add(new MinstList("Deposit to Account 01009823455","21 June 2016 06:32", "-560.00 Naira"));
    planetsList.add(new MinstList("Cash Withdrawal from 0213040404","20 June 2016 08:42", "-1200.00 Naira"));
    planetsList.add(new MinstList("Pay DSTV Account Number 01234123","20 June 2016 03:56", "-4500.00 Naira"));
    aAdpt = new MinListAdapter(planetsList, getActivity());
    lv.setAdapter(aAdpt);*/
}


    private void MiniStmtt(String params) {

        String endpoint= "core/stmt.action";


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





                    JSONArray comperf = obj.optJSONArray("data");
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




                                            String tran_date = json_data.optString("tran_date");

                                            String tran_remark = json_data.optString("tran_remark");
                                            String credit_debit = json_data.optString("credit_debit");
                                            String tran_amt = json_data.optString("tran_amt");



                                                planetsList.add(new MinistatData(tran_date,tran_remark,credit_debit,tran_amt));




                                        }
                                        if(!(getActivity() == null)) {
                                            aAdpt = new NewMinListAdapter(planetsList, getActivity());


                                            lv.setAdapter(aAdpt);
                                        }


                                    }else{
                                        Toast.makeText(
                                                getActivity(),
                                                "There are no records to display",
                                                Toast.LENGTH_LONG).show();
                                    }

                                }else{

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
                prgDialog2.dismiss();
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



                prgDialog2.dismiss();
            }
        });

    }

}
