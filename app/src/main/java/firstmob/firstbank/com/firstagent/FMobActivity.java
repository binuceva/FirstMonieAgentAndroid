package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

import rest.ApiInterface;
import rest.ApiSecurityClient;
import retrofit2.Call;
import retrofit2.Callback;
import security.SecurityLayer;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FMobActivity extends ActionBarActivity implements FragmentDrawer.FragmentDrawerListener,View.OnClickListener {
    private Toolbar mToolbar;
    int count = 1;
    private FragmentDrawer drawerFragment;

  //  public ResideMenu resideMenuClass;

    private static int SPLASH_TIME_OUT = 2000;
    TextView greet;
    private ShakeDetectionListener mShaker;
    SessionManagement session;
    ProgressDialog prgDialog;
    public static final long DISCONNECT_TIMEOUT = 180000; // 5 min = 5 * 60 * 1000 ms

 /*   private Handler disconnectHandler = new Handler(){
        public void handleMessage(Message msg) {
        }
    };

    private Runnable disconnectCallback = new Runnable() {
        @Override
        public void run() {
            // Perform any required operation on disconnect
            finish();
            Intent i = new Intent(FMobActivity.this, SignInActivity.class);

            // Staring Login Activity
            startActivity(i);
            Toast.makeText(FMobActivity.this, "Your have been inactive for too long. Please login again", Toast.LENGTH_LONG).show();

        }
    };*/

/*    public void resetDisconnectTimer(){
        disconnectHandler.removeCallbacks(disconnectCallback);
        disconnectHandler.postDelayed(disconnectCallback, DISCONNECT_TIMEOUT);
    }

    public void stopDisconnectTimer(){
        disconnectHandler.removeCallbacks(disconnectCallback);
    }*/

    @Override
    public void onUserInteraction(){
     //   resetDisconnectTimer();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
       // session = new SessionManagement(this);
        session = new SessionManagement(this);
        setContentView(R.layout.oldactivity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
       // getSupportActionBar().setLogo(R.drawable.fbnbluesmall);
        final Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        mShaker = new ShakeDetectionListener(this);
       drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        //   drawerFragment.setArguments(bundle);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        prgDialog = new ProgressDialog(getApplicationContext());
        prgDialog.setMessage("Loading....");
        prgDialog.setCancelable(false);

       displayView(40);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      MenuInflater inflater = getMenuInflater();

          inflater.inflate(R.menu.main, menu);

      return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


         if(id == R.id.inbox) {
            Fragment  fragment = new Inbox();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //  String tag = Integer.toString(title);
            fragmentTransaction.replace(R.id.container_body, fragment,"Inbox");
            fragmentTransaction.addToBackStack("Inbox");
       //   getSupportActionBar().setTitle("Notifications");
            //setActionBarTitle("Inbox");
            fragmentTransaction.commit();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
       boolean checklg = true;
        switch (position) {
            case 40:

                fragment = new HomeAccountFrag();
                title = "Welcome";

                break;
           case 0:

                    fragment = new HomeAccountFrag();
               title = "Welcome";

                break;

            case 1:
                fragment = new CashDepo();
                title = "Deposit";

break;


            case 2:

                fragment = new FTMenu();
                title = "Transfer";
            break;
            case 3:
                fragment = new Withdraw();
                title = "Withdrawal";
            break;
            case 4:
              fragment = new BillMenu();
                title = "Billers";
            break;
            case 5:
                fragment = new AirtimeTransf();
                title = "Airtime Transfer";
            break;
            case 6:
                 /*   fragment = new OpenAccMenu();
                    title = "Remittances";*/

                fragment = new ComingSoon();
                title = "Coming Soon";
                //   Toast.makeText(FMobActivity.this, "Open Account coming soon", Toast.LENGTH_LONG).show();
            break;


            case 7:
              /*  fragment = new SelChart();
                title = "My Stats";*/
               android.app.Fragment   fragmentt = new SelChart();
                android.app.FragmentManager fragmentManager = getFragmentManager();
                android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //  String tag = Integer.toString(title);
                fragmentTransaction.replace(R.id.container_body, fragmentt,title);

                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();

              break;


            case 8:

                fragment = new ChangeACName();
                title = "My Profile";

                break;
            case 9:

               /* Intent ppp = new Intent(FMobActivity.this, ChatActivity.class);

                // Staring Login Activity
                startActivity(ppp);*/
                fragment = new ComingSoon();
                title = "Coming Soon";
              //  Toast.makeText(FMobActivity.this, "Chat Feature coming soon", Toast.LENGTH_LONG).show();
                break;
            case 10:

           /*     fragment = new RequestbankVisit();
                title = "Bank Visit";*/
                fragment = new ComingSoon();
                title = "Coming Soon";
           //     Toast.makeText(FMobActivity.this, "Visit Agent Shop Feature coming soon", Toast.LENGTH_LONG).show();
                break;
            case 11:
            this.finish();
       //     session.logoutUser();
            // After logout redirect user to Loing Activity
         setLogout();
                  break;


            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //  String tag = Integer.toString(title);
            fragmentTransaction.replace(R.id.container_body, fragment,title);

                fragmentTransaction.addToBackStack(title);

            fragmentTransaction.commit();

            // set the toolbar title
        //   getSupportActionBar().setTitle(title);
        }
    }
    public void setActionBarTitle(String title) {

     //   getSupportActionBar().setTitle(title);
    }


    protected void onDestroy() {

        super.onDestroy();
       // session.logoutUser();
        // Put code here.

    }

    private void replaceFragment (Fragment fragment){
        String backStateName = fragment.getClass().getName();

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

        if (!fragmentPopped){ //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.container_body, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        FragmentManager fm = getSupportFragmentManager();
        int bentry = fm.getBackStackEntryCount();
        Log.i("TAG", "Frag Entry: " + bentry);

        if(bentry > 0){

/*finish();

            startActivity(new Intent(getApplicationContext(),FMobActivity.class));*/

            String ide = null;
            for(int entry = 0; entry < fm.getBackStackEntryCount(); entry++){
                ide = fm.getBackStackEntryAt(entry).getName();
                Log.i("TAG", "Found fragment: " + ide);
            }
        }else{

            new MaterialDialog.Builder(FMobActivity.this)
                    .title("Confirm Exit")
                    .content("Are you sure you want to exit from FirstAgent? ")
                    .positiveText("YES")
                    .negativeText("NO")

                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                          /*  finish();
                            session.logoutUser();*/
                            //  checkConnBio();
                            session.logoutUser();

                            // After logout redirect user to Loing Activity
                            Intent i = new Intent(getApplicationContext(), SignInActivity.class);

                            // Staring Login Activity
                            startActivity(i);
                            Toast.makeText(getApplicationContext(), "You have logged out successfully", Toast.LENGTH_LONG).show();


                        }

                        @Override
                        public void onNegative(MaterialDialog dialog)
                        {
                            // dialog.dismiss();

String title = "Welcome";
                            Fragment    fragment = new HomeAccountFrag();
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            //  String tag = Integer.toString(title);
                            fragmentTransaction.replace(R.id.container_body, fragment,title);

                                fragmentTransaction.addToBackStack(title);

                            fragmentTransaction.commit();

                            // set the toolbar title
                           // getSupportActionBar().setTitle(title);
                        }
                    })
                    .show();


        }
    }

  public void addFragment( Fragment frag,String title){
      Fragment  fragment = new ChangeTxnPin();
      FragmentManager fragmentManager = getSupportFragmentManager();
      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      //  String tag = Integer.toString(title);
      fragmentTransaction.replace(R.id.container_body, frag,title);
      fragmentTransaction.addToBackStack(title);
      fragmentTransaction.commit();
     setActionBarTitle(title);
  }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPause()
    {

        super.onPause();

        long secs = (new Date().getTime())/1000;
        Log.v("Seconds Loged",Long.toString(secs));
         session.putCurrTime(secs);
    }
    @Override
    public void onResume() {
        super.onResume();
       /* resetDisconnectTimer();*/

        HashMap<String, Long> nurl = session.getCurrTime();
        long newurl = nurl.get(SessionManagement.KEY_TIMEST);

        if (newurl > 0) {
            long secs = (new Date().getTime()) / 1000;
            long diff = 0;
            if (secs >= newurl) {
                diff = secs - newurl;
                if (diff > 180) {

                    this.finish();
                    session.logoutUser();
                    // After logout redirect user to Loing Activity
                    Intent i = new Intent(FMobActivity.this, SignInActivity.class);

                    // Staring Login Activity
                    startActivity(i);
                    Toast.makeText(FMobActivity.this, "Your session has expired. Please login again", Toast.LENGTH_LONG).show();
                }
            }
        }
    }




    private void setLogout() {


        String endpoint= "login/logout.action";


        String usid = Utility.gettUtilUserId(getApplicationContext());
        String appid = session.getString(SecurityLayer.KEY_APP_ID);
        SecurityLayer.Log("appid", appid);
        String params = "1/"+usid+"/"+appid;
        String urlparams = "";
        try {
            urlparams = SecurityLayer.genURLCBC(params,endpoint,getApplicationContext());
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
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                try {
                    // JSON Object

                    Log.v("response..:", response.body());
                    JSONObject obj = new JSONObject(response.body());
                    //obj = Utility.onresp(obj,getActivity());
                    obj = SecurityLayer.decryptTransaction(obj, getApplicationContext());
                    SecurityLayer.Log("decrypted_response", obj.toString());

                    String respcode = obj.optString("responseCode");
                    String responsemessage = obj.optString("message");



                    //session.setString(SecurityLayer.KEY_APP_ID,appid);


                    if(!(response.body() == null)) {
                        if (respcode.equals("00")) {

                            Log.v("Response Message", responsemessage);
                            session.setString(SessionManagement.CHKLOGIN,"N");
                            Toast.makeText(FMobActivity.this, "You have successfully signed out", Toast.LENGTH_LONG).show();

                            finish();
                            Intent i = new Intent(FMobActivity.this, SignInActivity.class);

                            // Staring Login Activity
                            startActivity(i);
//
                        }else{
                            Toast.makeText(
                                    getApplicationContext(),
                                    "There was an error processing your request",
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(
                                getApplicationContext(),
                                "There was an error processing your request",
                                Toast.LENGTH_LONG).show();
                    }



                } catch (JSONException e) {
                    SecurityLayer.Log("encryptionJSONException", e.toString());
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), getApplicationContext().getText(R.string.conn_error), Toast.LENGTH_LONG).show();
                    // SecurityLayer.Log(e.toString());

                } catch (Exception e) {
                    SecurityLayer.Log("encryptionJSONException", e.toString());
                    // SecurityLayer.Log(e.toString());
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Log error here since request failed
                Log.v("Throwable error",t.toString());
                Toast.makeText(
                        getApplicationContext(),
                        "There was a error processing your request",
                        Toast.LENGTH_LONG).show();
                //   pDialog.dismiss();



            }
        });

    }
}
