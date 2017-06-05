package firstmob.firstbank.com.firstagent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;


public class AgentTopUp extends Fragment {
ImageView imageView1;
    SearchableSpinner sp1, sp5, sp7;
    public AgentTopUp() {
        // Required empty public constructor
    }
  /*  private static Fragment newInstance(Context context) {
        LayoutOne f = new LayoutOne();

        return f;
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.agenttopup, null);
        sp1 = (SearchableSpinner) root.findViewById(R.id.spinner);
        sp1.setTitle("Select Item");
        sp1.setPositiveButton("OK");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.myagents, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter);
        return root;
    }




}
