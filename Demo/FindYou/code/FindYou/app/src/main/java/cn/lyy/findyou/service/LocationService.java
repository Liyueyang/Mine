package cn.lyy.findyou.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import cn.lyy.findyou.consts.Consts;
import cn.lyy.findyou.location.LocationHandler;
import cn.lyy.findyou.location.LocationManager;
import cn.lyy.findyou.receiver.StartLocationServiceReceiver;

public class LocationService extends Service {

    private static final String TAG = "LocationService";
    private SharedPreferences mPref;
    private SharedPreferences.Editor mPrefEditer;

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
        mPref = getSharedPreferences(Consts.SHARED_PREFERENCES_LAST_ALARM_TIME, MODE_PRIVATE);
        long lastAlarmTime = mPref.getLong(Consts.LAST_ALARM_TIME_KEY_SP, 0);
        if (lastAlarmTime != 0) {
            long intervalTime = System.currentTimeMillis() - lastAlarmTime;
            if (intervalTime < Consts.ALARM_INTERVAL_MILLIS) {
                Log.e(TAG, "间隔小于30分钟");
                Toast.makeText(LocationService.this, "间隔小于30分钟", Toast.LENGTH_SHORT).show();
                return super.onStartCommand(intent, flags, startId);
            }
        }

        mPrefEditer = mPref.edit();
        mPrefEditer.putLong(Consts.LAST_ALARM_TIME_KEY_SP, System.currentTimeMillis());
        mPrefEditer.commit();

        LocationManager.getInstance(LocationService.this, new LocationHandler()).start();
        Log.e(TAG, "提醒");
        Toast.makeText(LocationService.this, "提醒", Toast.LENGTH_SHORT).show();
        // 添加定时器
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent startServiceIntent = new Intent(LocationService.this, StartLocationServiceReceiver.class);
        startServiceIntent.setAction(Consts.CUSTOM_BROADCAST_ACTION);
        PendingIntent pi = PendingIntent.getBroadcast(LocationService.this, 0, startServiceIntent, 0);
        alarmManager.cancel(pi);

        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 10 * 1000, Consts.ALARM_INTERVAL_MILLIS, pi);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy()!");
    }

}