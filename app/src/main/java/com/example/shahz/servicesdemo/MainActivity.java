package com.example.shahz.servicesdemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button startService, stopService,bindService, unbindService, getRandomNo;
    TextView textView;
    private Intent intent;
    private MyService myService;
    boolean isServiceBound;
    ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("Main", "MainActivity thread id: " + Thread.currentThread().getId());

        textView=(TextView)findViewById(R.id.textView);
        startService = (Button) findViewById(R.id.startService);
        stopService = (Button) findViewById(R.id.stopService);
        bindService = (Button) findViewById(R.id.bindService);
        unbindService = (Button) findViewById(R.id.unbindService);
        getRandomNo = (Button) findViewById(R.id.getRandomNo);

        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);
        getRandomNo.setOnClickListener(this);

        intent = new Intent(getApplicationContext(), MyService.class);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.startService:
                startService(intent);
                break;
            case R.id.stopService:
                stopService(intent);
                break;
            case R.id.bindService:
                bindService();
                break;
            case R.id.unbindService:
                unbindService();
                break;
            case R.id.getRandomNo:
                setRandomNo();


        }

    }

    public void bindService()
    {
        if (serviceConnection==null)
        {
            serviceConnection=new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

                    MyService.MyserviceBinder myserviceBinder= (MyService.MyserviceBinder) iBinder;
                    myService=myserviceBinder.getService();
                    isServiceBound=true;
                }

                @Override
                public void onServiceDisconnected(ComponentName componentName) {

                    isServiceBound=false;
                }
            };
        }
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void unbindService()
    {
        if (isServiceBound)
        {
            unbindService(serviceConnection);
            isServiceBound=false;
        }
    }

    public void setRandomNo()
    {
        if (isServiceBound)
        {
            textView.setText("Random no =" +myService.getRandomNumber());
        }
        else
        {
            textView.setText("service not bound");
        }
    }
}
