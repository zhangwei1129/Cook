package zhangwei.mycook.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
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
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
    private RelativeLayout rlSearch;

    private String inputText;
    private CookListAdapter adapter;
    private ArrayList<CookDetail> temp;
    private String pn;
    private NiftyProgressBar bar;

    boolean isAnim = false;
    int searchHeight;
    int currentScrollHeight;

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
        rlSearch = (RelativeLayout) findViewById(R.id.rlSearch);
        btnBack = (FrameLayout) findViewById(R.id.flBack);
        etSearch = (EditText) findViewById(R.id.etSearch);
        btnClean = (ImageView) findViewById(R.id.ivClean);
        btnSearch = (TextView) findViewById(R.id.tvSearch);
        lvSearchList = (ListView) findViewById(R.id.searchList);

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
                    llTags.setVisibility(View.GONE);
                    adapter.setList(null);
                }
                return false;
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoftInput.hideSoftInput();
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
                switch (scrollState) {
                    case SCROLL_STATE_IDLE:
                        currentScrollHeight = getScrollY();
                        int count = view.getCount() - 1;
                        if (view.getLastVisiblePosition() >= (count - 1)) {
                            pn = String.valueOf(count);
//                    getCookDetailFromQuery(inputText, pn);
                            getData();
                        }
                        break;
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0) {
                    rlSearch.setVisibility(View.VISIBLE);
                }
                if (!isAnim) {
                    if (currentScrollHeight < getScrollY() && rlSearch.getVisibility() == View.VISIBLE) {
                        doAnim(rlSearch, searchHeight, 0);
                    }
                    if (currentScrollHeight > getScrollY() && rlSearch.getVisibility() == View.GONE) {
                        rlSearch.setVisibility(View.VISIBLE);
                        doAnim(rlSearch, 0, searchHeight);
                    }
                }
                currentScrollHeight = getScrollY();
            }
        });

    }

    private void doAnim(final View v, final int from, final int to) {
        isAnim = true;
        ValueAnimator animator = ValueAnimator.ofInt(from, to);
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int val = (int) animation.getAnimatedValue();
                setLayoutParams(v, val);
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if (from == 0) {
                    setLayoutParams(v, 0);
                }
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (to == 0) {
                    v.setVisibility(View.GONE);
                    setLayoutParams(v, from);
                }
                isAnim = false;
            }
        });
        animator.start();
    }

    private void setLayoutParams(View v, int height) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        v.setLayoutParams(params);
    }


    public int getScrollY() {
        View c = lvSearchList.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = lvSearchList.getFirstVisiblePosition();
        int top = c.getTop();
        return (-top + firstVisiblePosition * c.getHeight()) / 1000;
    }


    private void doSearch() {
        searchHeight = rlSearch.getHeight();
        inputText = etSearch.getText().toString();
//        if (TextUtils.isEmpty(inputText)) {
//            llTags.setVisibility(View.VISIBLE);
//            SoftInput.hideSoftInput(SearchActivity.this);
//            ToastUtil.showLongToast(SearchActivity.this, getString(R.string.search_text_is_empty));
//        } else {
            llTags.setVisibility(View.GONE);
//            getCookDetailFromQuery(inputText, "0");
            getData();
//        }
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
    int max = 10;

    private void getData() {
        Log.e(TAG, "getData: ");
        lvSearchList.setVisibility(View.VISIBLE);
        temp.clear();

        for (int i = 0; i < max; i++) {
            CookDetail detail = new CookDetail();
            detail.title = getString(R.string.test_detail_title) + i;
            detail.ingredients = getString(R.string.test_detail_ingredients);
            detail.intro = getString(R.string.test_detail_intro);
            detail.tags = getString(R.string.test_detail_tags);
            detail.burden = getString(R.string.test_detail_burden);
            detail.albums = new ArrayList<>();
            detail.albums.add(getString(R.string.test_detail_albums));
            detail.steps = new ArrayList<>();

            CookDetail.Step step1 = new CookDetail.Step();
            step1.img = getString(R.string.test_detail_step1);
            step1.step = getString(R.string.test_detail_step11);
            detail.steps.add(step1);

            CookDetail.Step step2 = new CookDetail.Step();
            step2.img = getString(R.string.test_detail_step2);
            step2.step = getString(R.string.test_detail_step22);
            detail.steps.add(step2);

            CookDetail.Step step3 = new CookDetail.Step();
            step3.img = getString(R.string.test_detail_step3);
            step3.step = getString(R.string.test_detail_step33);
            detail.steps.add(step3);

            CookDetail.Step step4 = new CookDetail.Step();
            step4.img = getString(R.string.test_detail_step4);
            step4.step = getString(R.string.test_detail_step44);
            detail.steps.add(step4);

            CookDetail.Step step5 = new CookDetail.Step();
            step5.img = getString(R.string.test_detail_step5);
            step5.step = getString(R.string.test_detail_step55);
            detail.steps.add(step5);

            CookDetail.Step step6 = new CookDetail.Step();
            step6.img = getString(R.string.test_detail_step6);
            step6.step = getString(R.string.test_detail_step66);
            detail.steps.add(step6);

            CookDetail.Step step7 = new CookDetail.Step();
            step7.img = getString(R.string.test_detail_step7);
            step7.step = getString(R.string.test_detail_step77);
            detail.steps.add(step7);

            temp.add(detail);
        }
        adapter.setList(temp);
        max += 10;
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
