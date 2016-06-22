package cn.lyy.findyou.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import cn.lyy.findyou.activity.MainActivity;

public class LocationService extends Service {
    private static final String TAG = "LocationService";

    public LocationService() {
    }

    private DownloadBinder mBinder = new DownloadBinder();

    public class DownloadBinder extends Binder {

        public void startDownload() {
            Log.e(TAG, "startDownload executed");
        }

        public int getProgress() {
            Log.e(TAG, "getProgress executed");
            return 0;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Notification notification = new Notification.Builder(this).setSmallIcon(android.R.drawable.stat_notify_chat).setContentTitle("Notification comes").setWhen(System.currentTimeMillis()).build();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        startForeground(1, notification);
        Log.e(TAG, "onCreate executed");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand executed");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy executed");
    }
}


