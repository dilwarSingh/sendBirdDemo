package com.va.sendbirddemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sendbird.android.AdminMessage;
import com.sendbird.android.BaseChannel;
import com.sendbird.android.BaseMessage;
import com.sendbird.android.FileMessage;
import com.sendbird.android.PreviousMessageListQuery;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;
import com.sendbird.android.UserMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Home extends AppCompatActivity {

    EditText messageEt;
    Button sendBt;
    RecyclerView recyclerView;
    List<dataHolder> list = new ArrayList<>();
    int roomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        roomId = Math.abs(new Random().nextInt() % 100) + 1;

        messageEt = findViewById(R.id.editText2);
        sendBt = findViewById(R.id.button2);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new recyAdapter(this, list));

        if (MainActivity.openChannel == null) {
            throw new NullPointerException("openChannel is null");
        }

        sendBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEt.getText().toString();
                Toast.makeText(Home.this, "Sending message", Toast.LENGTH_SHORT).show();
                MainActivity.openChannel.sendUserMessage(message, new BaseChannel.SendUserMessageHandler() {
                    @Override
                    public void onSent(UserMessage userMessage, SendBirdException e) {
                        if (e != null) {
                            Toast.makeText(Home.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        messageEt.setText("");
                        String message = userMessage.getMessage();
                        User sender = userMessage.getSender();
                        list.add(new dataHolder(sender.getUserId(), message));
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }

                });

            }
        });

        PreviousMessageListQuery previousMessageListQuery = MainActivity.openChannel.createPreviousMessageListQuery();
        previousMessageListQuery.load(20, true, new PreviousMessageListQuery.MessageListQueryResult() {
            @Override
            public void onResult(List<BaseMessage> list, SendBirdException e) {
                if (e != null) {
                    Toast.makeText(Home.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                for (BaseMessage baseMessage : list) {
                    if (baseMessage instanceof UserMessage) {
                        UserMessage um = (UserMessage) baseMessage;

                        User sender = um.getSender();
                        String message = um.getMessage();
                        Home.this.list.add(new dataHolder(sender.getUserId(), message));

                    } else if (baseMessage instanceof FileMessage) {


                    } else if (baseMessage instanceof AdminMessage) {


                    }
                }
                recyclerView.getAdapter().notifyDataSetChanged();

            }
        });


        SendBird.addChannelHandler(roomId + "", new SendBird.ChannelHandler() {
            @Override
            public void onMessageReceived(BaseChannel baseChannel, BaseMessage baseMessage) {
                if (baseMessage instanceof UserMessage) {
                    UserMessage um = (UserMessage) baseMessage;

                    User sender = um.getSender();
                    String message = um.getMessage();
                    list.add(new dataHolder(sender.getUserId(), message));

                    recyclerView.getAdapter().notifyDataSetChanged();

                } else if (baseMessage instanceof FileMessage) {


                } else if (baseMessage instanceof AdminMessage) {


                }
            }
        });

    }
}
