package cn.lyy.findyou.utils;

import android.app.Activity;
import android.os.Bundle;

public abstract class BaseActivity extends Activity {

    public abstract int getContentViewId();

    public void initData() {
    }

    public void findViewById() {
    }

    public void refreshView() {
    }

    public void addListener() {
    }

    public void onResumeView() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        initData();
        findViewById();
        refreshView();
        addListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onResumeView();
    }
}
