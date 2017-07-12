package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import adapter.FavList;
import adapter.TopFavAdapter;
import model.GetAirtimeBillers;
import model.GetAirtimeBillersData;
import rest.ApiClient;
import rest.ApiInterface;
import rest.ApiSecurityClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import security.SecurityLayer;


public class AirtimeTransf extends Fragment implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button2) {

            String phoneno = phonenumb.getText().toString();
            String amou = txtamount.getText().toString();
String mnop = sp1.getSelectedItem().toString();

            int spinpos = sp1.getSelectedItemPosition();
            String billid = planetsList.get(spinpos).getBillerId();
            String serviceid = planetsList.get(spinpos).getSId();

            if (Utility.checkInternetConnection(getActivity())) {


                if (Utility.isNotNull(phoneno)) {
                    if (Utility.isNotNull(amou)) {
                       // if ((planetsList.size() - 1) == sp1.getSelectedItemPosition()) {
if(!(serviceid.equals("0000"))){

                            Bundle b = new Bundle();
                            b.putString("mobno", phoneno);
                            b.putString("amou", amou);
                            b.putString("telcoop", mnop);

                            b.putString("billid", billid);
                            b.putString("serviceid", serviceid);
                            Fragment fragment = new ConfirmAirtime();

                            fragment.setArguments(b);
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            //  String tag = Integer.toString(title);
                            fragmentTransaction.replace(R.id.container_body, fragment, "Confirm Airtime");
                            fragmentTransaction.addToBackStack("Confirm Airtime");
                            ((FMobActivity) getActivity())
                                    .setActionBarTitle("Confirm Airtime");
                            fragmentTransaction.commit();

                       }   else {
                            Toast.makeText(
                                    getActivity(),
                                    "Please select a valid mobile network operator",
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
    }
    List<GetAirtimeBillersData> planetsList = new ArrayList<GetAirtimeBillersData>();
    EditText amon, edacc,pno,txtamount,txtnarr,phonenumb;
    Spinner sp1, sp5, sp7;
  //  RecyclerView lvbann;
  ArrayAdapter<GetAirtimeBillersData> mobadapt;
    TopFavAdapter FavAdapt;
    TextView tx;
    LinearLayoutManager layoutManager,layoutManager2;
    List<FavList> favlist = new ArrayList<FavList>();
    Button btn2;
    String telcochosen;
    ProgressDialog prgDialog;
    public AirtimeTransf() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.newairtimetp, container, false);


        layoutManager2 = new LinearLayoutManager(getActivity());
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        //layoutManager.scrollToPosition(currPos);

//        lvbann.setLayoutManager(layoutManager2);
      //  Pop();
        prgDialog = new ProgressDialog(getActivity());

        prgDialog.setMessage("Please wait...");
        prgDialog.setCancelable(false);
        sp1 = (Spinner) rootView.findViewById(R.id.spin1);
        btn2 = (Button) rootView.findViewById(R.id.button2);
        btn2.setOnClickListener(this);



        phonenumb = (EditText) rootView.findViewById(R.id.phonenumb);
        txtamount = (EditText) rootView.findViewById(R.id.amount);


        View.OnFocusChangeListener ofcListener = new MyFocusChangeListener();
        phonenumb.setOnFocusChangeListener(ofcListener);
        txtamount.setOnFocusChangeListener(ofcListener);
PopulateAirtime();

        return rootView;
    }


    public void StartChartAct(int i) {


    }

    @Override
    public void onResume() {
        super.onResume();
        // put your code here...

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

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean b) {

        }
    }
public void Pop(){
    favlist.add(new FavList("Airtel", R.drawable.airtel,"ATM",""));
    favlist.add(new FavList("Etisalat", R.drawable.etisalat,"ATM",""));
    favlist.add(new FavList("GLO", R.drawable.glo,"ATM",""));
    favlist.add(new FavList("MTN", R.drawable.mtn,"ATM",""));
    FavAdapt = new TopFavAdapter( getActivity(),favlist);
    //lvbann.setAdapter(FavAdapt);
}



    public void SetPop(){
        planetsList.clear();



        prgDialog.show();

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<GetAirtimeBillers> call = apiService.getAirBillPay("1","CEVA","ERWE0000000001","0000","1");
        call.enqueue(new Callback<GetAirtimeBillers>() {
            @Override
            public void onResponse(Call<GetAirtimeBillers>call, Response<GetAirtimeBillers> response) {
                String responsemessage = response.body().getMessage();

                Log.v("Response Message",responsemessage);

                planetsList = response.body().getResults();
                GetAirtimeBillersData sa = new GetAirtimeBillersData("0000","0000","Select Network Operator");
                planetsList.add(sa);

                if(!(planetsList == null)) {
                    if(planetsList.size() > 0) {
                        Log.v("Get Biller Data Name", planetsList.get(0).getBillerName());
                        Collections.sort(planetsList, new Comparator<GetAirtimeBillersData>(){
                            public int compare(GetAirtimeBillersData d1, GetAirtimeBillersData d2){
                                return d1.getBillerName().compareTo(d2.getBillerName());
                            }
                        });
                      //  Collections.swap(planetsList,0,planetsList.size()-1);

                        mobadapt = new ArrayAdapter<GetAirtimeBillersData>(getActivity(), android.R.layout.simple_spinner_item, planetsList);
                        mobadapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
sp1.setAdapter(mobadapt);
                        sp1.setSelection(planetsList.size() -1);
                    }else{
                        Toast.makeText(
                                getActivity(),
                                "No airtime billers available  ",
                                Toast.LENGTH_LONG).show();
                    }
                }
                prgDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetAirtimeBillers>call, Throwable t) {
                // Log error here since request failed
                Utility.errornexttoken();
                Toast.makeText(
                        getActivity(),
                        "Throwable Error: "+t.toString(),
                        Toast.LENGTH_LONG).show();
                prgDialog.dismiss();
            }
        });
    }


    private void PopulateAirtime() {

        String endpoint= "billpayment/MMObillers.action";

prgDialog.show();
        String usid = Utility.gettUtilUserId(getActivity());
        String agentid = Utility.gettUtilAgentId(getActivity());
        String params = "1/CEVA/ERWE0000000001/0000/1";
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


                    JSONArray plan = obj.optJSONArray("data");
                    //session.setString(SecurityLayer.KEY_APP_ID,appid);


                    if(!(response.body() == null)) {
                        if (respcode.equals("00")) {

                            Log.v("Response Message", responsemessage);

                            if(plan.length() > 0){


                                JSONObject json_data = null;
                                for (int i = 0; i < plan.length(); i++) {
                                    json_data = plan.getJSONObject(i);
                                    //String accid = json_data.getString("benacid");


                                    String sid = json_data.optString("sid");

                                    String id = json_data.optString("id");
                                    String billerName = json_data.optString("billerName");



                                        planetsList.add(new GetAirtimeBillersData(sid,id,billerName));




                                }
                                if(!(planetsList == null)) {
                                    if(planetsList.size() > 0) {
                                        Log.v("Get Biller Data Name", planetsList.get(0).getBillerName());
                                        Collections.sort(planetsList, new Comparator<GetAirtimeBillersData>(){
                                            public int compare(GetAirtimeBillersData d1, GetAirtimeBillersData d2){
                                                return d1.getBillerName().compareTo(d2.getBillerName());
                                            }
                                        });
                                        //  Collections.swap(planetsList,0,planetsList.size()-1);

                                        mobadapt = new ArrayAdapter<GetAirtimeBillersData>(getActivity(), android.R.layout.simple_spinner_item, planetsList);
                                        mobadapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        sp1.setAdapter(mobadapt);
                                        sp1.setSelection(planetsList.size() -1);
                                    }else{
                                        Toast.makeText(
                                                getActivity(),
                                                "No airtime billers available  ",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                                prgDialog.dismiss();

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
                Log.v("Throwable error",t.toString());
                Utility.errornexttoken();
                Toast.makeText(
                        getActivity(),
                        "There was a error processing your request",
                        Toast.LENGTH_LONG).show();
                prgDialog.dismiss();
            }
        });

    }
}


