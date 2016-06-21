package cn.lyy.findyou.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.baidu.location.BDLocation;

import java.util.ArrayList;

import cn.lyy.findyou.R;
import cn.lyy.findyou.core.Action;
import cn.lyy.findyou.location.LocationManager;
import cn.lyy.findyou.utils.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private final int SDK_PERMISSION_REQUEST = 127;
    private TextView mResultTv;
    private Button mGetLocationBtn;
    private LocationManager mLocationManager;
    private String permissionInfo;
    private Action.Two<BDLocation, String> mAction;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        super.initData();
        mLocationManager = new LocationManager(this);
        getPersimmions();
    }

    @Override
    public void findViewById() {
        super.findViewById();
        mResultTv = (TextView) findViewById(R.id.result_tv);
        mGetLocationBtn = (Button) findViewById(R.id.button);
    }

    @Override
    public void addListener() {
        super.addListener();
        mGetLocationBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                getLocation();
                break;
            default:
                break;
        }
    }

    private void getLocation() {
        mResultTv.setText("Hello");
        mAction = new Action().new Two<BDLocation, String>() {
            @Override
            public void invoke(BDLocation arg1, String resultStr) {
                mResultTv.setText(resultStr);
                String mtype = android.os.Build.MODEL; // 手机型号
                String mtyb = android.os.Build.BRAND;//手机品牌
                AVObject result = new AVObject("Location");
                result.put("MODEL", mtype);
                result.put("BRAND", mtyb);
                result.put("location", resultStr);
                result.saveInBackground();
            }
        };
        mLocationManager.start(mAction);
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
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            // 读取电话状态权限
            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
            }

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
