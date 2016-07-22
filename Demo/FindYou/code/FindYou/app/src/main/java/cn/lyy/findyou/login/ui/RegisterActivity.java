package cn.lyy.findyou.login.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;

import cn.lyy.findyou.R;
import cn.lyy.findyou.consts.UserConsts;
import cn.lyy.findyou.core.Action;
import cn.lyy.findyou.login.business.LoginBusiness;
import cn.lyy.findyou.utils.BaseActivity;
import cn.lyy.findyou.utils.Utils;
import cn.lyy.findyou.widget.navigationview.CustomNavigationView;

/**
 * Created by Administrator on 07-21.
 */
public class RegisterActivity extends BaseActivity implements CustomNavigationView.OnNavigationLeftImageViewListener, CustomNavigationView.OnNavigationRightImageViewListener {

    private CustomNavigationView mTopNav;
    private EditText mEtUserAccount;
    private EditText mEtUserNickName;
    private EditText mEtUserPassword;
    private EditText mEtUserPasswordConfirm;
    private EditText mEtUserPhoneNumber;
    private AVUser mUser;
    private Action.One<Boolean> mRegisterAction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById();

        addListener();
    }

    private void findViewById() {
        mTopNav = (CustomNavigationView) findViewById(R.id.top_nav);
        mEtUserAccount = (EditText) findViewById(R.id.et_user_account);
        mEtUserNickName = (EditText) findViewById(R.id.et_user_nick_name);
        mEtUserPassword = (EditText) findViewById(R.id.et_user_password);
        mEtUserPasswordConfirm = (EditText) findViewById(R.id.et_user_password_confirm);
        mEtUserPhoneNumber = (EditText) findViewById(R.id.et_user_phone_number);
    }

    private void addListener() {
        mTopNav.setOnNavigationLeftImageViewListener(this);
        mTopNav.setOnNavigationRightImageViewListener(this);
    }

    private boolean checkInputInfoIsRight() {
        mUser = new AVUser();
        String userAccount = mEtUserAccount.getText().toString().trim();
        if (Utils.isNullOrEmpty(userAccount)) {
            Toast.makeText(this, getString(R.string.user_account_not_null), Toast.LENGTH_SHORT).show();
            return false;
        }
        mUser.setUsername(userAccount);

        String userNickName = mEtUserNickName.getText().toString().trim();
        if (Utils.isNullOrEmpty(userNickName)) {
            Toast.makeText(this, getString(R.string.user_account_not_null), Toast.LENGTH_SHORT).show();
            return false;
        }
        mUser.put(UserConsts.USER_NAME, userNickName);

        String userPassword = mEtUserPassword.getText().toString().trim();
        if (Utils.isNullOrEmpty(userPassword)) {
            Toast.makeText(this, getString(R.string.user_account_not_null), Toast.LENGTH_SHORT).show();
            return false;
        }
        String userPasswordConfirm = mEtUserPasswordConfirm.getText().toString().trim();
        if (Utils.isNullOrEmpty(userPasswordConfirm)) {
            Toast.makeText(this, getString(R.string.user_account_not_null), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!mEtUserPassword.getText().toString().trim().equals(mEtUserPasswordConfirm.getText().toString().trim())) {
            Toast.makeText(this, getString(R.string.user_password_not_same), Toast.LENGTH_SHORT).show();
            return false;
        }
        mUser.setPassword(userPassword);

        String userPhoneNumber = mEtUserPhoneNumber.getText().toString().trim();
        if (Utils.isNullOrEmpty(userPhoneNumber)) {
            Toast.makeText(this, getString(R.string.user_account_not_null), Toast.LENGTH_SHORT).show();
            return false;
        }
        mUser.setMobilePhoneNumber(userPhoneNumber);
        mUser.put(UserConsts.USER_PHONE_MODEL, android.os.Build.MODEL);
        mUser.put(UserConsts.USER_PHONE_BRAND, android.os.Build.BRAND);
        return true;
    }

    @Override
    public void onLeftImageView(View v) {
        RegisterActivity.this.finish();
    }

    @Override
    public void onRightImageView(View v) {
        if (checkInputInfoIsRight()) {
            mRegisterAction = new Action().new One<Boolean>() {
                @Override
                public void invoke(Boolean success) {
                    if (success) {
                        Log.e("Register", "success");
                    } else {
                        Log.e("Register", "false");
                    }
                }
            };
//            LoginBusiness.checkUserIsExist(mUser.getUserAccount());
            LoginBusiness.registerUser(mUser, mRegisterAction);
        }
    }
}
