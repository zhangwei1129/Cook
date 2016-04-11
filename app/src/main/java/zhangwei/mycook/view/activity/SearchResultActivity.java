package zhangwei.mycook.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

import zhangwei.mycook.Config;
import zhangwei.mycook.R;
import zhangwei.mycook.common.FormatActivity;
import zhangwei.mycook.common.SimpleListener;
import zhangwei.mycook.manager.Manager;
import zhangwei.mycook.model.CookDetail;
import zhangwei.mycook.view.adapter.CookDetailAdapter;

/**
 * Created by zhangwei25 on 2016/4/8.
 */
public class SearchResultActivity extends FormatActivity {
    private static final String TAG = "SearchResultActivity";

    public static void start(Context context, int cid) {
        Intent starter = new Intent(context, SearchResultActivity.class);
        starter.putExtra("cid", cid);
        context.startActivity(starter);
    }

    public static void start(Context context, String menu) {
        Intent starter = new Intent(context, SearchResultActivity.class);
        starter.putExtra("menu", menu);
        context.startActivity(starter);
    }

    ListView list;
    CookDetailAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        initWidget();
        initData();
        initListener();
    }

    @Override
    public void initWidget() {
        list = (ListView) findViewById(R.id.list);
    }

    @Override
    public void initData() {

        adapter = new CookDetailAdapter(this);
        list.setAdapter(adapter);

        int cid = getIntent().getIntExtra("cid", 0);
        if (cid != 0) {
            getCookDetailFromCid(cid, "0");
        }
        String menu = getIntent().getStringExtra("menu");
        if (!TextUtils.isEmpty(menu)) {
            getCookDetailFromQuery(menu, "0");
        }
    }

    @Override
    public void initListener() {

    }

    public void getCookDetailFromCid(int cid, String pn) {
        Manager.getInstance().getCookDetailFromCid(this, cid, pn, new SimpleListener<ArrayList<CookDetail>>() {
            @Override
            public void onFinish(ArrayList<CookDetail> cookDetails) {
                adapter.setList(cookDetails);
            }

            @Override
            public void onError(String message) {

            }
        });

    }

    public void getCookDetailFromQuery(String menu, String pn) {
        Manager.getInstance().getCookDetailFromQuery(this, menu, pn, new SimpleListener<ArrayList<CookDetail>>() {
            @Override
            public void onFinish(ArrayList<CookDetail> cookDetails) {
                adapter.setList(cookDetails);
            }

            @Override
            public void onError(String message) {

            }
        });
    }


}
