package cn.lyy.findyou.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.baidu.location.BDLocation;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import cn.lyy.findyou.R;
import cn.lyy.findyou.location.LocationManager;
import cn.lyy.findyou.service.LocationService;
import cn.lyy.findyou.utils.BaseActivity;

public class MainActivity extends BaseActivity {

    private final int SDK_PERMISSION_REQUEST = 127;
    private TextView mMyLocationTv;
    private MyLocationHandler mMyLocationHandler;
    private LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPersimmions();
        setContentView(R.layout.activity_main);

        mMyLocationTv = (TextView) findViewById(R.id.my_location_text_view);
//        final PullToRefreshView mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
//        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                mPullToRefreshView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mPullToRefreshView.setRefreshing(false);
//                    }
//                }, 2000);
//            }
//        });
        mMyLocationHandler = new MyLocationHandler(this);
        mLocationManager = LocationManager.getInstance(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocationManager.setHandler(mMyLocationHandler);
        mLocationManager.start();
    }

    private void startLocationService() {
        Intent intent = new Intent(this, LocationService.class);
        intent.setAction("App start");
        startService(intent);
    }

    private static class MyLocationHandler extends Handler {

        private WeakReference<BaseActivity> mTag;

        public MyLocationHandler(BaseActivity activity) {
            mTag = new WeakReference<BaseActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    MainActivity activity = (MainActivity) mTag.get();
                    if (activity == null || activity.isFinishing()) {
                        return;
                    }
                    BDLocation bdLocation = (BDLocation) msg.obj;
                    activity.mMyLocationTv.setText(bdLocation.getAddrStr());
                    break;
            }
        }
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
