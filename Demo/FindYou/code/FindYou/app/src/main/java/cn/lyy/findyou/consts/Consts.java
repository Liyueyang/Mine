package cn.lyy.findyou.consts;

/**
 * Created by Administrator on 06-30.
 */
public class Consts {

    /**
     * 自定义定时广播Action
     */
    public static final String CUSTOM_BROADCAST_ACTION = "cn.lyy.findyou.action_find_you";

    /**
     * start Service Extra Name
     */
    public static final String EXTRA_START_SERVICE_ACTION_NAME = "EXTRA_START_SERVICE_ACTION_NAME";

    /**
     * 存储上次提醒时间SharedPreferences
     */
    public static final String SHARED_PREFERENCES_LAST_ALARM_TIME = "shared_preferences_last_alarm_time";

    /**
     * 保存用户信息的SharedPreferences
     */
    public static final String SHARED_PREFERENCES_USER_INFO = "shared_preferences_user_info";

    /**
     * 用户推送id
     */
    public static final String USER_AVINSTALLATION_ID_PREF = "user_avinstallation_id_pref";

    /**
     * 上次提醒的时间(ms)
     */
    public static final String LAST_ALARM_TIME_KEY_SP = "LAST_ALARM_TIME";

    /**
     * 时间间隔(ms)
     */
    public static final long ALARM_INTERVAL_MILLIS = 30 * 60 * 1000;
}
