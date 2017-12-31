package com.example.asif.servicedemo.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.asif.servicedemo.R;
import com.example.asif.servicedemo.services.MyBoundedService;
import com.example.asif.servicedemo.services.MyRemoteService;

public class RemoteBoundServiceActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnStartBoundedService, btnStopBoundedService, btnGetDataFromService, btnBind, btnUnbind;
    TextView tvServiceData;

    public static final int GET_RANDOM_NUMBER_FLAG=0;
    private boolean isBound;
    private Intent myRemoteServiceIntent;
    Messenger requestMessenger, receiveMessenger;
    private int randon_number_value;

    class ReceiveRandomNumberHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            randon_number_value=0;
            switch (msg.what){
                case GET_RANDOM_NUMBER_FLAG:
                    randon_number_value=msg.arg1;
                    tvServiceData.setText("Random number is: "+String.valueOf(randon_number_value));
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    }

    ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            requestMessenger=new Messenger(iBinder);
            receiveMessenger=new Messenger(new ReceiveRandomNumberHandler());
            isBound=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            requestMessenger=null;
            receiveMessenger=null;
            isBound=false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_bound_service);
        tvServiceData=findViewById(R.id.tvServiceData);
        btnStartBoundedService=findViewById(R.id.btnStartBoundedService);
        btnStopBoundedService=findViewById(R.id.btnStopBoundedService);
        btnGetDataFromService=findViewById(R.id.btnGetDataFromService);
        btnBind=findViewById(R.id.btnBind);
        btnUnbind=findViewById(R.id.btnUnbind);

        btnStartBoundedService.setOnClickListener(this);
        btnStopBoundedService.setOnClickListener(this);
        btnGetDataFromService.setOnClickListener(this);
        btnBind.setOnClickListener(this);
        btnUnbind.setOnClickListener(this);

        myRemoteServiceIntent=new Intent();
        myRemoteServiceIntent.setComponent(new ComponentName("com.example.asif.servicedemo","com.example.asif.servicedemo.services.MyRemoteService"));
        // This is for security checking
        myRemoteServiceIntent.putExtra("KEY","THISISMYKEY");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnStartBoundedService: {
                startService(myRemoteServiceIntent);
            } break;
            case R.id.btnStopBoundedService: {
                stopService(myRemoteServiceIntent);
            } break;
            case R.id.btnBind: {
                bindRemoteService();
            } break;
            case R.id.btnUnbind: {
                unbindRemoteService();
            } break;
            case R.id.btnGetDataFromService: {
                fetchRandomNumber();
            } break;
        }
    }

    private void bindRemoteService(){
        bindService(myRemoteServiceIntent,serviceConnection,BIND_AUTO_CREATE);
    }

    private void unbindRemoteService(){
        unbindService(serviceConnection);
        isBound=false;
    }

    private void fetchRandomNumber(){
        if(isBound){
            Message requestMessage=Message.obtain(null,GET_RANDOM_NUMBER_FLAG);
            requestMessage.replyTo=receiveMessenger;
            try{
                requestMessenger.send(requestMessage);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        serviceConnection=null;
    }
}
