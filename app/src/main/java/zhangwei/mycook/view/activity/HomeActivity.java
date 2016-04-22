package zhangwei.mycook.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import zhangwei.mycook.R;
import zhangwei.mycook.common.FormatActivity;

/**
 * Created by zhangwei25 on 2016/4/12.
 */
public class HomeActivity extends FormatActivity {

    private static final String TAG = "MainActivity";

    public static void start(Context context) {
        Intent starter = new Intent(context, HomeActivity.class);
        context.startActivity(starter);
    }

    FrameLayout btnSearch;
    TextView tvName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initWidget();
        initData();
        initListener();
    }

    @Override
    public void initWidget() {
        btnSearch = (FrameLayout) findViewById(R.id.flSearch);
        tvName = (TextView) findViewById(R.id.tvName);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.start(HomeActivity.this);
            }
        });

    }
}
