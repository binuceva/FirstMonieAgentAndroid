package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import adapter.ActivitiesAdapter;
import adapter.ActivitiesList;


public class ActivitiesFrag extends Fragment {

    ProgressDialog prgDialog;

    SessionManagement session;

    List<ActivitiesList> planetsList = new ArrayList<ActivitiesList>();
    ListView lv;
    ActivitiesAdapter aAdpt;
    public ActivitiesFrag() {
        // Required empty public constructor
    }
  /*  private static Fragment newInstance(Context context) {
        LayoutOne f = new LayoutOne();

        return f;
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activitiesfrag, null);
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.


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
                  /*  fragment = new Minstat();
                    title = "Mini-statement";*/
                }
             else   if(position == 6) {
                    fragment = new OpenAcc();
                    title = "Open Account";
                }
                if(fragment != null) {
                  /*  FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    //  String tag = Integer.toString(title);
                    fragmentTransaction.replace(R.id.container_body, fragment, title);
                    fragmentTransaction.addToBackStack(title);
                   *//* ((MainActivity)getActivity())
                            .setActionBarTitle(title);*//*
                    fragmentTransaction.commit();*/



                }


            }
        });

        return root;
    }



    public void StartChartAct(int i){


    }

    public void SetPop(){
        planetsList.clear();
        planetsList.add(new ActivitiesList("Signed in app successfully","mm",R.drawable.benef,"13th Aug 2017 10:11"));

        planetsList.add(new ActivitiesList("Successful Cash Deposit of 10,000 Naira to Account Number 201111111","mmm", R.drawable.cashdepo,"13th Aug 2016 12:43"));
        planetsList.add(new ActivitiesList("Failed Cash Withdrawal Transaction of 5,200 Naira due to daily transaction limit exceeded","ll", R.drawable.withdraw,"13th Aug 2016 11:45"));
        planetsList.add(new ActivitiesList("Signed out of App","mm",R.drawable.signout,"13th Aug 2016 10:11"));
       // planetsList.add(new TxnList("Payments","",R.drawable.payments));





       /* planetsList.add(new Dashboard("My Profile",R.drawable.icons40));*/

        aAdpt = new ActivitiesAdapter( planetsList,getActivity());
        lv.setAdapter(aAdpt);
    }
    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }

}
