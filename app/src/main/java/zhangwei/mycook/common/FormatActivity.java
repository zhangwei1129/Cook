package zhangwei.mycook.common;

import android.support.v4.app.FragmentActivity;

import com.thinkland.sdk.android.JuheData;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JuheData.cancelRequests(this);
    }
}
