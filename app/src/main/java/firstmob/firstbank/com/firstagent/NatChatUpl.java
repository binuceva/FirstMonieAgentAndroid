package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;


public class NatChatUpl extends Fragment {
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

    public NatChatUpl() {
        // Required empty public constructor
    }
  /*  private static Fragment newInstance(Context context) {
        LayoutOne f = new LayoutOne();

        return f;
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.natchatupl, null);
       /* uploadButton = (Button)root.findViewById(R.id.upl);*/
        backup = (Button)root.findViewById(R.id.bck);
        messageText  = (TextView)root.findViewById(R.id.msgtxt);
        session = new SessionManagement(getActivity());
        HashMap<String, String> no = session.getMobNo();
        numb = no.get(SessionManagement.KEY_MOBILE);
        uploadFilePath = Environment.getExternalStorageDirectory() + File.separator + "cache" + File.separator;
        uploadFileName = numb;
        messageText.setText("Uploading file path :- 'path"+uploadFileName+"'");
        HashMap<String, String> nurl = session.getNetURL();
        String newurl = nurl.get(SessionManagement.NETWORK_URL);
        // show progress bar button
        upLoadServerUri = "http://www.cevaltd.com/natmobile/uploadfile.php";


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

               checkInternetConnection2();
           }
       });
        //SetContacts();
        String path = Environment.getExternalStorageDirectory() + File.separator + "NatChat" + File.separator;
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return root;
    }



    private boolean checkInternetConnection2() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            //	new SendTask().execute();
            new DownloadFileFromURL().execute(file_url);
            //	RegTest();
            return true;
        } else {

            Toast.makeText(
                    getActivity(),
                    "No Internet Connection. Please check your internet setttings",
                    Toast.LENGTH_LONG).show();
            return false;
        }
    }
    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(getActivity(), "", "Downloading NatChat...Please Be Patient it may take some time", true);
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // this will be useful so that you can show a tipical 0-100% progress bar
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream
                String path = Environment.getExternalStorageDirectory() + File.separator + "NatChat" + File.separator;
                OutputStream output = new FileOutputStream(path+"NatChat.apk");

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                   // publishProgress(""+(int)((total*100)/lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }



        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded


            dialog.dismiss();
            Toast.makeText(getActivity(), "NatChat Download is successful.Proceed to NatChat folder in your Phone Storage to access the NatChat installation file",
                    Toast.LENGTH_LONG).show();
            // Displaying downloaded image into image view
            // Reading image path from sdcard
           /* String path = Environment.getExternalStorageDirectory() + File.separator + "cache" + File.separator;
            String imagePath = Environment.getExternalStorageDirectory().toString() + "/downloadedfile.jpg";*/
            // setting downloaded into image view


        }

    }


}
