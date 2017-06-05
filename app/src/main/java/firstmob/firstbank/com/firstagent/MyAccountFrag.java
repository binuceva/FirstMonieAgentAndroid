package firstmob.firstbank.com.firstagent;


import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyStore;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import adapter.BannList;
import adapter.BannerListAdapter;
import adapter.Dashboard;
import adapter.FavList;
import adapter.RvTopThreeAdapt;
import adapter.TopFavAdapter;
import adapter.TopThreeAdapter;
import adapter.adapter.NewAccountList;
import adapter.adapter.NewAccountRecListAdapter;

public class MyAccountFrag extends Fragment implements View.OnClickListener {
    Button sign_in, sign_up, proceed, link, open_nat;
    public TextView accid;
    public TextView curr;
    public TextView accamo,currtxt;
    LinearLayout lyben;
    CardView cv;
    int currPos = -1;
	ImageView ivIcon;
   RecyclerView gridView,gridView2;

    //GridView gridView;
    List<Dashboard> gridlist = new ArrayList<Dashboard>();
    List<FavList> favlist = new ArrayList<FavList>();
    TopThreeAdapter GridAdapt;
    RvTopThreeAdapt RvAdapt;
    TopFavAdapter FavAdapt;
	TextView tv1,tv2,tv3,nbkel;
    Button accessac,pinchange;
    RelativeLayout rll;
    boolean chkScroll = false;
    RecyclerView lvbann;
    TextView txphn;
    Button ltop;
    private Runnable animateViewPager;
    private Handler handler;
    private static final long ANIM_VIEWPAGER_DELAY = 5000;
    private static final long ANIM_VIEWPAGER_DELAY_USER_VIEW = 10000;
    boolean stopSliding = false;
    List<NewAccountList> planetsList = new ArrayList<NewAccountList>();
    LinearLayoutManager layoutManager,layoutManager2;
    NewAccountRecListAdapter aAdpt;
    RelativeLayout rl;

    List<BannList> banlist = new ArrayList<BannList>();
   BannerListAdapter banadpt;
    //CircleImageView iv,iv2,iv3;
    SessionManagement session;
    private TextView emptyView;
    ProgressBar prgDialog;
    boolean chkad = false;
    //private ViewPager mPager;
  /* ViewPageMyAccAdapter mAdapter;
    PageIndicator mIndicator;*/
    /*Button scr,scl;*//*
   */ Button scr,scl;
    static Hashtable<String, String> data1;
    String paramdata = "";
    LinearLayout lu;

