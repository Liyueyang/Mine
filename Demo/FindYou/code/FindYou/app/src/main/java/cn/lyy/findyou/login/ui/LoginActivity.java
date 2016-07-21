package cn.lyy.findyou.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.lyy.findyou.R;
import cn.lyy.findyou.utils.BaseActivity;

/**
 * Created by Administrator on 07-21.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText mUserNameEt;
    private EditText mUserPasswordEt;
    private Button mLoginBtn;
    private Button mRegisterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById();

        addListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void findViewById() {
        mUserNameEt = (EditText) findViewById(R.id.user_name_et);
        mUserPasswordEt = (EditText) findViewById(R.id.user_password_et);
        mLoginBtn = (Button) findViewById(R.id.login_btn);
        mRegisterBtn = (Button) findViewById(R.id.register_btn);
    }

    private void addListener() {
        mLoginBtn.setOnClickListener(this);
        mRegisterBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:

                break;
            case R.id.register_btn:
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
                break;
        }
    }
}
