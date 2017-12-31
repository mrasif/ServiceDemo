package com.example.asif.servicedemo.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by asif on 1/1/18.
 */

public class MyRemoteService extends Service {
    private static final String TAG = "MyBoundedService";
    private int my_random_number;
    private boolean isRandomNumberGenerateOn;

    private final int MIN=0;
    private final int MAX=100;

    public static final int GET_COUNT=0;

    public class RandomNumberRequestHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GET_COUNT:
                    Message messageSendRandomNumber=Message.obtain(null,GET_COUNT);
                    messageSendRandomNumber.arg1=getRandomNumber();
                    try {
                        msg.replyTo.send(messageSendRandomNumber);
                    }
                    catch (Exception e){

                    }
            }
            super.handleMessage(msg);
        }
    }

    private Messenger randomNumberMessenger=new Messenger(new RandomNumberRequestHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: executed...");
        // This is for security checking
        if(intent.getStringExtra("KEY").equals("THISISMYKEY")){
            Toast.makeText(getApplicationContext(),"Correct key !",Toast.LENGTH_SHORT).show();
            return randomNumberMessenger.getBinder();
        }
        else {
            Toast.makeText(getApplicationContext(),"Wrong key !",Toast.LENGTH_SHORT).show();
            return null;
        }

    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(TAG, "onRebind: executed...");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(TAG, "onStart: executed...");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: executed with thread id : "+Thread.currentThread().getId());
        isRandomNumberGenerateOn=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                startGenerator();
            }
        }).start();

        return START_STICKY;
    }

    private void startGenerator(){
        while (isRandomNumberGenerateOn){
            try{
                Thread.sleep(1000);
                if(isRandomNumberGenerateOn){
                    my_random_number=new Random().nextInt(MAX)+MIN;
                    Log.d(TAG, "startGenerator: Random Number is: "+my_random_number);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void stopGenerator(){
        Log.d(TAG, "stopGenerator: executed...");
        isRandomNumberGenerateOn=false;
    }

    public int getRandomNumber(){
        Log.d(TAG, "getRandomNumber: executed...");
        return my_random_number;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: executed...");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: executed...");
        stopGenerator();
    }
}