	public MyAccountFrag() {

	}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.my_account2, container,
				false);

       // lv = (RecyclerView) view.findViewById(R.id.listView1);
        lvbann = (RecyclerView) view.findViewById(R.id.listView2);
        rl = (RelativeLayout) view.findViewById(R.id.phn);
       lu = (LinearLayout) view.findViewById(R.id.lu);
        lyben = (LinearLayout) view.findViewById(R.id.lyben);
       //lu.setOnClickListener(this);
        rll = (RelativeLayout) view.findViewById(R.id.rl);
    lu.setOnTouchListener(new LinearLayout.OnTouchListener() {

         int downX
                 ,
                 upX;

         @Override
         public boolean onTouch(View v, MotionEvent event) {

             if (event.getAction() == MotionEvent.ACTION_DOWN) {
                 downX = (int) event.getX();
                 Log.i("event.getX()", " downX " + downX);

             } else if (event.getAction() == MotionEvent.ACTION_UP) {
                 upX = (int) event.getX();
                 Log.i("event.getX()", " upX " + upX);
                 if((upX - downX) > -100 && (upX - downX) < 100) {
                   /*  Log.i("No Swipe", "On Clicked");
                     Toast.makeText(getActivity(), "On Clicked", Toast.LENGTH_SHORT).show();
                     chkScroll = true;*/
                 /*    Log.v("Curr Pos On Click",Integer.toString(currPos));
                     String acc = planetsList.get(currPos).getAccId();
                     String ptype = planetsList.get(currPos).getProdtype();
                     Bundle b  = new Bundle();
                     b.putString("prtype",ptype);
                     session.putSelAcc(acc);
                     Fragment fragment = new LoggedInFrag();
                     fragment.setArguments(b);
                     FragmentManager fragmentManager = getFragmentManager();
                     FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                     //  String tag = Integer.toString(title);
                     fragmentTransaction.replace(R.id.container_body, fragment, "My Account");
                     fragmentTransaction.addToBackStack("My Account");
                     ((MainActivity)getActivity())
                             .setActionBarTitle("My Account");
                     fragmentTransaction.commit();*/
                 }
                   else  if (upX - downX > 100) {
                         currPos = currPos-1;
                         Move(currPos);
                     chkScroll = false;
                         // swipe right
                     } else if (downX - upX > -100) {
                     if(currPos+1 < planetsList.size()) {
                         currPos = currPos + 1;
                         Move(currPos);
                         chkScroll = false;
                     }
                         // swipe left
                     }


             }
             return false;
         }
     });
       // rll.setOnTouchListener(new RelativeLayoutTouchListener());
     /*   iv2 = (CircleImageView) view.findViewById(R.id.item2);
        iv3 = (CircleImageView) view.findViewById(R.id.item3);
        iv = (CircleImageView) view.findViewById(R.id.item1);*/
        ltop = (Button) view.findViewById(R.id.lytop3);
        ltop.setOnClickListener(this);
        cv = (CardView) view.findViewById(R.id.card_view6);


        accid = (TextView) view.findViewById(R.id.bname);
        curr = (TextView) view.findViewById(R.id.curr);
        accamo = (TextView) view.findViewById(R.id.vv);
        currtxt = (TextView) view.findViewById(R.id.curr9);
        curr = (TextView) view.findViewById(R.id.curr);
        //gridView = (GridView) view.findViewById(R.id.gridView1);

        gridView2 = (RecyclerView) view.findViewById(R.id.gridView2);


        scr = (Button) view.findViewById(R.id.scr);

        scr.setOnClickListener(this);

        scl = (Button) view.findViewById(R.id.scl);

        scl.setOnClickListener(this);
      /*  iv.setOnClickListener(this);
        iv2.setOnClickListener(this);
        iv3.setOnClickListener(this);*/
        //rl.setOnClickListener(this);
        //txphn.setOnClickListener(this);
        prgDialog = (ProgressBar) view.findViewById(R.id.progressBar);
        session = new SessionManagement(getActivity());
        // Set Progress Dialog Text
       // prgDialog.setMessage("Loading Account Info....");
        // Set Cancelable as False

     layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        gridView.setLayoutManager(layoutManager);

       LinearLayoutManager layoutManager3 = new LinearLayoutManager(getActivity());
        layoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        gridView2.setLayoutManager(layoutManager3);


        layoutManager2 = new LinearLayoutManager(getActivity());
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        //layoutManager.scrollToPosition(currPos);
        PopulateGrid();
        lvbann.setLayoutManager(layoutManager2);
        emptyView = (TextView) view.findViewById(R.id.empty_view);
planetsList.clear();

     //   checkInternetConnection();
SetAccounts();


       /* mPager = (ViewPager)view.findViewById(R.id.pager);
        mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);*/
         //SendEncUrl();
        //SetTopThree();

