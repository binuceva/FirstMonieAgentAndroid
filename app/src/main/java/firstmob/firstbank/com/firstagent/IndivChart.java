package firstmob.firstbank.com.firstagent;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;


public class IndivChart extends Fragment implements  View.OnClickListener {




  RecyclerView lvpie;
    ChartFragAdapter mAdapter;

    private static final long ANIM_VIEWPAGER_DELAY = 5000;
    private static final long ANIM_VIEWPAGER_DELAY_USER_VIEW = 10000;
    boolean stopSliding = false;
    private Runnable animateViewPager;
    private Handler handler;
    ViewPager mPager;
    String serv,pfrom,pto,serv2;
    SessionManagement session;
    ProgressBar prgDialog2;
    LinearLayout layl;
    RelativeLayout ryl,rlcash;
    TextView ttot,cashtot;


    public IndivChart() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.indivchart, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
           String type = bundle.getString("serv");
            serv2 = type;
            serv = type;
           pfrom = bundle.getString("pfrom");
            pto = bundle.getString("pto");
            if (type.equals("Airtime TopUp Self")) {
                type = "ARTISL";
            }
            if (type.equals("Airtime TopUp Other")) {
                type = "ARTIOT";
            }
            if (type.equals("Mobile Money")) {
                type = "MPESAB2C";
            }

            if (type.equals("Funds Transfer")) {
                type = "FTWIBK";
            }
            if (type.equals("School Fees")) {
                type = "SCHFEE";
            }
            if (type.equals("Billers")) {
                type = "CELBILL";
            }
            if (type.equals("Zuku")) {
                type = "CELLZUK";
            }
            if (type.equals("Airtel Money")) {
                type = "AIRTELC2B";
            }
           // serv = type;

        }
        layl = (LinearLayout) rootView.findViewById(R.id.layl);
        ryl = (RelativeLayout) rootView.findViewById(R.id.rl2);
        rlcash = (RelativeLayout) rootView.findViewById(R.id.rlcash);
        ttot = (TextView) rootView.findViewById(R.id.trantot);
        cashtot = (TextView) rootView.findViewById(R.id.cashcoll);
        session = new SessionManagement(getActivity());
rlcash.setOnClickListener(this);
        prgDialog2 = (ProgressBar) rootView.findViewById(R.id.progressBar);
         mPager = (ViewPager) rootView.findViewById(R.id.pager);
        mPager.setOffscreenPageLimit(3);

        mAdapter = new ChartFragAdapter(this.getChildFragmentManager(),serv,pfrom,pto);

        if(mPager.getAdapter() == null) {

            mPager.setAdapter(mAdapter);

            runnable(2);
        }

        mPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction()) {

                    case MotionEvent.ACTION_CANCEL:
                        break;

                    case MotionEvent.ACTION_UP:
                        // calls when touch release on ViewPager

                        stopSliding = false;
                        runnable(2);
                        handler.postDelayed(animateViewPager,
                                ANIM_VIEWPAGER_DELAY_USER_VIEW);

                        break;

                    case MotionEvent.ACTION_MOVE:
                        // calls when ViewPager touch
                        if (handler != null && stopSliding == false) {
                            stopSliding = true;
                            handler.removeCallbacks(animateViewPager);
                        }
                        break;
                }
                return false;
            }
        });
   //     checkConnStats();
        cashtot.setText("21309 Naira");


        ttot.setText("2");
        return rootView;
    }

    public void runnable(final int size) {
        handler = new Handler();
        animateViewPager = new Runnable() {
            public void run() {
                if (!stopSliding) {
                    if (mPager.getCurrentItem() == size - 1) {
                        mPager.setCurrentItem(0);
                    } else {
                        mPager.setCurrentItem(
                                mPager.getCurrentItem() + 1, true);
                    }
                    handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
                }
            }
        };
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
if(view.getId() == R.id.rlcash){
    Bundle b  = new Bundle();


    Fragment  fragment = new StatTrans();
    b.putString("serv",serv2);
    b.putString("pfrom",pfrom);
    b.putString("pto",pto);
    fragment.setArguments(b);
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    //  String tag = Integer.toString(title);
    fragmentTransaction.replace(R.id.container_body, fragment,serv2);
    fragmentTransaction.addToBackStack(serv2);
    ((MainActivity)getActivity())
            .setActionBarTitle(serv2);
    fragmentTransaction.commit();}
    }


    private PieData generateDataPie(int cnt) {

        /*ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int i = 0; i < 4; i++) {
            entries.add(new Entry((int) (Math.random() * 70) + 30, i));
        }

        PieDataSet d = new PieDataSet(entries, "");

        // space between slices
        d.setSliceSpace(2f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);

        PieData cd = new PieData(getQuarters(), d);
        return cd;*/
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();


        xVals.add("Mobile Money");
        xVals.add("Kenya Power");
        xVals.add("EFT");
        xVals.add("Airtime Top Up");

        int val = 10;
        for (int i = 0; i < 4; i++) {

            yVals1.add(new Entry(val, i));
            val+=10;
        }
        PieDataSet dataSet = null;
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = null;
        // data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);

        data.setValueTextColor(Color.WHITE);
        return  data;
    }

    private LineData generateDataLine(int cnt) {

        ArrayList<Entry> e1 = new ArrayList<Entry>();
        int val = 10;
        for (int i = 0; i < 12; i++) {
            e1.add(new Entry((int) (Math.random() * 70) + 30, i));
        }

        LineDataSet d1 = new LineDataSet(e1, "Services");
        d1.setLineWidth(2.5f);
        d1.setCircleSize(4.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(false);

        ArrayList<Entry> e2 = new ArrayList<Entry>();

        for (int i = 0; i < 4; i++) {
          //  e2.add(new Entry(e1.get(i).getVal() - 30, i));
        }

        ArrayList<String> xVals = getMonths();


        ArrayList<LineDataSet> sets = new ArrayList<LineDataSet>();
        sets.add(d1);


        LineData cd = null;
        return cd;
    }
    private ArrayList<String> getMonths() {

        ArrayList<String> m = new ArrayList<String>();
        m.add("Jan");
        m.add("Feb");
        m.add("Mar");
        m.add("Apr");
        m.add("May");
        m.add("Jun");
        m.add("Jul");
        m.add("Aug");
        m.add("Sep");
        m.add("Okt");
        m.add("Nov");
        m.add("Dec");

        return m;
    }

    private boolean checkConnStats() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            //	new SendTask().execute();
            HashMap<String, String> nurl = session.getMobNo();
            String newurl = nurl.get(SessionManagement.KEY_MOBILE);
            invokeFX(newurl);
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

    public void invokeFX( String mno){
        // Show Progress Dialog



        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(35000);
        HashMap<String, String> nurl = session.getNetURL();
        String newurl = nurl.get(SessionManagement.NETWORK_URL);
        // String url = newurl+"/natmobileapi/rest/customer/ministat?acctno="+acc+"";

       // String url =newurl+"/natmobileapi/rest/customer/servstats?mobno="+mno+"&pfrom="+pfrom+"&pto="+pto+"&serv="+serv+"";
        String url = ApplicationConstants.NET_URL+ApplicationConstants.AND_ENPOINT+"servstats/"+mno+"/"+pfrom+"/"+pto+"/"+serv;

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
                        String total = obj.optString("total");
                        if(total == null || total.equals("")){
                            total = "0";
                        }

                        cashtot.setText(total+"Kshs");

                        JSONArray js = obj.getJSONArray("servstats");
                        ttot.setText(Integer.toString(js.length()));
                        Log.v("JSON Aray", js.toString());

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

}
