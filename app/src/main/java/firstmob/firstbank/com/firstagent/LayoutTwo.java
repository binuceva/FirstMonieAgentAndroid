package firstmob.firstbank.com.firstagent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapter.ProdAdapter;
import adapter.ProdList;


public class LayoutTwo extends Fragment implements View.OnClickListener {
    ImageView imageView1;
    ListView lv;
    TextView tv;
    List<ProdList> planetsList = new ArrayList<ProdList>();
    ProdAdapter aAdpt;
    public LayoutTwo() {
        // Required empty public constructor
    }
  /*  private static Fragment newInstance(Context context) {
        LayoutOne f = new LayoutOne();

        return f;
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.productsfrag, null);

        lv = (ListView) root.findViewById(R.id.lv);
        tv = (TextView) root.findViewById(R.id.txt);
        tv.setOnClickListener(this);

        SetPop();


        return root;
    }


    public void SetPop(){
        planetsList.clear();
        planetsList.add(new ProdList("Clark Shoes","13000 naira",R.drawable.clarks));
        planetsList.add(new ProdList("Loafers","5000 Naira", R.drawable.loafers));






       /* planetsList.add(new Dashboard("My Profile",R.drawable.icons40));*/

        aAdpt = new ProdAdapter( planetsList,getActivity());
        lv.setAdapter(aAdpt);
    }
    public void StartChartAct(int i){


    }
    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.txt){
         startActivity(new Intent(getActivity(),AddProdActivity.class));
        }
    }
}