banlist.clear();
      banlist.add(new BannList("Nat eLocker", "Backup Contacts Securely"));
      banlist.add(new BannList("Nat Rings", "Enjoy Free Ringtones Today"));
        banlist.add(new BannList("Nat Discounts", "Enjoy Special Offers"));

        banadpt = new BannerListAdapter( getActivity(),banlist);
         SetFav();
        lvbann.setAdapter(banadpt);
        //Toast.makeText(getActivity(), planetsList.size(), Toast.LENGTH_LONG).show();
        lvbann.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });
     /*  lv.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        lv.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),lv,new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String acc = planetsList.get(position).getAccId();
                session.putSelAcc(acc);
                Fragment fragment = new LoggedInFrag();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //  String tag = Integer.toString(title);
                fragmentTransaction.replace(R.id.container_body, fragment, "My Account");
                fragmentTransaction.addToBackStack("My Account");
                ((MainActivity)getActivity())
                        .setActionBarTitle("My Account");
                fragmentTransaction.commit();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }

        ));*/



        gridView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), gridView, new ClickListener() {
            @Override
            public void onClick(View view, int i) {

                Dashboard  p = gridlist.get(i);
                String nav = p.getName();
                Log.v("Nav ",nav);
                SetNav(nav);


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }

        ));

        gridView2.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), gridView, new ClickListener() {
            @Override
            public void onClick(View view, int i) {

                FavList  p = favlist.get(i);
                String nav = p.getFType();
                String acn = p.getAccn();
                Bundle b  = new Bundle();
                if (nav.equals("WTB")) {


                } else if (nav.equals("MBM")) {


                }
                else if (nav.equals("ATM")) {
                    Fragment fragment = new AirtimeTransf();

                    b.putString("mobno",acn);
                    fragment.setArguments(b);
                    String title = "Airtime Topup";
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    //  String tag = Integer.toString(title);
                    fragmentTransaction.replace(R.id.container_body, fragment, title);
                    fragmentTransaction.addToBackStack(title);
                    fragmentTransaction.commit();
                    ((MainActivity)getActivity())
                            .setActionBarTitle(title);


                }


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }

        ));

        lvbann.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),lvbann,new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Fragment fragment = null;
                String title  = null;
                        if(position == 0){
                            fragment = new PhoneBackUp();
                                    title = "Phone Backup";
                        }
               else if(position == 1){
                            Toast.makeText(getActivity(), " Coming Soon", Toast.LENGTH_LONG).show();

                        }
                        else if(position == 2){
                            Toast.makeText(getActivity(), " Coming Soon", Toast.LENGTH_LONG).show();
                        }
            if(fragment != null) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //  String tag = Integer.toString(title);
                fragmentTransaction.replace(R.id.container_body, fragment, title);
                fragmentTransaction.addToBackStack(title);
                ((MainActivity)getActivity())
                        .setActionBarTitle(title);
                fragmentTransaction.commit();
            }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }

        ));
        /*lv.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View firstVisibleChild = lv.getChildAt(0);
                int currentPosition = lv.getChildPosition(firstVisibleChild);

                if(currentPosition == 0){
                   scl.setVisibility(View.GONE);
                }
                else{
                    scl.setVisibility(View.VISIBLE);
                }

                Log.v("Curr Position","Current Position is "+Integer.toString(currentPosition)+" ,Planets List Size is "+Integer.toString(planetsList.size()));

                if((currentPosition+1) == planetsList.size()) {
                   scr.setVisibility(View.GONE);

                }else{
                   scr.setVisibility(View.VISIBLE);
                }


            }
        });*/
       /* mPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction()) {

                    case MotionEvent.ACTION_CANCEL:
                        break;

                    case MotionEvent.ACTION_UP:
                        // calls when touch release on ViewPager

                        stopSliding = false;
                        runnable(3);
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
        });*/

       // checkInternetConnection2();
		return view;
	}
