package com.example.asif.servicedemo.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by asif on 12/31/17.
 */

public class MyService extends Service {
    private static final String TAG = "MyService";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: executed...");
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: executed...");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: executed with Thread Id: "+Thread.currentThread().getId());
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }
}
