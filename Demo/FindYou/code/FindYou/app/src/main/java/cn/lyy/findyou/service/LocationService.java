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

import cn.lyy.findyou.consts.Consts;
import cn.lyy.findyou.location.LocationHandler;
import cn.lyy.findyou.location.LocationManager;
import cn.lyy.findyou.receiver.StartLocationServiceReceiver;

public class LocationService extends Service {

    private static final String TAG = "LocationService";
    private SharedPreferences mPref;
    private SharedPreferences.Editor mPrefEditer;
    private AlarmManager mAlarmManager;
    private Intent mStartServiceIntent;
    private PendingIntent mPi;

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
        String actionType = "";
        if (intent.hasExtra(Consts.EXTRA_START_SERVICE_ACTION_NAME)) {
            actionType = intent.getStringExtra(Consts.EXTRA_START_SERVICE_ACTION_NAME);
        }
        if (lastAlarmTime != 0) {
            long intervalTime = System.currentTimeMillis() - lastAlarmTime;
            if (actionType.equals(Intent.ACTION_USER_PRESENT) && intervalTime < Consts.ALARM_INTERVAL_MILLIS) {
                return super.onStartCommand(intent, flags, startId);
            }
        }

        mPrefEditer = mPref.edit();
        mPrefEditer.putLong(Consts.LAST_ALARM_TIME_KEY_SP, System.currentTimeMillis());
        mPrefEditer.commit();

        LocationManager.getInstance(LocationService.this, new LocationHandler(this, actionType)).start();
        // 添加定时器
        mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mStartServiceIntent = new Intent(LocationService.this, StartLocationServiceReceiver.class);
        mStartServiceIntent.setAction(Consts.CUSTOM_BROADCAST_ACTION);
        mPi = PendingIntent.getBroadcast(LocationService.this, 0, mStartServiceIntent, 0);
        mAlarmManager.cancel(mPi);

        mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + Consts.ALARM_INTERVAL_MILLIS, Consts.ALARM_INTERVAL_MILLIS, mPi);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy()!");
        mAlarmManager.cancel(mPi);
    }

}