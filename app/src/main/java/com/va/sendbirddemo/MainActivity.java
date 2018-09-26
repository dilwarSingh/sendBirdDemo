package com.va.sendbirddemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sendbird.android.OpenChannel;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;

import static com.va.sendbirddemo.data.CHANNEL_URL;

public class MainActivity extends AppCompatActivity {

    EditText userIdET;
    Button connect;
    static OpenChannel openChannel = null;
    static String user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userIdET = findViewById(R.id.editText);
        connect = findViewById(R.id.button);


        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = userIdET.getText().toString();
                user = userID;
                Toast.makeText(MainActivity.this, "Connecting please wait", Toast.LENGTH_SHORT).show();

                SendBird.connect(userID, new SendBird.ConnectHandler() {
                    @Override
                    public void onConnected(User user, SendBirdException e) {
                        if (e != null) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        OpenChannel.getChannel(CHANNEL_URL, new OpenChannel.OpenChannelGetHandler() {
                            @Override
                            public void onResult(final OpenChannel openChannel, SendBirdException e) {
                                if (e != null) {    // Error.
                                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                openChannel.enter(new OpenChannel.OpenChannelEnterHandler() {
                                    @Override
                                    public void onResult(SendBirdException e) {
                                        if (e != null) {    // Error.
                                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        MainActivity.openChannel = openChannel;
                                        startActivity(new Intent(MainActivity.this, Home.class));
                                    }
                                });
                            }
                        });


                    }
                });

            }
        });


    }
}
