package cn.lyy.findyou.location;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;

import java.lang.ref.WeakReference;
import java.util.List;

import cn.lyy.findyou.core.Action;
import cn.lyy.findyou.utils.LocationConfig;

/**
 * Created by liyy on 06-20.
 */
public class LocationManager implements BDLocationListener {

    private static volatile LocationManager mInstance = null;
    private LocationClient mLocationClient = null;
    private Handler mHandler;

    public static LocationManager getInstance(Context context, Handler handler) {
        if (mInstance == null) {
            synchronized (LocationManager.class) {
                if (mInstance == null) {
                    mInstance = new LocationManager(context.getApplicationContext(), handler);
                }
            }
        }

        return mInstance;
    }

    public LocationManager(Context context, Handler handler) {
        mLocationClient = new LocationClient(context.getApplicationContext());
        mLocationClient.registerLocationListener(this);
        mHandler = handler;
        initLocation();
    }

    public void start() {
        if (mLocationClient != null) {
            mLocationClient.start();
        }
    }

    public void stop() {
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
    }

    @Override
    public void onReceiveLocation(BDLocation location) {
        //Receive Location
        StringBuffer sb;
        sb = new StringBuffer(256);
        sb.append("纬度 : ");
        sb.append(location.getLatitude());
        sb.append("\n经度 : ");
        sb.append(location.getLongitude());
        sb.append("\n定位精度 : ");
        sb.append(location.getRadius());
        if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
            sb.append("\n速度 : ");
            sb.append(location.getSpeed());// 单位：公里每小时
            sb.append("\nGPS锁定用的卫星数 : ");
            sb.append(location.getSatelliteNumber());
            sb.append("\n高度 : ");
            sb.append(location.getAltitude());// 单位：米
            sb.append("\n行进的方向 : ");
            sb.append(location.getDirection());// 单位度
            sb.append("\n详细地址信息 : ");
            sb.append(location.getAddrStr());
            sb.append("\n定位方式 : ");
            sb.append("gps定位成功");

        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
            sb.append("\n详细地址信息 : ");
            sb.append(location.getAddrStr());
            //运营商信息
            sb.append("\n运营商信息 : ");
            sb.append(location.getOperators());
            sb.append("\n定位方式 : ");
            sb.append("网络定位成功");
        } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
            sb.append("\n定位方式 : ");
            sb.append("离线定位成功，离线定位结果也是有效的");
        } else if (location.getLocType() == BDLocation.TypeServerError) {
            sb.append("\n定位方式 : ");
            sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
        } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
            sb.append("\n定位方式 : ");
            sb.append("网络不同导致定位失败，请检查网络是否通畅");
        } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
            sb.append("\n定位方式 : ");
            sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
        }
        sb.append("\n位置语义化信息 : ");
        sb.append(location.getLocationDescribe());// 位置语义化信息
//        List<Poi> list = location.getPoiList();// POI数据
//        if (list != null) {
//            for (Poi p : list) {
//                sb.append("\npoi= : ");
//                sb.append(p.getName() + " " + p.getRank());
//            }
//        }
        Log.i("LocationManager", sb.toString());
        stop();
        mHandler.obtainMessage(1, sb.toString()).sendToTarget();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(LocationConfig.LOCATIONSCANSPAN);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
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

}
