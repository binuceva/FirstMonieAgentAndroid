package firstmob.firstbank.com.firstagent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


public class CashIn extends Fragment implements View.OnClickListener {
ImageView imageView1;
    Button btnas;

    public CashIn() {
        // Required empty public constructor
    }
  /*  private static Fragment newInstance(Context context) {
        LayoutOne f = new LayoutOne();

        return f;
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.cashin, null);
        btnas = (Button) root.findViewById(R.id.button12);
        btnas.setOnClickListener(this);


        return root;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button12) {

         //   startActivity(new Intent(getActivity(), ScanQR.class));

        }
    }



}
