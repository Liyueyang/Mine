package cn.lyy.findyou.utils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

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
