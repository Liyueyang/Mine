package cn.lyy.findyou.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import com.avos.avoscloud.AVObject;

import cn.lyy.findyou.consts.Consts;
import cn.lyy.findyou.location.LocationManager;
import cn.lyy.findyou.receiver.StartLocationServiceReceiver;

public class LocationService extends Service {

    private static final String TAG = "LocationService";
    private SharedPreferences mPref;
    private SharedPreferences.Editor mPrefEditer;
    private AlarmManager mAlarmManager;
    private Intent mStartServiceIntent;
    private PendingIntent mPi;
    private StartLocationServiceReceiver mReceiver;
    private LocationHandler mLocationHandler;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLocationHandler = new LocationHandler(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);
        mReceiver = new StartLocationServiceReceiver();
        registerReceiver(mReceiver, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mPref = getSharedPreferences(Consts.SHARED_PREFERENCES_LAST_ALARM_TIME, MODE_PRIVATE);
        long lastAlarmTime = mPref.getLong(Consts.LAST_ALARM_TIME_KEY_SP, 0);

        if (lastAlarmTime != 0) {
            long intervalTime = System.currentTimeMillis() - lastAlarmTime;
            if (intervalTime < Consts.ALARM_INTERVAL_MILLIS) {
                return START_REDELIVER_INTENT;
            }
        }

        mPrefEditer = mPref.edit();
        mPrefEditer.putLong(Consts.LAST_ALARM_TIME_KEY_SP, System.currentTimeMillis());
        mPrefEditer.commit();

        LocationManager.getInstance(LocationService.this, mLocationHandler).start();
        // 添加定时器
//        mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        mStartServiceIntent = new Intent(LocationService.this, StartLocationServiceReceiver.class);
//        mStartServiceIntent.setAction(Consts.CUSTOM_BROADCAST_ACTION);
//        mPi = PendingIntent.getBroadcast(LocationService.this, 0, mStartServiceIntent, 0);
//        mAlarmManager.cancel(mPi);
//
//        mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
//                SystemClock.elapsedRealtime() + Consts.ALARM_INTERVAL_MILLIS, Consts.ALARM_INTERVAL_MILLIS, mPi);

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        Log.e(TAG, "onDestroy()!");
        serviceDestroyed();
    }

    private void serviceDestroyed() {
        AVObject serviceDestroyObj = new AVObject("ServiceDestroy");
        serviceDestroyObj.put("ServiceDestroy", "ServiceDestroy");
        serviceDestroyObj.saveInBackground();
    }

    private static class LocationHandler extends Handler {

        private Context mContext;

        public LocationHandler(Context context) {
            mContext = context;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    String mtype = android.os.Build.MODEL; // 手机型号
                    String mtyb = android.os.Build.BRAND;//手机品牌
                    AVObject result = new AVObject("Location");
                    result.put("MODEL", mtype);
                    result.put("BRAND", mtyb);
                    result.put("Location", (String) msg.obj);
                    result.saveInBackground();
                    break;
                default:
                    break;
            }

        }
    }
}