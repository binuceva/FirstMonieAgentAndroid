package firstmob.firstbank.com.firstagent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import weather.Weather;
import weather.WeatherHttpClient;


public class LayoutFive extends Fragment {
    TextView greet;
    LinearLayout weth;
    private TextView cityText;
    private TextView condDescr;
    private TextView temp;
    private TextView press;
    private TextView windSpeed;
    private TextView windDeg;
    String city = "Lagos,NG";
    private TextView hum;
    private ImageView imgView;
    SessionManagement session;

    public LayoutFive() {
        // Required empty public constructor
    }
  /*  private static Fragment newInstance(Context context) {
        LayoutOne f = new LayoutOne();

        return f;
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.layout_five, null);
        greet = (TextView) root.findViewById(R.id.greet);
        weth = (LinearLayout) root.findViewById(R.id.wthbg);
        session = new SessionManagement(getActivity());
        Calendar cal = Calendar.getInstance();



        cityText = (TextView) root.findViewById(R.id.cityText);
     condDescr = (TextView) root.findViewById(R.id.condDescr);
        temp = (TextView) root.findViewById(R.id.temp);
        Typeface font1 = Typeface.createFromAsset(getActivity().getAssets(), "musleo.ttf");
        cityText.setTypeface(font1);
        condDescr.setTypeface(font1);
        temp.setTypeface(font1);
        greet.setTypeface(font1);
       /* hum = (TextView) root.findViewById(R.id.hum);
        press = (TextView) root.findViewById(R.id.press);
        windSpeed = (TextView) root.findViewById(R.id.windSpeed);
        windDeg = (TextView) root.findViewById(R.id.windDeg);
       */
        imgView = (ImageView) root.findViewById(R.id.condIcon);
        String time = "";
        int v =  cal.getTime().getHours();
        if(v < 12){
            time = "Morning";
            weth.setBackgroundResource(R.drawable.morning);
            greet.setTextColor(getActivity().getResources().getColor(R.color.nbkdarkbrown));
            cityText.setTextColor(getActivity().getResources().getColor(R.color.nbkdarkbrown));
            condDescr.setTextColor(getActivity().getResources().getColor(R.color.nbkdarkbrown));
            temp.setTextColor(getActivity().getResources().getColor(R.color.nbkdarkbrown));
        }

        String newurl = "";
        HashMap<String, String> dis = session.getDisp();
        String disname = dis.get(SessionManagement.KEY_DISP);
        if(disname != null){
            newurl = disname;
        }else {
            HashMap<String, String> nurl = session.getCustName();
            newurl = nurl.get(SessionManagement.KEY_CUSTNAME);
            if (newurl == null || newurl.equals("")) {
                newurl = "Customer";
            }
        }
        String ftext = "Good "+time+" "+newurl;
        greet.setText(ftext);
        checkInternetConnection();

        return root;
    }



    public void StartChartAct(int i){


    }
    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }
    private boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            //	new SendTask().execute();
            JSONWeatherTask task = new JSONWeatherTask();
            task.execute(new String[]{city});
            //	RegTest();
            return true;
        } else {

        /*   Toast.makeText(
                    getActivity(),
                    "No Internet Connection. Please check your internet setttings",
                    Toast.LENGTH_LONG).show();*/
            return false;
        }
    }
    private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            Weather weather = new Weather();
            String data = null;
            try {
                data = ( (new WeatherHttpClient()).getWeatherData(params[0]));
            } catch (IOException e) {
                e.printStackTrace();
            }


            return weather;

        }




        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

            if (weather.iconData != null && weather.iconData.length > 0) {
                Bitmap img = BitmapFactory.decodeByteArray(weather.iconData, 0, weather.iconData.length);
                imgView.setImageBitmap(img);
                Log.v("Weather Icon","Weather Icon Has Been Set");
            }

            if((weather.location != null)) {

                cityText.setText(weather.location.getCity() + "," + weather.location.getCountry());

                condDescr.setText(weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescr() + ")");
                temp.setText("" + Math.round((weather.temperature.getTemp() - 273.15)) + " degrees Celsius");
           /* hum.setText("" + weather.currentCondition.getHumidity() + "%");
            press.setText("" + weather.currentCondition.getPressure() + " hPa");
            windSpeed.setText("" + weather.wind.getSpeed() + " mps");
            windDeg.setText("" + weather.wind.getDeg() + "�");*/

            }

        }







    }

}
