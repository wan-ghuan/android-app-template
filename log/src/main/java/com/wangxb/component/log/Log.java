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
 * 2022/03/10	         Alex           	    Create
 * ===========================================================================================
 */

package com.wangxb.component.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;

/**
 * Mock Log implementation for testing on non android host.
 */
public final class Log {

    private Log() {
    }

    /**
     * Send a {@link LogManager#VERBOSE} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    @SuppressWarnings("unused")
    public static void v(String tag, String msg) {
        println(LogManager.VERBOSE, tag, msg);
    }

    /**
     * Send a {@link LogManager#VERBOSE} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    @SuppressWarnings("unused")
    public static void v(String tag, String msg, Throwable tr) {
        println(LogManager.VERBOSE, tag, msg + '\n' + getStackTraceString(tr));
    }

    /**
     * Send a {@link LogManager#DEBUG} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    @SuppressWarnings("unused")
    public static void d(String tag, String msg) {
        println(LogManager.DEBUG, tag, msg);
    }

    /**
     * Send a {@link LogManager#DEBUG} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    @SuppressWarnings("unused")
    public static void d(String tag, String msg, Throwable tr) {
        println(LogManager.DEBUG, tag, msg + '\n' + getStackTraceString(tr));
    }

    /**
     * Send an {@link LogManager#INFO} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    @SuppressWarnings("unused")
    public static void i(String tag, String msg) {
        println(LogManager.INFO, tag, msg);
    }

    /**
     * Send a {@link LogManager#INFO} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    @SuppressWarnings("unused")
    public static void i(String tag, String msg, Throwable tr) {
        println(LogManager.INFO, tag, msg + '\n' + getStackTraceString(tr));
    }

    /**
     * Send a {@link LogManager#WARN} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    @SuppressWarnings("unused")
    public static void w(String tag, String msg) {
        println(LogManager.WARN, tag, msg);
    }

    /**
     * Send a {@link LogManager#WARN} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    @SuppressWarnings("unused")
    public static void w(String tag, String msg, Throwable tr) {
        println(LogManager.WARN, tag, msg + '\n' + getStackTraceString(tr));
    }

    /**
     * Send a {@link LogManager#WARN} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param tr An exception to log
     */
    @SuppressWarnings("unused")
    public static void w(String tag, Throwable tr) {
        println(LogManager.WARN, tag, getStackTraceString(tr));
    }

    /**
     * Send an {@link LogManager#ERROR} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    @SuppressWarnings("unused")
    public static void e(String tag, String msg) {
        println(LogManager.ERROR, tag, msg);
    }

    /**
     * Send a {@link LogManager#ERROR} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    @SuppressWarnings("unused")
    public static void e(String tag, String msg, Throwable tr) {
        println(LogManager.ERROR, tag, msg + '\n' + getStackTraceString(tr));
    }

    /**
     * Handy function to get a loggable stack trace from a Throwable
     * @param tr An exception to log
     */
    public static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }

        // This is to reduce the amount of log spew that apps do in the non-error
        // condition of the network being unavailable.
        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    /**
     * Low-level logging call.
     * @param priority The priority/type of this log message
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void println(int priority, String tag, String msg) {
        Logger logger = XLog.tag(tag)
                            .build();
        logger.log(priority, msg);
    }
}
