package zhangwei.mycook.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import zhangwei.mycook.R;
import zhangwei.mycook.common.FormatActivity;
import zhangwei.mycook.common.util.SoftInput;
import zhangwei.mycook.common.util.ToastUtil;
import zhangwei.mycook.view.adapter.CookListAdapter;

/**
 * Created by zhangwei25 on 2016/4/14.
 */
public class SearchActivity extends FormatActivity{
    private static final String TAG = "SearchActivity";

    FrameLayout btnBack;
    EditText inputText;
    ImageView btnClean;
    TextView btnSearch;
    ListView lvSearchList;

    String strSearch;
    CookListAdapter adapter;



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
        inputText = (EditText) findViewById(R.id.et_search);
        btnClean = (ImageView) findViewById(R.id.ivClean);
        btnSearch = (TextView) findViewById(R.id.tvSearch);
        lvSearchList = (ListView) findViewById(R.id.searchList);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (inputText.getText().length() > 0) {
                    btnClean.setVisibility(View.VISIBLE);
                } else {
                    btnClean.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    strSearch = inputText.getText().toString();
                    if (TextUtils.isEmpty(strSearch)) {
                        SoftInput.hideSoftInput(SearchActivity.this);
                        ToastUtil.showLongToast(SearchActivity.this, getString(R.string.search_text_is_empty));
                    } else {
                        populateData(strSearch);
                    }
                }
                return false;
            }
        });
    }

    private void populateData(String strSearch) {

    }
}
