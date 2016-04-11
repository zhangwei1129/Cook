package zhangwei.mycook.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

import zhangwei.mycook.R;
import zhangwei.mycook.common.FormatActivity;
import zhangwei.mycook.model.Category;
import zhangwei.mycook.view.adapter.SubcategoryAdapter;

/**
 * Created by zhangwei25 on 2016/4/8.
 */
public class SubcategoryActivity extends FormatActivity {
    private static final String TAG = "SubcategoryActivity";
    ListView list;
    SubcategoryAdapter adapter;

    public static void start(Context context, Category category) {
        Intent starter = new Intent(context, SubcategoryActivity.class);
        starter.putExtra("category", category);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategory);
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
        Category mCategory = (Category) getIntent().getSerializableExtra("category");
        Log.i(TAG, "mCategory: " + mCategory.name);
        ArrayList<Category.Subcategory> subcategories = mCategory.subcategories;
        if (subcategories == null) {
            finish();
        }
//        Log.i(TAG, "subcategories: " + subcategories.get(0).name);
        adapter = new SubcategoryAdapter(this);
        list.setAdapter(adapter);
        adapter.setList(subcategories);

    }

    @Override
    public void initListener() {

    }
}
