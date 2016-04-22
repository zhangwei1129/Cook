package zhangwei.mycook.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import zhangwei.mycook.Config;
import zhangwei.mycook.R;
import zhangwei.mycook.common.FormatActivity;

/**
 * Created by zhangwei25 on 2016/4/13.
 */
public class LoadingActivity extends FormatActivity {


    @Override
    protected void onResume() {
        super.onResume();

        if (switching) {
            checkLogin();
        }
    }

    private void checkLogin() {
        if (Config.User.isShowGuide()) {
            Config.User.setShowGuide(false);
            GuideActivity.start(LoadingActivity.this);
        } else {
            HomeActivity.start(LoadingActivity.this);
        }
        finish();
    }

    boolean switching = false;
    private Handler mHandler = new Handler();
    private long timer = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        timer = System.currentTimeMillis();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                checkLogin();
            }
        };
        timer = System.currentTimeMillis() - timer;
        if (timer > 1000) {
            mHandler.post(r);
        } else {
            mHandler.postDelayed(r, 1000 - timer);
        }
    }


    @Override
    public void initWidget() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
