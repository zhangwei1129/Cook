package zhangwei.mycook;

import android.content.Context;

import zhangwei.mycook.manager.App;

/**
 * Created by zhangwei25 on 2016/3/28.
 */
public class Config {

    public static final class User {
        public static final String IS_SHOW_GUIDE = "isShowGiude";

        public static boolean isShowGuide() {
            return App.getContext().getSharedPreferences("AccountInfo", Context.MODE_PRIVATE)
                    .getBoolean(IS_SHOW_GUIDE, true);

        }

        public static void setShowGuide(boolean isShowGuide) {
            App.getContext().getSharedPreferences("AccountInfo", Context.MODE_PRIVATE).edit()
                    .putBoolean(IS_SHOW_GUIDE, isShowGuide).commit();

        }
    }

}
