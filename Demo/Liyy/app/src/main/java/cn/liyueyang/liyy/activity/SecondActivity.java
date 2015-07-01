package cn.liyueyang.liyy.activity;

import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;

import cn.liyueyang.liyy.R;
import cn.liyueyang.liyy.base.BaseActivity;

public class SecondActivity extends BaseActivity {

    private RelativeLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        root = (RelativeLayout) findViewById(R.id.root);

        if (savedInstanceState == null) {
            root.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    root.getViewTreeObserver().removeOnPreDrawListener(this);
                    root.setScaleY(0.1f);
                    root.setPivotY(root.getY() + root.getHeight() / 2);

                    root.animate().scaleY(1).setDuration(1000).setInterpolator(new AccelerateInterpolator()).start();
                    return true;
                }
            });
        }
    }
}
