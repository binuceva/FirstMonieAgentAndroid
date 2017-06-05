package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import adapter.adapter.OffersAdapter;
import adapter.adapter.OffersList;


public class OffersFrag extends Fragment implements  View.OnClickListener {


    ProgressDialog prgDialog;

    SessionManagement session;

    List<OffersList> planetsList = new ArrayList<OffersList>();
    ListView lv;
    TextView tv;
    OffersAdapter aAdpt;
    LinearLayout linearLayoutMine;
    Spinner spinner,spinner2;
    public OffersFrag() {
        // Required empty public constructor
    }
  /*  private static Fragment newInstance(Context context) {
        LayoutOne f = new LayoutOne();

        return f;
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.offersfrag, null);
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.


        lv = (ListView) root.findViewById(R.id.lv);
        tv = (TextView) root.findViewById(R.id.txt);
        tv.setOnClickListener(this);


       SetPop();


        return root;
    }



    public void StartChartAct(int i){


    }

    public void SetPop(){
        planetsList.clear();
        planetsList.add(new OffersList("30% discount on all store items valid until 1st October 2016"));




       /* planetsList.add(new Dashboard("My Profile",R.drawable.icons40));*/

        aAdpt = new OffersAdapter( planetsList,getActivity());
        lv.setAdapter(aAdpt);
    }
    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }
    public void SetDialog(String title){
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View stdView = factory.inflate(R.layout.dialogcustom, null);
        linearLayoutMine  = (LinearLayout) stdView.findViewById(R.id.lay);
       final EditText tsd  = (EditText) stdView.findViewById(R.id.input_payacc);
        spinner = (Spinner) linearLayoutMine.findViewById(R.id.spin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.products, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner2 = (Spinner) linearLayoutMine.findViewById(R.id.spnn);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                getActivity(), R.array.offtype, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if(position == 0){
tsd.setHint("Enter Flat Discount Amount");
                }
                if(position == 1){
                    tsd.setHint("Enter Percentage Discount");
                }
                if(position == 2){
                    tsd.setHint("Enter Co-ordinates of Location of Offer");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        new MaterialDialog.Builder(getActivity())
                .title(title)

                .customView(linearLayoutMine,true)
                 .positiveText("Proceed")
                .negativeText("Close")

                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        Toast.makeText(
                                getActivity(),
                                "You have successfully added an offer",
                                Toast.LENGTH_LONG).show();

                    }
                })
                .show();
    }

    @Override
    public void onClick(View v) {
if(v.getId() == R.id.txt){
    SetDialog("Add Offer");
}
    }
}
