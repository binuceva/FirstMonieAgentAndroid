package firstmob.firstbank.com.firstagent;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import adapter.TxnAdapter;
import adapter.TxnList;
import model.GetCommPerfData;
import model.GetSummaryData;
import rest.ApiClient;
import rest.ApiInterface;
import rest.ApiSecurityClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import security.SecurityLayer;


public class SelChart extends Fragment implements View.OnClickListener,OnChartValueSelectedListener,DatePickerDialog.OnDateSetListener, OnChartGestureListener {

    Spinner sp1;

    private BarChart mChart;
    private LineChart mLineChart;
    private Typeface mTf;
    List<TxnList> planetsList = new ArrayList<TxnList>();
    TxnAdapter aAdpt;
    ListView lv, lvpie;
    Button ok, calendar;
    public String finalfx, finpfrom, finpto;
    ProgressDialog prgDialog2;
    RadioButton r1, r2, r3;
    LinearLayout layl;
    RelativeLayout ryl;
    ArrayList<PieEntry> finentry = new ArrayList<PieEntry>();
    ArrayList<BarEntry> barent = new ArrayList<BarEntry>();
    List<GetSummaryData> temp = new ArrayList<GetSummaryData>();
    List<GetCommPerfData> cperfdata = new ArrayList<GetCommPerfData>();
    SessionManagement session;
    private TextView emptyView, fromdate, endate;
    TextView succtrans;
    String fromd,endd;
    PieDataSet dataSet;
    ArrayList<String> labels = new ArrayList<>();
    Legend l ;

    public SelChart() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.selchart, container, false);
        mTf = Typeface.createFromAsset(getActivity().getAssets(), "musleo.ttf");
        layl = (LinearLayout) rootView.findViewById(R.id.layl);
        ryl = (RelativeLayout) rootView.findViewById(R.id.rl2);

        lv = (ListView) rootView.findViewById(R.id.lv);
        emptyView = (TextView) rootView.findViewById(R.id.empty_view);
        fromdate = (TextView) rootView.findViewById(R.id.bnamebn);
        succtrans = (TextView) rootView.findViewById(R.id.succtrans);
        ok = (Button) rootView.findViewById(R.id.button2);
        calendar = (Button) rootView.findViewById(R.id.button4);
        ok.setOnClickListener(this);
        calendar.setOnClickListener(this);
        session = new SessionManagement(getActivity());

        lvpie = (ListView) rootView.findViewById(R.id.lvpie);

        prgDialog2 = new ProgressDialog(getActivity());
        prgDialog2.setMessage("Loading ....");
        // Set Cancelable as False
        session = new SessionManagement(getActivity());

        prgDialog2.setCancelable(false);


        String dated = getToday();
        String datew = getWeek();
        finpfrom = datew;
        finpto = dated;

        mChart = (BarChart) rootView.findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);

        mChart.setDrawBarShadow(false);


     //   mChart.setDescription("Commisions per service");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn


        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        mChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);


        XAxis xAxis = mChart.getXAxis();

        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(7f);




        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(8, false);

        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setStartAtZero(true);

l = mChart.getLegend();



        l.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);

        l.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
        l.setYEntrySpace(5f); // set the space between the legend entries on the y-axis

        // set custom labels and colors

        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });



    //    setBarData();








        mLineChart = (LineChart) rootView.findViewById(R.id.lnchart);
        mLineChart.setOnChartGestureListener(this);
        mLineChart.setOnChartValueSelectedListener(this);
        mLineChart.setDrawGridBackground(false);

        // no description text
     //   mLineChart.setDescription("");
      //  mLineChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable touch gestures
        mLineChart.setTouchEnabled(true);

        // enable scaling and dragging
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(true);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mLineChart.setPinchZoom(true);

        // set an alternative background color
        // mChart.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it


        // x-axis limit line


        XAxis xLineAxis = mLineChart.getXAxis();
        xLineAxis.enableGridDashedLine(10f, 10f, 0f);
        //xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());
        //xAxis.addLimitLine(llXAxis); // add x-axis limit line




        YAxis leftLineAxis = mLineChart.getAxisLeft();
        leftLineAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines

        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)

        mLineChart.getAxisRight().setEnabled(false);

        //mChart.getViewPortHandler().setMaximumScaleY(2f);
        //mChart.getViewPortHandler().setMaximumScaleX(2f);

        // add data
     //   setData(10, 10);

