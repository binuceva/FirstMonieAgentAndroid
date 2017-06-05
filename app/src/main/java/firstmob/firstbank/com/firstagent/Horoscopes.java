package firstmob.firstbank.com.firstagent;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class Horoscopes extends Fragment {
    TextView tv,tv1,tv2,tv3;
    ImageView iv;
    private static int SPLASH_TIME_OUT = 200;
    Boolean connected;
    String distar = null;
    String newd;
    SessionManagement session;
    private String finalUrl="http://feeds.feedburner.com/AstroSageScorpio?format=xml";
    private HandleXML obj;
boolean chkfetch;
    public Horoscopes() {
        // Required empty public constructor
    }
  /*  private static Fragment newInstance(Context context) {
        LayoutOne f = new LayoutOne();

        return f;
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.common, null);
        session = new SessionManagement(getActivity());
        tv=(TextView)root.findViewById(R.id.textView);
        tv1=(TextView)root.findViewById(R.id.textView1);
        tv2=(TextView)root.findViewById(R.id.textView2);
        iv=(ImageView)root.findViewById(R.id.imageView);
        Typeface font1 = Typeface.createFromAsset(getActivity().getAssets(), "musleo.ttf");
        tv.setTypeface(font1);
        tv1.setTypeface(font1);
        tv2.setTypeface(font1);
        HashMap<String, String> dis = session.getStar();
      distar = dis.get(SessionManagement.STARS);

     newd = getDateTimeStamp();
        tv1.setText(newd);
        if(distar != null){
            checksign(distar);
        }else{
            distar = "Scorpio";
            tv.setText("Scorpio");
           // iv.setBackgroundResource(R.drawable.scorpiosign);
        }

        HashMap<String, String> dismsg = session.getStarMsg();
        String disda = dismsg.get(SessionManagement.STAR_MSG);

        chkfetch = CheckWhetherFetch(newd,distar);
        if(chkfetch) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    fetch();
                }


            }, SPLASH_TIME_OUT);
        }else{
            tv2.setText(disda);
        }
        return root;
    }


    public void fetch(){
        obj = new HandleXML(finalUrl);
        obj.fetchXML();
        while(obj.parsingComplete);
       // tv1.setText(obj.getTitle());
        tv2.setText(obj.getDescription());


        session.putStarMsg(obj.getDescription());
        session.putStarDate(newd);
        session.putFetchedStar(distar);

    }

public void checksign(String sign){

if(sign.equals("Aquarius")){
    tv.setText(sign);
    finalUrl="http://feeds.feedburner.com/AstroSageAquarius?format=xml";
    iv.setBackgroundResource(R.drawable.aquarius);
}
    if(sign.equals("Aries")){
        tv.setText(sign);
        finalUrl="http://feeds.feedburner.com/AstroSageAries?format=xml";
        iv.setBackgroundResource(R.drawable.aries);
    }
    if(sign.equals("Cancer")){
        tv.setText(sign);
        finalUrl="http://feeds.feedburner.com/AstroSageCancer?format=xml";
        iv.setBackgroundResource(R.drawable.cancer);
    }
    if(sign.equals("Capricorn")){
        tv.setText(sign);
        finalUrl="http://feeds.feedburner.com/AstroSageCapricorn?format=xml";
        iv.setBackgroundResource(R.drawable.capricorn);
    }
    if(sign.equals("Gemini")){
        tv.setText(sign);
        finalUrl="http://feeds.feedburner.com/AstroSageGemini?format=xml";
        iv.setBackgroundResource(R.drawable.gemini);
    }


    if(sign.equals("Leo")){
        tv.setText(sign);
        finalUrl="http://feeds.feedburner.com/AstroSageLeo?format=xml";
        iv.setBackgroundResource(R.drawable.leo);
    }
    if(sign.equals("Libra")){
        tv.setText(sign);
        finalUrl="http://feeds.feedburner.com/AstroSageLibra?format=xml";
        iv.setBackgroundResource(R.drawable.libra);
    }

    if(sign.equals("Pisces")){
        tv.setText(sign);
        finalUrl="http://feeds.feedburner.com/AstroSagePisces?format=xml";
        iv.setBackgroundResource(R.drawable.pisces);
    }
    if(sign.equals("Sagittarius")){
        tv.setText(sign);
        finalUrl="http://feeds.feedburner.com/AstroSageSagittarius?format=xml";
        iv.setBackgroundResource(R.drawable.sagittarius);
    }

    if(sign.equals("Scorpio")){
        tv.setText(sign);
      //  iv.setBackgroundResource(R.drawable.scorpiosign);
    }
    if(sign.equals("Taurus")){
        tv.setText(sign);
        finalUrl="http://feeds.feedburner.com/AstroSageSagittarius?format=xml";
        iv.setBackgroundResource(R.drawable.taurus);
    }
    if(sign.equals("Virgo")){
        tv.setText(sign);
        finalUrl="http://feeds.feedburner.com/AstroSageVirgo?format=xml";
        iv.setBackgroundResource(R.drawable.virgo);
    }

}
    public static String getDateTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMM yyy ");
        Date convertedCurrentDate = new Date();



        String strDate = sdf2.format(convertedCurrentDate);

        return strDate;
    }

public boolean CheckWhetherFetch(String date,String sign) {
    HashMap<String, String> disdate = session.getStarDate();
    String disda = disdate.get(SessionManagement.STAR_DATE);

    HashMap<String, String> disfetch = session.getFetchedStar();
    String disf = disfetch.get(SessionManagement.FETCHSTARS);

if(date.equals(disda) && disf.equals(sign)){
return false;
}else{
    return  true;
}


}

}
