package firstmob.firstbank.com.firstagent;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class Discounts extends Fragment implements View.OnClickListener {
ImageView imageView1;
    RelativeLayout rl;
    TextView txphn;

    public Discounts() {
        // Required empty public constructor
    }
  /*  private static Fragment newInstance(Context context) {
        LayoutOne f = new LayoutOne();

        return f;
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.layout_discounts, null);
        rl = (RelativeLayout) root.findViewById(R.id.phn);
        txphn = (TextView) root.findViewById(R.id.tvvb);
        Typeface font1 = Typeface.createFromAsset(getActivity().getAssets(), "musleo.ttf");
        txphn.setTypeface(font1);
        rl.setOnClickListener(this);
        return root;
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
       /* if (v.getId() == R.id.phn) {
            Fragment  fragment = new PhoneBackUp();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //  String tag = Integer.toString(title);
            fragmentTransaction.replace(R.id.container_body, fragment,"Phone BackUp");
            fragmentTransaction.addToBackStack("Phone BackUp");
            ((MainActivity)getActivity())
                    .setActionBarTitle("Phone BackUp");
            fragmentTransaction.commit();
        }*/
    }
}
