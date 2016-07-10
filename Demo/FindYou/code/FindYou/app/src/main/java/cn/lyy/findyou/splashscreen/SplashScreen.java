package cn.lyy.findyou.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import cn.lyy.findyou.R;
import cn.lyy.findyou.activity.MainActivity;
import cn.lyy.findyou.utils.BaseActivity;

/**
 * Created by liyy on 07-09.
 */
public class SplashScreen extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(SplashScreen.this, MainActivity.class);
                SplashScreen.this.startActivity(intent);
                SplashScreen.this.finish();
            }
        }, 3000);
    }
}
