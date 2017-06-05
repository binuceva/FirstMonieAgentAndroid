package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class ForceUpdate extends Fragment {
    TextView messageText;
    Button uploadButton,backup;
    int serverResponseCode = 0;
    ProgressDialog dialog = null;
    SessionManagement session;
    String upLoadServerUri = null;
    private static int SPLASH_TIME_OUT = 1000;
    // button to show progress dialog
    Button btnShowProgress;
    String displayName = "XYZ";
    String mobileNumber = "123456";
    String backupcont;
    String homeNumber = "1111";
    String workNumber = "2222";
    String emailID = "email@nomail.com";
    String company = "bad";
    String jobTitle = "abcd";

    // Progress Dialog
    private ProgressDialog pDialog;
    ImageView my_image;
    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;

    // File url to download
    private static String file_url = "http://www.cevaltd.com/natmobile/NatChat.apk";
   String numb;

    String uploadFilePath = null;
    String uploadFileName = null;

    private static final String[] PROJECTION = new String[] {
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };

    public ForceUpdate() {
        // Required empty public constructor
    }
  /*  private static Fragment newInstance(Context context) {
        LayoutOne f = new LayoutOne();

        return f;
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.forceupdate, null);
       /* uploadButton = (Button)root.findViewById(R.id.upl);*/
        backup = (Button)root.findViewById(R.id.bck);



        // Image view to show image after downloading

        /**
         * Show Progress bar click event
         * */
      /*  btnShowProgress.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // starting new Async Task

            }
        });*/


       /* uploadButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                dialog = ProgressDialog.show(getActivity(), "", "Uploading...", true);

                new Thread(new Runnable()
                {
                    public void run()
                    {
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                messageText.setText("uploading started.....");
                            }
                        });

                        uploadFile(uploadFilePath + "" + uploadFileName);

                    }
                }).start();
            }
        });*/

       backup.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.clancy.ChatUpdate.profile");
               intent = new Intent(Intent.ACTION_VIEW);
               intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               intent.setData(Uri.parse("market://details?id=" + "natmobile.natbank.com.natmobile"));
               startActivity(intent);

           }
       });

        return root;
    }





}
