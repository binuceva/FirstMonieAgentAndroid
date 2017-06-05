package firstmob.firstbank.com.firstagent;

import android.app.ProgressDialog;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;


public class PhoneBackUp extends Fragment {
    ProgressDialog prgDialog;
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
    private static String file_url = "http://www.cevaltd.com/natmobile/uploads/bckdata";
   String numb;

    String uploadFilePath = null;
    String uploadFileName = null;

    private static final String[] PROJECTION = new String[] {
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };

    public PhoneBackUp() {
        // Required empty public constructor
    }
  /*  private static Fragment newInstance(Context context) {
        LayoutOne f = new LayoutOne();

        return f;
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.phone_backup, null);
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

        btnShowProgress = (Button) root.findViewById(R.id.retr);
        // Image view to show image after downloading

        /**
         * Show Progress bar click event
         * */
        btnShowProgress.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // starting new Async Task

                new MaterialDialog.Builder(getActivity())
                        .title("Confirm ")
                        .content("Are you sure you want to restore your backed up contacts?")
                        .positiveText("YES")
                        .negativeText("NO")

                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                checkInternetConnection2();
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {

                            }
                        })
                        .show();

            }
        });


        prgDialog = new ProgressDialog(getActivity());
        prgDialog.setMessage("Setting Profile Picture....");

        prgDialog.setCancelable(true);
       backup.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

             //  dialog = ProgressDialog.show(getActivity(), "", "Backing Up Contacts...", true);
               new MaterialDialog.Builder(getActivity())
                       .title("Confirm ")
                       .content("Are you sure you want to back up your contacts?")
                       .positiveText("YES")
                       .negativeText("NO")

                       .callback(new MaterialDialog.ButtonCallback() {
                           @Override
                           public void onPositive(MaterialDialog dialog) {
prgDialog.show();
                               new Handler().postDelayed(new Runnable() {

                                   @Override
                                   public void run() {
                                       readContactData();
                                   }
                               }, SPLASH_TIME_OUT);
                           }

                           @Override
                           public void onNegative(MaterialDialog dialog) {

                           }
                       })
                       .show();

           }
       });
        //SetContacts();
        return root;
    }


    private void  addContacts(){

        ContactHelper.insertContact(getActivity().getContentResolver(),
               "My Ian Test", "0718888888");

        ContactHelper.insertContact(getActivity().getContentResolver(),
                "Boss Test", "0719999999");
    }
    private void readContactData() {
        ContentResolver cr = getActivity().getContentResolver();
        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, null, null, null);
        if (cursor != null) {
            try {
                final int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                final int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                String name, number;
                JSONArray notebookUsers = new JSONArray();
                int i = 0;
                while (cursor.moveToNext()) {
                    name = cursor.getString(nameIndex);
i++;
                    number = cursor.getString(numberIndex);
                    number = number.replaceAll("\\s", "");
                    try {
                        JSONObject user = new JSONObject();
                        user.put("name", name);
                        user.put("number", number);
                        notebookUsers.put(user);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }


                }
                Log.v("JSON Array String",notebookUsers.toString());
                Log.v("Contacts Count",Integer.toString(i));
                backupcont = Integer.toString(i);
                String path = Environment.getExternalStorageDirectory() + File.separator + "cache" + File.separator;
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                path += numb;
                File imgFile = new  File(path);



                    try {
                        objectToFile(notebookUsers.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


            } finally {
                cursor.close();
            }
            prgDialog.dismiss();
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
    }

    public void StartChartAct(int i){


    }
    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }

    public int uploadFile(String sourceFileUri)
    {


        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile())
        {

            dialog.dismiss();

            Log.e("uploadFile", "Source File not exist :"
                    +uploadFilePath + "" + uploadFileName);

            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(), "Backup did not complete successfully.Please try again.",
                            Toast.LENGTH_SHORT).show();
                }
            });

            return 0;

        }
        else
        {
            try
            {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                Log.v("Server URI",upLoadServerUri);
                URL url = new URL(upLoadServerUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + fileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0)
                {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200)
                {

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {

                            String msg = "File Upload Completed.\n\n See uploaded file here : \n\n" + " serverpath"
                                    + uploadFileName;

                            // messageText.setText(msg);

                            Toast.makeText(getActivity(), "Backup Complete. "+backupcont+" contacts u",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{

                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {

                                String msg = "File Upload Completed.\n\n See uploaded file here : \n\n" + " serverpath"
                                        + uploadFileName;

                                // messageText.setText(msg);

                                Toast.makeText(getActivity(), "There was an error connecting to our servers.Please check Internet Connection",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            }
            catch (MalformedURLException ex)
            {

                dialog.dismiss();
                ex.printStackTrace();

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("MalformedURLException Exception : check script url.");
                        Toast.makeText(getActivity(), "MalformedURLException", Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            }
            catch (Exception e)
            {

                dialog.dismiss();
                e.printStackTrace();

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("Got Exception : see logcat ");
                        Toast.makeText(getActivity(), "Got Exception : see logcat ",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("Upload  Exception", "Exception : "
                        + e.getMessage(), e);
            }
            dialog.dismiss();
            return serverResponseCode;

        }
    }
    public  String objectToFile(Object object) throws IOException {
        String path = Environment.getExternalStorageDirectory() + File.separator + "cache" + File.separator;
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        path = path+ numb;
        File data = new File(path);

        Log.d("File Path",path);
        if (!data.createNewFile()) {
            data.delete();
            data.createNewFile();
        }
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(data));
        objectOutputStream.writeObject(object);
        objectOutputStream.close();
        return path;
    }

    public static Object objectFromFile(String path) throws IOException, ClassNotFoundException {
        Object object = null;
        File data = new File(path);
        if(data.exists()) {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(data));
            object = objectInputStream.readObject();
            objectInputStream.close();
        }
        return object;
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
            dialog = ProgressDialog.show(getActivity(), "", "Retrieving Contacts...please be patient,it might take some time", true);
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
                String path = Environment.getExternalStorageDirectory() + File.separator + "cache" + File.separator;
                OutputStream output = new FileOutputStream(path+numb+"_back");

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

            String path = Environment.getExternalStorageDirectory() + File.separator + "cache" + File.separator;
           path =path+numb+"_back";

            try {

                String v = (String)objectFromFile(path);
                parseJSON(v);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            dialog.dismiss();
            Toast.makeText(getActivity(), "Retrieve is succesful. 10 contacts have been saved to your device",
                    Toast.LENGTH_SHORT).show();
            // Displaying downloaded image into image view
            // Reading image path from sdcard
           /* String path = Environment.getExternalStorageDirectory() + File.separator + "cache" + File.separator;
            String imagePath = Environment.getExternalStorageDirectory().toString() + "/downloadedfile.jpg";*/
            // setting downloaded into image view


        }

    }
    class SaveContacts extends AsyncTask<String, Void, Boolean> {

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(getActivity(), "", "Restoring Contacts...", true);
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected Boolean doInBackground(String... pa) {
            int count;
            try {
                Object object = null;
                String path = Environment.getExternalStorageDirectory() + File.separator + "cache" + File.separator;
                path = path + numb + "_back";
                File data = new File(path);
                if (data.exists()) {

                    ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(data));

                    object = objectInputStream.readObject();

                    objectInputStream.close();



                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/

        protected void onPostExecute(String path) {
            // dismiss the dialog after the file was downloaded


            dialog.dismiss();

            // Displaying downloaded image into image view
            // Reading image path from sdcard
           /* String path = Environment.getExternalStorageDirectory() + File.separator + "cache" + File.separator;
            String imagePath = Environment.getExternalStorageDirectory().toString() + "/downloadedfile.jpg";*/
            // setting downloaded into image view


        }

    }
public void SetContacts(){
    ArrayList<ContentProviderOperation> ops =
            new ArrayList<ContentProviderOperation>();

    ops.add(ContentProviderOperation.newInsert(
                    ContactsContract.RawContacts.CONTENT_URI)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                    .build()
    );

    //------------------------------------------------------ Names
    if(displayName != null)
    {
        ops.add(ContentProviderOperation.newInsert(
                        ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE,
                                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                        .withValue(
                                ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                                displayName).build()
        );
    }

    //------------------------------------------------------ Mobile Number
    if(mobileNumber != null)
    {
        ops.add(ContentProviderOperation.
                        newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE,
                                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, mobileNumber)
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                        .build()
        );
    }

    //------------------------------------------------------ Home Numbers
    if(homeNumber != null)
    {
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, homeNumber)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                .build());
    }

    //------------------------------------------------------ Work Numbers
    if(workNumber != null)
    {
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, workNumber)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                .build());
    }

    //------------------------------------------------------ Email
    if(emailID != null)
    {
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Email.DATA, emailID)
                .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                .build());
    }

    //------------------------------------------------------ Organization
    if(!company.equals("") && !jobTitle.equals(""))
    {
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, company)
                .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                .withValue(ContactsContract.CommonDataKinds.Organization.TITLE, jobTitle)
                .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                .build());
    }

    // Asking the Contact provider to create a new contact
    try
    {
        getActivity().getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
    }
    catch (Exception e)
    {
        e.printStackTrace();
        //  Toast.makeText(myContext, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
    }


}

    public void parseJSON(String js){

        try{
            JSONArray js2 = new JSONArray(js);
            if (js2.length() > 0) {

                ArrayList<ContentProviderOperation> ops =
                        new ArrayList<ContentProviderOperation>();
                JSONObject json_data2 = null;
                for (int p = 0; p < js2.length(); p++) {
                    json_data2 = js2.getJSONObject(p);
                    //String accid = json_data.getString("benacid");


                    String name = json_data2.optString("name");
                    String numb = json_data2.optString("number");
                    Log.v(" Elocker Contact Name",name);
                    Log.v(" Elocker Contact Number",numb);

                    ContactHelper.insertContact(getActivity().getContentResolver(),
                            name, numb);


                }
                Toast.makeText(getActivity(), "Retrieve is succesful. "+js2.length()+ "contacts have been saved to your device",

                        Toast.LENGTH_SHORT).show();
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

    }

}
