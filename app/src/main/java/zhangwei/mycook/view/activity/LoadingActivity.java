package zhangwei.mycook.view.activity;

import android.os.Bundle;
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
            MainActivity.start(LoadingActivity.this);
        }
        finish();
    }

    boolean switching = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        checkLogin();
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
