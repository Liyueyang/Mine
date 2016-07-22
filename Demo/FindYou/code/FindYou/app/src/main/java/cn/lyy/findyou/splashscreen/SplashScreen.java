package cn.lyy.findyou.splashscreen;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import cn.lyy.findyou.R;
import cn.lyy.findyou.activity.MainActivity;
import cn.lyy.findyou.login.business.LoginBusiness;
import cn.lyy.findyou.login.ui.LoginActivity;
import cn.lyy.findyou.utils.BaseActivity;

/**
 * Created by liyy on 07-09.
 */
public class SplashScreen extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (LoginBusiness.checkUserLoginState()) {
            startMainActivity();
        } else {
            setContentView(R.layout.activity_splash_screen);
            ImageView startIv = (ImageView) findViewById(R.id.start_imageview);

            Animator anim = AnimatorInflater.loadAnimator(this, R.anim.splash_screen_animation);
            anim.setTarget(startIv);
            anim.setDuration(1500);
            anim.start();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startLoginActivity();
                }
            }, 1500);

        }
    }

    private void startMainActivity() {
        Intent intent = new Intent();
        intent.setClass(SplashScreen.this, MainActivity.class);
        SplashScreen.this.startActivity(intent);
        SplashScreen.this.finish();
    }

    private void startLoginActivity() {
        Intent intent = new Intent();
        intent.setClass(SplashScreen.this, LoginActivity.class);
        SplashScreen.this.startActivity(intent);
        SplashScreen.this.finish();
    }
}
