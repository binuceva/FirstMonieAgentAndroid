package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
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

import adapter.NewCommListAdapter;
import model.GetCommPerfData;
import rest.ApiClient;
import rest.ApiInterface;
import rest.ApiSecurityClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import security.SecurityLayer;


public class CommReport extends Fragment  {
    Button signup;
   // Spinner sp1;
    List<String> acc  = new ArrayList<String>();
    List<GetCommPerfData> temp  = new ArrayList<GetCommPerfData>();
    static Hashtable<String, String> data1;
    public static ArrayList<String> instidacc = new ArrayList<String>();
    String paramdata = "";
    ArrayAdapter<String> mArrayAdapter;
    ArrayList<String> phoneContactList = new ArrayList<String>();
    List<GetCommPerfData> planetsList = new ArrayList<GetCommPerfData>();
    private TextView emptyView;
   NewCommListAdapter aAdpt;
    ListView lv;
    Button ok;
    String selacc;
    ProgressDialog prgDialog2;
    TextView acno,txtitle,txcomrepo,txtcomrepo,commamo;
    EditText accno,mobno,fnam;
    SessionManagement session;
    public CommReport() {
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
        View rootView = inflater.inflate(R.layout.commreport, container, false);
        lv = (ListView) rootView.findViewById(R.id.lv);
        txtitle = (TextView) rootView.findViewById(R.id.bname);
        txcomrepo = (TextView) rootView.findViewById(R.id.textViewweryu);
        txtcomrepo = (TextView) rootView.findViewById(R.id.textViewwaq);
        commamo = (TextView) rootView.findViewById(R.id.txtagcomm);
        //   sp1 = (Spinner) rootView.findViewById(R.id.accno);
        prgDialog2 = new ProgressDialog(getActivity());
        prgDialog2.setMessage("Loading ....");
        // Set Cancelable as False
        session = new SessionManagement(getActivity());

        prgDialog2.setCancelable(false);
        emptyView = (TextView) rootView.findViewById(R.id.empty_view);
        String usid = Utility.gettUtilUserId(getActivity());
txtcomrepo.setText("Total Commission for user "+usid+" is");
        Calendar cal = Calendar.getInstance();

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH); // Note: zero based!
        int day = now.get(Calendar.DAY_OF_MONTH);

        now.set(year,month,01);

        SimpleDateFormat format1 = new SimpleDateFormat("" +
                "MMMM dd yyyy");
        System.out.println(cal.getTime());
// Output "Wed Sep 26 14:23:28 EST 2012"

        String formattednow = format1.format(cal.getTime());
        String formattedstartdate = format1.format(now.getTime());
// Output "2012-09-26"
txtitle.setText("Commision Report for "+formattedstartdate+" to "+formattednow);

if(!(getActivity() == null)) {
   setBalInquSec();
}

        return rootView;
    }

    public void StartChartAct(int i){
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    private void setBalInquSec() {

        prgDialog2.show();
        String endpoint= "core/balenquirey.action";


        String usid = Utility.gettUtilUserId(getActivity());
        String agentid = Utility.gettUtilAgentId(getActivity());
        String params = "1/"+usid+"/"+agentid+"/0000";
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

                    Log.v("response..:", response.body());
                    JSONObject obj = new JSONObject(response.body());
                    //obj = Utility.onresp(obj,getActivity());
                    obj = SecurityLayer.decryptTransaction(obj, getActivity());
                    SecurityLayer.Log("decrypted_response", obj.toString());

                    String respcode = obj.optString("responseCode");
                    String responsemessage = obj.optString("message");


                    JSONObject plan = obj.optJSONObject("data");
                    //session.setString(SecurityLayer.KEY_APP_ID,appid);


                    if(!(response.body() == null)) {
                        if (respcode.equals("00")) {

                            Log.v("Response Message", responsemessage);

//                                    Log.v("Respnse getResults",datas.toString());
                            if (!(plan == null)) {
                                String balamo = plan.optString("balance");
                                String comamo = plan.optString("commision");




                                String cmbal = Utility.returnNumberFormat(comamo);
                                commamo.setText(Html.fromHtml("&#8358") + " " + cmbal);
                            } else {
                                Toast.makeText(
                                        getActivity(),
                                        "There was an error retrieving your balance ",
                                        Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(
                                    getActivity(),
                                    "There was an error retrieving your balance ",
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(
                                getActivity(),
                                "There was an error retrieving your balance ",
                                Toast.LENGTH_LONG).show();
                    }



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
                    if (Utility.checkInternetConnection(getActivity())) {
                       SetMinist();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Log error here since request failed
                Log.v("Throwable error",t.toString());
                Toast.makeText(
                        getActivity(),
                        "There was a error processing your request",
                        Toast.LENGTH_LONG).show();
                //   pDialog.dismiss();

                try {
                    if ((prgDialog2 != null) && prgDialog2.isShowing()) {
                        prgDialog2.dismiss();
                    }
                } catch (final IllegalArgumentException e) {
                    // Handle or log or ignore
                } catch (final Exception e) {
                    // Handle or log or ignore
                } finally {
                    prgDialog2 = null;
                }
                if(Utility.checkInternetConnection(getActivity())){
                 SetMinist();
                }
            }
        });

    }

public void SetMinist(){


    prgDialog2.show();


    Calendar now = Calendar.getInstance();
    int year = now.get(Calendar.YEAR);
    int month = now.get(Calendar.MONTH); // Note: zero based!
    int day = now.get(Calendar.DAY_OF_MONTH);

    month = month+1;

    String frmdymonth = Integer.toString(day);
    if (day < 10) {
        frmdymonth = "0" + frmdymonth;
    }
    String frmyear = Integer.toString(year);
    frmyear = frmyear.substring(2, 4);
    String tdate = frmdymonth + "-" + (month) + "-" + frmyear;
    String firdate = "01" + "-" + (month) + "-" + frmyear;

    Calendar calfrom = Calendar.getInstance();
    calfrom.set(year,month,1);
    ApiInterface apiService =
            ApiClient.getClient().create(ApiInterface.class);
    String usid = Utility.gettUtilUserId(getActivity());
    String agentid = Utility.gettUtilAgentId(getActivity());
    String mobnoo = Utility.gettUtilMobno(getActivity());
    String params = "1/"+usid+"/"+agentid+"/0000/CMSNRPT/"+firdate+"/"+tdate;
    CommReport(params);


}

    private void CommReport(String params) {

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
double tott = 0;
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

                                            if(((status.equals("SUCCESS"))) && (agentCmsn > 0)) {
                                                tott += agentCmsn;
                                                planetsList.add(new GetCommPerfData(txnCode, txndateTime, agentCmsn, status, amount, toAcNum, refNumber));

                                            }


                                        }
                                        if(!(getActivity() == null)) {
                                            aAdpt = new NewCommListAdapter(planetsList, getActivity());


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

                        txcomrepo.setText(Double.toString(tott)+ApplicationConstants.KEY_NAIRA);
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
