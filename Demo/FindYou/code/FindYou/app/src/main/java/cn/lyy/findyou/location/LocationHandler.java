package cn.lyy.findyou.location;

import android.os.Handler;
import android.os.Message;

import com.avos.avoscloud.AVObject;

/**
 * Created by Administrator on 06-23.
 */
public class LocationHandler extends Handler {

    public LocationHandler() {
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        switch (msg.what) {
            case 1:
                String mtype = android.os.Build.MODEL; // 手机型号
                String mtyb = android.os.Build.BRAND;//手机品牌
                AVObject result = new AVObject("Location");
                result.put("MODEL", mtype);
                result.put("BRAND", mtyb);
                result.put("location", (String) msg.obj);
                result.saveInBackground();
                break;
            default:
                break;
        }

    }
}