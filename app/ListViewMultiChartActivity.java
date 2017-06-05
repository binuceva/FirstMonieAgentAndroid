
package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import listviewitems.BarChartItem;
import listviewitems.ChartItem;
import listviewitems.LineChartItem;
import listviewitems.PieChartItem;

/**
 * Demonstrates the use of charts inside a ListView. IMPORTANT: provide a
 * specific height attribute for the chart inside your listview-item
 * 
 * @author Philipp Jahoda
 */
public class ListViewMultiChartActivity extends ActionBarActivity {
    private Toolbar mToolbar;
    ProgressDialog prgDialog;
    SessionManagement session;
    BarData newdata,collectdata;
    ListView lv;
    PieData pidata;

    String serv = null,title = null;
    ArrayList<ChartItem> list = new ArrayList<ChartItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_listview_chart);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        String newse = null;

        getSupportActionBar().setTitle(" Statistics");
        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Loading Dashboard...");
        session = new SessionManagement(this);
        prgDialog.setCancelable(false);
    /*ListView lv = (ListView) findViewById(R.id.listView1);*/
       lv  = (ListView) findViewById(R.id.lv);
 //checkInternetConnection();
      /*  checkPiConnect();
        checkCTodayConn();*/
        ArrayList<ChartItem> list = new ArrayList<ChartItem>();

        // 30 items
        for (int i = 0; i < 3; i++) {

            if(i % 3 == 0) {
                list.add(new LineChartItem(generateDataLine(i + 1), getApplicationContext()));
            } else if(i % 3 == 1) {
                list.add(new BarChartItem(generateDataBar(i + 1), getApplicationContext()));
            } else if(i % 3 == 2) {
                list.add(new PieChartItem(generateDataPie(i + 1), getApplicationContext()));
            }
        }

        ChartDataAdapter cda = new ChartDataAdapter(getApplicationContext(), list);
        lv.setAdapter(cda);




    }

    /** adapter that supports 3 different item types */
    private class ChartDataAdapter extends ArrayAdapter<ChartItem> {
        
        public ChartDataAdapter(Context context, List<ChartItem> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getItem(position).getView(position, convertView, getContext());
        }
        
        @Override
        public int getItemViewType(int position) {           
            // return the views type
            return getItem(position).getItemType();
        }
        
        @Override
        public int getViewTypeCount() {
            return 3; // we have 3 different item-types
        }
    }
    
    /**
     * generates a random ChartData object with just one DataSet
     * 
     * @return
     */
    private LineData generateDataLine(int cnt) {

        ArrayList<Entry> e1 = new ArrayList<Entry>();
        int val = 10;
        for (int i = 0; i < 4; i++) {
            e1.add(new Entry(val, i));

            val+=10;
        }

        LineDataSet d1 = new LineDataSet(e1, "Services");
        d1.setLineWidth(2.5f);
        d1.setCircleSize(4.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(false);
        
        ArrayList<Entry> e2 = new ArrayList<Entry>();

        for (int i = 0; i < 4; i++) {
            e2.add(new Entry(e1.get(i).getVal() - 30, i));
        }

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Mobile Money");
        xVals.add("Kenya Power");
        xVals.add("EFT");
        xVals.add("Airtime Top Up");
        
        ArrayList<LineDataSet> sets = new ArrayList<LineDataSet>();
        sets.add(d1);

        
        LineData cd = new LineData(xVals, sets);
        return cd;
    }
    
    /**
     * generates a random ChartData object with just one DataSet
     * 
     * @return
     */
    private BarData generateDataBar(int cnt) {

       /* ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 0; i < 12; i++) {
            entries.add(new BarEntry((int) (Math.random() * 70) + 30, i));
        }

        BarDataSet d = new BarDataSet(entries, "New DataSet " + cnt);
        d.setBarSpacePercent(20f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setHighLightAlpha(255);
        
        BarData cd = new BarData(getMonths(), d);

        return cd;*/
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Mobile Money");
        xVals.add("Kenya Power");
        xVals.add("EFT");
        xVals.add("Airtime Top Up");


        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        int val = 10;
        for (int i = 0; i < 4; i++) {

            yVals1.add(new BarEntry(val, i));
            val+=10;
        }

        BarDataSet set1 = new BarDataSet(yVals1, "Services");
        set1.setBarSpacePercent(35f);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        return data;
    }
    
    /**
     * generates a random ChartData object with just one DataSet
     * 
     * @return
     */
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
        PieDataSet dataSet = new PieDataSet(yVals1, "Services");
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

        PieData data = new PieData(xVals, dataSet);
        // data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);

        data.setValueTextColor(Color.WHITE);
        return  data;
    }
    
    private ArrayList<String> getQuarters() {
        
        ArrayList<String> q = new ArrayList<String>();
        q.add("1st Quarter");
        q.add("2nd Quarter");
        q.add("3rd Quarter");
        q.add("4th Quarter");
        
        return q;
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
    private boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            //	new SendTask().execute();
            registerUser();

            invokeCToday();
            invokePI();

            //	RegTest();
            return true;
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "No Internet Connection. Please check your internet setttings",
                    Toast.LENGTH_LONG).show();
            return false;
        }
    }
    public void registerUser(){

            invokeWS();




    }
    public void invokeWS( ){
        // Show Progress Dialog
        prgDialog.show();

        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        HashMap<String, String> nurl = session.getNetURL();
        String newurl = nurl.get(SessionManagement.NETWORK_URL);
        String url = newurl+"/countymobileapi/rest/staff/collectweek?&service="+serv;

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
                prgDialog.hide();
                try {
                    // JSON Object
                    Log.v("Collect Week response:", response);
                    JSONObject obj = new JSONObject(response);
                    JSONArray js = obj.getJSONArray("reports");
                    if(obj.optString("message").equals("SUCCESS")) {
                        ArrayList<String> xVals = new ArrayList<String>();
                        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
                        if(js.length() > 0){


                            JSONObject json_data = null;
                            for (int i = 0; i < js.length() ; i++) {
                                json_data = js.getJSONObject(i);
                                Log.v("Bar Chart Data", Integer.toString(i));
                                String rpdate = json_data.getString("Rpdate");
                                Log.v("Rpdate is", rpdate);
                               String newd = getDateTimeStamp(rpdate);
                                String rpamo = json_data.getString("Rpamo");
                                 xVals.add(newd);
                                yVals1.add(new BarEntry(Integer.parseInt(rpamo), i));

                            }


                            BarDataSet set1 = new BarDataSet(yVals1, "DataSet");
                            set1.setBarSpacePercent(35f);

                            ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
                            dataSets.add(set1);

                            BarData data = new BarData(xVals, dataSets);
                            newdata = data;
                        }else{
                            xVals.add("User");
                            yVals1.add(new BarEntry(0, 0));
                            BarDataSet set1 = new BarDataSet(yVals1, "DataSet");
                            set1.setBarSpacePercent(35f);

                            ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
                            dataSets.add(set1);

                            BarData data = new BarData(xVals, dataSets);
                            newdata = data;
                        }
                      //  list.add(new BarChartItem(newdata, getApplicationContext(),"Collections This Week"));

                        ChartDataAdapter cda = new ChartDataAdapter(getApplicationContext(), list);
                        lv.setAdapter(cda);


                    }
                    // Else display error message
                    else{
                        Toast.makeText(getApplicationContext(), "There is no data available for the Bar Chart Collections Per Week", Toast.LENGTH_LONG).show();
                        //Toast.makeText(getApplicationContext(), obj.optString("message"), Toast.LENGTH_LONG).show();


                    } }catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
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
                prgDialog.hide();
                // When Http response code is '404'
                if(statusCode == 404){
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(getApplicationContext(), " The device has not successfully connected to server. Please check your internet settings", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public static String getDateTimeStamp(String tdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyy ");
        Date convertedCurrentDate = null;
        try {
            convertedCurrentDate = sdf.parse(tdate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String strDate = sdf2.format(convertedCurrentDate);
        return strDate;
    }
    private boolean checkPiConnect() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            //	new SendTask().execute();
            invokePI();
            //	RegTest();
            return true;
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "No Internet Connection. Please check your internet setttings",
                    Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public void invokePI( ){
        // Show Progress Dialog
        prgDialog.show();

        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        HashMap<String, String> nurl = session.getNetURL();
        String newurl = nurl.get(SessionManagement.NETWORK_URL);
        String url = newurl+"/countymobileapi/rest/staff/paymodes?&service="+serv;

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
                prgDialog.hide();
                try {
                    // JSON Object
                    Log.v("response..:", response);
                    JSONObject obj = new JSONObject(response);
                    JSONArray js = obj.getJSONArray("reports");
                    if(obj.optString("message").equals("SUCCESS")) {
                        ArrayList<String> xVals = new ArrayList<String>();
                        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
                        if(js.length() > 0){


                            JSONObject json_data = null;
                            for (int i = 0; i < js.length() ; i++) {
                                json_data = js.getJSONObject(i);
                                Log.v("PieChart Data", Integer.toString(i));
                                String rptype = json_data.getString("Rptype");

                                String rpamo = json_data.getString("Rpamo");
                                xVals.add(rptype);
                                yVals1.add(new Entry(Integer.parseInt(rpamo), i));

                            }

                            PieDataSet dataSet = new PieDataSet(yVals1, "Payment Modes");
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

                            PieData data = new PieData(xVals, dataSet);
                            // data.setValueFormatter(new PercentFormatter());
                            data.setValueTextSize(11f);

                            data.setValueTextColor(Color.WHITE);


                           pidata = data;
                        }else if(js == null){
                            Log.v("JS Null", "JS Nll");
                            xVals.add("POS");
                            yVals1.add(new Entry(0, 0));
                            PieDataSet dataSet = new PieDataSet(yVals1, "Payment Modes");
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

                            PieData data = new PieData(xVals, dataSet);
                            // data.setValueFormatter(new PercentFormatter());
                            data.setValueTextSize(11f);

                            data.setValueTextColor(Color.WHITE);


                            pidata = data;
                        }
                        list.add(new PieChartItem(pidata, getApplicationContext()));
                        ChartDataAdapter cda = new ChartDataAdapter(getApplicationContext(), list);
                        lv.setAdapter(cda);

                    }
                    // Else display error message
                    else{
                        Toast.makeText(getApplicationContext(), "There is no data available for the Payment Modes Pie Chart", Toast.LENGTH_LONG).show();
                  } }catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
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
                prgDialog.hide();
                // When Http response code is '404'
                if(statusCode == 404){
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(getApplicationContext(), " The device has not successfully connected to server. Please check your internet settings", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean checkCTodayConn() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            //	new SendTask().execute();
            invokeCToday();
            //	RegTest();
            return true;
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "No Internet Connection. Please check your internet setttings",
                    Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public void invokeCToday( ){
        // Show Progress Dialog
        prgDialog.show();

        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        HashMap<String, String> nurl = session.getNetURL();
        String newurl = nurl.get(SessionManagement.NETWORK_URL);
        String url = newurl+"/countymobileapi/rest/staff/collectortoday?&service="+serv;

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
                prgDialog.hide();
                try {
                    // JSON Object
                    Log.v("response..:", response);
                    JSONObject obj = new JSONObject(response);
                    JSONArray js = obj.getJSONArray("reports");
                    if(obj.optString("message").equals("SUCCESS")) {
                        ArrayList<String> xVals = new ArrayList<String>();
                        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
                        if(js.length() > 0){


                            JSONObject json_data = null;
                            for (int i = 0; i < js.length() ; i++) {
                                json_data = js.getJSONObject(i);
                                Log.v("Bar Chart Data", Integer.toString(i));
                                String rpuser = json_data.getString("Rpuser");

                                String rpamo = json_data.getString("Rpamo");
                                xVals.add(rpuser);
                                yVals1.add(new BarEntry(Integer.parseInt(rpamo), i));

                            }

                            xVals.add("User");
                            yVals1.add(new BarEntry(0, 0));
                            BarDataSet set1 = new BarDataSet(yVals1, "DataSet");
                            set1.setBarSpacePercent(35f);

                            ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
                            dataSets.add(set1);

                            BarData data = new BarData(xVals, dataSets);
                            collectdata = data;
                        }else{
                            BarDataSet set1 = new BarDataSet(yVals1, "DataSet");
                            set1.setBarSpacePercent(35f);

                            ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
                            dataSets.add(set1);

                            BarData data = new BarData(xVals, dataSets);
                            collectdata = data;
                        }
                      //  list.add(new BarChartItem(collectdata, getApplicationContext(),"Collections Today Per Collector"));

                        ChartDataAdapter cda = new ChartDataAdapter(getApplicationContext(), list);
                        lv.setAdapter(cda);


                    }
                    // Else display error message
                    else{
                        Toast.makeText(getApplicationContext(), "There is no data available for the Bar Chart Collections Per Week", Toast.LENGTH_LONG).show();
                        //Toast.makeText(getApplicationContext(), obj.optString("message"), Toast.LENGTH_LONG).show();


                    } }catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
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
                prgDialog.hide();
                // When Http response code is '404'
                if(statusCode == 404){
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(getApplicationContext(), " The device has not successfully connected to server. Please check your internet settings", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
