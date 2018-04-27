package com.lrony.mread.util;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by Lrony on 18-4-27.
 */
public class ScreenUtil {

    public static boolean isLAndscape(Context context) {
        Configuration mConfiguration = context.getResources().getConfiguration();
        int ori = mConfiguration.orientation; //获取屏幕方向
        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
            return true;
        } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
            return false;
        }
        return false;
    }
}
