package firstmob.firstbank.com.firstagent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class DomesticBank extends Fragment {

Spinner sp1,sp2;
    public DomesticBank() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.domesticbank, container, false);

        sp1 = (Spinner)rootView.findViewById(R.id.spin1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.dombank, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter);

        sp2 = (Spinner)rootView.findViewById(R.id.spin2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
               getActivity(), R.array.branch, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(adapter2);
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
