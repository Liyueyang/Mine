package cn.lyy.findyou.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.avos.avoscloud.AVObject;
import com.baidu.location.BDLocation;

import cn.lyy.findyou.consts.Consts;
import cn.lyy.findyou.location.LocationManager;
import cn.lyy.findyou.receiver.StartLocationServiceReceiver;

public class LocationService extends Service {

    private static final String TAG = "LocationService";

    private StartLocationServiceReceiver mReceiver;
    private LocationManager mLocationManager;
    private LocationHandler mLocationHandler;
    private String mInstallationId;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLocationHandler = LocationHandler.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);
        mReceiver = new StartLocationServiceReceiver();
        registerReceiver(mReceiver, intentFilter);
        mLocationManager = LocationManager.getInstance(LocationService.this.getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SharedPreferences pref = getSharedPreferences(Consts.SHARED_PREFERENCES_LAST_ALARM_TIME, MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = pref.edit();
        prefEditor.putLong(Consts.LAST_ALARM_TIME_KEY_SP, System.currentTimeMillis());
        prefEditor.commit();

        SharedPreferences userPref = getSharedPreferences(Consts.SHARED_PREFERENCES_USER_INFO, MODE_PRIVATE);
        mInstallationId = userPref.getString(Consts.USER_AVINSTALLATION_ID_PREF, "");
        mLocationHandler.setInstallationId(mInstallationId);
        mLocationHandler.setAction(intent.getAction());

        mLocationManager.setHandler(mLocationHandler);
        mLocationManager.start();
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        serviceDestroyed();
    }

    private void serviceDestroyed() {
        AVObject serviceDestroyObj = new AVObject("ServiceDestroy");
        serviceDestroyObj.put("InstallationId", mInstallationId);
        serviceDestroyObj.saveInBackground();
    }

    private static class LocationHandler extends Handler {

        private Context mContext;
        private String mActionName = "";
        private String mInstallationId = "";
        private static volatile LocationHandler mInstance;

        /**
         * 此处写单例是因为，启动app后，可能是收到推送开启的服务也可能是收到广播开启的服务，造成new出来的对象不统一
         * @param context
         * @return
         */
        public static LocationHandler getInstance(Context context) {
            if (mInstance == null) {
                synchronized (LocationHandler.class) {
                    if (mInstance == null) {
                        mInstance = new LocationHandler(context);
                    }
                }
            }
            return mInstance;
        }

        private LocationHandler(Context context) {
            mContext = context;
        }

        public void setInstallationId(String installationId) {
            mInstallationId = installationId;
        }

        public void setAction(String actionName) {
            mActionName = actionName;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    BDLocation bdLocation = (BDLocation) msg.obj;
                    String latLonStr = bdLocation.getLatitude() + "," + bdLocation.getLongitude();
                    String mtype = android.os.Build.MODEL; // 手机型号
                    String mtyb = android.os.Build.BRAND;//手机品牌
                    AVObject result = new AVObject("Location");
                    result.put("MODEL", mtype);
                    result.put("BRAND", mtyb);
                    result.put("Location", bdLocation.getAddrStr());
                    result.put("Lat_Lon", latLonStr);
                    result.put("InstallationId", mInstallationId);
                    result.put("ActionName", mActionName);
                    result.saveInBackground();
                    break;
                default:
                    break;
            }

        }
    }
}