package firstmob.firstbank.com.firstagent;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;

import java.util.Calendar;


public class RequestbankVisit extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    ImageView imageView1;
    DatePickerDialog datePickerDialog;
    TextView tvdate;
    Button btn4;
    public static final String DATEPICKER_TAG = "datepicker";
    public RequestbankVisit() {
        // Required empty public constructor
    }
  /*  private static Fragment newInstance(Context context) {
        LayoutOne f = new LayoutOne();

        return f;
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.reqbnkvisit, null);
        btn4 = (Button) root.findViewById(R.id.button4);
        tvdate = (TextView) root.findViewById(R.id.bnameh);
        btn4.setOnClickListener(this);
        final Calendar calendar = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);


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
        if(v.getId()==  R.id.button4){
            datePickerDialog.show(getFragmentManager(), DATEPICKER_TAG);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        Calendar now = Calendar.getInstance();

        cal.set(year, month, day);
        if (cal.after(now)) {
            String finalmon = null;

            int nwm = month + 1;
            finalmon = Integer.toString(nwm);
            if (nwm < 10) {
                finalmon = "0" + nwm;
            }

            String tdate = day + "/" + finalmon + "/" + year;


            tvdate.setText(tdate);
        }else{
            Toast.makeText(
                    getActivity(),
                    "Please set a date for  after today ",
                    Toast.LENGTH_LONG).show();
        }
    }
}
