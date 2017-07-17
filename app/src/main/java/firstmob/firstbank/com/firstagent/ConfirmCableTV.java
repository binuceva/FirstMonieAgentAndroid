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

import java.net.SocketTimeoutException;
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

public class ConfirmCableTV extends Fragment  implements View.OnClickListener{
    TextView reccustid,recamo,recnarr,recsendnum,recsendnam,txtfee,txtlabel;
    TextView step2,step1,step3,stt;
    Button btnsub;
    String txtcustid, amou ,narra, ednamee,ednumbb,serviceid,billid,strlabl,servicename,billname,packid,paymentCode;
    String respfee;
    ProgressDialog prgDialog,prgDialog2;
    EditText amon, edacc,pno,txtamount,txtnarr,edname,ednumber;
    EditText etpin;
    public ConfirmCableTV() {
        // Required empty public constructor
    }
  /*  private static Fragment newInstance(Context context) {
        LayoutOne f = new LayoutOne();

        return f;
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.confirmcabletv, null);
        reccustid = (TextView) root.findViewById(R.id.textViewnb2);
        etpin = (EditText) root.findViewById(R.id.pin);
        stt = (TextView) root.findViewById(R.id.recip);
        recamo = (TextView) root.findViewById(R.id.textViewrrs);
        recnarr = (TextView) root.findViewById(R.id.textViewrr);
        txtlabel = (TextView) root.findViewById(R.id.textViewnb);
        recsendnam = (TextView) root.findViewById(R.id.sendnammm);
        recsendnum = (TextView) root.findViewById(R.id.sendno);
        prgDialog2 = new ProgressDialog(getActivity());
        prgDialog2.setMessage("Loading....");
        prgDialog2.setCancelable(false);
        txtfee = (TextView) root.findViewById(R.id.txtfee);

        step2 = (TextView) root.findViewById(R.id.tv2);
        step2.setOnClickListener(this);

        step1 = (TextView) root.findViewById(R.id.tv);
        step1.setOnClickListener(this);

        step3 = (TextView) root.findViewById(R.id.tv3);
        step3.setOnClickListener(this);



        btnsub = (Button) root.findViewById(R.id.button2);
        btnsub.setOnClickListener(this);

        Bundle bundle = this.getArguments();
        if (bundle != null) {

            txtcustid = bundle.getString("custid");
            amou = bundle.getString("amou");
            narra = bundle.getString("narra");
            ednamee = bundle.getString("ednamee");
            ednumbb = bundle.getString("ednumbb");
            billname= bundle.getString("billname");
            billid = bundle.getString("billid");
            serviceid = bundle.getString("serviceid");
            servicename = bundle.getString("servicename");
            strlabl = bundle.getString("label");
            packid = bundle.getString("packId");
            paymentCode = bundle.getString("paymentCode");
            reccustid.setText(txtcustid);


            recamo.setText(amou);
            recnarr.setText(narra);

            recsendnam.setText(ednamee);
            recsendnum.setText(ednumbb);
            txtlabel.setText(strlabl);
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
        Call<GetFee> call = apiService.getFee("1", usid, agentid, "BILLPAYMENT",  amou);
        call.enqueue(new Callback<GetFee>() {
            @Override
            public void onResponse(Call<GetFee> call, Response<GetFee> response) {
                if (!(response.body() == null)) {
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
                }else{
                    txtfee.setText("N/A");
                }
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
                if (Utility.isNotNull(txtcustid)) {
                    if (Utility.isNotNull(amou)) {
                        if (Utility.isNotNull(narra)) {
                            if (Utility.isNotNull(ednamee)) {
                                if (Utility.isNotNull(ednumbb)) {
                                    if (Utility.isNotNull(agpin)) {
                                        String encrypted = null;
                                        String key = "97206B46CE46376894703ECE161F31F2";
                                        try {

                                            encrypted = EncryptTransactionPin.encrypt(key, agpin, 'F');
                                            System.out.println("Encrypt Pin "+EncryptTransactionPin.encrypt(key, agpin, 'F'));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    prgDialog2.show();
                                    OkHttpClient client = new OkHttpClient();
                                    try {
                                        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                                        trustStore.load(null, null);
                                        MySSLSocketFactory  sf = new MySSLSocketFactory(trustStore);
                                        sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                                        client.sslSocketFactory();
                                    }
                                    catch (Exception e) {
                                    }

                                    ApiInterface apiService =
                                            ApiClient.getClient().create(ApiInterface.class);
                                    String usid = Utility.gettUtilUserId(getActivity());
                                    String agentid = Utility.gettUtilAgentId(getActivity());
                                        String emaill = Utility.gettUtilEmail(getActivity());
                                    final String mobnoo = "0"+Utility.gettUtilMobno(getActivity());
                                        if(Utility.isNotNull(paymentCode)|| paymentCode.equals("")){
                                            paymentCode = billid+"01";
                                        }
                                        if((!Utility.isNotNull(packid))|| packid.equals("")){
                                            packid = "01";
                                        }
                                        String params = "1/"+usid+"/"+agentid+"/"+mobnoo+"/"+billid+"/"+serviceid+"/"+amou+"/"+packid+"/"+ednumbb+"/"+emaill+"/"+txtcustid+"/"+paymentCode+"/"+encrypted;

                                                PayBillResp(params);

                                        ClearPin();
                                }  else {
                                    Toast.makeText(
                                            getActivity(),
                                            "Please enter a valid value for Agent PIN",
                                            Toast.LENGTH_LONG).show();
                                }
                                }  else {
                                    Toast.makeText(
                                            getActivity(),
                                            "Please enter a valid value for Depositor Number",
                                            Toast.LENGTH_LONG).show();
                                }
                            }else {
                                Toast.makeText(
                                        getActivity(),
                                        "Please enter a valid value for Depositor Name",
                                        Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(
                                    getActivity(),
                                    "Please enter a valid value for Narration",
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
                            "Please enter a value for Customer ID",
                            Toast.LENGTH_LONG).show();
                }
            }
        }

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
        if (view.getId() == R.id.tv) {
            Bundle b  = new Bundle();
            b.putString("serviceid",serviceid);
            b.putString("servicename",servicename);


            Fragment    fragment = new SpecBillMenu();
            String  title = servicename;


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
        if (view.getId() == R.id.tv3) {
            Bundle b  = new Bundle();
            b.putString("serviceid",serviceid);
            b.putString("servicename",servicename);
            b.putString("billid",billid);
            b.putString("billname",billname);
            b.putString("label",strlabl);
          Fragment  fragment = new CableTV();
         String   title = "Cable";

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

        String params = "1/"+usid+"/"+agentid+"/BILLPAYMENT/"+amou;
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
                     respfee = obj.optString("fee");


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
                            Bundle b  = new Bundle();
                            b.putString("serviceid",serviceid);
                            b.putString("servicename",servicename);
                            b.putString("billid",billid);
                            b.putString("billname",billname);
                            b.putString("label",strlabl);

                            Fragment  fragment = new CableTV();
                            String   title = "Cable";

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
                    Utility.errornexttoken();
                    // TODO Auto-generated catch block
                    Toast.makeText(getActivity(), getActivity().getText(R.string.conn_error), Toast.LENGTH_LONG).show();
                    // SecurityLayer.Log(e.toString());

                } catch (Exception e) {
                    SecurityLayer.Log("encryptionJSONException", e.toString());
                    Utility.errornexttoken();
                    // SecurityLayer.Log(e.toString());
                }
                prgDialog2.dismiss();
               validatecust();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Log error here since request failed
                Log.v("Throwable error",t.toString());
                if(t instanceof SocketTimeoutException){
                    Utility.errornexttoken();
                    Log.v("socket timeout error",t.toString());
                }
                Toast.makeText(
                        getActivity(),
                        "There was a error processing your request",
                        Toast.LENGTH_LONG).show();
                prgDialog2.dismiss();
                validatecust();
            }
        });

    }

    private void validatecust() {
        prgDialog2.show();
        //{channel}/{userId}/{merchantId}/{mobileNumber}/{billerId}/{customerId}/{pamentCode}
        String endpoint= "billpayment/validateCustomer.action";


        String usid = Utility.gettUtilUserId(getActivity());
        String agentid = Utility.gettUtilAgentId(getActivity());
        String mobnoo = Utility.gettUtilMobno(getActivity());
        String params = "1/"+usid+"/"+agentid+"/"+mobnoo+"/"+billid+"/"+txtcustid+"/"+paymentCode;
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
                    JSONObject respfee = obj.optJSONObject("data");


                    //session.setString(SecurityLayer.KEY_APP_ID,appid);


                    if(!(response.body() == null)) {
                        if (respcode.equals("00")) {

                            Log.v("Response Message", responsemessage);

//                                    Log.v("Respnse getResults",datas.toString());
                            if (respfee == null || respfee.equals("")) {

                            } else {
                                String bs = respfee.optString("FullName");
                              /*  Toast.makeText(
                                        getActivity(),
                                        bs,
                                        Toast.LENGTH_LONG).show();*/
                              stt.setText(bs);
                            }

                        }else  if (respcode.equals("93")) {
                            Toast.makeText(
                                    getActivity(),
                                    responsemessage,
                                    Toast.LENGTH_LONG).show();
                            Bundle b  = new Bundle();
                            b.putString("serviceid",serviceid);
                            b.putString("servicename",servicename);
                            b.putString("billid",billid);
                            b.putString("billname",billname);
                            b.putString("label",strlabl);

                            Fragment  fragment = new CableTV();
                            String   title = "Cable";

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
                    Utility.errornexttoken();
                    SecurityLayer.Log("encryptionJSONException", e.toString());
                    // TODO Auto-generated catch block
                    Toast.makeText(getActivity(), getActivity().getText(R.string.conn_error), Toast.LENGTH_LONG).show();
                    // SecurityLayer.Log(e.toString());

                } catch (Exception e) {
                    Utility.errornexttoken();
                    SecurityLayer.Log("encryptionJSONException", e.toString());
                    // SecurityLayer.Log(e.toString());
                }
                try {
                    if ((prgDialog2 != null) && prgDialog2.isShowing()) {
                        prgDialog2.dismiss();
                    }
                } catch (final IllegalArgumentException e) {
                    // Handle or log or ignore
                } catch (final Exception e) {
                    // Handle or log or ignore
                } finally {

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Log error here since request failed
                Log.v("Throwable error",t.toString());
                Utility.errornexttoken();
                Toast.makeText(
                        getActivity(),
                        "There was a error processing your request",
                        Toast.LENGTH_LONG).show();
                try {
                    if ((prgDialog2 != null) && prgDialog2.isShowing()) {
                        prgDialog2.dismiss();
                    }
                } catch (final IllegalArgumentException e) {
                    // Handle or log or ignore
                } catch (final Exception e) {
                    // Handle or log or ignore
                } finally {

                }
            }
        });

    }

    private void PayBillResp(String params) {

        String endpoint= "billpayment/dobillpayment.action";


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
                                if (respcode.equals("00")){

//                                    Log.v("Respnse getResults",datas.toString());
                                    String totfee = "0.00";
                                    String tref = "N/A";
                                    if (!(datas == null)) {




                                        String ttf = datas.optString("fee");
                                        if(ttf == null || ttf.equals("")){

                                        }else{
                                            totfee = ttf;
                                        }


                                        tref = datas.optString("refNumber");
                                    }
                                    Bundle b = new Bundle();
                                    b.putString("custid", txtcustid);
                                    b.putString("amou", amou);
                                    b.putString("narra", narra);
                                    b.putString("ednamee", ednamee);
                                    b.putString("ednumbb", ednumbb);
                                    b.putString("billid", billid);
                                    b.putString("serviceid", serviceid);
                                    b.putString("label",strlabl);
                                    b.putString("tref",tref);
                                    b.putString("agcmsn", agcmsn);
                                    b.putString("fee", respfee);
                                    Fragment fragment = new FinalConfirmCableTV();

                                    fragment.setArguments(b);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    //  String tag = Integer.toString(title);
                                    fragmentTransaction.replace(R.id.container_body, fragment, "Final Conf Cable");
                                    fragmentTransaction.addToBackStack("Final Conf Cable");
                                    ((FMobActivity) getActivity())
                                            .setActionBarTitle("Final Conf Cable");
                                    fragmentTransaction.commit();
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
                        Utility.errornexttoken();
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
                prgDialog2.dismiss();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Log error here since request failed
                // Log error here since request failed
                Log.v("throwable error",t.toString());
                if(t instanceof SocketTimeoutException){
                    Utility.errornexttoken();
                    Log.v("socket timeout error",t.toString());
                }
                Utility.errornexttoken();

                Toast.makeText(
                        getActivity(),
                        "There was an error on your request",
                        Toast.LENGTH_LONG).show();



                prgDialog2.dismiss();
            }
        });

    }
}
