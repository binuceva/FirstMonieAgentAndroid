package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.BillerMenuAdapt;
import model.GetBillersData;
import rest.ApiClient;
import rest.ApiInterface;
import rest.ApiSecurityClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import security.SecurityLayer;


public class SpecBillMenu extends Fragment implements View.OnClickListener{

    GridView gridView;
    public static final String KEY_TOKEN = "token";
    List<GetBillersData> planetsList = new ArrayList<GetBillersData>();
    String ptype;
    ListView lv;
    TextView txtservice,step2;
    String serviceid,servicename,servlabel;
    BillerMenuAdapt aAdpt;
    ProgressDialog prgDialog, prgDialog2;
    SessionManagement session;
    String sbpam = "0", pramo = "0";
    boolean blsbp = false, blpr = false, blpf = false, bllr = false, blms = false, blmpesa = false, blcash = false;
    ArrayList<String> ds = new ArrayList<String>();

    public SpecBillMenu() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.specbillermenu, container, false);
        session = new SessionManagement(getActivity());

        prgDialog = new ProgressDialog(getActivity());

        prgDialog.setMessage("Please wait...");

        prgDialog2 = new ProgressDialog(getActivity());
        prgDialog2.setMessage("Loading....");

        gridView = (GridView) rootView.findViewById(R.id.gridView1);
        txtservice = (TextView) rootView.findViewById(R.id.textView1);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            serviceid = bundle.getString("serviceid");
            servicename = bundle.getString("servicename");

            txtservice.setText(servicename);
        }

        prgDialog.setCancelable(false);

        step2 = (TextView) rootView.findViewById(R.id.tv2);
        step2.setOnClickListener(this);

        //checkInternetConnection2();
        lv = (ListView) rootView.findViewById(R.id.lv);
        SetPop();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Fragment fragment = null;
                String title = null;
                String idd = planetsList.get(position).getId();
                 String billid = planetsList.get(position).getBillerId();
                String billname = planetsList.get(position).getBillerName();
                servlabel = planetsList.get(position).getcustomerField();
                Bundle b  = new Bundle();
                b.putString("serviceid",serviceid);
                b.putString("servicename",servicename);
                b.putString("billid",billid);
                b.putString("billname",billname);
                b.putString("idd",idd);
                b.putString("label",servlabel);

                    fragment = new GetPaybillItems();
                    title = "Packages";

                if (fragment != null) {
                    fragment.setArguments(b);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    //  String tag = Integer.toString(title);
                    fragmentTransaction.replace(R.id.container_body, fragment, title);
                    fragmentTransaction.addToBackStack(title);
                    ((FMobActivity)getActivity())
                            .setActionBarTitle(title);
                    fragmentTransaction.commit();
                }


            }
        });

        return rootView;
    }


    public void StartChartAct(int i) {


    }

    @Override
    public void onResume() {
        super.onResume();
        // put your code here...

    }


    public void SetPop(){
        planetsList.clear();



        prgDialog.show();

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        String usid = Utility.gettUtilUserId(getActivity());
        String agentid = Utility.gettUtilAgentId(getActivity());
        String mobnoo = Utility.gettUtilMobno(getActivity());


        String params = "1/"+usid+"/"+agentid+"/0000/"+serviceid;
        GetServv(params);

    }




    @Override
    public void onClick(View view) {


        if (view.getId() == R.id.tv2) {
            Fragment  fragment = new BillMenu();


            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //  String tag = Integer.toString(title);
            fragmentTransaction.replace(R.id.container_body, fragment,"Biller Menu");
            fragmentTransaction.addToBackStack("Biller Menu");
            ((FMobActivity)getActivity())
                    .setActionBarTitle("Biller Menu");
            fragmentTransaction.commit();
        }
    }


    private void GetServv(String params) {

        String endpoint= "billpayment/getbillers.action";


        String usid = Utility.gettUtilUserId(getActivity());
        String agentid = Utility.gettUtilAgentId(getActivity());

        Log.v("Before Req Tok",session.getString(KEY_TOKEN));


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





                    JSONArray servdata = obj.optJSONArray("data");
                    //session.setString(SecurityLayer.KEY_APP_ID,appid);

                    if(!(response.body() == null)) {
                        String respcode = obj.optString("responseCode");
                        String responsemessage = obj.optString("message");

                        Log.v("Response Message", responsemessage);

                        if (Utility.isNotNull(respcode) && Utility.isNotNull(respcode)) {
                            if (!(Utility.checkUserLocked(respcode))) {
                                Log.v("Response Message", responsemessage);

                                if (respcode.equals("00")){
                                    Log.v("JSON Aray", servdata.toString());
                                    if(servdata.length() > 0){


                                        JSONObject json_data = null;
                                        for (int i = 0; i < servdata.length(); i++) {
                                            json_data = servdata.getJSONObject(i);
                                            //String accid = json_data.getString("benacid");


                                            String id = json_data.optString("id");

                                            String billerId = json_data.optString("billerId");
                                            String billerDesc = json_data.optString("billerDesc");
                                            String billerName = json_data.optString("billerName");

                                            String customerField = json_data.optString("customerField");
                                            String charge = json_data.optString("charge");


                                            planetsList.add( new GetBillersData(id,billerId,billerDesc,billerName,customerField,charge)  );




                                        }
                                        if(!(getActivity() == null)) {
                                            aAdpt = new BillerMenuAdapt(planetsList, getActivity());


                                            lv.setAdapter(aAdpt);
                                        }


                                    }else{
                                        Toast.makeText(
                                                getActivity(),
                                                "No billers available  ",
                                                Toast.LENGTH_LONG).show();
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
                    Utility.errornexttoken();
                    // TODO Auto-generated catch block
                    Toast.makeText(getActivity(), getActivity().getText(R.string.conn_error), Toast.LENGTH_LONG).show();
                    // SecurityLayer.Log(e.toString());

                } catch (Exception e) {
                    Utility.errornexttoken();
                    SecurityLayer.Log("encryptionJSONException", e.toString());
                    // SecurityLayer.Log(e.toString());
                }
                prgDialog.dismiss();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Log error here since request failed
                // Log error here since request failed
                Log.v("throwable error",t.toString());
                Utility.errornexttoken();

                Toast.makeText(
                        getActivity(),
                        "There was an error on your request",
                        Toast.LENGTH_LONG).show();



                prgDialog.dismiss();
            }
        });

    }
}