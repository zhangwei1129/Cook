package zhangwei.mycook.common;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;

import com.thinkland.sdk.android.JuheData;

import zhangwei.mycook.manager.App;

/**
 * Created by zhangwei25 on 2016/3/29.
 */
public abstract class FormatActivity extends FragmentActivity {

    /* 初始化UI控件 */
    public abstract void initWidget();

    /* 初始化数据 */
    public abstract void initData();

    /* 初始化Listenner */
    public abstract void initListener();

    public static enum ActivityState {
        RESUME, PAUSE, STOP, DESTROY
    }

    @Override
    protected void onStart() {
        super.onStart();
        App.setCurrentActivity(this);
    }

    // 屏幕方向的状态
    public static enum ScreenOrientation {
        VERTICAL, HORIZONTAL, AUTO
    }

    // 当前Activity的状态
    public ActivityState activityState = ActivityState.DESTROY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.setCurrentActivity(this);
//		mApp = App.getContext();
//		mApp.resumeActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityState = ActivityState.RESUME;
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityState = ActivityState.PAUSE;
    }

    @Override
    protected void onStop() {
        super.onStop();
        activityState = ActivityState.STOP;
    }





    public void setScreenOrientation(ScreenOrientation orientation) {
        switch (orientation) {
            case HORIZONTAL:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            case VERTICAL:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case AUTO:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
                break;
        }
    }

    public void setFullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    // public void showActivity(Activity aty, Intent it) {
    // aty.startActivity(it);
    // }
    //
    // public void showActivity(Activity aty, Class<?> cls) {
    // Intent it = new Intent();
    // it.setClass(aty, cls);
    // aty.startActivity(it);
    // }
    //
    // public void showActivity(Activity aty, Class<?> cls, Bundle extras) {
    // Intent it = new Intent();
    // it.setClass(aty, cls);
    // it.putExtras(extras);
    // aty.startActivity(it);
    // }

    // public void add(int containerViewId, Fragment fm, boolean
    // isAddToBackStack) {
    // FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    // ft.replace(containerViewId, fm);
    // if (isAddToBackStack) {
    // ft.addToBackStack("");
    // }
    // ft.commit();
    // }
    //
    // public void replace(int containerViewId, Fragment fm) {
    // FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    // ft.replace(containerViewId, fm);
    // ft.commit();
    // }

    public void addFragment(int containerId, Fragment fragment, boolean isAddToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(containerId, fragment);
        if (isAddToBackStack) {
            ft.addToBackStack("");
        }
        ft.commit();
    }

    public boolean popBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
            return true;
        } else {
            return false;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JuheData.cancelRequests(this);
        activityState = ActivityState.DESTROY;
    }
}
