package zhangwei.mycook.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import zhangwei.mycook.R;
import zhangwei.mycook.common.FormatActivity;
import zhangwei.mycook.model.CookDetail;
import zhangwei.mycook.view.adapter.CookStepAdapter;
import zhangwei.mycook.volleyutil.VolleyUtil;

/**
 * Created by Administrator on 2016.04.16.
 */
public class CookDetailActivity extends FormatActivity {
    private static final String TAG = "CookDetailActivity";
    private CookDetail detail;

    public static void start(Context context, CookDetail detail) {
        Intent starter = new Intent(context, CookDetailActivity.class);
        starter.putExtra("detail", detail);
        context.startActivity(starter);
    }

    ListView cookDetailList;
    View headerView;
    ImageView ivPhoto, ivEnlarge;
    TextView tvTitle, tvIngredients, tvBurden, tvIntro, tvMore;
    CookStepAdapter adapter;
    ArrayList<CookDetail.Step> steps;
    FrameLayout flDashLine, flBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_detail);
        initWidget();
        initData();
        initListener();
    }

    @Override
    public void initWidget() {
        ivEnlarge = (ImageView) findViewById(R.id.ivEnlarge);
        flBack = (FrameLayout) findViewById(R.id.flBack);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        cookDetailList = (ListView) findViewById(R.id.lvCookDetail);
        headerView = getLayoutInflater().inflate(R.layout.header_recipe_detail, null);
        ivPhoto = (ImageView) headerView.findViewById(R.id.ivPhoto);
        tvIngredients = (TextView) headerView.findViewById(R.id.tvIngredients);
        tvBurden = (TextView) headerView.findViewById(R.id.tvBurden);
        tvIntro = (TextView) headerView.findViewById(R.id.tvIntro);
        cookDetailList.addHeaderView(headerView);
        flDashLine = (FrameLayout) findViewById(R.id.flDashLine);
        flDashLine.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    public void initData() {
        detail = (CookDetail) getIntent().getSerializableExtra("detail");
        tvTitle.setText(detail.title);
        VolleyUtil.load(detail.albums.get(0), ivPhoto, R.drawable.image, 0);
        tvIngredients.setText(detail.ingredients);
        tvBurden.setText(detail.burden);
        tvIntro.setText(detail.intro);
        adapter = new CookStepAdapter(CookDetailActivity.this,detail);
        cookDetailList.setAdapter(adapter);
        steps = detail.steps;
        adapter.setList(steps);
    }

    @Override
    public void initListener() {
        flBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvIngredients.getLineCount() != 1) {
                    tvIngredients.setMaxLines(1);
                } else {
                    tvIngredients.setMaxLines(100);
                }

            }
        });
        tvBurden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvBurden.getLineCount() != 2) {
                    tvBurden.setMaxLines(2);
                } else {
                    tvBurden.setMaxLines(100);
                }

            }
        });
        tvIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvIntro.getLineCount() != 3) {
                    tvIntro.setMaxLines(3);
                } else {
                    tvIntro.setMaxLines(100);
                }

            }
        });


    }
}
