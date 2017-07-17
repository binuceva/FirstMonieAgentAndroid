package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adapter.ProdAdapter;
import adapter.ProdList;
import model.BalInquiryData;
import model.BalanceInquiry;
import model.GetAgentId;
import model.GetAgentIdData;
import rest.ApiClient;
import rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SupHomeFrag extends Fragment implements View.OnClickListener {
    ImageView imageView1;
    ListView lv;
    TextView tv,tvacco,tvcomm;
    List<ProdList> planetsList = new ArrayList<ProdList>();
    ProdAdapter aAdpt;
    Button btn1,btn2,btn3;
    RelativeLayout rlbuttons,rlagbal,rlcomm;
    TextView curbal,lastl,greet,commamo;
    SessionManagement session;
    ProgressDialog prgDialog;
    ProgressBar prgbar;
    ImageView iv;
//
// ProgressBar pDialog;
    List<GetAgentIdData> plan = new ArrayList<GetAgentIdData>();
    String agid;
    public SupHomeFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.suphomefrag, null);
        btn1 = (Button) root.findViewById(R.id.btn1);
        curbal = (TextView) root.findViewById(R.id.currentbal);
        tvacco = (TextView) root.findViewById(R.id.hfjdj);
        lastl = (TextView) root.findViewById(R.id.txt2);
        greet = (TextView) root.findViewById(R.id.greet);
        tvcomm = (TextView) root.findViewById(R.id.comm);
        commamo = (TextView) root.findViewById(R.id.accountbal);
        iv = (ImageView) root.findViewById(R.id.img);
        prgDialog = new ProgressDialog(getActivity());
        prgbar = (ProgressBar) root.findViewById(R.id.prgbar);
     //   prgbar.getIndeterminateDrawable().setColorFilter(getActivity().getResources().getColor(R.color.nbkyellow), android.graphics.PorterDuff.Mode.MULTIPLY);
        prgDialog.setMessage("Loading....");
        prgDialog.setCancelable(false);
