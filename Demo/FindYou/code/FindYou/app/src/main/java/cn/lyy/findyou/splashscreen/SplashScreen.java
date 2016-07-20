package cn.lyy.findyou.splashscreen;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import cn.lyy.findyou.R;
import cn.lyy.findyou.activity.MainActivity;
import cn.lyy.findyou.service.LocationService;
import cn.lyy.findyou.utils.BaseActivity;
import cn.lyy.findyou.utils.ServiceUtils;

/**
 * Created by liyy on 07-09.
 */
public class SplashScreen extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean mServiceIsWork = ServiceUtils.isServiceWork(this, LocationService.class.getName());
        if (!mServiceIsWork) {
            setContentView(R.layout.activity_splash_screen);
            ImageView startIv = (ImageView) findViewById(R.id.start_imageview);

            Animator anim = AnimatorInflater.loadAnimator(this, R.anim.splash_screen_animation);
            anim.setTarget(startIv);
            anim.setDuration(1500);
            anim.start();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startMainActivity();
                }
            }, 1500);
        } else {
            startMainActivity();
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent();
        intent.setClass(SplashScreen.this, MainActivity.class);
        SplashScreen.this.startActivity(intent);
        SplashScreen.this.finish();
    }
}
