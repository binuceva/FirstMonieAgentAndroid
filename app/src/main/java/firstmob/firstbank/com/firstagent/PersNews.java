package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.Dashboard;
import adapter.DashboardAdapter;


public class PersNews extends Fragment implements View.OnClickListener {


    GridView gridView;
    List<Dashboard> planetsList = new ArrayList<Dashboard>();
    DashboardAdapter aAdpt;
    ProgressDialog prgDialog;
    CheckBox chk1,chk2,chk3,chk4,chk5,chk6,chk7,chk8,chk9,chk10,chk11,chk12,chk13,chk14,chk15,chk16;
int checkserv = 0;
    Button ok;
    SessionManagement session;


    public PersNews() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.persnews, container, false);
       chk1 = (CheckBox) rootView.findViewById(R.id.ftran);
        chk2 = (CheckBox) rootView.findViewById(R.id.mbmon);
        chk5 = (CheckBox) rootView.findViewById(R.id.minstat);

        chk3 = (CheckBox) rootView.findViewById(R.id.atopup);
        chk4 = (CheckBox) rootView.findViewById(R.id.fstat);





        chk7 = (CheckBox) rootView.findViewById(R.id.lstyle);
        chk8 = (CheckBox) rootView.findViewById(R.id.tech);


        chk9 = (CheckBox) rootView.findViewById(R.id.agrib);
        chk10 = (CheckBox) rootView.findViewById(R.id.stckmkt);

        chk11 = (CheckBox) rootView.findViewById(R.id.namer);
        chk12 = (CheckBox) rootView.findViewById(R.id.eup);

        chk13 = (CheckBox) rootView.findViewById(R.id.foot);
        chk14 = (CheckBox) rootView.findViewById(R.id.ath);

        chk15 = (CheckBox) rootView.findViewById(R.id.hlth);
        chk16 = (CheckBox) rootView.findViewById(R.id.edu);
        session = new SessionManagement(getActivity());
        ok = (Button) rootView.findViewById(R.id.button1);
        ok.setOnClickListener(this);
        CheckServicesBool();
        if(checkserv == 0){
            chk1.setChecked(true);
            chk2.setChecked(true);
            chk3.setChecked(true);
            chk4.setChecked(true);
            chk5.setChecked(true);
            chk7.setChecked(true);
            chk8.setChecked(true);
        }
        return rootView;
    }



    public void StartChartAct(int i){


    }
    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }
    @Override
    public void onClick(View view) {
if(view.getId() == R.id.button1){
    if(chk1.isChecked() == true){
    session.SetPersNews("BSN");
    }else{
        session.SetPersNewsFalse("BSN");


    }
    if(chk2.isChecked() == true){
        session.SetPersNews("WLD");
    }else{
        session.SetPersNewsFalse("WLD");


    }
    if(chk3.isChecked() == true){
        session.SetPersNews("LCL");
    }else{
        session.SetPersNewsFalse("LCL");


    }
    if(chk4.isChecked() == true){
        session.SetPersNews("PLT");
    }else{
        session.SetPersNewsFalse("PLT");


    }
    if(chk5.isChecked() == true){
        session.SetPersNews("SPT");
    }else{
        session.SetPersNewsFalse("SPT");


    }




    if(chk7.isChecked() == true){
        session.SetPersNews("LST");
    }else{
        session.SetPersNewsFalse("LST");
 }

    if(chk8.isChecked() == true){
        session.SetPersNews("TCH");
    }else{
        session.SetPersNewsFalse("TCH");


    }

    if(chk9.isChecked() == true){
        session.SetPersNews("AGB");
    }else{
        session.SetPersNewsFalse("AGB");
    }

    if(chk10.isChecked() == true){
        session.SetPersNews("STC");
    }else{
        session.SetPersNewsFalse("STC");
 }

    if(chk11.isChecked() == true){
        session.SetPersNews("NAM");
    }else{
        session.SetPersNewsFalse("NAM");
    }

    if(chk12.isChecked() == true){
        session.SetPersNews("EUP");
    }else{
        session.SetPersNewsFalse("EUP");
    }

    if(chk13.isChecked() == true){
        session.SetPersNews("FOT");
    }else{
        session.SetPersNewsFalse("FOT");
    }

    if(chk14.isChecked() == true){
        session.SetPersNews("ATH");
    }else{
        session.SetPersNewsFalse("ATH");
    }

    if(chk15.isChecked() == true){
        session.SetPersNews("HTH");
    }else{
        session.SetPersNewsFalse("HTH");
    }

    if(chk16.isChecked() == true){
        session.SetPersNews("EDU");
    }else{
        session.SetPersNewsFalse("EDU");
    }
    Toast.makeText(getActivity(), "Settings Applied Successfully ", Toast.LENGTH_LONG).show();
 // SetHome();

    getActivity().finish();
    Intent i = new Intent(getActivity(), MainActivity.class);

    // Staring Login Activity
    startActivity(i);

 /*   getActivity().finish();
    startActivity(new Intent(getActivity(), MainActivity.class));*/
}
        if(view.getId() == R.id.tdispedit){
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
    }


    public void CheckServicesBool(){
        boolean chkftra = session.checkPersNews("BSN");
        boolean chkmbm = session.checkPersNews("WLD");
        boolean chkmstat = session.checkPersNews("LCL");

        boolean atopup = session.checkPersNews("PLT");
        boolean fst = session.checkPersNews("SPT");
        boolean lst = session.checkPersNews("LST");
        boolean tch = session.checkPersNews("TCH");
        boolean chagb = session.checkPersNews("AGB");
        boolean chst = session.checkPersNews("STC");
        boolean cheup = session.checkPersNews("EUP");
        boolean chnam = session.checkPersNews("NAM");
        boolean chfot = session.checkPersNews("FOT");
        boolean chath = session.checkPersNews("ATH");
        boolean chhth = session.checkPersNews("HTH");
        boolean chedu = session.checkPersNews("EDU");
        if(chkftra == true){
            chk1.setChecked(true);
            checkserv++;
        }else{
            chk1.setChecked(false);

        }
        if(chkmbm == true){
            chk2.setChecked(true);
            checkserv++;
        }else{
            chk2.setChecked(false);

        }
        if(chkmstat == true){
            chk3.setChecked(true);
            checkserv++;
        }else{
            chk3.setChecked(false);

        }

        if(atopup == true){
            chk4.setChecked(true);
            checkserv++;
        }else{
            chk4.setChecked(false);

        }
        if(fst == true){
            chk5.setChecked(true);
            checkserv++;
        }else{
            chk5.setChecked(false);

        }
        if(lst == true){
            chk7.setChecked(true);
            checkserv++;
        }else{
            chk7.setChecked(false);

        }
        if(tch == true){
            chk8.setChecked(true);
            checkserv++;
        }else{
            chk8.setChecked(false);

        }
        if(chagb == true){
            chk9.setChecked(true);
            checkserv++;
        }else{
            chk9.setChecked(false);

        }
        if(chst == true){
            chk10.setChecked(true);
            checkserv++;
        }else{
            chk10.setChecked(false);

        }
        if(cheup == true){
            chk12.setChecked(true);
            checkserv++;
        }else{
            chk12.setChecked(false);

        }
        if(chnam == true){
            chk11.setChecked(true);
            checkserv++;
        }else{
            chk11.setChecked(false);

        }
        if(chfot == true){
            chk13.setChecked(true);
            checkserv++;
        }else{
            chk13.setChecked(false);

        }
        if(chath == true){
            chk14.setChecked(true);
            checkserv++;
        }else{
            chk14.setChecked(false);

        }


        if(chhth == true){
            chk15.setChecked(true);
            checkserv++;
        }else{
            chk15.setChecked(false);

        }
        if(chedu == true){
            chk16.setChecked(true);
            checkserv++;
        }else{
            chk16.setChecked(false);

        }
    }

    public void SetHome(){
        Fragment fragment = new MyAccountFrag();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //  String tag = Integer.toString(title);
        fragmentTransaction.replace(R.id.container_body, fragment, "Welcome");
        fragmentTransaction.addToBackStack("Welcome");
        fragmentTransaction.commit();
        ((MainActivity)getActivity())
                .setActionBarTitle("Welcome");
    }
}
