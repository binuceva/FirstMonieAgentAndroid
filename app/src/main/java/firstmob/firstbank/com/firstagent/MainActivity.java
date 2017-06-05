package firstmob.firstbank.com.firstagent;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import adapter.FHomeAdapter;
import tablayout.SlidingTabLayout;

public class MainActivity extends ActionBarActivity  {
    private Toolbar mToolbar;
int count = 1;
    private FragmentDrawer drawerFragment;
    SessionManagement session;

    ViewPager pager;
    FHomeAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"HOME","ACTIVITIES","OFFERS","PRODUCTS"};
    int Numboftabs =4;

    private static int SPLASH_TIME_OUT = 2000;
    TextView greet;
    private ShakeDetectionListener mShaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        session = new SessionManagement(this);

        super.onCreate(savedInstanceState);
       // session = new SessionManagement(this);
        setContentView(R.layout.activity_main);
       // setupWindowAnimations();
        adapter =  new FHomeAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
      /*  setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/
        Bundle bundle = new Bundle();
    //    greet = (TextView) findViewById(R.id.greet);

        TextView marque = (TextView) findViewById(R.id.txtt);
        marque.setSelected(true);


        final Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        mShaker = new ShakeDetectionListener(this);
        mShaker.setOnShakeListener(new ShakeDetectionListener.OnShakeListener () {
            public void onShake()
            {
                vibe.vibrate(100);
                Toast.makeText(
                        getApplicationContext(),
                        "Calling Firstmonie Customer Care.....",
                        Toast.LENGTH_LONG).show();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + Uri.encode("07003477866643")));

                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(callIntent);
            }
        });

      /*  drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        //   drawerFragment.setArguments(bundle);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);*/

       /* FragmentManager fm = getSupportFragmentManager();
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if(getFragmentManager().getBackStackEntryCount() == 0) finish();
            }
        });*/

        // display the first navigation drawer view on app launch
      //  displayView(40);
    }
    private boolean returnBackStackImmediate(FragmentManager fm) {
        List<Fragment> fragments = fm.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment fragment : fragments) {
                if (fragment.getChildFragmentManager().getBackStackEntryCount() > 0) {
                    if (fragment.getChildFragmentManager().popBackStackImmediate()) {
                        return true;
                    } else {
                        return returnBackStackImmediate(fragment.getChildFragmentManager());
                    }
                }
            }
        }
        return false;
    }


  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      MenuInflater inflater = getMenuInflater();
      boolean chcklg = session.checkLogin();
      if(chcklg == true){
          inflater.inflate(R.menu.main, menu);
      }
      return true;
    }




  /*  public  void showSweet(){
        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                pDialog.dismiss();
                resideMenuClass.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        }, SPLASH_TIME_OUT);
    }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        /*else if(id == R.id.signout) {
            this.finish();
            session.logoutUser();
            Toast.makeText(getApplicationContext(), "You have logged out successfully", Toast.LENGTH_LONG).show();

            return true;
        }*/
    /*    else if(id == R.id.myben) {
            Fragment  fragment = new ContactMgmt();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //  String tag = Integer.toString(title);
            fragmentTransaction.replace(R.id.container_body, fragment,"Beneficiaries");
            fragmentTransaction.addToBackStack("Beneficiaries");
            fragmentTransaction.commit();

                    setActionBarTitle("Beneficiaries");

            return true;
        }*/



        return super.onOptionsItemSelected(item);
    }


    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
       boolean checklg = true;
        switch (position) {

           case 40:

               /* if(checklg == false){
                    this.finish();
                    session.logoutUser();
                    // After logout redirect user to Loing Activity
                    Intent i = new Intent(MainActivity.this, SignInActivity.class);

                    // Staring Login Activity

                    startActivity(i);
                }else {*/
                    fragment = new FirstHomeFrag();
               title = "FirstAgent";
                  /*  HashMap<String, String> dis = session.getDisp();
                    String names = dis.get(SessionManagement.KEY_DISP);

                    HashMap<String, String> sessname = session.getUserDetails();
                    String sesn = sessname.get(SessionManagement.KEY_USERID);

                    if (!(names == null)) {
                        if (names.contains(" ")) {
                            String fname = names.substring(0, names.indexOf(" "));
                            String lname = names.substring(names.lastIndexOf(" "), names.length());
                            Log.v("Fname is", fname);
                            Log.v("Lname is", lname);

                            title =lname;
                        }else{
                            title = names;
                        }
                    } else {
                        if (!(sesn == null)) {
                            if (sesn.contains(" ")) {
                                String fname = sesn.substring(0, sesn.indexOf(" "));
                                String lname = sesn.substring(sesn.lastIndexOf(" "), sesn.length());
                                Log.v(" SessFname is", fname);
                                Log.v("L Sessname is", lname);

                                title =  lname;
                            } else {
                                title =  names;
                            }
                        }

                    }
                    Log.d("Title",title);*/

                break;
            case 0:
                fragment = new OpenAcc();
                title = "Open Account";
                break;
            case 1:
                fragment = new Withdraw();
                title = "Cash Withdrawal";
                break;



            case 2:
                fragment = new FTMenu();
                title = "Fund Transfer";
                break;
            case 3:
                fragment = new AirtimeTransf();
                title = "Airtime Purchase";
                break;
            case 4:
            fragment = new BillersMenu();
                title = "Pay Bills";
                break;
            case 5:

                    fragment = new Minstat();
                    title = "Mini-Statement";

                break;

            case 7:
            this.finish();
       //     session.logoutUser();
            // After logout redirect user to Loing Activity
            Intent i = new Intent(MainActivity.this, SignInActivity.class);

            // Staring Login Activity
            startActivity(i);
            Toast.makeText(MainActivity.this, "You have successfully signed out", Toast.LENGTH_LONG).show();
                  break;

          /*  case 7:

                SetNav(0);

                break;
            case 8:

                SetNav(1);

                break;
            case 9:

                SetNav(2);

                break;

            case 10:

                    SetNav(3);

                break;
            case 11:

                    SetNav(4);

                break;
            case 12:

                    SetNav(5);

                break;
            case 13:

                    SetNav(6);

                break;
            case 14:
                if(checklg == false){
                    fragment = new Sign_In();
                    title = "Sign In";
                }else {
                    SetNav(7);
                }
                break;
            case 15:
                if(checklg == false){
                    fragment = new Sign_In();
                    title = "Sign In";
                }else {
                    SetNav(8);
                }
                break;

            case 16:
                if(checklg == false){
                    fragment = new Sign_In();
                    title = "Sign In";
                }else {
                    SetNav(9);
                }
                break;
            case 17:
                if(checklg == false){
                    fragment = new Sign_In();
                    title = "Sign In";
                }else {
                    SetNav(10);
                }
                break;
            case 18:
                if(checklg == false){
                    fragment = new Sign_In();
                    title = "Sign In";
                }else {
                    SetNav(11);
                }
                break;
            case 19:
                if(checklg == false){
                    fragment = new Sign_In();
                    title = "Sign In";
                }else {
                    SetNav(12);
                }
                break;
            case 20:
                if(checklg == false){
                    fragment = new Sign_In();
                    title = "Sign In";
                }else {
                    SetNav(13);
                }
                break;
            case 21:
                if(checklg == false){
                    fragment = new Sign_In();
                    title = "Sign In";
                }else {
                    SetNav(14);
                }
                break;
            case 22:
                if(checklg == false){
                    fragment = new Sign_In();
                    title = "Sign In";
                }else {
                    SetNav(15);
                }
                break;
            case 23:
                if(checklg == false){
                    fragment = new Sign_In();
                    title = "Sign In";
                }else {
                    SetNav(16);
                }
                break;
            case 24:
                if(checklg == false){
                    fragment = new Sign_In();
                    title = "Sign In";
                }else {
                    SetNav(17);
                }
                break;
            case 25:
                if(checklg == false){
                    fragment = new Sign_In();
                    title = "Sign In";
                }else {
                    SetNav(18);
                }
                break;
            case 26:
                if(checklg == false){
                    fragment = new Sign_In();
                    title = "Sign In";
                }else {
                    SetNav(19);
                }
                break;
            case 27:
                if(checklg == false){
                    fragment = new Sign_In();
                    title = "Sign In";
                }else {
                    SetNav(20);
                }
                break;
            case 28:
                if(checklg == false){
                    fragment = new Sign_In();
                    title = "Sign In";
                }else {
                    SetNav(21);
                }
                break;
            case 29:
                if(checklg == false){
                    fragment = new Sign_In();
                    title = "Sign In";
                }else {
                    SetNav(22);
                }
                break;
            case 30:
                if(checklg == false){
                    fragment = new Sign_In();
                    title = "Sign In";
                }else {
                    SetNav(23);
                }

            case 31:
                if(checklg == false){
                    fragment = new Sign_In();
                    title = "Sign In";
                }else {
                    SetNav(24);
                }
                break;

            case 32:
                if(checklg == false){
                    fragment = new Sign_In();
                    title = "Sign In";
                }else {
                    SetNav(25);
                }
                break;

            case 33:
                if(checklg == false){
                    fragment = new Sign_In();
                    title = "Sign In";
                }else {
                    SetNav(26);
                }
                break;

            case 34:
                if(checklg == false){
                    fragment = new Sign_In();
                    title = "Sign In";
                }else {
                    SetNav(27);
                }
                break;
            case 41:
                fragment = new PServices();
                title = "Personalise Menu";
                break;
            case 42:
                fragment = new PersWidgets();
                title = "Personalise Widgets";
                break;

            case 43:
                fragment = new PersThree();
                title = "Personalise Top Three";
                break;
            case 44:
                fragment = new PersNews();
                title = "Personalise News";
                break;*/
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //  String tag = Integer.toString(title);
            fragmentTransaction.replace(R.id.container_body, fragment,title);
            if(!(title.equals("Welcome"))) {
                fragmentTransaction.addToBackStack(title);
            }
            fragmentTransaction.commit();

            // set the toolbar title
          //  getSupportActionBar().setTitle(title);
        }
    }
    public void setActionBarTitle(String title) {

//        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onPause()
    {
        mShaker.pause();
        super.onPause();
        long secs = (new Date().getTime())/1000;
        Log.v("Seconds Loged",Long.toString(secs));
        session.putCurrTime(secs);
    }
   @Override
    protected void onResume()
    {
        mShaker.resume();
        super.onResume();
        boolean checklg = false;
        checklg = session.checkLogin();
        if (checklg == true) {
        HashMap<String, Long> nurl = session.getCurrTime();
        long newurl = nurl.get(SessionManagement.KEY_TIMEST);
            if(newurl > 0) {
                long secs = (new Date().getTime()) / 1000;
                long diff = 0;
                if (secs >= newurl) {
                    diff = secs - newurl;
                    if (diff > 180) {

                        this.finish();
                        session.logoutUser();
                        // After logout redirect user to Loing Activity
                        Intent i = new Intent(MainActivity.this, SignInActivity.class);

                        // Staring Login Activity
                        startActivity(i);
                        Toast.makeText(MainActivity.this, "Your session has expired. Please login again", Toast.LENGTH_LONG).show();
                    }
                }
            }
}


    }
    protected void onDestroy() {
        super.onDestroy();
       // session.logoutUser();
        // Put code here.

    }
    @Override
    public void onBackPressed()
    {
        if (!returnBackStackImmediate(getSupportFragmentManager())) {
            super.onBackPressed();
        }
       /* if(count > 0) {
            pager.setCurrentItem(0, true);
            count--;
        }else {
            super.onBackPressed();
        }*/
       /* FragmentManager fm = getSupportFragmentManager();
        int bentry = fm.getBackStackEntryCount();
        Log.i("TAG", "Frag Entry: " + bentry);
	*//*  if(bentry >0){
	  FragmentManager.BackStackEntry backEntry=getSupportFragmentManager().getBackStackEntryAt(bentry-1);
	    String str=backEntry.getName();
	    Log.v("Fragment Tag", str);
		setTitle(navMenuTitles[Integer.parseInt(str)]);
	   }*//*
        if(bentry > 0){
            String ide = null;
            for(int entry = 0; entry < fm.getBackStackEntryCount(); entry++){
                ide = fm.getBackStackEntryAt(entry).getName();
                Log.i("TAG", "Found fragment: " + ide);
            }

            //setActionBarTitle(ide);
        }else{

            new MaterialDialog.Builder(MainActivity.this)
                    .title("Confirm Exit")
                    .content("Are you sure you want to exit from NatMobile? ")
                    .positiveText("YES")
                    .negativeText("NO")

                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            finish();
                            session.logoutUser();

                        }

                        @Override
                        public void onNegative(MaterialDialog dialog) {
                            dialog.dismiss();
                        }
                    })
                    .show();


        }
       *//* Fragment fragment = (Fragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":"+pager.getCurrentItem());
        if (fragment != null) // could be null if not instantiated yet
        {
            if (fragment.getView() != null) {
                // Pop the backstack on the ChildManager if there is any. If not, close this activity as normal.
                if (!fragment.getChildFragmentManager().popBackStackImmediate()) {
                    finish();
                }
            }
        }*/
    }
    public void SetNav(int i){
        boolean nav = false;
        Log.v("Nav Id Count",Integer.toString(FragmentDrawer.planetsList.size()));
            // String
        int cont = FragmentDrawer.planetsList.size();
        if(cont > 0){
            String v = FragmentDrawer.planetsList.get(i).getNavId();
        Log.v("Selected Nav Id",v);
            if (nav == false) {


            }}
    }

    public void SetTheme(){
        HashMap<String, Integer> nmob = session.getCurrTheme();
        int position = nmob.get(SessionManagement.KEY_THEME);
        Log.v("Current Theme Pos ","Theme Pos is"+Integer.toString(position));
        //setTheme(R.style.MyMaterialTheme);
        switch (position) {


            case 1:

                setTheme(R.style.MyMaterialTheme);

                break;

            case 2:

                setTheme(R.style.BlueTheme);

                break;

            case 3:
                setTheme(R.style.RedTheme);
                break;


            case 4:
                setTheme(R.style.GreenTheme);
                break;
        }

            HashMap<String, String> nurl = session.getMobNo();
            String mno = nurl.get(SessionManagement.KEY_MOBILE);

            HashMap<String, String> nu = session.getFulln();
            String fname = nu.get(SessionManagement.KEY_FULLN);

            HashMap<String, String> ntxn = session.getTxnFlag();
            String txnflag = ntxn.get(SessionManagement.KEY_TXPIN);

            HashMap<String, String> nema = session.getEmail();
            String emal = nema.get(SessionManagement.KEY_EMAIL);

            HashMap<String, String> ninst = session.getInst();
            String insttyp = ninst.get(SessionManagement.KEY_INST);

            HashMap<String, String> nlstl = session.getLastl();
            String lastl = nlstl.get(SessionManagement.LASTL);

            HashMap<String, String> ndisp = session.getDisp();
            String dispn = ndisp.get(SessionManagement.KEY_DISP);

            session.createLoginSession(fname);
            session.putMobNo(mno);
            session.putTxnFlag(txnflag);
            session.putInst(insttyp);
            session.putEmail(emal);
            session.putLastl(lastl);
            session.putFulln(fname);
            if(!(dispn == null)) {
                dispn = dispn.replace("_"," ");
                session.putDisp(dispn);
            }



    }


}
