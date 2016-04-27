package zhangwei.mycook.common.util;

import android.content.Context;

import zhangwei.mycook.manager.App;

/**
 * Created by zhangwei25 on 2016/4/19.
 */
public class Util {

    public static int dp2px(int dipValue) {
        final float scale = App.getCurrentActivity().getResources().getDisplayMetrics().densityDpi;
        return (int) (dipValue * (scale / 160) + 0.5f);
    }

    public static int px2dp( int pxValue) {
        final float scale = App.getCurrentActivity().getResources().getDisplayMetrics().densityDpi;
        return (int) ((pxValue * 160) / scale + 0.5f);
    }

    public static int getDisplayHeight() {
        final int heightPixels = App.getCurrentActivity().getResources().getDisplayMetrics().heightPixels;
        return heightPixels;
    }

    public static int getDisplayWidth() {
        final int widthPixels = App.getCurrentActivity().getResources().getDisplayMetrics().widthPixels;
        return widthPixels;
    }



}
