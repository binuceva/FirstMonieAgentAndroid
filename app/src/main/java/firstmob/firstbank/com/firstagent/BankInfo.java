package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapter.Forex;
import adapter.ForexAdapter;
import adapter.Products;
import adapter.ProductsAdapter;


public class BankInfo extends Fragment implements View.OnClickListener {


    GridView gridView;
    /*List<Dashboard> planetsList = new ArrayList<Dashboard>();
    DashboardAdapter aAdpt;*/
EditText etfrom,etto;
    CheckBox chk1,chk2,chk3;
    LinearLayout ly1,ly2,ly3;
    RelativeLayout rl1,rl2,rl3;
    Button ok;
    ListView lvfx;

    ProgressDialog prgDialog;
    ProgressBar prgDialog2;
    List<Products> planetsList = new ArrayList<Products>();
    ProductsAdapter aAdpt;
    String finalfx;
    LinearLayout layl;
    RelativeLayout ryl;
    LinearLayout lycur,lyfx,lylo;

    List<Forex> fxList = new ArrayList<Forex>();
    ForexAdapter fxAdap;
    TextView bena,bencon;
    SessionManagement session;
    String bna,bmo;
    Spinner sp1;


    public BankInfo() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.bank_info, container, false);
        session = new SessionManagement(getActivity());
        etfrom = (EditText) rootView.findViewById(R.id.ed);
        etto = (EditText) rootView.findViewById(R.id.edto);
       sp1 = (Spinner) rootView.findViewById(R.id.from);
        layl = (LinearLayout) rootView.findViewById(R.id.layl);
        ryl = (RelativeLayout) rootView.findViewById(R.id.rl2);

        lycur = (LinearLayout) rootView.findViewById(R.id.lycurcal);
       lyfx = (LinearLayout) rootView.findViewById(R.id.lyfrx);
        lylo = (LinearLayout) rootView.findViewById(R.id.lylcal);
        boolean chkftra = session.checkPersWidg("CURC");
        boolean chkmbm = session.checkPersWidg("FRX");
        boolean chkpas = session.checkPersWidg("LOC");

        boolean atopup = session.checkPersWidg("STC");
        if(chkftra == false){
           lycur.setVisibility(View.GONE);
        }
        if(chkmbm == false){
            lyfx.setVisibility(View.GONE);
        }
        if(chkpas == false){
            lylo.setVisibility(View.GONE);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
               getActivity(), R.array.currf, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       sp1.setAdapter(adapter);



        lvfx = (ListView) rootView.findViewById(R.id.lvfx);
        prgDialog2 = (ProgressBar) rootView.findViewById(R.id.progressBar);

        // Set Cancelable as False

        planetsList.clear();


        checkInternetConnectionFx();
        etfrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if((!(s.toString() == null))&& (!(s.toString().equals(""))) && s.length() > 0 )  {
                    String from = sp1.getSelectedItem().toString();
                    Double amo = Double.parseDouble(s.toString());
                    CurrCalc(from, "KES", amo);
                }else{
                    etto.setText("");
                }

            }
        });

      /*  ly1 = (LinearLayout) rootView.findViewById(R.id.blayid);
        ly2 = (LinearLayout) rootView.findViewById(R.id.bpers);
        ly3 = (LinearLayout) rootView.findViewById(R.id.cplayid);

        rl1 = (RelativeLayout) rootView.findViewById(R.id.persb);
        rl2 = (RelativeLayout) rootView.findViewById(R.id.busb);
        rl3 = (RelativeLayout) rootView.findViewById(R.id.corpb);

        rl1.setOnClickListener(this);
        rl2.setOnClickListener(this);
        rl3.setOnClickListener(this);*/


        lvfx.setOnTouchListener(new ListView.OnTouchListener()

                              {
                                  @Override
                                  public boolean onTouch (View v, MotionEvent event){
                                      int action = event.getAction();
                                      switch (action) {
                                          case MotionEvent.ACTION_DOWN:
                                              // Disallow ScrollView to intercept touch events.
                                              v.getParent().requestDisallowInterceptTouchEvent(true);
                                              break;

                                          case MotionEvent.ACTION_UP:
                                              // Allow ScrollView to intercept touch events.
                                              v.getParent().requestDisallowInterceptTouchEvent(false);
                                              break;
                                      }

                                      // Handle ListView touch events.
                                      v.onTouchEvent(event);
                                      return true;
                                  }
                              }

        );
        return rootView;
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
       /* if (view.getId() == R.id.busb) {
              ly1.setVisibility(View.VISIBLE);
            ly3.setVisibility(View.GONE);
            ly2.setVisibility(View.GONE);
        }
        if (view.getId() == R.id.persb) {
            ly1.setVisibility(View.GONE);
            ly3.setVisibility(View.GONE);
            ly2.setVisibility(View.VISIBLE);
        }
        if (view.getId() == R.id.corpb) {
            ly1.setVisibility(View.GONE);
            ly3.setVisibility(View.VISIBLE);
            ly2.setVisibility(View.GONE);
        }*/
    }






    private boolean checkInternetConnectionFx() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            //	new SendTask().execute();
            invokeFX();
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

    public void invokeFX( ){
        // Show Progress Dialog



        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(35000);
        HashMap<String, String> nurl = session.getNetURL();
        String newurl = nurl.get(SessionManagement.NETWORK_URL);
        // String url = newurl+"/natmobileapi/rest/customer/ministat?acctno="+acc+"";

        String url =newurl+"/natmobileapi/rest/customer/loadfx";
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
                ryl.setVisibility(View.GONE);
                layl.setVisibility(View.VISIBLE);
                try {
                    // JSON Object
                    Log.v("response..:", response);
                    JSONObject obj = new JSONObject(response);
                    if(obj.optString("message").equals("SUCCESS")){
                        JSONArray js = obj.getJSONArray("fxdetails");
                        Log.v("JSON Aray", js.toString());
                        if(js.length() > 0){

                          finalfx = js.toString();
                            fxList.clear();

                           // CurrCalc("GBP", "KES", 1);
                            SetForex("USD");
                            SetForex("EUR");
                            SetForex("GBP");
                            SetForex("ZAR");
                            fxAdap = new ForexAdapter(fxList, getActivity());

                            lvfx.setAdapter(fxAdap);
                        }
                    }
                    // Else display error message
                    else{
                        Toast.makeText(getActivity(), " The device has not successfully connected to server. Please check your internet settings", Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getActivity(), " The device has not successfully connected to server. Please check your internet settings", Toast.LENGTH_LONG).show();
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
                ryl.setVisibility(View.GONE);
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
                    Toast.makeText(getActivity(), " The device has not successfully connected to server. Please check your internet settings", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void CurrCalc(String from,String to,double amo){
        try{
            JSONArray js2 = new JSONArray(finalfx);
            if (js2.length() > 0) {


                JSONObject json_data2 = null;
                for (int p = 0; p < js2.length(); p++) {
                    json_data2 = js2.getJSONObject(p);
                    //String accid = json_data.getString("benacid");

                    String exratetyp = json_data2.optString("exratetyp");
                    String fromc = json_data2.optString("fromc");
                    String toc = json_data2.optString("toc");
                    String multd = json_data2.optString("multdiv");
                    String rate = json_data2.optString("rate");

                   if(exratetyp.equals("BUY01") && fromc.equals(from) && toc.equals(to)){
                       double result = 0;
                       double newrt = Double.parseDouble(rate);
                       if(multd.equals("D")){
                           result = amo/newrt;
                       }
                       else if(multd.equals("M")){
                           result = amo*newrt;
                       }
                    etto.setText( Double.toString(result));

                   }
                }
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

    }

    public void SetForex(String curr){
        try{
            JSONArray js2 = new JSONArray(finalfx);
            if (js2.length() > 0) {


                JSONObject json_data2 = null;
                String sell = null;
                String buy = null;
                for (int p = 0; p < js2.length(); p++) {
                    json_data2 = js2.getJSONObject(p);
                    //String accid = json_data.getString("benacid");

                    String exratetyp = json_data2.optString("exratetyp");
                    String fromc = json_data2.optString("fromc");
                    String toc = json_data2.optString("toc");
                    String multd = json_data2.optString("multdiv");
                    String rate = json_data2.optString("rate");
if(fromc.equals(curr) && toc.equals("KES")) {
    if (exratetyp.equals("BUY01")) {
      double rt =   Double.parseDouble(rate);
        double v = (double) Math.round(rt * 100) / 100;

        sell = Double.toString(v);

    }
    if (exratetyp.equals("SEL01")) {
        double rt =   Double.parseDouble(rate);
        double v = (double) Math.round(rt * 100) / 100;

        buy = Double.toString(v);

    }
}
                }
                //fxList.add(new Forex(curr,buy,sell));

            }
        }catch(JSONException e){
            e.printStackTrace();
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

}
