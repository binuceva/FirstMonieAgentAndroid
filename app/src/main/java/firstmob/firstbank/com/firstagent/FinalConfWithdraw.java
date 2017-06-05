package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FinalConfWithdraw extends Fragment  implements View.OnClickListener{
    TextView recacno,recname,recamo,recnarr,recsendnum,recsendnam,recfee,rectrref,recagcmn;
    Button btnsub;
    String recanno, amou ,narra, ednamee,ednumbb,txtname,strfee,txref,otp,stragcms ;
    ProgressDialog prgDialog,prgDialog2;

    public FinalConfWithdraw() {
        // Required empty public constructor
    }
  /*  private static Fragment newInstance(Context context) {
        LayoutOne f = new LayoutOne();

        return f;
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.finalconfwithdraw, null);
        recacno = (TextView) root.findViewById(R.id.textViewnb2);
        recname = (TextView) root.findViewById(R.id.textViewcvv);

        recamo = (TextView) root.findViewById(R.id.textViewrrs);
        recnarr = (TextView) root.findViewById(R.id.textViewrr);
        rectrref = (TextView) root.findViewById(R.id.tranref);
        recfee = (TextView) root.findViewById(R.id.txtfee);
        recsendnam = (TextView) root.findViewById(R.id.sendnammm);
        recsendnum = (TextView) root.findViewById(R.id.sendno);
        prgDialog2 = new ProgressDialog(getActivity());
        prgDialog2.setMessage("Loading....");
        prgDialog2.setCancelable(false);

        btnsub = (Button) root.findViewById(R.id.button2);
        btnsub.setOnClickListener(this);
        recagcmn = (TextView) root.findViewById(R.id.txtaccom);


        Bundle bundle = this.getArguments();
        if (bundle != null) {

            recanno = bundle.getString("recanno");
            amou = bundle.getString("amou");
            strfee = bundle.getString("fee");
            txtname = bundle.getString("txtname");
            txref = bundle.getString("txref");
            stragcms = Utility.returnNumberFormat(bundle.getString("agcmsn"));
            otp = bundle.getString("otp");
            recacno.setText(recanno);
            recname.setText(txtname);
            recfee.setText(ApplicationConstants.KEY_NAIRA+strfee);
            rectrref.setText(txref);
            recamo.setText(ApplicationConstants.KEY_NAIRA+amou);
            recagcmn.setText(stragcms);

        }
        return root;
    }



    public void StartChartAct(int i){


    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.button2) {
           getActivity().finish();
            startActivity(new Intent(getActivity(), FMobActivity.class));
        }
    }


    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }

}
