package zhangwei.mycook.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import zhangwei.mycook.R;
import zhangwei.mycook.common.FormatActivity;
import zhangwei.mycook.common.util.ToastUtil;
import zhangwei.mycook.view.fragment.CategoryFragment;
import zhangwei.mycook.view.fragment.HomeFragment;

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

    FrameLayout flContent;
    TextView tvHome, tvSearch, tvCategory;

    Fragment fmHome, fmCategory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initWidget();
        initData();
        initListener();
        getSupportFragmentManager().beginTransaction().replace(R.id.flContent, fmHome).commit();
    }

    @Override
    public void initWidget() {
        btnSearch = (FrameLayout) findViewById(R.id.flSearch);
        tvName = (TextView) findViewById(R.id.tvName);

        flContent = (FrameLayout) findViewById(R.id.flContent);
        tvHome = (TextView) findViewById(R.id.tvHome);
        tvSearch = (TextView) findViewById(R.id.tvSearch);
        tvCategory = (TextView) findViewById(R.id.tvCategory);
    }

    @Override
    public void initData() {
        fmHome = HomeFragment.newInstance();
        fmCategory = CategoryFragment.newInstance();

    }

    @Override
    public void initListener() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.start(HomeActivity.this);
            }
        });
        tvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showLongToast(getBaseContext(), "tvHome");
                getSupportFragmentManager().beginTransaction().replace(R.id.flContent, fmHome).commit();
            }
        });
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.start(HomeActivity.this);
            }
        });

        tvCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showLongToast(getBaseContext(), "tab04");
                getSupportFragmentManager().beginTransaction().replace(R.id.flContent, fmCategory).commit();
            }
        });

    }
}
