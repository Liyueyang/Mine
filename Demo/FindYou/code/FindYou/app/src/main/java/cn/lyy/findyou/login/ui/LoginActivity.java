package cn.lyy.findyou.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.lyy.findyou.R;
import cn.lyy.findyou.activity.MainActivity;
import cn.lyy.findyou.core.Action;
import cn.lyy.findyou.login.business.LoginBusiness;
import cn.lyy.findyou.utils.BaseActivity;
import cn.lyy.findyou.utils.Utils;
import cn.lyy.findyou.widget.navigationview.CustomNavigationView;

/**
 * Created by Administrator on 07-21.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener,
        CustomNavigationView.OnNavigationLeftImageViewListener,
        CustomNavigationView.OnNavigationRightButtonListener {

    private CustomNavigationView mTopNav;
    private EditText mUserNameEt;
    private EditText mUserPasswordEt;
    private Button mLoginBtn;
    private Action.One<Boolean> mLoginAction;

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
        mTopNav = (CustomNavigationView) findViewById(R.id.top_nav);
        mUserNameEt = (EditText) findViewById(R.id.user_name_et);
        mUserPasswordEt = (EditText) findViewById(R.id.user_password_et);
        mLoginBtn = (Button) findViewById(R.id.login_btn);
    }

    private void addListener() {
        mTopNav.setOnNavigationLeftImageViewListener(this);
        mTopNav.setOnNavigationRightButtonListener(this);
        mLoginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                login();
                break;
        }
    }

    private void login() {
        String userAccount = mUserNameEt.getText().toString().trim();
        String userPassword = mUserPasswordEt.getText().toString().trim();
        if (Utils.isNullOrEmpty(userAccount)) {
            Toast.makeText(this, getString(R.string.user_account_not_null), Toast.LENGTH_SHORT).show();
            return;
        } else if (Utils.isNullOrEmpty(userPassword)) {
            Toast.makeText(this, getString(R.string.user_password_not_null), Toast.LENGTH_SHORT).show();
            return;
        }
        mLoginAction = new Action().new One<Boolean>() {
            @Override
            public void invoke(Boolean success) {
                if (success) {
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(intent);
                    LoginActivity.this.finish();
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.user_login_error_text), Toast.LENGTH_SHORT).show();
                }
            }
        };
        LoginBusiness.login(LoginActivity.this, userAccount, userPassword, mLoginAction);
    }

    @Override
    public void onLeftImageView(View v) {
        finish();
    }

    @Override
    public void onRightButton(View v) {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, RegisterActivity.class);
        LoginActivity.this.startActivity(intent);
    }
}
