package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapter.ViewPagerAdapter;


public class NewsFrag extends Fragment implements View.OnClickListener{
    String kpin = null;
    String finalamo = null;
    ProgressDialog prgDialog;
    TextView tv,tvamo,tvpdate,tvrenewdate,tvnatid,tvmobno,tvpena,tvpdue,tvtam,notdue,sbpid,starrat,occ;
    Button cpay,okbut;
    private ListView listView;
    ArrayList<Integer> fval = new ArrayList<>() ;
    private Toolbar mToolbar;
    List<String> planetsList2 = new ArrayList<String>();
    List<String> Titles = new ArrayList<String>();
    SessionManagement session;
    ViewPager pager;
    ViewPagerAdapter adapter;
  //  SlidingTabLayout tabs;
    //CharSequence Titles[]={"Business","World","Local","Politics","Sports"};
    int Numboftabs =7;

    public NewsFrag() {
        // Required empty public constructor
    }
  /*  private static Fragment newInstance(Context context) {
        LayoutOne f = new LayoutOne();

        return f;
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.newsmain, null);
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.

        session = new SessionManagement(getActivity());
        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) root.findViewById(R.id.pager);
        int numtab = setBoo();
        if(numtab == 0){
            numtab = 7;
            setNullBoo();
        }
        adapter =  new ViewPagerAdapter(getFragmentManager(),Titles,numtab,planetsList2);
        pager.setAdapter(adapter);


        return root;
    }



    public void StartChartAct(int i){


    }
    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }



    public int setBoo(){
        boolean chkftra = session.checkPersNews("BSN");
        boolean chkmbm = session.checkPersNews("WLD");
        boolean chkmstat = session.checkPersNews("LCL");

        boolean atopup = session.checkPersNews("PLT");
        boolean fst = session.checkPersNews("SPT");

        boolean chkmpesa = session.checkPersNews("MPS");

        boolean chklst = session.checkPersNews("LST");
        boolean chktch = session.checkPersNews("TCH");

        boolean chagb = session.checkPersNews("AGB");
        boolean chst = session.checkPersNews("STC");

        boolean cheup = session.checkPersNews("EUP");
        boolean chnam = session.checkPersNews("NAM");

        boolean chfot = session.checkPersNews("FOT");
        boolean chath = session.checkPersNews("ATH");

        boolean chhth = session.checkPersNews("HTH");
        boolean chedu = session.checkPersNews("EDU");
int yu = 0;
        if(chkftra == true){
          yu++;
            planetsList2.add("BSN");
            Titles.add("Business");
        }else{

        }
        if(chkmbm == true){
        yu++;
            planetsList2.add("WLD");
            Titles.add("World");
        }else{

        }
        if(chkmstat == true){
            yu++;
            planetsList2.add("LCL");
            Titles.add("Local");
        }else{

        }

        if(atopup == true){
            yu++;
            planetsList2.add("PLT");
            Titles.add("Politics");
        }else{

        }
        if(fst == true){
            yu++;
            planetsList2.add("SPT");
            Titles.add("Sports");
        }else{

        }




        if(chklst == true){
            yu++;
            planetsList2.add("LST");
            Titles.add("Lifestyle");
        }else{

        }
        if(chktch == true){
            yu++;
            planetsList2.add("TCH");
            Titles.add("Technology");
        }else{

        }
        if(chagb == true){
            yu++;
            planetsList2.add("AGB");
            Titles.add("AgriBusiness");
        }else{

        }
        if(chst == true){
            yu++;
            planetsList2.add("STC");
            Titles.add("Stock Markets");
        }else{

        }


        if(cheup == true){
            yu++;
            planetsList2.add("EUP");
            Titles.add("Europe");
        }else{

        }
        if(chnam == true){
            yu++;
            planetsList2.add("NAM");
            Titles.add("North America");
        }else{

        }


        if(chfot == true){
            yu++;
            planetsList2.add("FOT");
            Titles.add("Football");
        }else{

        }
        if(chath == true){
            yu++;
            planetsList2.add("ATH");
            Titles.add("Athletics");
        }else{

        }


        if(chhth == true){
            yu++;
            planetsList2.add("HTH");
            Titles.add("Health");
        }else{

        }
        if(chedu == true){
            yu++;
            planetsList2.add("EDU");
            Titles.add("Education");
        }else{

        }
        return yu;
    }


    public void setNullBoo(){

            planetsList2.add("BSN");
            Titles.add("Business");

            planetsList2.add("WLD");
            Titles.add("World");

            planetsList2.add("LCL");
            Titles.add("Local");

            planetsList2.add("PLT");
            Titles.add("Politics");

            planetsList2.add("SPT");
            Titles.add("Sports");

        planetsList2.add("LST");
        Titles.add("Lifestyle");

        planetsList2.add("TCH");
        Titles.add("Technology");

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.action_button){
            Fragment  fragment = new PersNews();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //  String tag = Integer.toString(title);
            fragmentTransaction.replace(R.id.container_body, fragment,"Personalise News");
            fragmentTransaction.addToBackStack("Personalise News");
            fragmentTransaction.commit();
            ((MainActivity)getActivity())
                    .setActionBarTitle("Personalise News");
        }
    }
}
