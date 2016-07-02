package cn.lyy.findyou.location;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.avos.avoscloud.AVObject;

/**
 * Created by Administrator on 06-23.
 */
public class LocationHandler extends Handler {

    private Context mContext;
    private String mActionType;

    public LocationHandler(Context context, String actionType) {
        mContext = context;
        mActionType = actionType;
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
                result.put("ActionType", mActionType);
                result.put("Location", (String) msg.obj);
                result.saveInBackground();
                break;
            default:
                break;
        }

    }
}