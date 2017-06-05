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
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.Dashboard;
import adapter.DashboardAdapter;


public class PersThree extends Fragment implements View.OnClickListener {


    GridView gridView;
    List<Dashboard> planetsList = new ArrayList<Dashboard>();
    DashboardAdapter aAdpt;
    ProgressDialog prgDialog;
  Spinner sp1,sp2,sp3;
    Button ok;
    SessionManagement session;


    public PersThree() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.perstopthree, container, false);
       sp1 = (Spinner) rootView.findViewById(R.id.spin1);
        sp2 = (Spinner) rootView.findViewById(R.id.sp2);
        sp3 = (Spinner) rootView.findViewById(R.id.spin3);
        session = new SessionManagement(getActivity());




        ok = (Button) rootView.findViewById(R.id.button1);
        ok.setOnClickListener(this);

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
if(view.getId() == R.id.button1) {

    String menu1 = sp1.getSelectedItem().toString();
    String menu2 = sp2.getSelectedItem().toString();
    String menu3 = sp3.getSelectedItem().toString();
    if (!((menu1.equals(menu2)) || (menu2.equals(menu3)) || (menu1.equals(menu3)))) {
        session.putTop1(menu1);
        session.putTop2(menu2);
        session.putTop3(menu3);
        Toast.makeText(getActivity(), "Settings Applied Successfully", Toast.LENGTH_LONG).show();
        getActivity().finish();
        Intent i = new Intent(getActivity(), MainActivity.class);

        // Staring Login Activity
        startActivity(i);

        //SetHome();

    }else{
        Toast.makeText(getActivity(), "Please select unique services for each of the three menu items", Toast.LENGTH_LONG).show();
    }
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
