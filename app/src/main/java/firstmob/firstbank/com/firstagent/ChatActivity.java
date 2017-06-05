package firstmob.firstbank.com.firstagent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adapter.ChatAdapter;
import adapter.ChatMessage;
import cn.pedant.SweetAlert.SweetAlertDialog;
import model.Chat;
import model.GetChatData;
import model.SaveChat;
import rest.ApiClient;
import rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;
    SweetAlertDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        initControls();

    }
    private void initControls() {
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.chatSendButton);



        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        loadDummyHistory();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             final   String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }
/*
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setId(122);//dummy
                chatMessage.setMessage(messageText);
                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                chatMessage.setMe(false);

                messageET.setText("");

                displayMessage(chatMessage);*/


                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                String usid = Utility.gettUtilUserId(getApplicationContext());
                String agentid = Utility.gettUtilAgentId(getApplicationContext());
                String mobnoo = Utility.gettUtilMobno(getApplicationContext());
                Call<SaveChat> call = apiService.savechat("1", usid, agentid, "9493818389",messageText);
                call.enqueue(new Callback<SaveChat>() {
                    @Override
                    public void onResponse(Call<SaveChat> call, Response<SaveChat> response) {

                        String resmessg = response.body().getMessage();
                        String respcode = response.body().getRespCode();

//                                    Log.v("Respnse getResults",datas.toString());
if(respcode.equals("00")){
    ChatMessage chatMessage = new ChatMessage();
    chatMessage.setId(122);//dummy
    chatMessage.setMessage(messageText);
    chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
    chatMessage.setMe(false);

    messageET.setText("");

    displayMessage(chatMessage);
}else{
    Toast.makeText(
            getApplicationContext(),
           resmessg,
            Toast.LENGTH_LONG).show();
}

                    }

                    @Override
                    public void onFailure(Call<SaveChat> call, Throwable t) {
                        // Log error here since request failed
                        Log.v("Throwable error", t.toString());
                        Toast.makeText(
                                getApplicationContext(),
                                "There was an error processing your request",
                                Toast.LENGTH_LONG).show();

                    }
                });


            }
        });
    }

    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    private void loadDummyHistory(){
pDialog.show();
        chatHistory = new ArrayList<ChatMessage>();


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String usid = Utility.gettUtilUserId(getApplicationContext());
        String agentid = Utility.gettUtilAgentId(getApplicationContext());
        String mobnoo = Utility.gettUtilMobno(getApplicationContext());
        Call<Chat> call = apiService.getChat("1", usid, agentid, "9493818389");
        call.enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> call, Response<Chat> response) {

                List<GetChatData> dataaa = response.body().getResults();

//                                    Log.v("Respnse getResults",datas.toString());

                if (!(dataaa == null)) {
int s = 1;
                    for (int i = 0; i < dataaa.size(); i++) {
                        String id = dataaa.get(i).getid();
                        String getmessage = dataaa.get(i).getmessage();
                        String getcreatedtime = dataaa.get(i).getcreationDate();
                        String getrespsmessage = dataaa.get(i).getresponseMessage();
                        String getresptime = dataaa.get(i).getresponseTime();
                        String makerid = dataaa.get(i).getmakerId();
                       if(Utility.isNotNull(getmessage)){

                          /* ChatMessage msg = new ChatMessage();
                           msg.setId(1);
                           msg.setMe(false);
                           msg.setMessage("Want a raise a dispute in a recent transaction");
                           msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                           chatHistory.add(msg);*/

                           Log.v("Sender msg",getmessage);

                           ChatMessage msg = new ChatMessage();
                           msg.setId(s);
                           msg.setMe(false);
                           msg.setMessage(getmessage);
                           msg.setDate(getcreatedtime);
                           chatHistory.add(msg);
                           s++;
                       }
                        if(Utility.isNotNull(getrespsmessage)){
                            ChatMessage msgg = new ChatMessage();
                            msgg.setId(s);
                            msgg.setMe(true);
                            msgg.setMessage(getrespsmessage+" -"+makerid);
                            msgg.setDate(getresptime);
                            chatHistory.add(msgg);
                            s++;

                            Log.v("Response msg",getrespsmessage);
                        }

                    }
                    adapter = new ChatAdapter(ChatActivity.this, new ArrayList<ChatMessage>());
                    messagesContainer.setAdapter(adapter);

                    for(int i=0; i<chatHistory.size(); i++) {
                        ChatMessage message = chatHistory.get(i);
                        displayMessage(message);
                    }

                } else {

                }


pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
                // Log error here since request failed
                Log.v("Throwable error", t.toString());
                Toast.makeText(
                        getApplicationContext(),
                        "There was an error processing your request",
                        Toast.LENGTH_LONG).show();

            }
        });


pDialog.dismiss();


    }

}
