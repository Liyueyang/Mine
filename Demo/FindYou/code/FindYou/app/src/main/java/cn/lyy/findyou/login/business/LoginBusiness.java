package cn.lyy.findyou.login.business;

import android.content.Context;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;

import cn.lyy.findyou.core.Action;
import cn.lyy.findyou.utils.ServiceUtils;

/**
 * Created by Administrator on 07-22.
 */
public class LoginBusiness {

    public static boolean checkServiceIsWork(Context context, String serviceName) {
        return ServiceUtils.isServiceWork(context, serviceName);
    }

    public static boolean checkUserLoginState() {

        return false;
    }

    public static void registerUser(AVUser user, final Action.One<Boolean> action) {
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    action.invoke(true);
                } else {
                    action.invoke(false);
                }
            }
        });
    }

    public static void login(String userName, String passwork, final Action.One<Boolean> action) {
        AVUser.logInInBackground(userName, passwork, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser user, AVException e) {
                if (e == null) {
                    action.invoke(true);
                } else {
                    action.invoke(false);
                }
            }
        });
    }

}
