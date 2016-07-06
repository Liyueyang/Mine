package cn.lyy.findyou.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.lyy.findyou.consts.Consts;
import cn.lyy.findyou.service.LocationService;

public class StartLocationServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_USER_PRESENT.equals(action)) {
            Intent startServiceIntent = new Intent(context.getApplicationContext(), LocationService.class);
            context.startService(startServiceIntent);
        }
    }
}
