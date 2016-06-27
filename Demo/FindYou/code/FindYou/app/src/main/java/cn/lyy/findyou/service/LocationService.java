package cn.lyy.findyou.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import java.util.Date;

import cn.lyy.findyou.receiver.StartLocationServiceReceiver;
import cn.lyy.findyou.utils.TimeUtils;

public class LocationService extends Service {
    private static final String TAG = "LocationService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        LocationManager.getInstance(LocationService.this, new LocationHandler()).start();
        Log.e(TAG, "alarm!");
        // 添加定时器
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent startServiceIntent = new Intent(LocationService.this, StartLocationServiceReceiver.class);
        PendingIntent pi = PendingIntent.getService(LocationService.this, 0, startServiceIntent, 0);
        long triggerAtMillis = TimeUtils.getDate(new Date(System.currentTimeMillis()), true).getTime();
//        triggerAtMillis = triggerAtMillis + 20 * 60 * 60 * 1000;
//        triggerAtMillis = System.currentTimeMillis() + 2 * 60 * 60 * 1000;
        triggerAtMillis = SystemClock.currentThreadTimeMillis() + 60 * 1000;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, pi);
        } else {
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, pi);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}