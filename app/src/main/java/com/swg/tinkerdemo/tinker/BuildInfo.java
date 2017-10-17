package com.swg.tinkerdemo.tinker;

import com.swg.tinkerdemo.BuildConfig;

/**
 * Created by swg on 2017/10/17.
 */

public class BuildInfo {

    /**
     * they are not final, so they won't change with the BuildConfig values!
     */
    public static boolean DEBUG        = BuildConfig.DEBUG;
    public static String VERSION_NAME = BuildConfig.VERSION_NAME;
    public static int     VERSION_CODE = BuildConfig.VERSION_CODE;

    public static String MESSAGE       = BuildConfig.MESSAGE;
    public static String TINKER_ID     = BuildConfig.TINKER_ID;
    public static String PLATFORM      = BuildConfig.PLATFORM;

}
