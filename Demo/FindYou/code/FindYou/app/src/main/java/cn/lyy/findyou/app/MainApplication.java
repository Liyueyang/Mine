package cn.lyy.findyou.app;

import android.app.Application;
import android.content.SharedPreferences;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;

import cn.lyy.findyou.activity.MainActivity;
import cn.lyy.findyou.consts.Consts;

/**
 * Created by Administrator on 06-20.
 */
public class MainApplication extends Application {

    private String mInstallationId;

    @Override
    public void onCreate() {
        super.onCreate();
        initLeanCloud();

//        AVIMClient.setClientEventHandler(new AVIMClientEventHandler() {
//            @Override
//            public void onConnectionPaused(AVIMClient avimClient) {
//
//            }
//
//            @Override
//            public void onConnectionResume(AVIMClient avimClient) {
//
//            }
//
//            @Override
//            public void onClientOffline(AVIMClient avimClient, int i) {
//                 // 接收单点登录返回强制下线消息
//            }
//        });
    }

    private void initLeanCloud() {
        AVOSCloud.initialize(this, "G6hj8Xl7mHK60Y3XUBDdVU7Q-gzGzoHsz", "HFLEwtXwMKFuFXouQfQXluw2");
        AVInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    String installationId = AVInstallation.getCurrentInstallation().getInstallationId();
                    saveInstallationId(installationId);
                    PushService.setDefaultPushCallback(MainApplication.this, MainActivity.class);
                }
            }
        });
    }

    public void saveInstallationId(String installationId) {
        mInstallationId = installationId;
        SharedPreferences.Editor editor = getSharedPreferences(Consts.SHARED_PREFERENCES_USER_INFO, MODE_PRIVATE).edit();
        editor.putString(Consts.USER_AVINSTALLATION_ID_PREF, installationId);
        editor.commit();
    }

    public String getInstallationId() {
        return mInstallationId;
    }
}
