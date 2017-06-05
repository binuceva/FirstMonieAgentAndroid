package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapter.ViewPagerAdapter;
import tablayout.SlidingTabLayout;

public class News extends ActionBarActivity {
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
    SlidingTabLayout tabs;
    //CharSequence Titles[]={"Business","World","Local","Politics","Sports"};
    int Numboftabs =5;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newsact);


        session = new SessionManagement(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        int numtab = setBoo();
        if(numtab == 0){
            numtab = 5;
            setNullBoo();
        }
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,numtab,planetsList2);
        pager.setAdapter(adapter);

	}



    public int setBoo(){
        boolean chkftra = session.checkPersNews("BSN");
        boolean chkmbm = session.checkPersNews("WLD");
        boolean chkmstat = session.checkPersNews("LCL");

        boolean atopup = session.checkPersNews("PLT");
        boolean fst = session.checkPersNews("SPT");
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

    }
}