//pDialog = new ProgressBar(getActivity());
        btn2 = (Button) root.findViewById(R.id.btn2);
        btn3 = (Button) root.findViewById(R.id.btn3);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        rlbuttons = (RelativeLayout) root.findViewById(R.id.balances);
        rlagbal = (RelativeLayout) root.findViewById(R.id.rlagentaccount);
        rlcomm = (RelativeLayout) root.findViewById(R.id.rlcommisionaccount);
        rlagbal.setOnClickListener(this);
        rlcomm.setOnClickListener(this);
        session = new SessionManagement(getActivity());
     //   pDialog.setBackgroundColor(getActivity().getResources().getColor(R.color.nbkyellow));
        boolean  checktpref = session.checkShwBal();
        Log.v("Boolean checkpref", String.valueOf(checktpref));
        if(checktpref == false){
setBalInqu();
        }else{
rlbuttons.setVisibility(View.GONE);
        }

        String strlastl = Utility.getLastl(getActivity());
        strlastl = Utility.convertDate(strlastl);
        lastl.setText("Last Login: "+strlastl);
        String accnoo = Utility.getAcountno(getActivity());
        tvacco.setText(""+accnoo);
        Calendar cal = Calendar.getInstance();
        String time = "";
        int v =  cal.getTime().getHours();
        if(v < 12){
            time = "Morning";

        }
        if(v >= 12 && v < 18){
            time = "Afternoon";


        }
        if(v >= 18 && v <24){
            time = "Evening";

        }
        String custname = Utility.gettUtilCustname(getActivity());
        greet.setText("Good "+time+" "+Utility.returnCustname(custname));
        String agentid = Utility.gettUtilAgentId(getActivity());
        if(Utility.isNotNull(agentid)){
            tvcomm.setText(agentid);
        }
        if(Utility.checkInternetConnection(getActivity())){
            getAgentIDs();
        }
        return root;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn1) {
            //   SetDialog("Select Bank");

            Fragment  fragment = new AgentTopUp();
            String title = "Agent Top Up";
            ((SupAgentActivity)getActivity()).addFragment(fragment,title);

          /*  FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //  String tag = Integer.toString(title);
            fragmentTransaction.replace(R.id.container_body, fragment,"Deposit");

            ((FMobActivity)getActivity())
                    .setActionBarTitle("Deposit");
            fragmentTransaction.commit();*/

        }
        if (view.getId() == R.id.btn2) {
            //   SetDialog("Select Bank");

            Fragment  fragment = new FTMenu();
String title = "Transfer";
            ((SupAgentActivity)getActivity()).addFragment(fragment,title);

         /*   FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //  String tag = Integer.toString(title);
            fragmentTransaction.replace(R.id.container_body, fragment,"Transfer");

            ((FMobActivity)getActivity())
                    .setActionBarTitle("Transfer");
            fragmentTransaction.commit();*/

        }
        if (view.getId() == R.id.btn3) {
            //   SetDialog("Select Bank");

            Fragment  fragment = new Withdraw();
String title = "Withdraw";
            ((SupAgentActivity)getActivity()).addFragment(fragment,title);
          /*  FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //  String tag = Integer.toString(title);
            fragmentTransaction.replace(R.id.container_body, fragment,"Withdraw");

            ((FMobActivity)getActivity())
                    .setActionBarTitle("Withdraw");
            fragmentTransaction.commit();*/

        }
        if (view.getId() == R.id.rlagentaccount) {
            //   SetDialog("Select Bank");

            Fragment  fragment = new Minstat();
            String title = "Mini Statement";
            ((SupAgentActivity)getActivity()).addFragment(fragment,title);

           /* FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //  String tag = Integer.toString(title);
            fragmentTransaction.replace(R.id.container_body, fragment,"Mini Statement");

            ((FMobActivity)getActivity())
                    .setActionBarTitle("Mini Statement");
            fragmentTransaction.commit();*/

        }
        if (view.getId() == R.id.rlcommisionaccount) {
            //   SetDialog("Select Bank");

            Fragment  fragment = new CommReport();


            String title = "Commissions Report";
            ((SupAgentActivity)getActivity()).addFragment(fragment,title);
          /*  FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //  String tag = Integer.toString(title);
            fragmentTransaction.replace(R.id.container_body, fragment,"Commissions Report");

            ((FMobActivity)getActivity())
                    .setActionBarTitle("Commisions Report");
            fragmentTransaction.commit();*/

        }
    }
    public void StartChartAct(int i){


    }
    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }
