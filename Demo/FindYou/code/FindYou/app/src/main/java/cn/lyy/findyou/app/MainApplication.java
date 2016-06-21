package cn.lyy.findyou.app;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by Administrator on 06-20.
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
            AVOSCloud.initialize(this, "G6hj8Xl7mHK60Y3XUBDdVU7Q-gzGzoHsz", "HFLEwtXwMKFuFXouQfQXluw2");
    }
}
