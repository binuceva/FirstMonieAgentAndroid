package firstmob.firstbank.com.firstagent;


import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapter.NavBeforeSignIn;
import adapter.NavDrawerItem;
import adapter.NavDrawerPOJO;
import adapter.SimpleSectionedRecyclerViewAdapter;
import de.hdodenhof.circleimageview.CircleImageView;


public class FragmentDrawSignIn extends Fragment implements View.OnClickListener {

    private static String TAG = FragmentDrawSignIn.class.getSimpleName();
TextView tv,home;
    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
   static public List<NavDrawerPOJO> planetsList = new ArrayList<NavDrawerPOJO>();
    CircleImageView iv;
    ImageView padl;
    public static TypedArray navMenuIcons,navIcpad;
    private NavBeforeSignIn adapter;
    private View containerView;
    SessionManagement session;
    private  static  String[] titles = null;
    private  static int [] dricons = null;
private static ArrayList<String> vd = new ArrayList<String>();
    protected static ArrayList<Integer> vimges = new ArrayList<Integer>();
    protected static ArrayList<Integer> vicpad = new ArrayList<Integer>();
    private static Integer[] icons = null;
    private static Integer[] iconspad = null;

    private FragmentSignListener drawerListener;

    public FragmentDrawSignIn() {

    }

    public void setDrawerListener(FragmentSignListener listener) {
        this.drawerListener = listener;
    }

    public static List<NavDrawerItem> getData() {
        List<NavDrawerItem> data = new ArrayList<>();


        // preparing navigation drawer items
        for (int i = 0; i < vd.size(); i++) {
            NavDrawerItem navItem = new NavDrawerItem();
            navItem.setTitle(vd.get(i));
            navItem.setIcon(vimges.get(i));
            navItem.setIconPad(vicpad.get(i));
            data.add(navItem);
        }
        return data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      titles = getActivity().getResources().getStringArray(R.array.nav_drawer_items);
       dricons = getActivity().getResources().getIntArray(R.array.nav_drawer_icons);
        session = new SessionManagement(getActivity());
vd.clear();

        vd.add("Banking Information");


        vd.add("NatChat");
        vd.add("NatLocator");
        vd.add("News");





vimges.clear();

        vimges.add(0);


        vimges.add(0);
        vimges.add(0);
        vimges.add(0);




vicpad.clear();

        vicpad.add(0);

        vicpad.add(0);
        vicpad.add(0);
        vicpad.add(0);



       // CheckServicesBool();

        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);


