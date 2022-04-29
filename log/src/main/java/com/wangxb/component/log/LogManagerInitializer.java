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
 * 2022/03/23	         Alex           	    Create
 * ===========================================================================================
 */

package com.wangxb.component.log;

import java.io.File;
import java.util.Collections;
import java.util.List;

import com.wangxb.component.log.BuildConfig;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

public class LogManagerInitializer implements Initializer<LogManager> {
    @NonNull
    @Override
    public LogManager create(@NonNull Context context) {
        final String EXTERNAL_STORAGE_DIRECTORY = context.getFilesDir().getAbsolutePath();
        final String LOG_DIRECTORY = "log";

        String logDirectory = EXTERNAL_STORAGE_DIRECTORY + File.separator + LOG_DIRECTORY;
        int logLevel = BuildConfig.DEBUG ? LogManager.ALL : LogManager.WARN;
        LogManager.initialize(logLevel, logDirectory);
        return null;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}
