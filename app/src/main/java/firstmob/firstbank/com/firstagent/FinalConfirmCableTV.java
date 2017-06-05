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

public class FinalConfirmCableTV extends Fragment  implements View.OnClickListener{
    TextView reccustid,recamo,recnarr,recsendnum,recsendnam,recfee,recagcmn,rectrref,txtlabel;
    Button btnsub;
    String txtcustid, amou ,narra, ednamee,ednumbb,serviceid,billid,txtfee,stragcms,strfee,strtref,strlabel;
    ProgressDialog prgDialog,prgDialog2;
    EditText amon, edacc,pno,txtamount,txtnarr,edname,ednumber;
    public FinalConfirmCableTV() {
        // Required empty public constructor
    }
    /*  private static Fragment newInstance(Context context) {
          LayoutOne f = new LayoutOne();

          return f;
      }
  */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.finalconfirmcabletv, null);
        reccustid = (TextView) root.findViewById(R.id.textViewnb2);
        txtlabel = (TextView) root.findViewById(R.id.textViewnb);

        recamo = (TextView) root.findViewById(R.id.textViewrrs);

        recnarr = (TextView) root.findViewById(R.id.textViewrr);

        recsendnam = (TextView) root.findViewById(R.id.sendnammm);
        recsendnum = (TextView) root.findViewById(R.id.sendno);

        prgDialog2 = new ProgressDialog(getActivity());
        prgDialog2.setMessage("Loading....");
        prgDialog2.setCancelable(false);


        recagcmn = (TextView) root.findViewById(R.id.txtaccom);
        recfee = (TextView) root.findViewById(R.id.txtfee);
        rectrref = (TextView) root.findViewById(R.id.txref);

        btnsub = (Button) root.findViewById(R.id.button2);
        btnsub.setOnClickListener(this);

        Bundle bundle = this.getArguments();
        if (bundle != null) {

            txtcustid = bundle.getString("custid");
            amou = bundle.getString("amou");
            narra = bundle.getString("narra");
            ednamee = bundle.getString("ednamee");
            ednumbb = bundle.getString("ednumbb");
            strlabel = bundle.getString("label");
            billid = bundle.getString("billid");
            serviceid = bundle.getString("serviceid");
            strfee = bundle.getString("fee");
            strtref = bundle.getString("tref");
            stragcms = Utility.returnNumberFormat(bundle.getString("agcmsn"));
            reccustid.setText(txtcustid);


            recamo.setText(amou);
            recnarr.setText(narra);
            recfee.setText(ApplicationConstants.KEY_NAIRA+strfee);
            rectrref.setText(strtref);
            recsendnam.setText(ednamee);
            recsendnum.setText(ednumbb);
            txtlabel.setText(strlabel);
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
