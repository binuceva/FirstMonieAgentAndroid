package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import adapter.FHomeAdapter;
import adapter.TxnAdapter;
import adapter.TxnList;
import tablayout.SlidingTabLayout;


public class FirstHomeFrag extends Fragment {

    ProgressDialog prgDialog;

    SessionManagement session;
    ViewPager pager;
    FHomeAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"HOME","ACTIVITIES","OFFERS","PRODUCTS"};
    int Numboftabs =4;
    List<TxnList> planetsList = new ArrayList<TxnList>();
    ListView lv;
    TxnAdapter aAdpt;
    public FirstHomeFrag() {
        // Required empty public constructor
    }
  /*  private static Fragment newInstance(Context context) {
        LayoutOne f = new LayoutOne();

        return f;
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.homefrag, null);
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new FHomeAdapter(getFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) root.findViewById(R.id.pager);
        pager.setAdapter(adapter);




/*
        lv = (ListView) root.findViewById(R.id.lv);
       SetPop();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Fragment  fragment = null;
                String title = null;

                if(position == 0) {
                    fragment = new FTMenu();
                    title = "Deposit";
                }
          else      if(position == 1) {
                    fragment = new WithdrawalMenu();
                    title = "Withdrawal";
                }
           else     if(position == 2) {
                    fragment = new BillerChoiceMenu();
                    title = "Billers";
                }
             else   if(position == 3) {
                    fragment = new AirtimeTransf();
                    title = "Airtime Transfer";
                }
             else   if(position == 4) {
                    fragment = new OpenAccMenu();
                    title = "Remittances";
                }

              else  if(position == 5) {
               *//*   fragment = new Minstat();
                    title = "Mini-statement";*//*
                }
             else   if(position == 6) {
                    fragment = new OpenAcc();
                    title = "Open Account";
                }
                if(fragment != null) {


                    ((MainActivity)getActivity()).changeFragment(fragment,title);

                }


            }
        });*/

        return root;
    }



    public void StartChartAct(int i){


    }

    public void SetPop(){
        planetsList.clear();
        planetsList.add(new TxnList("Open Account","",R.drawable.openacc));
        planetsList.add(new TxnList("Deposit","", R.drawable.cashdepo));
        planetsList.add(new TxnList("Withdraw","", R.drawable.withdraw));
        planetsList.add(new TxnList("Bill Payment","",R.drawable.billpay));
       // planetsList.add(new TxnList("Payments","",R.drawable.payments));
        planetsList.add(new TxnList("Airtime TopUp","",R.drawable.airtime));
        planetsList.add(new TxnList("Remittances","",R.drawable.ft));
        planetsList.add(new TxnList("Mini-Statement","",R.drawable.statement));





       /* planetsList.add(new Dashboard("My Profile",R.drawable.icons40));*/

        aAdpt = new TxnAdapter( planetsList,getActivity());
        lv.setAdapter(aAdpt);
    }
    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }

}
