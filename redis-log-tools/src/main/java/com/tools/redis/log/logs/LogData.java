package com.tools.redis.log.logs;

import ch.qos.logback.classic.spi.LoggingEvent;
import lombok.Data;

/**
 * Created by yuyu on 2018/3/15.
 * 用于保存日志数据
 */
@Data
public class LogData {

    private String message;//日志的信息
    private String loggerTime;//上下文产生时间
    private String loggerName;//记录器名称
    private String threadName;//线程名称
    private String happenStamp;//日志发生时间

    public LogData(LoggingEvent loggingEvent) {

        this.message = loggingEvent.toString();
        this.loggerTime = DataFormatBuilder.getTimeStampFormat(null,
                loggingEvent.getContextBirthTime());
        this.loggerName = loggingEvent.getLoggerName();
        this.threadName = loggingEvent.getThreadName();
        this.happenStamp = DataFormatBuilder.getTimeStampFormat(null,
                loggingEvent.getTimeStamp());
    }
}