package com.paricio.livestreamhighlights.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.paricio.livestreamhighlights.Model.Clip;
import com.paricio.livestreamhighlights.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ClipUtils {

    public static final String KEY_QUALITY = "com.paricio.livestreamhighlights.quality";

    public static final String LOW_QUALITY = "360p";
    public static final String MEDIUM_QUALITY = "480p";
    public static final String HIGH_QUALITY = "720p";


    public static String clipDurationFormat(float seconds) {
        float milis = seconds*1000;
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss", Locale.getDefault());
        return formatter.format(milis);
    }
}

