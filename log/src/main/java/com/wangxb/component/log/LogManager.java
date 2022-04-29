/*
 * ===========================================================================================
 * = COPYRIGHT
 *          PAX Computer Technology(Shenzhen) CO., LTD PROPRIETARY INFORMATION
 *   This software is supplied under the terms of a license agreement or nondisclosure
 *   agreement with PAX Computer Technology(Shenzhen) CO., LTD and may not be copied or
 *   disclosed except in accordance with the terms in that agreement.
 *     Copyright (C) YYYY-? PAX Computer Technology(Shenzhen) CO., LTD All rights reserved.
 * Description: // Detail description about the function of this module,
 *             // interfaces with the other modules, and dependencies.
 * Revision History:
 * Date	                 Author	                Action
 * 2022/03/09	         Alex           	    Create
 * ===========================================================================================
 */

package com.wangxb.component.log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.flattener.PatternFlattener;
import com.elvishew.xlog.formatter.border.DefaultBorderFormatter;
import com.elvishew.xlog.formatter.message.json.DefaultJsonFormatter;
import com.elvishew.xlog.formatter.message.throwable.DefaultThrowableFormatter;
import com.elvishew.xlog.formatter.message.xml.DefaultXmlFormatter;
import com.elvishew.xlog.formatter.stacktrace.DefaultStackTraceFormatter;
import com.elvishew.xlog.formatter.thread.DefaultThreadFormatter;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.backup.FileSizeBackupStrategy2;
import com.elvishew.xlog.printer.file.clean.FileLastModifiedCleanStrategy;
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator;
import com.elvishew.xlog.printer.file.writer.SimpleWriter;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;

import androidx.annotation.IntDef;
import androidx.annotation.RestrictTo;

public final class LogManager {
    /**
     * Log level for Log.v.
     */
    public static final int VERBOSE = 2;

    /**
     * Log level for Log.d.
     */
    public static final int DEBUG = 3;

    /**
     * Log level for Log.i.
     */
    public static final int INFO = 4;

    /**
     * Log level for Log.w.
     */
    public static final int WARN = 5;

    /**
     * Log level for Log.e.
     */
    public static final int ERROR = 6;

    /**
     * Log level for Log#init, printing all logs.
     */
    public static final int ALL = Integer.MIN_VALUE;

    /**
     * Log level for Log#init, printing no log.
     */
    public static final int NONE = Integer.MAX_VALUE;

    @RestrictTo(LIBRARY_GROUP)
    @IntDef({VERBOSE, DEBUG, INFO, WARN, ERROR, ALL, NONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LogLevel {}

    /**
     * Initialize log system, should be called only once.
     *
     * @param logLevel The level of this log message.
     * @param logDirectory The folder path of log file.
     */
    @SuppressWarnings("unused")
    public static void initialize(@LogLevel int logLevel, String logDirectory) {
        /*
         * XLog 配置器
         *
         * logLevel
         * 指定日志级别，低于该级别的日志将不会被打印，默认为 LogLevel.ALL
         *
         * tag
         * 指定 TAG，默认为 "X-LOG"
         *
         * enableThreadInfo
         */
        LogConfiguration config = new LogConfiguration.Builder()
                .logLevel(logLevel)
//                .enableThreadInfo()                                    // 允许打印线程信息，默认禁止
//                .enableStackTrace(2)                                   // 允许打印深度为 2 的调用栈信息，默认禁止
//                .enableBorder()                                        // 允许打印日志边框，默认禁止
                .jsonFormatter(new DefaultJsonFormatter())                  // 指定 JSON 格式化器，默认为 DefaultJsonFormatter
                .xmlFormatter(new DefaultXmlFormatter())                    // 指定 XML 格式化器，默认为 DefaultXmlFormatter
                .throwableFormatter(new DefaultThrowableFormatter())        // 指定可抛出异常格式化器，默认为 DefaultThrowableFormatter
                .threadFormatter(new DefaultThreadFormatter())              // 指定线程信息格式化器，默认为 DefaultThreadFormatter
                .stackTraceFormatter(new DefaultStackTraceFormatter())      // 指定调用栈信息格式化器，默认为 DefaultStackTraceFormatter
                .borderFormatter(new DefaultBorderFormatter())               // 指定边框格式化器，默认为 DefaultBorderFormatter
//                .addObjectFormatter(AnyClass.class, new AnyClassObjectFormatter())  // 为指定类型添加对象格式化器， 默认使用 Object.toString()
//                .addInterceptor(new BlacklistTagsFilterInterceptor("blacklist1", "blacklist2", "blacklist3"))    // 添加黑名单 TAG 过滤器
//                .addInterceptor(new MyInterceptor()) // 添加一个日志拦截器
                .build();

        /*
         * 通过 android.util.Log 打印日志的打印器
         */
        Printer androidPrinter = new AndroidPrinter(true);

        /*
         * 通过 System.out打印日志到控制台的打印器
         * Android平台不需要改打印器
         */
        // Printer consolePrinter = new ConsolePrinter();

        /*
         * 日志信心写入到文件的打印器
         *
         * logDirectory
         * 指定保存日志文件的路径
         *
         * fileNameGenerator
         * 指定日志文件名生成器，默认为 ChangelessFileNameGenerator("log")
         *
         * backupStrategy
         * 指定日志文件备份策略，默认为 new FileSizeBackupStrategy(1024 * 1024)
         *
         * cleanStrategy
         * 指定日志文件清除策略，默认为 NeverCleanStrategy()
         *
         * flattener
         * 指定日志平铺器，默认为 DefaultFlattener
         *
         * writer
         * 指定日志写入器，默认为 SimpleWriter
         *
         */
        Printer filePrinter = new FilePrinter.Builder(logDirectory)
                .fileNameGenerator(new DateFileNameGenerator())
                .backupStrategy(new FileSizeBackupStrategy2(1024 * 1024, 32))
                .cleanStrategy(new FileLastModifiedCleanStrategy(7 * 24 * 60 * 60 * 1000))
                .flattener(new PatternFlattener("{d HH:mm:ss.SSS} {L}/{t}: {m}"))
                .writer(new SimpleWriter())
                .build();

        /*
         * 初始化 XLog
         *
         * config
         * 指定日志配置，如果不指定，会默认使用 new LogConfiguration.Builder().build()
         *
         * androidPrinter, filePrinter
         * 添加任意多的日志打印器。如果没有添加，会默认使用 AndroidPrinter(Android)/ConsolePrinter(java)
         */
        XLog.init(config, androidPrinter, filePrinter /* consolePrinter, */);
    }
}
