package cn.lyy.findyou.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.lyy.findyou.service.LocationService;

public class StartLocationServiceReceiver extends BroadcastReceiver {
    public StartLocationServiceReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startServiceIntent = new Intent(context.getApplicationContext(), LocationService.class);
        context.getApplicationContext().startService(startServiceIntent);
    }
}
