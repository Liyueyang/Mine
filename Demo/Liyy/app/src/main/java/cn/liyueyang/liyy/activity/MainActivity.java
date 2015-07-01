package cn.liyueyang.liyy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.liyueyang.liyy.R;
import cn.liyueyang.liyy.base.BaseActivity;
import cn.liyueyang.liyy.slidedelete.SlideDeleteDemo;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.animation_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.animation_button:
                intent = new Intent(MainActivity.this, SecondActivity.class);
                break;
            case R.id.slide_delete:
                intent = new Intent(MainActivity.this, SlideDeleteDemo.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
