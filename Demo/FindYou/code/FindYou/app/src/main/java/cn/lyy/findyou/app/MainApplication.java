package cn.lyy.findyou.app;

import android.app.Application;
import android.content.SharedPreferences;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SaveCallback;

import cn.lyy.findyou.activity.MainActivity;
import cn.lyy.findyou.consts.Consts;

/**
 * Created by Administrator on 06-20.
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initLeanCloud();
    }

    private void initLeanCloud() {
        AVOSCloud.initialize(this, "G6hj8Xl7mHK60Y3XUBDdVU7Q-gzGzoHsz", "HFLEwtXwMKFuFXouQfQXluw2");
        AVInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    String installationId = AVInstallation.getCurrentInstallation().getInstallationId();
                    SharedPreferences.Editor editor = getSharedPreferences(Consts.SHARED_PREFERENCES_USER_INFO, MODE_PRIVATE).edit();
                    editor.putString(Consts.USER_AVINSTALLATION_ID_PREF, installationId);
                    editor.commit();
                    PushService.setDefaultPushCallback(MainApplication.this, MainActivity.class);
                }
            }
        });
    }
}
