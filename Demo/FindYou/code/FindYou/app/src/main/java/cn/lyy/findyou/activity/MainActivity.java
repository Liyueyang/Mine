package cn.lyy.findyou.activity;

import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import cn.lyy.findyou.R;
import cn.lyy.findyou.location.LocationListener;
import cn.lyy.findyou.utils.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener, LocationListener.LocationCallBack {

    private TextView mResultTv;
    private Button mGetLocationBtn;
    private LocationClient mLocationClient = null;
    private LocationListener mLocationListener;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        super.initData();
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationListener = new LocationListener();
        mLocationListener.setLocationCallBack(this);
        mLocationClient.registerLocationListener(mLocationListener);
        initLocation();
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
        mLocationClient.start();
    }


    @Override
    public void returnLocationResult(BDLocation location, String resultStr) {
        mResultTv.setText(resultStr);
        mLocationClient.stop();
        String mtype = android.os.Build.MODEL; // 手机型号
        String mtyb = android.os.Build.BRAND;//手机品牌
        AVObject result = new AVObject("Location");
        result.put("MODEL", mtype);
        result.put("BRAND", mtyb);
        result.put("location", resultStr);
        result.saveInBackground();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationClient.stop();
    }
}