        navIcpad = getResources()
                .obtainTypedArray(R.array.nav_pad_icons);
//CheckServicesBool();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_navsignin, container, false);


        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);

        adapter = new NavBeforeSignIn(getActivity(), getData());
        //This is the code to provide a sectioned list

        tv = (TextView) layout.findViewById(R.id.section_text);
        padl = (ImageView) layout.findViewById(R.id.pad);
        iv = (CircleImageView) layout.findViewById(R.id.profile_image);
        iv.setOnClickListener(this);
        boolean checklg = false;
        padl.setBackgroundResource(0);
        checklg = session.checkLogin();
        if(checklg == true) {
            String root = Environment.getExternalStorageDirectory().toString();
            File imgFile = new File(root + "/req_images/CamProfile.jpg");

            if (imgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());


                iv.setImageBitmap(myBitmap);

            }
            padl.setBackgroundResource(R.mipmap.ic_unlocked);
        }else{

            padl.setBackgroundResource(R.mipmap.ic_locked);
        }
        home = (TextView) layout.findViewById(R.id.home);
        home.setOnClickListener(this);
        home.setText("");
        tv.setOnClickListener(this);
        HashMap<String, String> dis = session.getDisp();
        String names = dis.get(SessionManagement.KEY_DISP);

        HashMap<String, String> sessname = session.getUserDetails();
        String sesn = sessname.get(SessionManagement.KEY_USERID);

        if(!(names == null)){
            if(names.contains(" ")) {
                String fname = names.substring(0, names.indexOf(" "));
                String lname = names.substring(names.lastIndexOf(" "), names.length());
                Log.v("Fname is", fname);
                Log.v("Lname is", lname);

                tv.setText("Hi " + lname);
            }
            }else{
                if(!(sesn == null)){
                    if(sesn.contains(" ")) {
                        String fname = sesn.substring(0,sesn.indexOf(" "));
                        String lname = sesn.substring(sesn.lastIndexOf(" "),sesn.length() );
                        Log.v(" SessFname is", fname);
                        Log.v("L Sessname is", lname);

                        tv.setText("Hi "+lname);
                    }else {
                        tv.setText("Hi " + names);
                    }
            }
        }/*else{
          if(  checklg == false) {
              home.setText("");
              home.setEnabled(false);
              tv.setText("Please Sign In");
          }else{
              tv.setText("Choose Display Name");
          }
        }*/
        List<SimpleSectionedRecyclerViewAdapter.Section> sections =
                new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();

        //Sections
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(5,""));



        //Add your adapter to the sectionAdapter


        //Apply this adapter to the RecyclerView
        recyclerView.setAdapter(adapter);
        //recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                drawerListener.onDrawerItemSelected(view, position);
                mDrawerLayout.closeDrawer(containerView);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return layout;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
             //  invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
               //invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
               // toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.home){

            getActivity().finish();
            session.logoutUser();
            // After logout redirect user to Loing Activity
            Intent i = new Intent(getActivity(), MainActivity.class);

            // Staring Login Activity
           startActivity(i);
            Toast.makeText(getActivity(), "You have logged out successfully", Toast.LENGTH_LONG).show();

        }
        if(view.getId() == R.id.profile_image) {
            drawerListener.onDrawerItemSelected(view, 40);
            mDrawerLayout.closeDrawer(containerView);
        }

        if(view.getId() == R.id.section_text) {
            drawerListener.onDrawerItemSelected(view, 40);
            mDrawerLayout.closeDrawer(containerView);
            Fragment fragment = null;
            String title = null;
            boolean checklg = false;

            if (fragment != null) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //  String tag = Integer.toString(title);
                fragmentTransaction.replace(R.id.container_body, fragment,title);
                fragmentTransaction.addToBackStack(title);
                fragmentTransaction.commit();

            }
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

    public interface FragmentSignListener {
        public void onDrawerItemSelected(View view, int position);
    }
    public void CheckServicesBool(){

        boolean chkftra = session.checkPers("FTR");
        boolean chkmbm = session.checkPers("MBM");
        boolean chkpas = session.checkPers("PAS");

        boolean atopup = session.checkPers("ATP");
        boolean fst = session.checkPers("FST");
        boolean chq = session.checkPers("CHQ");

        boolean sch = session.checkPers("SCH");
        boolean kpow = session.checkPers("KPO");
        boolean blnhif = session.checkPers("NHF");

        boolean blzuku = session.checkPers("ZUK");
        boolean blds = session.checkPers("DST");
        boolean blkra = session.checkPers("KRA");
        planetsList.clear();
        if(chkftra == true){
vd.add("Funds Transfer");
            vimges.add(0);
            vicpad.add(0);
            planetsList.add(new NavDrawerPOJO("FT"));
        }else{

        }


        if(chkmbm == true){
            vd.add("Mobile Money");
            vimges.add(0);
            vicpad.add(0);
            planetsList.add(new NavDrawerPOJO("MBM"));
        }else{

        }

        if(chkpas == true){
            vd.add("Payment Services");
            vimges.add(0);
            vicpad.add(0);
            planetsList.add(new NavDrawerPOJO("PAS"));
        }else{

        }

        if(atopup == true){
            vd.add("Airtime TopUp");
            vimges.add(0);
            vicpad.add(0);
            planetsList.add(new NavDrawerPOJO("ATP"));
        }else{

        }


        if(fst == true){
            vd.add("Full Statement");
            vimges.add(0);
            vicpad.add(0);
            planetsList.add(new NavDrawerPOJO("FST"));
        }else{

        }

        if(chq == true){

        }else{

        }

        if(sch == true){
            vd.add("School Fees");
            vimges.add(R.drawable.grad);
            vicpad.add(0);
            planetsList.add(new NavDrawerPOJO("SCH"));
        }else{

        }


        if(kpow == true){
            vd.add("Kenya Power");
            vimges.add(R.drawable.kpower);
            vicpad.add(0);
            planetsList.add(new NavDrawerPOJO("KPO"));
        }else{

        }

        if(blnhif == true){
            vd.add("NHIF");
            vimges.add(R.drawable.nhif);
            vicpad.add(0);
            planetsList.add(new NavDrawerPOJO("NHF"));
        }else{

        }

        if(blzuku == true){
            vd.add("Zuku");
            vimges.add(R.drawable.dstv);
            vicpad.add(0);
            planetsList.add(new NavDrawerPOJO("ZUKU"));
        }else{

        }


        if(blds == true){
            vd.add("DSTV");
            vimges.add(R.drawable.dstv);
            vicpad.add(0);
            planetsList.add(new NavDrawerPOJO("DST"));
        }else{

        }

        if(blkra == true){

        }else{

        }
        vd.add("Personalise Menu");
        vimges.add(0);
        vicpad.add(0);
        planetsList.add(new NavDrawerPOJO("PSM"));
    }

}
