package com.example.asif.servicedemo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.asif.servicedemo.R;
import com.example.asif.servicedemo.services.MyService;

public class UnboundServiceActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnStartService, btnStopService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unbound_service);
        btnStartService=findViewById(R.id.btnStartService);
        btnStopService=findViewById(R.id.btnStopService);
        btnStartService.setOnClickListener(this);
        btnStopService.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnStartService: {
                startService(new Intent(getApplicationContext(), MyService.class));
            } break;
            case R.id.btnStopService: {
                stopService(new Intent(getApplicationContext(),MyService.class));
            } break;
        }
    }
}
