package cn.lyy.findyou.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import cn.lyy.findyou.consts.Consts;
import cn.lyy.findyou.service.LocationService;

public class StartLocationServiceReceiver extends BroadcastReceiver {

    private static final String TAG = "LocationServiceReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Intent startServiceIntent = new Intent(context.getApplicationContext(), LocationService.class);
        if (Intent.ACTION_USER_PRESENT.equals(action)) {
            SharedPreferences pref = context.getSharedPreferences(Consts.SHARED_PREFERENCES_LAST_ALARM_TIME, context.MODE_PRIVATE);
            long lastAlarmTime = pref.getLong(Consts.LAST_ALARM_TIME_KEY_SP, 0);

            if (lastAlarmTime != 0) {
                long intervalTime = System.currentTimeMillis() - lastAlarmTime;
                if (intervalTime < Consts.ALARM_INTERVAL_MILLIS) {
                    return;
                }
            }
            startServiceIntent.setAction(Intent.ACTION_USER_PRESENT);
        } else if (Consts.CUSTOM_BROADCAST_ACTION.equals(action)) {
            startServiceIntent.setAction(Consts.CUSTOM_BROADCAST_ACTION);
        } else if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            startServiceIntent.setAction(Intent.ACTION_BOOT_COMPLETED);
        }

        startServiceIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        startServiceIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startService(startServiceIntent);
    }
}
