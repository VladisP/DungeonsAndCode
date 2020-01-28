package ru.iu9.game.dungeonsandcode.log;

import android.util.Log;

public class Logger {

    private static final String LOG_TAG = "DnC_Log_Tag";

    public static void log(String msg) {
        Log.d(LOG_TAG, msg);
    }
}
