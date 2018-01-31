package com.paricio.livestreamhighlights.utils;


import java.text.SimpleDateFormat;
import java.util.Locale;

public class ClipUtils {

    public static String clipDurationFormat(float seconds) {
        float milis = seconds*1000;
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss", Locale.getDefault());
        return formatter.format(milis);
    }


}

