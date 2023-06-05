package com.tools.redis.log.logs;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DataFormatBuilder {

    /**
     * 根据传进来的时间戳 获取对应的时间格式
     *
     * @param format 时间格式
     * @param stamp  时间戳
     * @return
     */
    public static String getTimeStampFormat(String format, Long stamp) {
        if (stamp == null) {
            return null;
        }
        if (format == null) {
            format = "yyyy-MM-dd HH:mm:ss/SSS";
        }
        SimpleDateFormat df = new SimpleDateFormat(format);//设置日期格式
        return df.format(stamp);//传进来的时间戳为获取当前系统时间
    }
}
