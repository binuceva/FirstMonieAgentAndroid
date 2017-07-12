package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.FavList;
import adapter.TopFavAdapter;
import rest.ApiInterface;
import rest.ApiSecurityClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import security.SecurityLayer;


public class SendOtherWallet extends Fragment implements View.OnClickListener {
ImageView imageView1;
    EditText amon, phonenumb,pno,txtamount,txtnarr,edname,ednumber;
    Button btnsub;
    SessionManagement session;
    ProgressDialog prgDialog;
    RecyclerView lvbann;
    TopFavAdapter FavAdapt;
    LinearLayoutManager layoutManager,layoutManager2;
    String depositid,walletname,walletcode;
    List<FavList> favlist = new ArrayList<FavList>();
    Spinner sp1;
    TextView walletchosen,walletselected;
    TextView step2;
    String acname;
    EditText accountoname;
    public SendOtherWallet() {
        // Required empty public constructor
    }
  /*  private static Fragment newInstance(Context context) {
        LayoutOne f = new LayoutOne();

        return f;
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.sendotherwallets, null);
        session = new SessionManagement(getActivity());
        amon = (EditText) root.findViewById(R.id.phone);
        sp1 = (Spinner) root.findViewById(R.id.spin1);

        prgDialog = new ProgressDialog(getActivity());
        prgDialog.setMessage("Loading Request....");
        pno = (EditText) root.findViewById(R.id.scodepnam);

        phonenumb = (EditText) root.findViewById(R.id.phonenumb);

        txtamount = (EditText) root.findViewById(R.id.amount);
        txtnarr = (EditText) root.findViewById(R.id.ednarr);
        edname = (EditText) root.findViewById(R.id.sendname);
        ednumber = (EditText) root.findViewById(R.id.sendnumber);

        btnsub = (Button) root.findViewById(R.id.button2);
        btnsub.setOnClickListener(this);
        accountoname = (EditText) root.findViewById(R.id.cname);
        walletchosen = (TextView) root.findViewById(R.id.textmmo);
        walletselected = (TextView) root.findViewById(R.id.textVipp);
        walletselected.setOnClickListener(this);
    //    lvbann = (RecyclerView) root.findViewById(R.id.listView2);
        layoutManager2 = new LinearLayoutManager(getActivity());
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);


        View.OnFocusChangeListener ofcListener = new MyFocusChangeListener();
        txtamount.setOnFocusChangeListener(ofcListener);
        txtnarr.setOnFocusChangeListener(ofcListener);
        phonenumb.setOnFocusChangeListener(ofcListener);
        edname.setOnFocusChangeListener(ofcListener);
        ednumber.setOnFocusChangeListener(ofcListener);



        Bundle bundle = this.getArguments();
        if (bundle != null) {
            walletname = bundle.getString("walletname");
            walletcode = bundle.getString("wallcode");
        }

        if(Utility.isNotNull(walletname)){
            walletchosen.setText(walletname);
        }

        step2 = (TextView) root.findViewById(R.id.tv2);
        step2.setOnClickListener(this);


        phonenumb.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (phonenumb.getText().toString().length() == 11) {

                    if (!(walletcode == null)) {
                        if (Utility.checkInternetConnection(getActivity())) {
                            Utility.hideKeyboardFrom(getActivity(), phonenumb);
                            prgDialog.show();
                            String mobno = phonenumb.getText().toString();
                            mobno = "234"+mobno.substring(mobno.length() - 10);
                            String usid = Utility.gettUtilUserId(getActivity());
                            String agentid = Utility.gettUtilAgentId(getActivity());
                            String mobnoo = Utility.gettUtilMobno(getActivity());
                            String params = "1/"+usid+"/"+agentid+"/0000/"+walletcode+"/"+mobno;
                            NameInquirySec(params);

                        }
                    }
                    else {
                        Toast.makeText(
                                getActivity(),
                                "Please select a wallet ",
                                Toast.LENGTH_LONG).show();
                    }
                    // TODO Auto-generated method stub
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });
        return root;
    }


    public void Pop(){
        favlist.add(new FavList("Airtel", R.drawable.airtel,"ATM",""));
        favlist.add(new FavList("Etisalat", R.drawable.etisalat,"ATM",""));
        favlist.add(new FavList("GLO", R.drawable.glo,"ATM",""));
        favlist.add(new FavList("MTN", R.drawable.mtn,"ATM",""));
        FavAdapt = new TopFavAdapter( getActivity(),favlist);
        lvbann.setAdapter(FavAdapt);
    }



    private class MyFocusChangeListener implements View.OnFocusChangeListener {

        public void onFocusChange(View v, boolean hasFocus){

            if(v.getId() == R.id.amount && !hasFocus) {
                InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                String txt = txtamount.getText().toString();
                String fbal = Utility.returnNumberFormat(txt);
                txtamount.setText(fbal);

            }

            if(v.getId() == R.id.ednarr && !hasFocus) {
                InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);


            }
            if(v.getId() == R.id.sendname && !hasFocus) {
                InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);


            }
            if(v.getId() == R.id.sendnumber && !hasFocus) {
                InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);


            }
            if(v.getId() == R.id.input_payacc && !hasFocus) {
                InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);


            }
        }
    }
    @Override
    public void onClick(View view) {


        if (view.getId() == R.id.button2) {

            String phoneno = phonenumb.getText().toString();
            if(phoneno.length() >= 10) {
                phoneno = "234" + phoneno.substring(phoneno.length() - 10);
            }else{
                phoneno = null;
            }
            String amou = txtamount.getText().toString();
            String narra = txtnarr.getText().toString();
            String ednamee = edname.getText().toString();
            String ednumbb = ednumber.getText().toString();

            if (Utility.isNotNull(walletname)) {
            if (Utility.isNotNull(phoneno)) {
                if (Utility.isNotNull(amou)) {
                    if (Utility.isNotNull(narra)) {
                        if (Utility.isNotNull(ednamee)) {
                            if (Utility.isNotNull(ednumbb)) {
                                if (Utility.isNotNull(acname)) {
                             /*   new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("Other Wallets Transaction")
                                        .setContentText("Are you sure you want to proceed with this Transaction? \n \n" +
                                                " Mobile Money Operator: " + walletname + " \n  Phone Number : " + phoneno + "  \n Amount: " + amou + " Naira \n Narration: " + narra)
                                        .setConfirmText("Confirm")
                                        .setCancelText("Cancel")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismissWithAnimation();
                                            }
                                        })
                                        .show();*/

