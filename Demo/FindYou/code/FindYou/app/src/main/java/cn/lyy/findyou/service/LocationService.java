package cn.lyy.findyou.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import cn.lyy.findyou.location.LocationHandler;
import cn.lyy.findyou.location.LocationManager;

public class LocationService extends Service {
    private static final String TAG = "LocationService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate executed");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand executed");

        LocationManager.getInstance(LocationService.this, new LocationHandler()).start();
        // TODO 添加定时器
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy executed");
    }
}


