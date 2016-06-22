package cn.lyy.findyou.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import cn.lyy.findyou.R;
import cn.lyy.findyou.location.LocationManager;
import cn.lyy.findyou.service.LocationService;
import cn.lyy.findyou.utils.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private final int SDK_PERMISSION_REQUEST = 127;
    private TextView mResultTv;
    private Button mGetLocationBtn;
    private Button mStartServiceBtn;
    private Button mStopServiceBtn;
    private Button mBindServiceBtn;
    private Button mUnBindServiceBtn;
    private LocationManager mLocationManager;
    private LocationHandler mLocationHandler;
    private LocationService.DownloadBinder mDownloadBinder;
    private ServiceConnection mConnection;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        super.initData();
        mLocationHandler = new LocationHandler(this);
        mLocationManager = LocationManager.getInstance(this, mLocationHandler);
        getPersimmions();
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mDownloadBinder = (LocationService.DownloadBinder) service;
                mDownloadBinder.startDownload();
                mDownloadBinder.getProgress();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
    }

    @Override
    public void findViewById() {
        super.findViewById();
        mResultTv = (TextView) findViewById(R.id.result_tv);
        mGetLocationBtn = (Button) findViewById(R.id.button);
        mStartServiceBtn = (Button) findViewById(R.id.start_service_button);
        mStopServiceBtn = (Button) findViewById(R.id.stop_service_button);
        mBindServiceBtn = (Button) findViewById(R.id.bind_service_button);
        mUnBindServiceBtn = (Button) findViewById(R.id.unbind_service_button);
    }

    @Override
    public void addListener() {
        super.addListener();
        mGetLocationBtn.setOnClickListener(this);
        mStartServiceBtn.setOnClickListener(this);
        mStopServiceBtn.setOnClickListener(this);
        mBindServiceBtn.setOnClickListener(this);
        mUnBindServiceBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                getLocation();
                break;
            case R.id.start_service_button:
                startLocationService();
                break;
            case R.id.stop_service_button:
                stopLocationService();
                break;
            case R.id.bind_service_button:
                bindLocationService();
                break;
            case R.id.unbind_service_button:
                unBindLocationService();
                break;
            default:
                break;
        }
    }

    private void bindLocationService() {
        Intent intent = new Intent(this, LocationService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    private void unBindLocationService() {
        unbindService(mConnection);
    }

    private void startLocationService() {
        Intent intent = new Intent(this, LocationService.class);
        startService(intent);
    }

    private void stopLocationService() {
        Intent intent = new Intent(this, LocationService.class);
        stopService(intent);
    }

    private void getLocation() {
        mResultTv.setText("Hello");
        mLocationManager.start();
    }

    private static class LocationHandler extends Handler {

        private WeakReference<MainActivity> mTarget;

        public LocationHandler(MainActivity activity) {
            mTarget = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            MainActivity activity = mTarget.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            switch (msg.what) {
                case 1:
                    activity.mResultTv.setText((String)msg.obj);
                    String mtype = android.os.Build.MODEL; // 手机型号
                    String mtyb = android.os.Build.BRAND;//手机品牌
                    AVObject result = new AVObject("Location");
                    result.put("MODEL", mtype);
                    result.put("BRAND", mtyb);
                    result.put("location", (String)msg.obj);
                    result.saveInBackground();
                    break;
                default:
                    break;
            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationManager.stop();
    }

    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
			 */
            // 读写权限
            addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            // 读取电话状态权限
            addPermission(permissions, Manifest.permission.READ_PHONE_STATE);

            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }

        } else {
            return true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
