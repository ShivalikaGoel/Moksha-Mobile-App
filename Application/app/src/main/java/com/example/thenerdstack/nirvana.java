package com.example.thenerdstack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;

import java.util.UUID;

import ai.api.AIServiceContext;
import ai.api.AIServiceContextBuilder;
import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

public class nirvana extends AppCompatActivity {
    private Context context;
    private String uuid = UUID.randomUUID().toString();
    private LinearLayout chatLayout;
    private EditText queryEditText;
    private static final int USER = 10001;
    private static final int BOT = 10002;
    private SessionsClient sessionsClient;
    AIRequest aiRequest;
    private SessionName session;
    AIServiceContext customAIServiceContext;
    //    DatabaseReference ref;
    AIDataService aiDataService;
    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nirvana);
        final ScrollView scrollview = findViewById(R.id.chatScrollView); //added scroll view for the bot
        scrollview.post(new Runnable() {
            public void run() {
                scrollview.fullScroll(View.FOCUS_DOWN);
            }
        });
        chatLayout = findViewById(R.id.chatLayout);
        ImageView sendBtn = findViewById(R.id.sendBtn);
        queryEditText = findViewById(R.id.queryEditText);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg=queryEditText.getText().toString();
                sendMessage(msg);
            }
        });


        initChatBot(); //initialization

    }
    private void initChatBot()
    {
        context = getApplicationContext();
        final ai.api.android.AIConfiguration configuration = new ai.api.android.AIConfiguration("a5e6eb5c6c394d54b9ca1226692cf15a", AIConfiguration.SupportedLanguages.English,

                ai.api.android.AIConfiguration.RecognitionEngine.System);
        aiDataService = new AIDataService(context, configuration);
        customAIServiceContext= AIServiceContextBuilder.buildFromSessionId(uuid);
        aiRequest=new AIRequest();
    }
    private void sendMessage(String msg) { //function to send the messge from the bot

        if (msg.trim().isEmpty()) {
            Toast.makeText(nirvana.this, "Please enter your query!", Toast.LENGTH_LONG).show();
        } else {
            showTextView(msg, USER);
            queryEditText.setText("");
            // Android client
            aiRequest.setQuery(msg);
            RequestTask requestTask = new RequestTask(nirvana.this, aiDataService, customAIServiceContext);
            requestTask.execute(aiRequest);
        }
    }


    private void showTextView(String message, int type) { //function to show the response
        FrameLayout layout;
        switch (type) {
            case USER:
                layout = getUserLayout();
                break;
            case BOT:
                layout = getBotLayout();
                break;
            default:
                layout = getBotLayout();
                break;
        }
        layout.setFocusableInTouchMode(true);
        chatLayout.addView(layout); // move focus to text view to automatically make it scroll up if softfocus
        TextView tv = layout.findViewById(R.id.chatMsg);
        tv.setText(message);
        layout.requestFocus();
        queryEditText.requestFocus(); // change focus back to edit text to continue typing
    }
    FrameLayout getUserLayout() { //to get the user layout
        LayoutInflater inflater = LayoutInflater.from(nirvana.this);
        return (FrameLayout) inflater.inflate(R.layout.user_msg_layout, null);
    }

    FrameLayout getBotLayout() { //to get the bot layout
        LayoutInflater inflater = LayoutInflater.from(nirvana.this);
        return (FrameLayout) inflater.inflate(R.layout.bot_msg_layout, null);
    }
    public void callback(AIResponse aiResponse) //to process the bot msg
    {
        if (aiResponse != null) {
            
            String botReply = aiResponse.getResult().getFulfillment().getSpeech();
            //Log.d(TAG, "Bot Reply: " + botReply);
            showTextView(botReply, BOT);
        } else {
            //Log.d(TAG, "Bot Reply: Null");
            showTextView("There was some communication issue. Please Try again!", BOT);
        }
    }
}

