package cn.lyy.findyou.utils;

/**
 * Created by Administrator on 07-22.
 */
public class Utils {

    /**
     * 字符串是否为{@code null}或者空字符串。
     *
     * @param s
     * @return
     */
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0;
    }
}
