package cn.lyy.findyou.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import cn.lyy.findyou.consts.Consts;
import cn.lyy.findyou.service.LocationService;

public class StartLocationServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.e("ServiceReceiver", "action->" + action);
        if (Consts.CUSTOM_BROADCAST_ACTION.equals(action)
                || Intent.ACTION_USER_PRESENT.equals(action)) {
            Log.e("LocationServiceReceiver", "发送广播通知");
            Toast.makeText(context, "发送广播通知", Toast.LENGTH_SHORT).show();
            Intent startServiceIntent = new Intent(context.getApplicationContext(), LocationService.class);
            context.startService(startServiceIntent);
        }
    }
}