public void setBalInqu(){
    if (Utility.checkInternetConnection(getActivity())) {
        prgDialog.show();
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String usid = Utility.gettUtilUserId(getActivity());
        String agentid = Utility.gettUtilAgentId(getActivity());
        String mobnoo = Utility.gettUtilMobno(getActivity());
        Call<BalanceInquiry> call = apiService.getBalInq("1", usid, agentid, "0000");
        call.enqueue(new Callback<BalanceInquiry>() {
            @Override
            public void onResponse(Call<BalanceInquiry> call, Response<BalanceInquiry> response) {
                if(!(response.body() == null)) {
                    String responsemessage = response.body().getMessage();
                    BalInquiryData baldata = response.body().getData();
                    Log.v("Response Message", responsemessage);

//                                    Log.v("Respnse getResults",datas.toString());
                    if (!(baldata == null)) {
                        String balamo = baldata.getbalance();
                        String comamo =baldata.getcommision();


                        String fbal = Utility.returnNumberFormat(balamo);
                        curbal.setText(Html.fromHtml("&#8358") + " " + fbal);

                        String cmbal = Utility.returnNumberFormat(comamo);
                        commamo.setText(Html.fromHtml("&#8358") + " " + cmbal);
                    } else {
                        Toast.makeText(
                                getActivity(),
                                "There was an error retrieving your balance ",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(
                            getActivity(),
                            "There was an error retrieving your balance ",
                            Toast.LENGTH_LONG).show();
                }  try {
                    if ((prgDialog != null) && prgDialog.isShowing()) {
                        prgDialog.dismiss();
                    }
                } catch (final IllegalArgumentException e) {
                    // Handle or log or ignore
                } catch (final Exception e) {
                    // Handle or log or ignore
                } finally {
                    prgDialog = null;
                }

              //  prgDialog.dismiss();
            }

            @Override
            public void onFailure(Call<BalanceInquiry> call, Throwable t) {
                // Log error here since request failed
                Log.v("Throwable error",t.toString());
                Toast.makeText(
                        getActivity(),
                        "There was an error retrieving your balance ",
                        Toast.LENGTH_LONG).show();
                prgDialog.dismiss();
            }
        });
    }
}
    private void dismissProgressDialog() {
        if (prgDialog != null && prgDialog.isShowing()) {
            prgDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }
    public void getAgentIDs(){




        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String usid = Utility.gettUtilUserId(getActivity());
        String agentid = Utility.gettUtilAgentId(getActivity());
        String mobnoo = Utility.gettUtilMobno(getActivity());
        Call<GetAgentId> call = apiService.GetAgId("1", usid, agentid, "0000");
        call.enqueue(new Callback<GetAgentId>() {
            @Override
            public void onResponse(Call<GetAgentId> call, Response<GetAgentId> response) {
                String responsemessage = response.body().getMessage();

                Log.v("Response Message", responsemessage);

                plan = response.body().getData();
//                                    Log.v("Respnse getResults",datas.toString());
                if (!(plan == null)) {
                    for(int sw = 0;sw < plan.size();sw++) {
                        if (!(plan.get(sw) == null)) {
                            String imgloc = plan.get(sw).getimgLoc();
                            if (imgloc.equals("FOOTER")) {
                                agid = plan.get(sw).getId();
                                new DownloadImg().execute("");
                            }
                        }
                    }
                }
                else{
                    Toast.makeText(
                            getActivity(),
                            "There was an error loading ad image",
                            Toast.LENGTH_LONG).show();
                }
           //     pDialog.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<GetAgentId> call, Throwable t) {
                // Log error here since request failed
                Log.v("Throwable error",t.toString());
                Toast.makeText(
                        getActivity(),
                        "There was a error processing your request",
                        Toast.LENGTH_LONG).show();
            //    pDialog.setVisibility(View.INVISIBLE);
            }
        });

    }

    class DownloadImg extends AsyncTask<String, String, String> {
        Bitmap bmp=null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // prgDialog.show();
        }

        // Download Music File from Internet
        @Override
        protected String doInBackground(String... f_url) {


            try{
                //   http://localhost:9399/agencyapi/app/adverts/pic.action/1/CEVA/PAND00000000019493818389/2
                String usid = Utility.gettUtilUserId(getActivity());
                String agentid = Utility.gettUtilAgentId(getActivity());
                String mobnoo = Utility.gettUtilMobno(getActivity());
                //  http://localhost:9399/agencyapi/app/adverts/pic.action/1/CEVA/PAND00000000019493818389/2
                String url = ApplicationConstants.UNENC_URL+"adverts/pic.action/1/"+usid+"/"+agentid+"9493818389/"+agid;
                bmp = downloadBitmap(url);

                Log.v("Download Pic Url",url);
            }catch(Exception e){
                Log.e("ERROR While Downloadin", e.getLocalizedMessage());
//                Toast.makeText(getActivity(), "Error While Downloading File", Toast.LENGTH_LONG);
            }
            return "34";
        }


        private Bitmap downloadBitmap(String url) {
            HttpURLConnection urlConnection = null;
            try {
                Log.i("thumb", url);
                URL uri = new URL(url);
                urlConnection = (HttpURLConnection) uri.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setUseCaches(false);
                urlConnection.connect();

           /* int statusCode = urlConnection.getResponseCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }*/

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                }
            } catch (Exception e) {
                urlConnection.disconnect();
                Log.w("thumb dnwld", "Error downloading image from " + url);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }





        @Override
        protected void onPostExecute(String file_url) {
            //  prgDialog.dismiss();
       //     pDialog.setVisibility(View.INVISIBLE);
            if(bmp != null)
            {

                iv.setVisibility(View.VISIBLE);
                iv.setImageBitmap(bmp);
            }
            else
            {
                iv.setImageBitmap(null);
            }
            prgbar.setVisibility(View.GONE);

        }
    }

}
