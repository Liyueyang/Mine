package cn.lyy.findyou.login.business;

import android.content.Context;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;

import cn.lyy.findyou.app.MainApplication;
import cn.lyy.findyou.consts.UserConsts;
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
        AVUser currentUser = AVUser.getCurrentUser();
        if (currentUser != null) {
            return true;
        }
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

    public static void login(final Context context, String userName, String password, final Action.One<Boolean> action) {
        AVUser.logInInBackground(userName, password, new LogInCallback<AVUser>() {
            @Override
            public void done(final AVUser user, AVException e) {
                if (e == null) {
                    String installationId = ((MainApplication) context.getApplicationContext()).getInstallationId();
                    AVUser.getCurrentUser().put(UserConsts.USER_INSTALLATION_ID, installationId);
                    AVUser.getCurrentUser().saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                action.invoke(true);
                                // 通过clientId设置单点登录唯一tag，可以使用用户名
//                                AVIMClient currentClient = AVIMClient.getInstance(user.getUsername(),"Mobile");
//                                currentClient.open(new AVIMClientCallback() {
//                                    @Override
//                                    public void done(AVIMClient avimClient, AVIMException e) {
//                                        if(e == null){
//                                            // 与云端建立连接成功
//                                        }
//                                    }
//                                });
                            } else {
                                action.invoke(false);
                            }
                        }
                    });
                } else {
                    action.invoke(false);
                }
            }
        });
    }

}
