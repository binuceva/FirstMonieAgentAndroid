package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FinalConfAirtime extends Fragment  implements View.OnClickListener{
    TextView reccustid,recamo,rectelco,recfee,rectrref,recagcmn;
    Button btnsub;
    String txtcustid, amou ,narra, ednamee,ednumbb,serviceid,billid,strfee,strtref,stragcms;
    ProgressDialog prgDialog,prgDialog2;
    String telcoop;
    EditText amon, edacc,pno,txtamount,txtnarr,edname,ednumber;
    public FinalConfAirtime() {
        // Required empty public constructor
    }
  /*  private static Fragment newInstance(Context context) {
        LayoutOne f = new LayoutOne();

        return f;
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.finalconfairtime, null);
        reccustid = (TextView) root.findViewById(R.id.textViewnb2);


        recamo = (TextView) root.findViewById(R.id.textViewrrs);
        rectelco = (TextView) root.findViewById(R.id.textViewrr);

        recagcmn = (TextView) root.findViewById(R.id.txtaccom);
        recfee = (TextView) root.findViewById(R.id.txtfee);
        rectrref = (TextView) root.findViewById(R.id.txref);
        prgDialog2 = new ProgressDialog(getActivity());
        prgDialog2.setMessage("Loading....");
        prgDialog2.setCancelable(false);



        btnsub = (Button) root.findViewById(R.id.button2);
        btnsub.setOnClickListener(this);

        Bundle bundle = this.getArguments();
        if (bundle != null) {

            txtcustid = bundle.getString("mobno");
            amou = bundle.getString("amou");
            telcoop = bundle.getString("telcoop");

 String txtamou = Utility.returnNumberFormat(amou);
            if(txtamou.equals("0.00")){
                txtamou = amou;
            }
            billid = bundle.getString("billid");
            serviceid = bundle.getString("serviceid");
            strfee = bundle.getString("fee");
            strtref = bundle.getString("tref");
            stragcms = Utility.returnNumberFormat(bundle.getString("agcmsn"));
            reccustid.setText(txtcustid);
            recfee.setText(ApplicationConstants.KEY_NAIRA+strfee);
            rectrref.setText(strtref);

            recamo.setText(ApplicationConstants.KEY_NAIRA+txtamou);
            rectelco.setText(telcoop);
            recagcmn.setText(ApplicationConstants.KEY_NAIRA+stragcms);

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
