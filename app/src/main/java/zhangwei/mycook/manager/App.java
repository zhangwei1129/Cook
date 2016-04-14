package zhangwei.mycook.manager;

import android.app.Activity;
import android.app.Application;

import java.lang.ref.SoftReference;

/**
 * Created by zhangwei25 on 2016/4/8.
 */
public class App {
    private static Application app;
    private static SoftReference<Activity> currentActivity;
    private static boolean isAppRun = false;

    public static void setApp(Application app) {
        App.app = app;
    }

    public static Application getContext() {
        return app;
    }

    public static Activity getCurrentActivity() {
        return currentActivity.get();
    }

    public static void setCurrentActivity(Activity activity) {
        currentActivity = new SoftReference<>(activity);
    }

    public static void setIsAppRun(boolean isAppRun) {
        App.isAppRun = isAppRun;
    }

    public static boolean isAppRun() {
        return isAppRun;
    }

}
