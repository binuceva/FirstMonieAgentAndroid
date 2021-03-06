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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyStore;

import model.GetFee;
import okhttp3.OkHttpClient;
import rest.ApiClient;
import rest.ApiInterface;
import rest.ApiSecurityClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import security.EncryptTransactionPin;
import security.SecurityLayer;

public class ConfirmWithdrawal extends Fragment  implements View.OnClickListener{
    TextView recacno,recname,recamo,recnarr,recsendnum,recsendnam,step2,txtfee;
    Button btnsub;
    String recanno, amou ,txtname,txref,otp ;
    ProgressDialog prgDialog,prgDialog2;
    EditText etpin;
    public ConfirmWithdrawal() {
        // Required empty public constructor
    }
  /*  private static Fragment newInstance(Context context) {
        LayoutOne f = new LayoutOne();

        return f;
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.confirmwithdrawal, null);
        recacno = (TextView) root.findViewById(R.id.textViewnb2);
        recname = (TextView) root.findViewById(R.id.textViewcvv);
        etpin = (EditText) root.findViewById(R.id.pin);
        recamo = (TextView) root.findViewById(R.id.textViewrrs);
        recnarr = (TextView) root.findViewById(R.id.textViewrr);
        txtfee = (TextView) root.findViewById(R.id.txtfee);
        recsendnam = (TextView) root.findViewById(R.id.sendnammm);
        recsendnum = (TextView) root.findViewById(R.id.sendno);
        prgDialog2 = new ProgressDialog(getActivity());
        prgDialog2.setMessage("Loading....");
        prgDialog2.setCancelable(false);

        btnsub = (Button) root.findViewById(R.id.button2);
       btnsub.setOnClickListener(this);

        step2 = (TextView) root.findViewById(R.id.tv2);
        step2.setOnClickListener(this);

        Bundle bundle = this.getArguments();
        if (bundle != null) {

            recanno = bundle.getString("recanno");
            amou = bundle.getString("amou");

            txtname = bundle.getString("txtname");
            txref = bundle.getString("txref");

            otp = bundle.getString("otp");
            recacno.setText(recanno);
            recname.setText(txtname);

            recamo.setText(amou);
            amou = Utility.convertProperNumber(amou);
            getFeeSec();

        }
        return root;
    }



    public void StartChartAct(int i){


    }


    public void getFee(){
        prgDialog2.show();
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String usid = Utility.gettUtilUserId(getActivity());
        String agentid = Utility.gettUtilAgentId(getActivity());
        Call<GetFee> call = apiService.getFee("1", usid, agentid, "CWDBYACT",  amou);
        call.enqueue(new Callback<GetFee>() {
            @Override
            public void onResponse(Call<GetFee> call, Response<GetFee> response) {
                String responsemessage = response.body().getMessage();
                String respfee = response.body().getFee();
                Log.v("Response Message", responsemessage);
                if(respfee == null || respfee.equals("")){
                    txtfee.setText("N/A");
                }else{
                    respfee = Utility.returnNumberFormat(respfee);
                    txtfee.setText(ApplicationConstants.KEY_NAIRA+respfee);
                }

                prgDialog2.dismiss();
            }

            @Override
            public void onFailure(Call<GetFee> call, Throwable t) {
                // Log error here since request failed
                Log.v("Throwable error",t.toString());
                Toast.makeText(
                        getActivity(),
                        "There was a error processing your request",
                        Toast.LENGTH_LONG).show();
                prgDialog2.dismiss();
            }
        });
    }
    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.button2) {
            if (Utility.checkInternetConnection(getActivity())) {
                String agpin  = etpin.getText().toString();
                if (Utility.isNotNull(recanno)) {
                    if (Utility.isNotNull(amou)) {
                        if (Utility.isNotNull(agpin)) {

                            prgDialog2.show();
                            String encrypted = null;
                            String key = "97206B46CE46376894703ECE161F31F2";
                            try {

                                encrypted = EncryptTransactionPin.encrypt(key, agpin, 'F');
                                System.out.println("Encrypt Pin " + EncryptTransactionPin.encrypt(key, agpin, 'F'));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            OkHttpClient client = new OkHttpClient();
                            try {
                                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                                trustStore.load(null, null);
                                MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
                                sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                                client.sslSocketFactory();
                            } catch (Exception e) {
                            }


                            String usid = Utility.gettUtilUserId(getActivity());
                            String agentid = Utility.gettUtilAgentId(getActivity());
                            String mobnoo = Utility.gettUtilMobno(getActivity());
                            String params = "1/"+ usid+"/"+agentid+"/"+mobnoo+"/"+amou+"/"+txref+"/"+recanno+"/"+txtname+"/Narr/"+otp+"/"+encrypted;

                     WithdrawResp(params);
                          /*  ApiInterface apiService =
                                    ApiClient.getClient().create(ApiInterface.class);
                            //  /agencyapi/app/withdrawal/cashbyaccountconfirm.action/1/CEVA/BATA00000000019493818389/1000/1480430145451/2017812696/AMAGREEMOMINE/androticashcwithdrawal/168697/43211
                            Call<WithdrawalConfirmOTP> call2 = apiService.getConfirmOTP("1", usid, agentid, "0000", amou, txref, recanno, txtname, "Narr", otp, encrypted);
                            call2.enqueue(new Callback<WithdrawalConfirmOTP>() {
                                @Override
                                public void onResponse(Call<WithdrawalConfirmOTP> call, Response<WithdrawalConfirmOTP> response) {
                                    if (!(response.body() == null)) {
                                        String responsemessage = response.body().getMessage();
                                        String respcode = response.body().getRespCode();
                                        String agcmsn = response.body().getFee();
                                        WithdrawalConfirmOTPData datas = response.body().getResults();
                                        if (Utility.isNotNull(respcode) && Utility.isNotNull(responsemessage)) {
                                            if (!(Utility.checkUserLocked(respcode))) {
                                                Log.v("Response Message", responsemessage);
                                                Toast.makeText(
                                                        getActivity(),
                                                        "" + responsemessage,
                                                        Toast.LENGTH_LONG).show();
                                                if (respcode.equals("00")) {
                                                    String totfee = "0.00";
                                                    if (!(datas == null)) {
                                                        totfee = datas.getfee();
                                                    }
                                                    Bundle b = new Bundle();
                                                    b.putString("recanno", recanno);
                                                    b.putString("amou", amou);
                                                    b.putString("otp", otp);
                                                    b.putString("txtname", txtname);
                                                    b.putString("txref", txref);
                                                    b.putString("agcmsn", agcmsn);
                                                    b.putString("fee", totfee);
                                                    Fragment fragment = new FinalConfWithdraw();

                                                    fragment.setArguments(b);
                                                    FragmentManager fragmentManager = getFragmentManager();
                                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                    //  String tag = Integer.toString(title);
                                                    fragmentTransaction.replace(R.id.container_body, fragment, "Final Confirm Withdrawal");
                                                    fragmentTransaction.addToBackStack("Final Confirm Withdrawal");
                                                    ((FMobActivity) getActivity())
                                                            .setActionBarTitle("Final Confirm Withdrawal");
                                                    fragmentTransaction.commit();
                                                }
                                            } else {
                                                getActivity().finish();
                                                startActivity(new Intent(getActivity(), SignInActivity.class));
                                                Toast.makeText(
                                                        getActivity(),
                                                        "You have been locked out of the app.Please call customer care for further details",
                                                        Toast.LENGTH_LONG).show();

                                            }
                                        }else {

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
                                    prgDialog2.dismiss();
                                }

                                @Override
                                public void onFailure(Call<WithdrawalConfirmOTP> call, Throwable t) {
                                    // Log error here since request failed
                                    Log.v("throwable error", t.toString());


                                    Toast.makeText(
                                            getActivity(),
                                            "There was an error on your request",
                                            Toast.LENGTH_LONG).show();


                                    prgDialog2.dismiss();
                                }
                            });*/
                            ClearPin();
                        } else {
                            Toast.makeText(
                                    getActivity(),
                                    "Please enter a valid value for Agent PIN",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(
                                getActivity(),
                                "Please enter a valid value for Amount",
                                Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(
                            getActivity(),
                            "Please enter a value for Account Number",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
        if (view.getId() == R.id.tv2) {
            Fragment  fragment = new Withdraw();


            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //  String tag = Integer.toString(title);
            fragmentTransaction.replace(R.id.container_body, fragment,"Withdraw");
            fragmentTransaction.addToBackStack("Withdraw");
            ((FMobActivity)getActivity())
                    .setActionBarTitle("Withdraw");
            fragmentTransaction.commit();
        }
    }


    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }
    public void ClearPin(){
        etpin.setText("");
    }


    private void getFeeSec() {
        prgDialog2.show();
        String endpoint= "fee/getfee.action";


        String usid = Utility.gettUtilUserId(getActivity());
        String agentid = Utility.gettUtilAgentId(getActivity());

        String params = "1/"+usid+"/"+agentid+"/CWDBYACT/"+amou;
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
                    String respfee = obj.optString("fee");


                    //session.setString(SecurityLayer.KEY_APP_ID,appid);


                    if(!(response.body() == null)) {
                        if (respcode.equals("00")) {

                            Log.v("Response Message", responsemessage);

//                                    Log.v("Respnse getResults",datas.toString());
                            if (respfee == null || respfee.equals("")) {
                                txtfee.setText("N/A");
                            } else {
                                respfee = Utility.returnNumberFormat(respfee);
                                txtfee.setText(ApplicationConstants.KEY_NAIRA + respfee);
                            }

                        }else  if (respcode.equals("93")) {
                            Toast.makeText(
                                    getActivity(),
                                    responsemessage,
                                    Toast.LENGTH_LONG).show();
                            Fragment  fragment = new Withdraw();
                            String title = "Withdraw";

                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            //  String tag = Integer.toString(title);
                            fragmentTransaction.replace(R.id.container_body, fragment,title);
                            fragmentTransaction.addToBackStack(title);
                            ((FMobActivity)getActivity())
                                    .setActionBarTitle(title);
                            fragmentTransaction.commit();
                            Toast.makeText(
                                    getActivity(),
                                    "Please ensure amount set is below the set limit",
                                    Toast.LENGTH_LONG).show();

                        }
                        else{
                            btnsub.setVisibility(View.GONE);
                            Toast.makeText(
                                    getActivity(),
                                    responsemessage,
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        txtfee.setText("N/A");
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
                prgDialog2.dismiss();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Log error here since request failed
                Log.v("Throwable error",t.toString());
                Toast.makeText(
                        getActivity(),
                        "There was a error processing your request",
                        Toast.LENGTH_LONG).show();
                prgDialog2.dismiss();
            }
        });

    }

    private void WithdrawResp(String params) {

        String endpoint= "withdrawal/cashbyaccountconfirm.action";


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


                    SecurityLayer.Log("Intra Bank Resp", response.body());
                    Log.v("response..:", response.body());
                    JSONObject obj = new JSONObject(response.body());
                    //obj = Utility.onresp(obj,getActivity());
                    obj = SecurityLayer.decryptTransaction(obj, getActivity());
                    SecurityLayer.Log("decrypted_response", obj.toString());




                    JSONObject datas = obj.optJSONObject("data");
                    //session.setString(SecurityLayer.KEY_APP_ID,appid);

                    if(!(response.body() == null)) {
                        String respcode = obj.optString("responseCode");
                        String responsemessage = obj.optString("message");
                        String agcmsn = obj.optString("fee");
                        Log.v("Response Message", responsemessage);

                        if (Utility.isNotNull(respcode) && Utility.isNotNull(respcode)) {
                            if (!(Utility.checkUserLocked(respcode))) {
                                Log.v("Response Message", responsemessage);
                                Toast.makeText(
                                        getActivity(),
                                        "" + responsemessage,
                                        Toast.LENGTH_LONG).show();
                                if (respcode.equals("00")) {
                                    String totfee = "0.00";
                                    if (!(datas == null)) {
                                        totfee = datas.optString("fee");
                                    }
                                    Bundle b = new Bundle();
                                    b.putString("recanno", recanno);
                                    b.putString("amou", amou);
                                    b.putString("otp", otp);
                                    b.putString("txtname", txtname);
                                    b.putString("txref", txref);
                                    b.putString("agcmsn", agcmsn);
                                    b.putString("fee", totfee);
                                    Fragment fragment = new FinalConfWithdraw();

                                    fragment.setArguments(b);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    //  String tag = Integer.toString(title);
                                    fragmentTransaction.replace(R.id.container_body, fragment, "Final Confirm Withdrawal");
                                    fragmentTransaction.addToBackStack("Final Confirm Withdrawal");
                                    ((FMobActivity) getActivity())
                                            .setActionBarTitle("Final Confirm Withdrawal");
                                    fragmentTransaction.commit();
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