public void SetAccounts(){
    planetsList.add(new NewAccountList("01243031739100","KES","18,408.91 Naira","Agent Account","01080"));
    planetsList.add(new NewAccountList("01243031751200","KES","2031.91 Naira","Transactional Account","01080"));

    // planetsList.add(new NewAccountList("01243031739101","KES","KES 25,301.02","MPESA Float Balance","01080"));
  //  planetsList.add(new NewAccountList("01243031739101","KES","KES 12,345.45","Commisions Earned","01080"));
    aAdpt = new NewAccountRecListAdapter(getActivity(),planetsList);
    currPos = 0;
    Move(currPos);
}
    public void PopulateGrid(){
       gridlist.clear();

/*
        gridlist.add(new Dashboard("Funds Transfer", R.drawable.icons39));
        gridlist.add(new Dashboard("Mobile Money", R.drawable.icons42));
        gridlist.add(new Dashboard("Airtime TopUp", R.drawable.icons41));*/

        HashMap<String, String> topo = session.getTop1();
        String top1 = topo.get(SessionManagement.KEY_TOP1);

        HashMap<String, String> topo2 = session.getTop2();
        String top2 = topo2.get(SessionManagement.KEY_TOP2);

        HashMap<String, String> topo3 = session.getTop3();
        String top3 = topo3.get(SessionManagement.KEY_TOP3);

        if(!(top1 == null)){
            setTop3Grid(top1);
        }else{
            top1 = "Open Account";
            setTop3Grid("Open Account");
        }

        if(!(top2 == null)){
            setTop3Grid(top2);
        }else{
            top2 = "Fund Transfer";
            setTop3Grid("Fund Transfer");
        }

        if(!(top3 == null)){
            setTop3Grid(top3);
        }else{
            top3 = "Airtime TopUp";
            setTop3Grid("Airtime TopUp");
        }
        setNotTop3Grid(top1,top2,top3);
        RvAdapt = new RvTopThreeAdapt( getActivity(),gridlist);
        //GridAdapt = new TopThreeAdafpter(gridlist ,getActivity());
        gridView.setAdapter(RvAdapt);
    }
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.phn) {
            Fragment  fragment = new PhoneBackUp();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //  String tag = Integer.toString(title);
            fragmentTransaction.replace(R.id.container_body, fragment,"Phone BackUp");
            fragmentTransaction.addToBackStack("Phone BackUp");
            ((MainActivity)getActivity())
                    .setActionBarTitle("Phone BackUp");
            fragmentTransaction.commit();
        }
        if (v.getId() == R.id.tvvb) {
            Fragment  fragment = new PhoneBackUp();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //  String tag = Integer.toString(title);
            fragmentTransaction.replace(R.id.container_body, fragment,"Phone BackUp");
            fragmentTransaction.addToBackStack("Phone BackUp");
            ((MainActivity)getActivity())
                    .setActionBarTitle("Phone BackUp");
            fragmentTransaction.commit();
        }
        if(v.getId() == R.id.lytop3){
            Fragment  fragment = new PersThree();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //  String tag = Integer.toString(title);
            fragmentTransaction.replace(R.id.container_body, fragment,"Personalise Top Three");
            fragmentTransaction.addToBackStack("Personalise Top Three");
            ((MainActivity)getActivity())
                    .setActionBarTitle("Personalise Top Three");
            fragmentTransaction.commit();
        }
        if(v.getId() == R.id.lu){
if(currPos > -1) {
   /* String acc = planetsList.get(currPos).getAccId();
    session.putSelAcc(acc);
    Fragment fragment = new LoggedInFrag();
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    //  String tag = Integer.toString(title);
    fragmentTransaction.replace(R.id.container_body, fragment, "My Account");
    fragmentTransaction.addToBackStack("My Account");
    ((MainActivity)getActivity())
            .setActionBarTitle("My Account");
    fragmentTransaction.commit();*/
}
        }
        if (v.getId() == R.id.scr) {
           /* View firstVisibleChild = lv.getChildAt(0);
            int currentPosition = lv.getChildPosition(firstVisibleChild)+1;
            if(currentPosition < planetsList.size()) {
                lv.smoothScrollToPosition(currentPosition);


            }*/
            currPos = currPos+1;
            Move(currPos);


        }
        if (v.getId() == R.id.scl) {
           /* View firstVisibleChild = lv.getChildAt(0);
            int currentPosition = lv.getChildPosition(firstVisibleChild);
            if (currentPosition > 0) {
                lv.smoothScrollToPosition(currentPosition - 1);

            }*/
            currPos = currPos-1;
            Move(currPos);
        }
       if (v.getId() == R.id.item1) {
            HashMap<String, String> topo = session.getTop1();
            String top1 = topo.get(SessionManagement.KEY_TOP1);
            if(!(top1 == null)){
                SetNav(top1);
            }else{
                SetNav("Funds Transfer");
            }

        }
         if (v.getId() == R.id.item2) {

            HashMap<String, String> topo = session.getTop2();
            String top2 = topo.get(SessionManagement.KEY_TOP2);
            if(!(top2 == null)){
                SetNav(top2);
            }else{
                SetNav("Cash Deposit");
            }
        }
       if (v.getId() == R.id.item3) {
            HashMap<String, String> topo = session.getTop3();
            String top3 = topo.get(SessionManagement.KEY_TOP3);
            if(!(top3 == null)){
                SetNav(top3);
            }else{
                SetNav("Airtime TopUp");
            }
        }
    }

    private boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            //	new SendTask().execute();
            registerUser();
            //	RegTest();
            return true;
        } else {
            CheckAdaptEmpty();
            Toast.makeText(
                    getActivity(),
                    "No Internet Connection. Please check your internet setttings",
                    Toast.LENGTH_LONG).show();
            return false;
        }
    }
    public void registerUser(){
        HashMap<String, String> nurl = session.getMobNo();
        String newurl = nurl.get(SessionManagement.KEY_MOBILE);
        String numbers = newurl.substring(Math.max(0, newurl.length() - 9));

        HashMap<String, String> instit = session.getInst();
        String finalinst  = instit.get(SessionManagement.KEY_INST);
        Log.v("Mobile Number",numbers);

        invokeWS(numbers,finalinst);
       // SendEncUrl(numbers,finalinst);
 }
    public void invokeWS( String tplate,String inst){
        // Show Progress Dialog
        prgDialog.setVisibility(View.VISIBLE);

        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(40000);
        HashMap<String, String> nurl = session.getNetURL();
        String newurl = nurl.get(SessionManagement.NETWORK_URL);
       // String url = newurl+"/natmobileapi/rest/customer/ministat?acctno="+acc+"";

      //  String url =newurl+"/natmobileapi/rest/customer/balinquiry?mobno="+tplate+"&acctype="+inst;
        String url = ApplicationConstants.NET_URL+ApplicationConstants.AND_ENPOINT+"balinquiry/"+tplate+"/"+inst;


        Log.v("Bal inquiry URL",url);
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
                prgDialog.setVisibility(View.INVISIBLE);
                planetsList.clear();
                try {
                    // JSON Object
                    Log.v("response..:", response);
                    JSONObject obj = new JSONObject(response);
                    if(obj.optString("message").equals("SUCCESS")){
                        JSONArray js = obj.getJSONArray("accdetails");
                        Log.v("JSON Aray", js.toString());
                        if(js.length() > 0){


                            JSONObject json_data = null;
                            for (int i = 0; i < js.length(); i++) {
                                json_data = js.getJSONObject(i);
                                String accid = json_data.getString("accountid");
                                String balance = json_data.getString("clearedbalance");

                                String curr = json_data.getString("currencycode");
                                String prid = json_data.getString("prodid");
                                String prtype = json_data.optString("prodtype");
                                chkad = true;

                                double amount = Double.parseDouble(balance);
                                DecimalFormat formatter = new DecimalFormat("#,###.00");
                             String v =   formatter.format(amount);

                                planetsList.add(new NewAccountList("01243031739100","KES","KES 18,408.91","Transactional Accounts: National","01080"));
                                planetsList.add(new NewAccountList("01243031739101","KES","KES 25,301.02","MPESA Float Balance","01080"));
                                planetsList.add(new NewAccountList("01243031739101","KES","KES 12,345.45","Commisions Earned","01080"));
                                aAdpt = new NewAccountRecListAdapter(getActivity(),planetsList);


                            }
                            int finalindex = 0;

                            Collections.swap(planetsList,finalindex,0);
                            //SetScrollDiss();
                            currPos = 0;
                            Move(currPos);

                            //lv.setAdapter(aAdpt);

                        }
                    }
                    // Else display error message
                    else{
                        Toast.makeText(getActivity(), getActivity().getText(R.string.conn_error), Toast.LENGTH_LONG).show();
                       // CheckAdaptEmpty();
                        SetScrollDiss();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getActivity(), getActivity().getText(R.string.conn_error), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    SetScrollDiss();

                }
                catch(Exception e){
                    e.printStackTrace();
                }
                SetScrollDiss();
                CheckAdaptEmpty();
            }
            @Override
            public void onFailure(int statusCode, Throwable error,
                                 String content) {
                CheckAdaptEmpty();
                SetScrollDiss();
                // Hide Progress Dialog
                prgDialog.setVisibility(View.GONE);
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
                    Toast.makeText(getActivity(), getActivity().getText(R.string.conn_error), Toast.LENGTH_LONG).show();

                }
            }
        });
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
    public void CheckAdaptEmpty(){





















































































































































        emptyView.setText(getActivity().getText(R.string.conn_error));
        Log.v("Planet List Size is",Integer.toString(planetsList.size()));

            if (planetsList.size() == 0) {
                lu.setVisibility(View.GONE);
                cv.setVisibility(View.GONE);
                scr.setVisibility(View.GONE);
                scl.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            } else {
                cv.setVisibility(View.VISIBLE);
                lu.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
            }

    }
    public void SetScrollDiss(){
       //Toast.makeText(getActivity(), planetsList.size(), Toast.LENGTH_LONG).show();
        if (planetsList.size() <= 1) {
         scr.setVisibility(View.GONE);
            scl.setVisibility(View.GONE);
        }

        else if(planetsList.size() > 0){
            scl.setVisibility(View.GONE);
           scr.setVisibility(View.VISIBLE);
        }
    }

  /*  public  void SetTopThree(){
        HashMap<String, String> topo = session.getTop1();
        String top1 = topo.get(SessionManagement.KEY_TOP1);

        HashMap<String, String> topo2 = session.getTop2();
        String top2 = topo2.get(SessionManagement.KEY_TOP2);

        HashMap<String, String> topo3 = session.getTop3();
        String top3 = topo3.get(SessionManagement.KEY_TOP3);

        if(!(top1 == null)){
setTop3(top1,iv,tv1);
        }else{
            setTop3("Funds Transfer",iv,tv1);
        }

        if(!(top2 == null)){
            setTop3(top2,iv2,tv2);
        }else{
            setTop3("Mobile Money",iv2,tv2);
        }

        if(!(top3 == null)){
            setTop3(top3,iv3,tv3);
        }else{
            setTop3("Airtime Top Up",iv3,tv3);
        }
    }*/



    public void SetFav(){
       /* favlist.add(new FavList("Limo Robert", R.drawable.icons19));
        favlist.add(new FavList("Ronald", R.drawable.icons19));
        favlist.add(new FavList("Ian", R.drawable.icons42));*/

        FavAdapt = new TopFavAdapter( getActivity(),favlist);

        //gridView2.setAdapter(FavAdapt);
    }

    private boolean checkInternetConnection2() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            //	new SendTask().execute();
            registerUser2();
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
    public void registerUser2(){

        HashMap<String, String> nurl = session.getMobNo();
        String newurl = nurl.get(SessionManagement.KEY_MOBILE);
        String numbers = newurl.substring(Math.max(0, newurl.length() - 9));
        invokeWS(numbers);
    }
    public void invokeWS( String tplate){
        // Show Progress Dialog


        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(35000);
        HashMap<String, String> nurl = session.getNetURL();
        String newurl = nurl.get(SessionManagement.NETWORK_URL);
        // String url = newurl+"/natmobileapi/rest/customer/ministat?acctno="+acc+"";

        //String url =newurl+"/natmobileapi/rest/customer/loadben?mobno="+tplate+"";
        String url = ApplicationConstants.NET_URL+ApplicationConstants.AND_ENPOINT+"loadben/"+tplate;

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

                try {
                    // JSON Object
                    Log.v("response..:", response);
                    JSONObject obj = new JSONObject(response);
                    if(obj.optString("message").equals("SUCCESS")){
                        JSONArray js = obj.getJSONArray("bendetails");
                        Log.v("JSON Aray", js.toString());
                        if(js.length() > 0){
                                    lyben.setVisibility(View.VISIBLE);

                            JSONObject json_data = null;
                            for (int i = 0; i < js.length(); i++) {
                                json_data = js.getJSONObject(i);
                                //String accid = json_data.getString("benacid");


                                String bname = json_data.optString("shname");
                                bname = bname.replace("_"," ");
                                String ms = json_data.optString("msisdn");
                                String bid = json_data.optString("commonid");
                                String accountid = json_data.optString("benacid");
                                String btype = json_data.optString("ben_type");
                                String ban = json_data.optString("bank");
                                String bran = json_data.optString("branch");
                                ms = "0"+ms;
                                String fbenty = null;
                                if(btype.equals("WTB")){
                                    favlist.add(new FavList(bname, 0,"WTB",accountid));
                                }
                                else  if(btype.equals("OTB")){
                                    favlist.add(new FavList(bname, 0,"OTB",accountid));
                                }
                                else  if(btype.equals("MBM")){
                                    favlist.add(new FavList(bname, 0,"MBM",ms));
                                }
                                else  if(btype.equals("ATM")){
                                    favlist.add(new FavList(bname, 0,"ATM",ms));
                                }


                            }
                            FavAdapt = new TopFavAdapter( getActivity(),favlist);
                           gridView2.setAdapter(FavAdapt);
                        }
                    }
                    // Else display error message
                    else{
                        Toast.makeText(getActivity(), getActivity().getText(R.string.conn_error), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getActivity(), getActivity().getText(R.string.conn_error), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getActivity(), getActivity().getText(R.string.conn_error), Toast.LENGTH_LONG).show();

                }
            }
        });
    }
    public void setTop3Grid(String ds){
        if(ds.equals("Open Account")){


            gridlist.add(new Dashboard("Open Account",0));
        }


        if(ds.equals("Fund Transfer")){

           gridlist.add(new Dashboard("Fund Transfer",0));
        }
        if(ds.equals("Airtime TopUp")){


           // gridlist.add(new Dashboard("Airtime TopUp", R.drawable.icons41));



        }

        if(ds.equals("Withdraw")){


          //  gridlist.add(new Dashboard("Withdraw", R.drawable.icons41));



        }
        if(ds.equals("Full Statement")){

            gridlist.add(new Dashboard("Full Statement", 0));

        }


        if(ds.equals("DSTV")){

            gridlist.add(new Dashboard("DSTV", R.drawable.dstv));

        }
        if(ds.equals("Mini Statement")){

           gridlist.add(new Dashboard("Mini Statement", 0));

        }

        if(ds.equals("GOTV")){

            gridlist.add(new Dashboard("GOTV", R.drawable.gotv));

        }

    }

    public void setNotTop3Grid(String ds,String ds2,String ds3){
        if(!(ds.equals("Open Account")) && !(ds2.equals("Open Account")) && !(ds3.equals("Open Account"))){


         //   gridlist.add(new Dashboard("Open Account", R.drawable.icons19));
        }
        if(!(ds.equals("Fund Transfer")) && !(ds2.equals("Fund Transfer")) && !(ds3.equals("Fund Transfer"))){

           // gridlist.add(new Dashboard("Fund Transfer", R.drawable.icons42));
        }
        if(!(ds.equals("Withdraw")) && !(ds2.equals("Withdraw")) && !(ds3.equals("Withdraw"))){


          // gridlist.add(new Dashboard("Withdraw", R.drawable.icons41));



        }
        if(!(ds.equals("Airtime TopUp")) && !(ds2.equals("Airtime TopUp")) && !(ds3.equals("Airtime TopUp"))){


          //  gridlist.add(new Dashboard("Airtime TopUp", R.drawable.icons41));



        }
        if(!(ds.equals("Mini Statement")) && !(ds2.equals("Mini Statement")) && !(ds3.equals("Mini Statement"))){


          // gridlist.add(new Dashboard("Mini Statement", R.drawable.icons55));



        }
  /*      if(!(ds.equals("Full Statement")) && !(ds2.equals("Full Statement")) && !(ds3.equals("Full Statement"))){

            gridlist.add(new Dashboard("Full Statement", R.drawable.icons55));

        }*/




        if(!(ds.equals("DSTV")) && !(ds2.equals("DSTV")) && !(ds3.equals("DSTV"))){

            gridlist.add(new Dashboard("DSTV", R.drawable.dstv));

        }

        if(!(ds.equals("GOTV")) && !(ds2.equals("GOTV")) && !(ds3.equals("GOTV"))){

            gridlist.add(new Dashboard("GOTV", R.drawable.gotv));

        }

    }
    public void SetNav(String v){
        boolean nav = false;

            if (nav == false) {
                if (v.equals("Open Account")) {
                    Fragment fragment = new OpenAcc();
                    String title = "Open Account";
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    //  String tag = Integer.toString(title);
                    fragmentTransaction.replace(R.id.container_body, fragment, title);
                    fragmentTransaction.addToBackStack(title);
                    fragmentTransaction.commit();
                    ((MainActivity)getActivity())
                            .setActionBarTitle(title);
                    nav = true;
                } else if (v.equals("Fund Transfer")) {
                    Fragment fragment = new FTMenu();
                    String title = "Fund Transfer";
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    //  String tag = Integer.toString(title);
                    fragmentTransaction.replace(R.id.container_body, fragment, title);
                    fragmentTransaction.addToBackStack(title);
                    fragmentTransaction.commit();
                    ((MainActivity)getActivity())
                            .setActionBarTitle(title);
                    nav = true;

                }
                else if (v.equals("Mini Statement")) {
                    Fragment fragment = new Minstat();
                    String title = "Mini Statement";
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    //  String tag = Integer.toString(title);
                    fragmentTransaction.replace(R.id.container_body, fragment, title);
                    fragmentTransaction.addToBackStack(title);
                    fragmentTransaction.commit();
                    ((MainActivity)getActivity())
                            .setActionBarTitle(title);
                    nav = true;

                }
                else if (v.equals("Withdraw")) {
                    Fragment fragment = new Withdraw();
                    String title = "Cash Withdrawal";
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    //  String tag = Integer.toString(title);
                    fragmentTransaction.replace(R.id.container_body, fragment, title);
                    fragmentTransaction.addToBackStack(title);
                    fragmentTransaction.commit();
                    ((MainActivity)getActivity())
                            .setActionBarTitle(title);
                    nav = true;
                }
                else if (v.equals("Airtime TopUp")) {
                    Fragment fragment = new AirtimeTransf();
                    String title = "Airtime Top Up";
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    //  String tag = Integer.toString(title);
                    fragmentTransaction.replace(R.id.container_body, fragment, title);
                    fragmentTransaction.addToBackStack(title);
                    fragmentTransaction.commit();
                    ((MainActivity)getActivity())
                            .setActionBarTitle(title);
                    nav = true;
                }
                else if (v.equals("School Fees")) {


                }

                else if (v.equals("Full Statement")) {

                } else if (v.equals("CHQ")) {

                    nav = true;

                }
                else if( v.equals("GOTV")){
                    Bundle b  = new Bundle();
                    String serv ="GOTV";
                    b.putString("serv",serv);
                    Fragment  fragment = new CableTV();
                    fragment.setArguments(b);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    //  String tag = Integer.toString(title);
                    fragmentTransaction.replace(R.id.container_body, fragment,serv);
                    fragmentTransaction.addToBackStack(serv);
                    fragmentTransaction.commit();
                    ((MainActivity)getActivity())
                            .setActionBarTitle(serv);

                }
                else if( v.equals("DSTV")){
                    Bundle b  = new Bundle();
                    String serv ="DSTV";
                    b.putString("serv",serv);
                    Fragment  fragment = new CableTV();
                    fragment.setArguments(b);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    //  String tag = Integer.toString(title);
                    fragmentTransaction.replace(R.id.container_body, fragment,serv);
                    fragmentTransaction.addToBackStack(serv);
                    fragmentTransaction.commit();
                    ((MainActivity)getActivity())
                            .setActionBarTitle(serv);

                }
                else if( v.equals("ZUKU")){


                }
                else if( v.equals("JamboJet")){


                }
                else if( v.equals("Nairobi Water")){

                }

                else if( v.equals("3GDirect")){


                }

            }
    }






    public void Move(int pos){
        if(pos >= 0 && pos < planetsList.size()) {
            cv.setVisibility(View.VISIBLE);
            NewAccountList p = planetsList.get(pos);
            accid.setText(p.getAccId());
            session.putSelAcc(p.getAccId());
            accamo.setText(p.getAmo());
            curr.setText(p.getAcctype());


            cv.setCardBackgroundColor(getActivity().getResources().getColor(R.color.white));
            accid.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
            currtxt.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
            accamo.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
            curr.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));

        }
        if(planetsList.size() == 1){
            scl.setVisibility(View.GONE);
            scr.setVisibility(View.GONE);
        }
        if(pos >0){
            scl.setVisibility(View.VISIBLE);
        }
        if(pos == 0){
            scl.setVisibility(View.GONE);
            scr.setVisibility(View.VISIBLE);
        }
        if(pos == (planetsList.size()-1)){
            scr.setVisibility(View.GONE);
        }
        if((planetsList.size() >1) && (pos <(planetsList.size()-1)) ){
            scr.setVisibility(View.VISIBLE);
        }
    }

    public class RelativeLayoutTouchListener implements View.OnTouchListener {

        static final String logTag = "ActivitySwipeDetector";
       // private Activity activity;
        static final int MIN_DISTANCE = 100;// TODO change this runtime based on screen resolution. for 1920x1080 is to small the 100 distance
        private float downX, downY, upX, upY;

        // private MainActivity mMainActivity;

        public RelativeLayoutTouchListener() {
            //activity = mainActivity;
        }

        public void onRightToLeftSwipe() {
            Log.i(logTag, "RightToLeftSwipe!");
            Toast.makeText(getActivity(), "RightToLeftSwipe", Toast.LENGTH_SHORT).show();
            // activity.doSomething();
        }

        public void onLeftToRightSwipe() {
            Log.i(logTag, "LeftToRightSwipe!");
            Toast.makeText(getActivity(), "LeftToRightSwipe", Toast.LENGTH_SHORT).show();
            // activity.doSomething();
        }

        public void onTopToBottomSwipe() {
            Log.i(logTag, "onTopToBottomSwipe!");
            Toast.makeText(getActivity(), "onTopToBottomSwipe", Toast.LENGTH_SHORT).show();
            // activity.doSomething();
        }

        public void onBottomToTopSwipe() {
            Log.i(logTag, "onBottomToTopSwipe!");
            Toast.makeText(getActivity(), "onBottomToTopSwipe", Toast.LENGTH_SHORT).show();
            // activity.doSomething();
        }

        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    downX = event.getX();
                    downY = event.getY();
                    return true;
                }
                case MotionEvent.ACTION_UP: {
                    upX = event.getX();
                    upY = event.getY();

                    float deltaX = downX - upX;
                    float deltaY = downY - upY;

                    // swipe horizontal?
                    if (Math.abs(deltaX) > MIN_DISTANCE) {
                        // left or right
                        if (deltaX < 0) {
                            this.onLeftToRightSwipe();
                            return true;
                        }
                        if (deltaX > 0) {
                            this.onRightToLeftSwipe();
                            return true;
                        }
                    } else {
                        Log.i(logTag, "Swipe was only " + Math.abs(deltaX) + " long horizontally, need at least " + MIN_DISTANCE);
                        // return false; // We don't consume the event
                    }

                    // swipe vertical?
                    if (Math.abs(deltaY) > MIN_DISTANCE) {
                        // top or down
                        if (deltaY < 0) {
                            this.onTopToBottomSwipe();
                            return true;
                        }
                        if (deltaY > 0) {
                            this.onBottomToTopSwipe();
                            return true;
                        }
                    } else {
                        Log.i(logTag, "Swipe was only " + Math.abs(deltaX) + " long vertically, need at least " + MIN_DISTANCE);
                        // return false; // We don't consume the event
                    }

                    return false; // no swipe horizontally and no swipe vertically
                }// case MotionEvent.ACTION_UP:
            }
            return false;
        }

    }
}
