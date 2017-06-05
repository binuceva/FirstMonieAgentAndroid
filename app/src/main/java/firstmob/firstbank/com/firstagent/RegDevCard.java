package firstmob.firstbank.com.firstagent;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyStore;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/*import com.fourmob.datetimepicker.date.DatePickerDialog;*/

public class RegDevCard extends ActionBarActivity implements View.OnClickListener {
    SessionManagement session;
    Button signup;
    List<String> acc  = new ArrayList<String>();
    ArrayAdapter<String> mArrayAdapter;
    String finl;
    String ema,mon,cust,otp,imeic;
    EditText cno,cpin;
    TextView tvdate;
    ProgressDialog prgDialog;
    final Calendar calendar = Calendar.getInstance();
    RelativeLayout rft;

    public static final String DATEPICKER_TAG = "datepicker";
    DatePickerDialog dpd;
    private Toolbar mToolbar;
    Spinner sp1,sp5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selfreg);
        session = new SessionManagement(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        cno = (EditText) findViewById(R.id.cardno);
        cpin = (EditText) findViewById(R.id.cardpin);
        tvdate = (TextView) findViewById(R.id.bnameh);
        signup = (Button) findViewById(R.id.sign_up);
        signup.setOnClickListener(this);
        rft = (RelativeLayout)findViewById(R.id.relto);

        mon = getIntent().getExtras().getString("mobno");
        cust = getIntent().getExtras().getString("cust");
        otp = getIntent().getExtras().getString("otp");
        imeic = getIntent().getExtras().getString("imeic");
        rft.setOnClickListener(this);
        sp1 = (Spinner) findViewById(R.id.month);
        sp5 = (Spinner) findViewById(R.id.yr);
        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Processing request....");


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Register Device");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.month, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter);
        for(int i = 2015;i <= 2050;i++) {
            acc.add(Integer.toString(i));
            mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, acc);
            mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }
        sp5.setAdapter(mArrayAdapter);

       // datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);



    }
  /*  @Override
    public void onDateSet(DatePickerDialog datePicker, int year, int month, int day) {
        //Toast.makeText(getActivity(), "new date:" + year + "-" + month + "-" + day, Toast.LENGTH_LONG).show();
        String finalmon = null;
        int nwm = month+1;
        finalmon = Integer.toString(nwm);
        if(nwm < 10){
            finalmon = "0"+nwm;
        }

        String tdate = year + "-" + finalmon;

        if (datePicker == datePickerDialog) {
            tvdate.setText(tdate);
            finl = tdate;

        }
    }*/

    @Override
    public void onBackPressed() {

    }
    public static String getDateTimeStamp(String tdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMM yyy ");
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
    public static String ConvTimeStamp(String tdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMM yyy ");
        Date convertedCurrentDate = null;

        try {

            convertedCurrentDate = sdf2.parse(tdate);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String strDate = sdf.format(convertedCurrentDate);
        return strDate;
    }

    private boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            //	new SendTask().execute();
            registerUser();
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

    public void registerUser() {
        String cardn = cno.getText().toString();
        String cardp = cpin.getText().toString();
        String fg = otp;
        int mon = sp1.getSelectedItemPosition()+1;
        String mymon = Integer.toString(mon);
        if(mon < 10){
            mymon = "0"+mymon;
        }
        finl = sp5.getSelectedItem().toString()+"-"+mymon;

        if (Utility.isNotNull(cardn)) {
            if (Utility.isNotNull(cardp)) {
                if (Utility.isNotNull(finl)) {

                    final String strPssword = "AeH6GrLRGK2SBtNiziAdl+Z9HK+98qChhGuCaLZ7O5M";
                    AES encrypter = null;
                    try {
                        encrypter = new AES(strPssword);


                        cardp = encrypter.encrypt(cardp.getBytes("UTF-8")).trim();
                        // mobilenum = new String(encrypted, "UTF-8");
                        Log.d("Mobile Encrypted",cardp);
                        String decrypted = encrypter.decrypt(cardp);
                        Log.d("Decrypted String",decrypted);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            invokeWS(cardn,cardp,finl,fg);

        } else{
            Toast.makeText(getApplicationContext(), "Please select a card expiry date", Toast.LENGTH_LONG).show();
        }} else{
                Toast.makeText(getApplicationContext(), "The Card Pin Field is empty. Please fill in appropiately", Toast.LENGTH_LONG).show();
            }
        } else{
            Toast.makeText(getApplicationContext(), "The Card Number Field is empty. Please fill in appropiately", Toast.LENGTH_LONG).show();
        }






    }




    public void invokeWS( String cno,String cpin,String exp,String otp){
        // Show Progress Dialog
        prgDialog.show();

        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(40000);
        HashMap<String, String> nurl = session.getNetURL();
        String newurl = nurl.get(SessionManagement.NETWORK_URL);
        // String url = newurl+"/natmobileapi/rest/customer/ministat?acctno="+acc+"";
        String imei = "";

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        imei = telephonyManager.getDeviceId();


        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        String mac = wInfo.getMacAddress();

        String build = Build.SERIAL;
       /* String brand = Build.BRAND;
        String model = Build.MODEL;
        String dtype = brand + " "+model;
        dtype = dtype.replace(" ","_");*/
        String dtype = getDeviceName();
        dtype = dtype.replace(" ","_");
        cpin = cpin.replace("/", "_");
        Log.v("Card Pin",cpin);
     /*   String url =newurl+"/natmobileapi/rest/customer/regdevice?msisdn="+mon+"&otp="+otp+"&custcode="+cust+"&imei="+imei+"&macadress="+mac+"&ipadress="+mac+"&serial="+build+"&cardno="+cno+"&cardpin="+cpin+"&expd="+exp+"&imeicount="+imeic+"&dtype="+dtype;
     */   String url = ApplicationConstants.NET_URL+ApplicationConstants.AND_ENPOINT+"regdevice/"+mon+"/"+otp+"/"+cust+"/"+imei+"/"+mac+"/"+mac+"/"+build+"/"+cno+"/"+exp+"/"+imeic+"/"+dtype+"/"+cpin+"";

        Log.v("Reg Device Card",url);
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


                            String rsmesaage = obj.optString("responsemessage");
                            String rpcode = obj.optString("responsecode");

                            Log.v("Response Code", rsmesaage);
                            if (rpcode.equals("00")) {


                            } else {
                                Toast.makeText(getApplicationContext(), rsmesaage, Toast.LENGTH_LONG).show();


                    }


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), " The device has not successfully connected to server. Please check your internet settings", Toast.LENGTH_LONG).show();
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

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.sign_up){
            checkInternetConnection();
        }
        if(view.getId() == R.id.relto){
          /*  datePickerDialog.setYearRange(1985, 2028);

            //   datePickerDialog.setCloseOnSingleTapDay(isCloseOnSingleTapDay());
            datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);*/
          //  createDialogWithoutDateField().show();
        }
    }

  /*  private DatePickerDialog createDialogWithoutDateField() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int mon,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                String finalmon = null;

                int nwm = mon + 1;
                finalmon = Integer.toString(nwm);
                if (nwm < 10) {
                    finalmon = "0" + nwm;
                }

                String tdate = year + "-" + finalmon;


                tvdate.setText(tdate);
                finl = tdate;

            }
        };
        dpd = new DatePickerDialog(this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));



        try {
            java.lang.reflect.Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
            for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField.get(dpd);

                    java.lang.reflect.Field[] datePickerFields = datePickerDialogField.getType().getDeclaredFields();
                    for (java.lang.reflect.Field datePickerField : datePickerFields) {
                        Log.i("test", datePickerField.getName());
                        if ("mDaySpinner".equals(datePickerField.getName())) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
        }
        return dpd;
    }*/

/*
    @Override
    public void onDateSet(DatePicker datePicker, int year, int mon, int i3) {
        String finalmon = null;

        int nwm = mon + 1;
        finalmon = Integer.toString(nwm);
        if (nwm < 10) {
            finalmon = "0" + nwm;
        }

        String tdate = year + "-" + finalmon;


        tvdate.setText(tdate);
        finl = tdate;
    }*/

    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }


    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }
}