                                Bundle b = new Bundle();
                                b.putString("walletname", walletname);
                                b.putString("walletcode", walletcode);
                                b.putString("wphoneno", phoneno);
                                b.putString("txtname", acname);
                                b.putString("amou", amou);
                                b.putString("narra", narra);
                                b.putString("ednamee", ednamee);
                                b.putString("ednumbb", ednumbb);

                                Fragment fragment = new ConfirmOtherWallet();

                                fragment.setArguments(b);
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                //  String tag = Integer.toString(title);
                                fragmentTransaction.replace(R.id.container_body, fragment, "Confirm Other Wallet");
                                fragmentTransaction.addToBackStack("Confirm Other Wallet");
                                ((FMobActivity) getActivity())
                                        .setActionBarTitle("Confirm Other Wallet");
                                fragmentTransaction.commit();
                            }  else {
                                Toast.makeText(
                                        getActivity(),
                                        "Please enter a valid account number",
                                        Toast.LENGTH_LONG).show();
                            }
                            }else {
                                Toast.makeText(
                                        getActivity(),
                                        "Please enter a valid value for Depositor Number",
                                        Toast.LENGTH_LONG).show();
                            }
                        } else {
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
                } else {
                    Toast.makeText(
                            getActivity(),
                            "Please enter a valid value for Amount",
                            Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(
                        getActivity(),
                        "Please enter a value for Wallet Phone Number",
                        Toast.LENGTH_LONG).show();
            }
            } else {
                Toast.makeText(
                        getActivity(),
                        "Please select a mobile money operator",
                        Toast.LENGTH_LONG).show();
            }
        }
        if (view.getId() == R.id.textVipp) {
            //   SetDialog("Select Bank");

            Fragment  fragment = new OtherWalletsPage();


            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //  String tag = Integer.toString(title);
            fragmentTransaction.replace(R.id.container_body, fragment,"Select Wallets");
            fragmentTransaction.addToBackStack("Select Wallets");
            ((FMobActivity)getActivity())
                    .setActionBarTitle("Select Wallets");
            fragmentTransaction.commit();

        }

        if (view.getId() == R.id.tv2) {
            Fragment  fragment = new FTMenu();


            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //  String tag = Integer.toString(title);
            fragmentTransaction.replace(R.id.container_body, fragment,"Confirm Transfer");
            fragmentTransaction.addToBackStack("Confirm Transfer");
            ((FMobActivity)getActivity())
                    .setActionBarTitle("Confirm Transfer");
            fragmentTransaction.commit();
        }
    }


    public void SetDialog(String msg,String title){
        new MaterialDialog.Builder(getActivity())
                .title(title)
                .content(msg)

                .negativeText("Close")
                .show();
    }

    private void NameInquirySec(String params) {

        String endpoint= "transfer/nameenq.action";


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
                                acname = plan.optString("accountName");

                                Toast.makeText(
                                        getActivity(),
                                        " Name: " + acname,
                                        Toast.LENGTH_LONG).show();
                                accountoname.setText(acname);
                            } else {

                                Toast.makeText(
                                        getActivity(),
                                        "This is not a valid account number.Please check again",
                                        Toast.LENGTH_LONG).show();


                            }

                        }else{
                            Toast.makeText(
                                    getActivity(),
                                    responsemessage,
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(
                                getActivity(),
                                "There was an error processing your request ",
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
                prgDialog.dismiss();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Log error here since request failed
                Log.v("Throwable error",t.toString());
                Toast.makeText(
                        getActivity(),
                        "There was a error processing your request",
                        Toast.LENGTH_LONG).show();
                prgDialog.dismiss();
            }
        });

    }
}
