package com.example.asif.servicedemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.asif.servicedemo.activities.BoundServiceActivity;
import com.example.asif.servicedemo.activities.RemoteBoundServiceActivity;
import com.example.asif.servicedemo.activities.UnboundServiceActivity;
import com.example.asif.servicedemo.services.MyBoundedService;
import com.example.asif.servicedemo.services.MyService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    Button btnUnboundService, btnLocalBoundService, btnRemoteBoundService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnUnboundService=findViewById(R.id.btnUnboundService);
        btnLocalBoundService=findViewById(R.id.btnLocalBoundService);
        btnRemoteBoundService=findViewById(R.id.btnRemoteBoundService);

        btnUnboundService.setOnClickListener(this);
        btnLocalBoundService.setOnClickListener(this);
        btnRemoteBoundService.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnUnboundService: {
                startActivity(new Intent(getApplicationContext(), UnboundServiceActivity.class));
            } break;
            case R.id.btnLocalBoundService: {
                startActivity(new Intent(getApplicationContext(),BoundServiceActivity.class));
            } break;
            case R.id.btnRemoteBoundService: {
                startActivity(new Intent(getApplicationContext(), RemoteBoundServiceActivity.class));
            } break;
        }
    }
}
