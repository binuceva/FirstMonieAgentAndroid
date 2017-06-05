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
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FinalConfDepoTrans extends Fragment  implements View.OnClickListener{
    TextView recacno,recname,recamo,recnarr,recsendnum,recsendnam,recfee,recagcmn;
    Button btnsub;
    String recanno, amou ,narra, ednamee,ednumbb,txtname,strfee,stragcms ;
    ProgressDialog prgDialog,prgDialog2;
    RelativeLayout rlsendname,rlsendno;
    EditText etpin;
    public FinalConfDepoTrans() {
        // Required empty public constructor
    }
  /*  private static Fragment newInstance(Context context) {
        LayoutOne f = new LayoutOne();

        return f;
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.finalconfirmcashdepo, null);
        recacno = (TextView) root.findViewById(R.id.textViewnb2);
        recname = (TextView) root.findViewById(R.id.textViewcvv);
        etpin = (EditText) root.findViewById(R.id.pin);
        recamo = (TextView) root.findViewById(R.id.textViewrrs);
        recnarr = (TextView) root.findViewById(R.id.textViewrr);
        recfee = (TextView) root.findViewById(R.id.txtfee);
        recagcmn = (TextView) root.findViewById(R.id.txtaccom);
        recsendnam = (TextView) root.findViewById(R.id.sendnammm);
        recsendnum = (TextView) root.findViewById(R.id.sendno);
        prgDialog2 = new ProgressDialog(getActivity());
        prgDialog2.setMessage("Loading....");
        prgDialog2.setCancelable(false);
        rlsendname = (RelativeLayout) root.findViewById(R.id.rlsendnam);
        rlsendno = (RelativeLayout) root.findViewById(R.id.rlsendnum);
        btnsub = (Button) root.findViewById(R.id.button2);
        btnsub.setOnClickListener(this);

        Bundle bundle = this.getArguments();
        if (bundle != null) {

            recanno = bundle.getString("recanno");
            amou = bundle.getString("amou");
            narra = bundle.getString("narra");
            ednamee = bundle.getString("ednamee");
            ednumbb = bundle.getString("ednumbb");
            txtname = bundle.getString("txtname");
            stragcms = Utility.returnNumberFormat(bundle.getString("agcmsn"));
            String trantype =  bundle.getString("trantype");
            strfee = bundle.getString("fee");
            if(trantype.equals("D")){
                rlsendname.setVisibility(View.GONE);
                rlsendno.setVisibility(View.GONE);
            }
            recacno.setText(recanno);
            recname.setText(txtname);
            recamo.setText(ApplicationConstants.KEY_NAIRA+amou);
            recnarr.setText(narra);
            recfee.setText(ApplicationConstants.KEY_NAIRA+strfee);
            recsendnam.setText(ednamee);
            recsendnum.setText(ednumbb);
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
