package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import adapter.Dashboard;
import adapter.DashboardAdapter;


public class FixedDepo extends Fragment {

    GridView gridView;
    List<Dashboard> planetsList = new ArrayList<Dashboard>();
    DashboardAdapter aAdpt;
    ProgressDialog prgDialog;


    public FixedDepo() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fixeddepo, container, false);

        return rootView;
    }



    public void StartChartAct(int i){


    }
    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }

}
