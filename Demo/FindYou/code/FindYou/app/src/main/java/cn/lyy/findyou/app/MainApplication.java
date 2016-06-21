package cn.lyy.findyou.app;

import android.app.Application;
import android.os.Debug;
import android.telephony.TelephonyManager;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;

import cn.lyy.findyou.R;

/**
 * Created by Administrator on 06-20.
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
            AVOSCloud.initialize(this, "G6hj8Xl7mHK60Y3XUBDdVU7Q-gzGzoHsz", "HFLEwtXwMKFuFXouQfQXluw2");
            AVObject phoneInfo = new AVObject(getString(R.string.phone_info));
            TelephonyManager mTm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
            String mtype = android.os.Build.MODEL; // 手机型号
            String mtyb = android.os.Build.BRAND;//手机品牌
            phoneInfo.put("MODEL", mtype);
            phoneInfo.put("BRAND", mtyb);
            phoneInfo.saveInBackground();
    }
}
