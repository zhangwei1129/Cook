package zhangwei.mycook.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import zhangwei.mycook.R;
import zhangwei.mycook.common.FormatActivity;
import zhangwei.mycook.common.SimpleListener;
import zhangwei.mycook.common.util.SoftInput;
import zhangwei.mycook.common.util.ToastUtil;
import zhangwei.mycook.manager.Manager;
import zhangwei.mycook.model.CookDetail;
import zhangwei.mycook.view.adapter.CookListAdapter;
import zhangwei.mycook.view.customview.NiftyProgressBar;

/**
 * Created by zhangwei25 on 2016/4/14.
 */
public class SearchActivity extends FormatActivity {
    private static final String TAG = "SearchActivity";

    private FrameLayout btnBack;
    private EditText etSearch;
    private ImageView btnClean;
    private TextView btnSearch;
    private ListView lvSearchList;

    private LinearLayout llTags;

    private String inputText;
    private CookListAdapter adapter;
    private ArrayList<CookDetail> temp;
    private String pn;
    private NiftyProgressBar bar;


    public static void start(Context context) {
        Intent starter = new Intent(context, SearchActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initWidget();
        initData();
        initListener();
    }

    @Override
    public void initWidget() {
        btnBack = (FrameLayout) findViewById(R.id.flBack);
        etSearch = (EditText) findViewById(R.id.etSearch);
        btnClean = (ImageView) findViewById(R.id.ivClean);
        btnSearch = (TextView) findViewById(R.id.tvSearch);
        lvSearchList = (ListView) findViewById(R.id.searchList);
//        lvSearchList.setVisibility(View.GONE);

        llTags = (LinearLayout) findViewById(R.id.llTags);
    }

    @Override
    public void initData() {
        bar = NiftyProgressBar.newInstance(this);
        temp = new ArrayList<>();
        pn = "0";
        adapter = new CookListAdapter(SearchActivity.this);
        lvSearchList.setAdapter(adapter);
//        adapter.setList(temp);
        lvSearchList.setEmptyView(findViewById(R.id.rl_search_empty));
    }

    @Override
    public void initListener() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etSearch.getText().length() > 0) {
                    btnClean.setVisibility(View.VISIBLE);
                } else {
                    btnClean.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    doSearch();
                }
                return false;
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSearch();
            }
        });

        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setText("");
            }
        });

        lvSearchList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_FLING) {

                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (view.getLastVisiblePosition() >= (totalItemCount - 2)) {
                    pn = String.valueOf(temp.size());
//                    getCookDetailFromQuery(inputText, pn);
                    getData();
                }
            }
        });
    }

    private void doSearch() {
        llTags.setVisibility(View.GONE);
        inputText = etSearch.getText().toString();
        if (TextUtils.isEmpty(inputText)) {
            SoftInput.hideSoftInput(SearchActivity.this);
            ToastUtil.showLongToast(SearchActivity.this, getString(R.string.search_text_is_empty));
        } else {
//            getCookDetailFromQuery(inputText, "0");
            getData();
        }
    }

    /**
     * "id": "45",
     * "title": "秘制红烧肉",
     * "tags": "家常菜;热菜;烧;煎;炖;红烧;炒锅",
     * "imtro": "做红烧肉的豆亲们很多，大家对红烧肉的热爱更不用我说，从名字上就能反映出来。一些高手
     * 们对红烧肉的认识更是令我佩服，单单就红烧肉的做法、菜谱都看得我是眼花缭乱，口水横流。单纯的红
     * 烧肉我平时还真没做过，再不抓紧时间做一回解解馋，不是对不起别人，而是太对不起我自己了！ 这道菜
     * 的菜名用了秘制二字来形容，当然是自己根据自己多年吃货的经验想象出来的，我不介意把自己的做法与
     * 大家共享，只为大家能同我一样，吃到不同口味的红烧肉。不同的人们根据自己的习惯都有不同的做法，
     * 味道也不尽相同。我的秘制的关键就是必须用玫瑰腐乳、冰糖和米醋这三种食材，腐乳和冰糖可以使烧出
     * 来的肉色泽红亮，米醋能解腻，令肥肉肥而不腻，此法烧制的红烧肉软糯中略带咸甜，的确回味无穷！",
     * "ingredients": "五花肉,500g",
     * "burden": "玫瑰腐乳,适量;盐,适量;八角,适量;草果,适量;香叶,适量;料酒,适量;米醋,适量;生姜,适量",
     * "albums": ["http:\/\/img.juhe.cn\/cookbook\/t\/0\/45_854851.jpg"],
     */

    private void getData() {
        lvSearchList.setVisibility(View.VISIBLE);
        temp.clear();
        for (int i = 0; i < 10; i++) {
            CookDetail detail = new CookDetail();
            detail.title = getString(R.string.test_detail_title) + i;
            detail.ingredients = getString(R.string.test_detail_ingredients);
            detail.intro = getString(R.string.test_detail_intro);
            detail.tags = getString(R.string.test_detail_tags);
            detail.burden = getString(R.string.test_detail_burden);
            detail.albums = new ArrayList<>();
            detail.albums.add(getString(R.string.test_detail_albums));
            detail.steps = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                CookDetail.Step step = new CookDetail.Step();
                step.img = getString(R.string.test_detail_albums);
                step.step = "1.将五花肉煮至断生状态";
                detail.steps.add(step);
            }
            temp.add(detail);
        }
        adapter.setList(temp);

    }

    public void getCookDetailFromQuery(String menu, final String pn) {
        bar.show();
        Manager.getInstance().getCookDetailFromQuery(this, menu, pn, new SimpleListener<ArrayList<CookDetail>>() {
            @Override
            public void onFinish(ArrayList<CookDetail> cookDetails) {
                if (!pn.equals("0")) {
                    int index = temp.size();
                    temp.addAll(index, cookDetails);
                } else {
                    temp = cookDetails;
                }
                if (!temp.isEmpty()) {
                    lvSearchList.setVisibility(View.VISIBLE);
                    adapter.setList(temp);
                } else {
                    adapter.setList(null);
                }
                if (bar.isShowing()) {
                    bar.dismiss();
                }
            }

            @Override
            public void onError(String message) {
                adapter.setList(null);
                if (bar.isShowing()) {
                    bar.dismiss();
                }
                if (!TextUtils.isEmpty(message)) {
                    ToastUtil.showLongToast(SearchActivity.this, message);
                }
            }
        });
    }
}
