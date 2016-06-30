package cn.lyy.findyou.consts;

/**
 * Created by Administrator on 06-30.
 */
public class Consts {

    /**
     * 自定义定时广播Action
     */
    public static final String CUSTOM_BROADCAST_ACTION = "ACTION_FIND_YOU";

    /**
     * 存储上次提醒时间SharedPreferences
     */
    public static final String SHARED_PREFERENCES_LAST_ALARM_TIME = "shared_preferences_last_alarm_time";

    /**
     * 上次提醒的时间(ms)
     */
    public static final String LAST_ALARM_TIME_KEY_SP = "LAST_ALARM_TIME";

    /**
     * 时间间隔(ms)
     */
    public static final long ALARM_INTERVAL_MILLIS = 30 * 60 * 1000;
}
