package cn.lyy.findyou.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import cn.lyy.findyou.R;
import cn.lyy.findyou.service.LocationService;
import cn.lyy.findyou.utils.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private final int SDK_PERMISSION_REQUEST = 127;
    private Button mGetLocationBtn;
    private Button mStartServiceBtn;
    private Button mStopServiceBtn;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        super.initData();
        getPersimmions();
//        startLocationService(); // TODO 暂时不设置启动界面获取位置信息
    }

    @Override
    public void findViewById() {
        super.findViewById();
        mGetLocationBtn = (Button) findViewById(R.id.button);
        mStartServiceBtn = (Button) findViewById(R.id.start_service_button);
        mStopServiceBtn = (Button) findViewById(R.id.stop_service_button);
    }

    @Override
    public void addListener() {
        super.addListener();
        mGetLocationBtn.setOnClickListener(this);
        mStartServiceBtn.setOnClickListener(this);
        mStopServiceBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_service_button:
                startLocationService();
                break;
            case R.id.stop_service_button:
                stopLocationService();
                break;
            default:
                break;
        }
    }

    private void startLocationService() {
        Intent intent = new Intent(this, LocationService.class);
        startService(intent);
    }

    private void stopLocationService() {
        Intent intent = new Intent(this, LocationService.class);
        stopService(intent);
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
