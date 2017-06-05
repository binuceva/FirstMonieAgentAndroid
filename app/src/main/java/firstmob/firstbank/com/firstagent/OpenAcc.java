package firstmob.firstbank.com.firstagent;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;


public class OpenAcc extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {


    Button sigin;
    TextView gendisp;
    SessionManagement session;
    EditText idno,mobno,fnam,lnam,yob;
    List<String> planetsList = new ArrayList<String>();
    List<String> prodid = new ArrayList<String>();
    ArrayAdapter<String> mArrayAdapter;
    Spinner sp1,sp2,sp5,sp3,sp4;
    Button btn4;
    static Hashtable<String, String> data1;
    String paramdata = "";
    ProgressDialog prgDialog,prgDialog2,prgDialog7;
    TextView tnc;
    List<String> mobopname  = new ArrayList<String>();
    List<String> mobopid  = new ArrayList<String>();
    DatePickerDialog datePickerDialog;
    TextView tvdate;
    public static final String DATEPICKER_TAG = "datepicker";

    public OpenAcc() {
        // Required empty public constructor
    }
  /*  private static Fragment newInstance(Context context) {
        LayoutOne f = new LayoutOne();

        return f;
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.opennat625, null);
        sigin = (Button) root.findViewById(R.id.button1);
        sigin.setOnClickListener(this);
        btn4 = (Button) root.findViewById(R.id.button4);
        btn4.setOnClickListener(this);
        prgDialog = new ProgressDialog(getActivity());
        prgDialog.setMessage("Opening Account....");
        tvdate = (TextView) root.findViewById(R.id.bnameh);
        // Set Cancelable as False

        prgDialog.setCancelable(false);
        session = new SessionManagement(getActivity());



        gendisp = (TextView) root.findViewById(R.id.tdispedit);
        gendisp.setOnClickListener(this);

        sp2 = (Spinner) root.findViewById(R.id.spin2);
        idno = (EditText) root.findViewById(R.id.user_id);
        mobno = (EditText) root.findViewById(R.id.user_id45);
        fnam = (EditText) root.findViewById(R.id.user_id2);
        lnam = (EditText) root.findViewById(R.id.user_id29);
     //   yob = (EditText) root.findViewById(R.id.user_id7);
        sigin.setOnClickListener(this);
        sp5 = (Spinner) root.findViewById(R.id.spin5);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp5.setAdapter(adapter);

        prgDialog2 = new ProgressDialog(getActivity());
        prgDialog2.setMessage("Loading Account Types....");
        // Set Cancelable as False

        prgDialog7 = new ProgressDialog(getActivity());
        prgDialog7.setMessage("Loading....");
        // Set Cancelable as False

        prgDialog7.setCancelable(false);

        prgDialog2.setCancelable(false);

        sp1 = (Spinner)root.findViewById(R.id.spin2);
        sp3 = (Spinner)root.findViewById(R.id.spin3);
        sp4 = (Spinner)root.findViewById(R.id.spin4);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
               getActivity(), R.array.netop, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter2);


        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
                getActivity(), R.array.states, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp3.setAdapter(adapter3);



        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(
                getActivity(), R.array.lga, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp4.setAdapter(adapter4);
        final Calendar calendar = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);

        // checkInternetConnection2();

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
    public void onClick(View view) {
        if (view.getId() == R.id.button1) {
         /*   new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Open Account")
                    .setContentText("Are you sure you want to proceed with Open Account? \n \n" +
                            " First Name:  Test \n   Last Name: Customer \n Identification Number: 01010101 \n Network Operator: Airtel \n State: Lagos \n LGA: Eti-Osa \n Gender: Male \n DOB: 01/05/1980 \n Mobile Number: 0818888888   ")
                    .setConfirmText("Confirm")
                    .setCancelText("Cancel")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();*/
            Fragment  fragment = new OpenAccStepTwo();


            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //  String tag = Integer.toString(title);
            fragmentTransaction.replace(R.id.container_body, fragment,"Step Two");
            fragmentTransaction.addToBackStack("Step Two");
            ((FMobActivity)getActivity())
                    .setActionBarTitle("Step Two");
            fragmentTransaction.commit();
        }




        if(view.getId()==  R.id.button4){
            datePickerDialog.show(getFragmentManager(), DATEPICKER_TAG);
        }
        if(view.getId() == R.id.tdispedit){

          /*  Fragment fragment =  new NatWebProd();;
String title = "Bank Info";
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //  String tag = Integer.toString(title);
            fragmentTransaction.replace(R.id.container_body, fragment, title);
            fragmentTransaction.addToBackStack(title);
            fragmentTransaction.commit();
            Activity activity123 = getActivity();
            if(activity123 instanceof MainActivity) {
                ((MainActivity)getActivity())
                        .setActionBarTitle(title);
            }
            if(activity123 instanceof SignInActivity) {
                ((SignInActivity) getActivity())
                        .setActionBarTitle(title);
            }*/
        }

        if(view.getId() == R.id.textView3){

            Fragment fragment =  new NatWebProd();;
            String title = "Bank Info";
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //  String tag = Integer.toString(title);
            fragmentTransaction.replace(R.id.container_body, fragment, title);
            fragmentTransaction.addToBackStack(title);
            fragmentTransaction.commit();
            Activity activity123 = getActivity();
            if(activity123 instanceof MainActivity) {
                ((MainActivity)getActivity())
                        .setActionBarTitle(title);
            }
            if(activity123 instanceof SignInActivity) {

            }
        }
    }


    public void ClearOpenAcc(){
    //   sp1.setSelection(0);

        mobno.setText(" ");
        idno.setText(" ");
        fnam.setText(" ");
        lnam.setText(" ");
     //   yob.setText(" ");
    }


    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        String finalmon = null;

        int nwm = month + 1;
        finalmon = Integer.toString(nwm);
        if (nwm < 10) {
            finalmon = "0" + nwm;
        }

        String tdate = day+"/"+finalmon + "/" +year ;


        tvdate.setText(tdate);
    }



    public void SetDialog(String msg,String title){
        new MaterialDialog.Builder(getActivity())
                .title(title)
                .content(msg)

                .negativeText("Close")
                .show();
    }

    public String setMobFormat(String mobno){
        String vb = mobno.substring(Math.max(0, mobno.length() - 9));
        Log.v("Logged Number is", vb);
        if(vb.length() == 9 && (vb.substring(0, Math.min(mobno.length(), 1)).equals("7"))){
            return "254"+vb;
        }else{
            return  "N";
        }
    }


}
