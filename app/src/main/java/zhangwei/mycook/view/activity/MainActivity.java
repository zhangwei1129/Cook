package zhangwei.mycook.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import zhangwei.mycook.R;
import zhangwei.mycook.common.FormatActivity;

/**
 * Created by zhangwei25 on 2016/4/12.
 */
public class MainActivity extends FormatActivity {

    private static final String TAG = "MainActivity";

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mian);
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
