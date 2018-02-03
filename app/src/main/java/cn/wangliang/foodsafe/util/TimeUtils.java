package cn.wangliang.foodsafe.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by wangliang on 2018/1/31.
 */

public class TimeUtils {

    private static SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.CHINA);
    private static SimpleDateFormat mDateFormat2 = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
    private static SimpleDateFormat mDateFormat3 = new SimpleDateFormat("yyyy", Locale.CHINA);

    public static String format(long time) {
        String format = mDateFormat.format(new Date(time));
        return format;
    }

    public static String format2(long time) {
        String format = mDateFormat2.format(new Date(time));
        return format;
    }
    public static String format3(long time) {
        String format = mDateFormat3.format(new Date(time));
        return format;
    }
}
