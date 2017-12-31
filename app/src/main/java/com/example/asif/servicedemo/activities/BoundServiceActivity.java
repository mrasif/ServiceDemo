package com.example.asif.servicedemo.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.asif.servicedemo.R;
import com.example.asif.servicedemo.services.MyBoundedService;

public class BoundServiceActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnStartBoundedService, btnStopBoundedService, btnGetDataFromService;
    TextView tvServiceData;

    Intent myBoundedServiceIntent;
    private MyBoundedService myBoundedService;
    private boolean isServiceBound;
    private ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bound_service);
        myBoundedServiceIntent=new Intent(getApplicationContext(), MyBoundedService.class);
        tvServiceData=findViewById(R.id.tvServiceData);
        btnStartBoundedService=findViewById(R.id.btnStartBoundedService);
        btnStopBoundedService=findViewById(R.id.btnStopBoundedService);
        btnGetDataFromService=findViewById(R.id.btnGetDataFromService);


        btnStartBoundedService.setOnClickListener(this);
        btnStopBoundedService.setOnClickListener(this);
        btnGetDataFromService.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnStartBoundedService: {
                startService(myBoundedServiceIntent);
                bindService();
            } break;
            case R.id.btnStopBoundedService: {
                unBindService();
                stopService(myBoundedServiceIntent);
            } break;
            case R.id.btnGetDataFromService: {
                if(isServiceBound){
                    tvServiceData.setText("Random Number is: "+String.valueOf(myBoundedService.getRandomNumber()));
                }
                else {
                    tvServiceData.setText("Service is not bound.");
                }
            } break;
        }
    }

    private void bindService(){
        if (serviceConnection==null){
            serviceConnection=new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    MyBoundedService.MyServiceBinder myServiceBinder=(MyBoundedService.MyServiceBinder)iBinder;
                    myBoundedService=myServiceBinder.getService();
                    isServiceBound=true;
                }

                @Override
                public void onServiceDisconnected(ComponentName componentName) {
                    isServiceBound=false;
                }
            };
        }
        bindService(myBoundedServiceIntent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void unBindService(){
        if(isServiceBound){
            unbindService(serviceConnection);
            isServiceBound=false;
        }
    }
}
