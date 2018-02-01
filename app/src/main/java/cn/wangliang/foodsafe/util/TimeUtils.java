package cn.wangliang.foodsafe.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by wangliang on 2018/1/31.
 */

public class TimeUtils {

    private static SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.CHINA);

    public static String format(long time) {
        String format = mDateFormat.format(new Date(time));
        return format;
    }
}
