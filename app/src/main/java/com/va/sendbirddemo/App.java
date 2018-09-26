package com.va.sendbirddemo;

import android.app.Application;

import com.sendbird.android.SendBird;

import static com.va.sendbirddemo.data.APP_ID;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SendBird.init(APP_ID, this);

    }
}
