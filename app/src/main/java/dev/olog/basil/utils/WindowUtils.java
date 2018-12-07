package dev.olog.basil.utils;

import android.content.res.Resources;

public class WindowUtils {

    public static int getStatusBarHeight(Resources resources){
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

}
