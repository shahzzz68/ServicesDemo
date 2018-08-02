package com.example.shahz.servicesdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

public class MyService extends Service {

    int randomNumber;
    boolean isRandomGeneratorOn;

    final int Min=0;
    final int Max=100;
////////////////////////// binder class//////////////////////
    class MyserviceBinder extends Binder
    {
        public MyService getService()
        {
            return MyService.this;
        }
    }

    private IBinder iBinder = new MyserviceBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Msg","service started");

        isRandomGeneratorOn=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                startRandomGenerator();
            }
        }).start();

        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRandomGenerator();
        Log.i("Destroy","Service Destroyed");
    }

/////////////////////// Random number generator///////////////////////////////////
    public void startRandomGenerator()
    {
        while (isRandomGeneratorOn)
        {
            try {
                Thread.sleep(1000);
                if (isRandomGeneratorOn)
                {
                    randomNumber=new Random().nextInt(Max)+Min;
                    Log.i("Random Number" ,""+randomNumber);
                }
            }
            catch (Exception e)
            {
                Log.i("Random number","Intrupped");
            }
        }
    }

    public void stopRandomGenerator()
    {
        isRandomGeneratorOn=false;
    }

    public int getRandomNumber()
    {
        return randomNumber;
    }
}
