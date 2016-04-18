package zhangwei.mycook.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;

import zhangwei.mycook.R;
import zhangwei.mycook.common.FormatActivity;
import zhangwei.mycook.model.CookDetail;
import zhangwei.mycook.view.adapter.StepShowAdapter;

/**
 * Created by Administrator on 2016.04.16.
 */
public class StepShowActivity extends FormatActivity {
    private static final String TAG = "StepShowActivity";

    CookDetail detail;
    ArrayList<CookDetail.Step> stps;
    int position;

    public static void start(Context context, CookDetail detail, int position) {
        Intent starter = new Intent(context, StepShowActivity.class);
        starter.putExtra("detail", detail);
        starter.putExtra("position", position);
        context.startActivity(starter);
    }

    ViewPager stepPager;
    StepShowAdapter adapter;
    private FrameLayout btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_show);
        initWidget();
        initData();
        initListener();
    }

    @Override
    public void initWidget() {
        btnBack = (FrameLayout) findViewById(R.id.flBack);
        stepPager = (ViewPager) findViewById(R.id.viewPager);
    }

    @Override
    public void initData() {
        detail = (CookDetail) getIntent().getSerializableExtra("detail");
        stps = detail.steps;
        position = getIntent().getIntExtra("position", -1);
        if (position < 0) {
            finish();
        } else {
            adapter = new StepShowAdapter(getSupportFragmentManager(), stps);
            stepPager.setAdapter(adapter);
            stepPager.setCurrentItem(position);
        }
    }

    @Override
    public void initListener() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
