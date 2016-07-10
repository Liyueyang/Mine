package cn.lyy.findyou.entity;

/**
 * 用户类
 * Created by liyy on 07-09.
 */
public class User {
    private String userAccount;
    private String userName;
    private String userPassword;
    private String userInstallationId;
    private String userPhoneNumber;
    private String userPhoneModel;
    private String userPhoneBrand;

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserInstallationId() {
        return userInstallationId;
    }

    public void setUserInstallationId(String userInstallationId) {
        this.userInstallationId = userInstallationId;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserPhoneModel() {
        return userPhoneModel;
    }

    public void setUserPhoneModel(String userPhoneModel) {
        this.userPhoneModel = userPhoneModel;
    }

    public String getUserPhoneBrand() {
        return userPhoneBrand;
    }

    public void setUserPhoneBrand(String userPhoneBrand) {
        this.userPhoneBrand = userPhoneBrand;
    }
}