//        mChart.setVisibleXRange(20);
//        mChart.setVisibleYRange(20f, AxisDependency.LEFT);
//        mChart.centerViewTo(20, 50, AxisDependency.LEFT);

        mLineChart.animateX(2500);
        //mChart.invalidate();

        // get the legend (only possible after setting data)


        // // dont forget to refresh the drawing
        // mChart.invalidate();
        return rootView;
    }

    //new onDateSet
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        String date = "You picked the following date: From- " + dayOfMonth + "/" + (++monthOfYear) + "/" + year + " To " + dayOfMonthEnd + "/" + (++monthOfYearEnd) + "/" + yearEnd;
        Calendar calfrom = Calendar.getInstance();
        Calendar calto = Calendar.getInstance();
        calto.set(yearEnd,monthOfYearEnd,dayOfMonthEnd);
        calfrom.set(year,monthOfYear,dayOfMonth);

        if(calfrom.before(calto)) {
            fromdate.setText(date);
            String frmdymonth = Integer.toString(dayOfMonth);
            if (dayOfMonth < 10) {
                frmdymonth = "0" + frmdymonth;
            }
            String frmyear = Integer.toString(year);
            frmyear = frmyear.substring(2, 4);
            fromd = frmdymonth + "-" +(monthOfYear) + "-" + frmyear;
            String frmenddymonth = Integer.toString(dayOfMonthEnd);
            if (dayOfMonthEnd < 10) {
                frmenddymonth = "0" + frmenddymonth;
            }

            String frmendyr = Integer.toString(yearEnd);
            frmendyr = frmendyr.substring(2, 4);
            endd = frmenddymonth + "-" + (monthOfYearEnd) + "-" + frmendyr;
        }else{
            Toast.makeText(
                    getActivity(),
                    "Please ensure the from date is before the after date",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void StartChartAct(int i) {


    }

    @Override
    public void onResume() {
        super.onResume();
        // put your code here...

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                getActivity().finish();

                startActivity(new Intent(getActivity(),FMobActivity.class));
                return true;
        }
        return false;
    }
    @Override
    public void onValueSelected(Entry e, Highlight h) {


    }


    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
        Toast.makeText(getActivity(), "Nothing Selected", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button2) {
            if(Utility.checkInternetConnection(getActivity())) {
                if(Utility.isNotNull(fromd) || Utility.isNotNull(endd)) {
                    loadDataset(fromd, endd);
                }else{
                    Toast.makeText(getActivity(), "Please select appropriate from date and end date", Toast.LENGTH_LONG).show();
                }
            }

        }
        if (view.getId() == R.id.button4) {
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(
                    this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
dpd.setMaxDate(now);
            dpd.show(getFragmentManager(), "Datepickerdialog");


        }
    }




    public void loadDataset(String fromdt,String enddt) {
        prgDialog2.show();
        final PieData[] data = {null};
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String usid = Utility.gettUtilUserId(getActivity());
        String agentid = Utility.gettUtilAgentId(getActivity());
        String mobnoo = Utility.gettUtilMobno(getActivity());
        String params = "1/"+ usid+"/"+agentid+"/9493818389/CMSNRPT/"+fromdt+"/"+enddt;
        Loadd(params);
        /*Call<GetPerf> call = apiService.getPerfData("1", usid, agentid, "9493818389", "TXNRPT", fromdt, enddt);

        call.enqueue(new Callback<GetPerf>() {
            @Override
            public void onResponse(Call<GetPerf> call, Response<GetPerf> response) {

                GetPerfData dataaa = response.body().getResults();

//                                    Log.v("Respnse getResults",datas.toString());
                finentry.clear();
                temp = dataaa.getsummdata();
                cperfdata = dataaa.getResults();
                boolean chktxncnt = false;
                if (!(temp == null)) {

                    for (int i = 0; i < temp.size(); i++) {
                        String status = temp.get(i).getStatus();
                        String amon = temp.get(i).getamount();
                        String txncode = temp.get(i).getTxnCode();
                        String txnname =  Utility.convertTxnCodetoServ(txncode);
                        float dbagcmsn = Float.parseFloat(amon);
                        if(Utility.isNotNull(status)) {
                            if (((status.equals("SUCCESS"))) && (dbagcmsn > 0)) {
                                finentry.add(new PieEntry(dbagcmsn, txnname));
                                chktxncnt = true;
                                Log.v("Entries", dbagcmsn + " Code" + txnname);

                            }
                        }
                    }
                    convertarraytochart();
                    if(chktxncnt == false){
                        Toast.makeText(
                                getActivity(),
                                "There are no transaction records to display",
                                Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(
                            getActivity(),
                            "There are no records to display",
                            Toast.LENGTH_LONG).show();
                }



int counter = 0;
                if (!(cperfdata == null)) {

                    for (int i = 0; i < cperfdata.size(); i++) {
                        String status = cperfdata.get(i).getStatus();
                        String amon = cperfdata.get(i).getAmount();
                        String txncode = cperfdata.get(i).getTxnCode();
                        String txnname =  Utility.convertTxnCodetoServ(txncode);
                        float dbagcmsn = Float.parseFloat(amon);
                        if (((status.equals("SUCCESS"))) && (dbagcmsn > 0)) {
                        counter++;

                        }
                    }


                }
                String sctr = Integer.toString(counter);
                succtrans.setText(sctr);
                prgDialog2.dismiss();
            }

            @Override
            public void onFailure(Call<GetPerf> call, Throwable t) {
                // Log error here since request failed
                Log.v("Throwable error", t.toString());
                Toast.makeText(
                        getActivity(),
                        "There was an error processing your request",
                        Toast.LENGTH_LONG).show();
                prgDialog2.dismiss();
            }
        });*/







    }

/*public void convertarraytochart(){
    Log.v("Finentry size", Integer.toString(finentry.size()));
    dataSet = new PieDataSet(finentry, "");

    dataSet.setSliceSpace(3f);
    dataSet.setSelectionShift(5f);

    // add a lot of colors

    ArrayList<Integer> colors = new ArrayList<Integer>


    ();

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
    dataSet.setValueTextColor(Color.BLUE);

   PieData data = new PieData(dataSet);

    //   new PieData(dataSet);

    // data.setValueFormatter(new PercentFormatter());
    data.setValueTextSize(11f);


    data.setValueTextColor(Color.BLUE);
    mChart.setData(data);
    mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
    // mChart.spin(2000, 0, 360);


    Legend l = mChart.getLegend();
    l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);
    l.setOrientation(Legend.LegendOrientation.VERTICAL);
    l.setXEntrySpace(5f);
    l.setYEntrySpace(0f);
    l.setYOffset(1f);

    // entry label styling
    mChart.setEntryLabelColor(Color.BLUE);
    mChart.setEntryLabelTypeface(mTf);
    mChart.setEntryLabelTextSize(12f);
    mChart.setDrawEntryLabels(false);
    prgDialog2.dismiss();
}*/

    public String getToday(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Log.d("Date Today is",sdf.format(date));
        String datetod = sdf.format(date);
        return  datetod;
    }

    public String getWeek(){
        long DAY_IN_MS = 1000 * 60 * 60 * 24;

        Date date = new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Log.d("Date Week is",sdf.format(date));
        String datetod = sdf.format(date);
        return  datetod;
    }
    public String getMonth(){
        long DAY_IN_MS = 1000 * 60 * 60 * 24;

        Date date = new Date(System.currentTimeMillis() - (30 * DAY_IN_MS));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Log.d("Date Month is",sdf.format(date));
        String datetod = sdf.format(date);
        return  datetod;
    }
 public void setBarData(){
     float groupSpace = 0.04f;
     float barSpace = 0.02f; // x3 dataset
     float barWidth = 0.3f; // x3 dataset
     // (0.3 + 0.02) * 3 + 0.04 = 1.00 -> interval per "group"

     int startYear = 0;
     int endYear = 2010;



Log.v("Bar Data Size",Integer.toString(barent.size()));


     BarDataSet set1, set2;

     if (mChart.getData() != null &&
             mChart.getData().getDataSetCount() > 0) {
         set1 = (BarDataSet)mChart.getData().getDataSetByIndex(0);
      //   set2 = (BarDataSet)mBarChart.getData().getDataSetByIndex(1);

         set1.setValues(barent);
       //  set2.setValues(yVals2);

         mChart.getData().notifyDataChanged();
         mChart.notifyDataSetChanged();
     } else {
         // create 3 datasets with different types
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
         set1 = new BarDataSet(barent, "Success");

         set1.setColors(colors);



        /* set2 = new BarDataSet(yVals2, "Failure");
         set2.setColor(Color.rgb(164, 228, 251));*/


         ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
         dataSets.add(set1);



         BarData data = new BarData(dataSets);

        // data.setValueTextSize(10f);

         data.setBarWidth(0.4f);
         mChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
         mChart.setData(data);
     }


//     mBarChart.groupBars(startYear, groupSpace, barSpace);

     mChart.invalidate();
 }

    private void Loadd(String params) {

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
                int counter = 0;
                barent.clear();
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
                    JSONArray summdata = comdatas.optJSONArray("summary");
                    boolean chktxncnt = false;
                    //session.setString(SecurityLayer.KEY_APP_ID,appid);

                    if(!(response.body() == null)) {
                        String respcode = obj.optString("responseCode");
                        String responsemessage = obj.optString("message");

                        Log.v("Response Message", responsemessage);

                        if (Utility.isNotNull(respcode) && Utility.isNotNull(respcode)) {
                            if (!(Utility.checkUserLocked(respcode))) {
                                Log.v("Response Message", responsemessage);

                                if (respcode.equals("00")){
                                    Log.v("JSON Aray", comperf.toString());
                                    if(summdata.length() > 0){


                                        JSONObject json_data = null;
                                        for (int i = 0; i < summdata.length(); i++) {
                                            json_data = summdata.getJSONObject(i);
                                            //String accid = json_data.getString("benacid");



                                            String txncode = json_data.optString("txnCode");

                                            String amon = json_data.optString("amount");
                                            String status = json_data.optString("status");
                                            String txnname =  Utility.convertTxnCodetoServ(txncode);
                                            float dbagcmsn = Float.parseFloat(amon);
                                            if(Utility.isNotNull(status)) {
                                                if (((status.equals("SUCCESS"))) && (dbagcmsn > 0)) {
                                                 //   finentry.add(new PieEntry(dbagcmsn, txnname));

                                                    barent.add(new BarEntry(i, dbagcmsn,txnname));
                                                    labels.add(txnname);
                                                    chktxncnt = true;
                                                    Log.v("Entries", dbagcmsn + " Code" + txnname);

                                                }
                                            }

                                        }
                                        //convertarraytochart();
                                        setBarData();
                                        if(chktxncnt == false){
                                            Toast.makeText(
                                                    getActivity(),
                                                    "There are no transaction records to display",
                                                    Toast.LENGTH_LONG).show();
                                        }




                                    }  else {
                                        Toast.makeText(
                                                getActivity(),
                                                "There are no records to display",
                                                Toast.LENGTH_LONG).show();
                                    }
                                    if(comperf.length() > 0){


                                        JSONObject json_data = null;
                                        for (int i = 0; i < comperf.length(); i++) {
                                            json_data = comperf.getJSONObject(i);
                                            //String accid = json_data.getString("benacid");


                                            String txncode = json_data.optString("txnCode");

                                            String amon = json_data.optString("amount");
                                            String status = json_data.optString("status");
                                            String txnname =  Utility.convertTxnCodetoServ(txncode);
                                            float dbagcmsn = Float.parseFloat(amon);
                                            if (((status.equals("SUCCESS"))) && (dbagcmsn > 0)) {
                                                counter++;

                                            }

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
                String sctr = Integer.toString(counter);
                succtrans.setText(sctr);
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

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    private void setData(int count, float range) {

        ArrayList<Entry> values = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {

            float val = (float) (Math.random() * range) + 3;
            values.add(new Entry(i, val));
        }

        LineDataSet set1;

        if (mLineChart.getData() != null &&
                mLineChart.getData().getDataSetCount() > 0) {
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setValues(values);
            mLineChart.getData().notifyDataChanged();
            mLineChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            // set the line to be drawn like this "- - - - - -"
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.YELLOW);
            set1.setCircleColor(Color.BLUE);
            set1.setLineWidth(1f);
            set1.setCircleRadius(0.5f);
            set1.setDrawCircleHole(false);





                set1.setFillColor(Color.BLUE);


            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data
            mLineChart.setData(data);
        }
    }
}
