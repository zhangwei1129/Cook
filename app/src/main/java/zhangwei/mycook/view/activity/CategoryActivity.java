package zhangwei.mycook.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

import zhangwei.mycook.common.FormatActivity;
import zhangwei.mycook.common.SimpleListener;
import zhangwei.mycook.R;
import zhangwei.mycook.manager.Manager;
import zhangwei.mycook.model.Category;
import zhangwei.mycook.model.CookDetail;
import zhangwei.mycook.view.adapter.CategoryAdapter;

/**
 * Created by zhangwei25 on 2016/3/29.
 */
public class CategoryActivity extends FormatActivity {
    private static final String TAG = "MainActivity";
    ListView list;
    CategoryAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
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
        adapter = new CategoryAdapter(CategoryActivity.this);
        list.setAdapter(adapter);
        getCookCategory();
    }

    @Override
    public void initListener() {

    }



    public void getCookCategory() {
        Manager.getInstance().getCookCategory(this, new SimpleListener<ArrayList<Category>>() {
            @Override
            public void onFinish(ArrayList<Category> cookCategories) {
                adapter.setList(cookCategories);
            }

            @Override
            public void onError(String message) {
                list.setEmptyView(null);

            }
        });
    }



    public void getCookDetailFromId() {
        Manager.getInstance().getCookDetailFromId(this, 202, new SimpleListener<ArrayList<CookDetail>>() {
            @Override
            public void onFinish(ArrayList<CookDetail> cookDetails) {

            }

            @Override
            public void onError(String message) {

            }
        });
    }
}
