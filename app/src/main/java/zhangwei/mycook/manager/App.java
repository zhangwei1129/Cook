package zhangwei.mycook.manager;

import android.app.Application;

/**
 * Created by zhangwei25 on 2016/4/8.
 */
public class App {
    private static Application app;

    public static Application getApp() {
        return app;
    }

    public static void setApp(Application app) {
        App.app = app;
    }

}
