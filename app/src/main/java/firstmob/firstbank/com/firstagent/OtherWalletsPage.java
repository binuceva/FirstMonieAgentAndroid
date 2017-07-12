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
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import adapter.OtherWalletsAdapt;
import model.GetWallets;
import model.GetWalletsData;
import rest.ApiClient;
import rest.ApiInterface;
import rest.ApiSecurityClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import security.SecurityLayer;


public class OtherWalletsPage extends Fragment {
    ListView lv;
    OtherWalletsAdapt aAdpt;
    String bankname,bankcode;
    ProgressDialog prgDialog;
    List<GetWalletsData> planetsList = new ArrayList<GetWalletsData>();
    public OtherWalletsPage() {
        // Required empty public constructor
    }
  /*  private static Fragment newInstance(Context context) {
        LayoutOne f = new LayoutOne();

        return f;
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.dialoglistview, null);
   lv = (ListView) root.findViewById(R.id.lv);
        prgDialog = new ProgressDialog(getActivity());
        prgDialog.setMessage("Loading....");
        prgDialog.setCancelable(false);
      //  SetBanks();
GetServv();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String walletname = planetsList.get(position).getBankName();
                String walletcode = planetsList.get(position).getBankCode();
                Bundle b  = new Bundle();
                b.putString("walletname",walletname);
                b.putString("wallcode",walletcode);
                Fragment  fragment = new SendOtherWallet();

                fragment.setArguments(b);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //  String tag = Integer.toString(title);
                fragmentTransaction.replace(R.id.container_body, fragment,"Mobile Money Wallet");
                fragmentTransaction.addToBackStack("Mobile Money Wallet");
                ((FMobActivity)getActivity())
                        .setActionBarTitle("Mobile Money Wallet");
                fragmentTransaction.commit();

            }
        });
        return root;
    }



    public void StartChartAct(int i){


    }

/*
 planetsList.add(new OTBList("FET","057"));
        planetsList.add(new OTBList("Paga","058"));

        planetsList.add(new OTBList("Parkway-ReadyCash","058"));
        planetsList.add(new OTBList("Paycom","059"));
        planetsList.add(new OTBList("Cellulant","057"));
        planetsList.add(new OTBList("eTranzact","058"));
        planetsList.add(new OTBList("EcoMobile","059"));
        planetsList.add(new OTBList("GTMobile","057"));
        planetsList.add(new OTBList("TeasyMobile","058"));
        planetsList.add(new OTBList("VTNetworks","059"));
        planetsList.add(new OTBList("AccessMobile","059"));

        planetsList.add(new OTBList("ChamsMobile","058"));
        planetsList.add(new OTBList("FortisMobile","059"));

        planetsList.add(new OTBList("Hedonmark","058"));
        planetsList.add(new OTBList("ZenithMobile","059"));
        planetsList.add(new OTBList("MoneyBox","059"));
        planetsList.add(new OTBList("Fidelity Mobile","057"));
        planetsList.add(new OTBList("Sterling Mobile","058"));
        planetsList.add(new OTBList("Eartholeum","059"));
        planetsList.add(new OTBList("TagPay","059"));

        Collections.sort(planetsList, new Comparator<OTBList>(){
            public int compare(OTBList d1, OTBList d2){
                return d1.getBenName().compareTo(d2.getBenName());
            }
        });
        aAdpt = new OTBAdapt(planetsList, getActivity());
        lv.setAdapter(aAdpt);*/


        public void SetBanks(){

            prgDialog.show();

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<GetWallets> call = apiService.getWallets();
            call.enqueue(new Callback<GetWallets>() {
                @Override
                public void onResponse(Call<GetWallets>call, Response<GetWallets> response) {
                    String responsemessage = response.body().getMessage();

                    Log.v("Response Message",responsemessage);
                    planetsList = response.body().getResults();
//                                    Log.v("Respnse getResults",datas.toString());
                    if(!(planetsList == null)) {
                        Log.v("Get Wallets Data Name",planetsList.get(0).getBankName());
                        Collections.sort(planetsList, new Comparator<GetWalletsData>(){
                            public int compare(GetWalletsData d1, GetWalletsData d2){
                                return d1.getBankName().compareTo(d2.getBankName());
                            }
                        });
                        aAdpt = new OtherWalletsAdapt(planetsList, getActivity());
                        lv.setAdapter(aAdpt);
                    }
                    prgDialog.dismiss();
                }

                @Override
                public void onFailure(Call<GetWallets>call, Throwable t) {
                    // Log error here since request failed
                    Toast.makeText(
                            getActivity(),
                            "Throwable Error: "+t.toString(),
                            Toast.LENGTH_LONG).show();
                    prgDialog.dismiss();
                }
            });




    }
    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }
    private void GetServv() {
        prgDialog.show();
        String endpoint= "transfer/getwallets.action";


        String usid = Utility.gettUtilUserId(getActivity());
        String agentid = Utility.gettUtilAgentId(getActivity());


        String params = usid+"/"+agentid+"/93939393";

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


                    SecurityLayer.Log("Get Wallets Resp", response.body());
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


                                            String instName = json_data.optString("instName");

                                            String bankCode = json_data.optString("bankCode");




                                            planetsList.add( new GetWalletsData(instName,bankCode) );




                                        }
                                        if(!(getActivity() == null)) {
                                            Log.v("Get Wallets Data Name",planetsList.get(0).getBankName());
                                            Collections.sort(planetsList, new Comparator<GetWalletsData>(){
                                                public int compare(GetWalletsData d1, GetWalletsData d2){
                                                    return d1.getBankName().compareTo(d2.getBankName());
                                                }
                                            });
                                            aAdpt = new OtherWalletsAdapt(planetsList, getActivity());
                                            lv.setAdapter(aAdpt);
                                        }


                                    }else{
                                        Toast.makeText(
                                                getActivity(),
                                                "No services available  ",
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
                    // TODO Auto-generated catch block
                    Toast.makeText(getActivity(), getActivity().getText(R.string.conn_error), Toast.LENGTH_LONG).show();
                    // SecurityLayer.Log(e.toString());

                } catch (Exception e) {
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


                Toast.makeText(
                        getActivity(),
                        "There was an error on your request",
                        Toast.LENGTH_LONG).show();



                prgDialog.dismiss();
            }
        });

    }
}
