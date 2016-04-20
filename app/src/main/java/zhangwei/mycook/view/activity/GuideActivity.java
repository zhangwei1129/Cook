package zhangwei.mycook.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import zhangwei.mycook.R;
import zhangwei.mycook.common.FormatActivity;

/**
 * Created by zhangwei25 on 2016/4/13.
 */
public class GuideActivity extends FormatActivity {

    private ViewPager guidePagers;
    private Button go;
    private LinearLayout llPointer;
    private ArrayList<View> mViews = null;
    private GuideAdapter mAdapter;
    private static final int[] DEFAULT_GUIDE_PICS = {R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3,
            R.drawable.guide_4};

    public static void start(Context context) {
        Intent starter = new Intent(context, GuideActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initWidget();
        initData();
        initListener();
    }

    @Override
    public void initWidget() {
        guidePagers = (ViewPager) findViewById(R.id.guidePages);
        go = (Button) findViewById(R.id.go);
        llPointer = (LinearLayout) findViewById(R.id.llPointer);
    }

    @Override
    public void initData() {
        initImages();
        mAdapter = new GuideAdapter(mViews);
        guidePagers.setAdapter(mAdapter);
        setPointerView(mViews.size(), 0);
    }

    private void initImages() {
        mViews = new ArrayList<View>();
        ImageView mImage;
        for (int i = 0; i < DEFAULT_GUIDE_PICS.length; i++) {
            mImage = new ImageView(this);
            mImage.setImageResource(DEFAULT_GUIDE_PICS[i]);
            mImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mViews.add(mImage);
        }
    }

    /**
     * 设置指示器
     *
     * @param count
     * @param position
     */
    private void setPointerView(int count, int position) {
        for (int i = 0; i < count; i++) {
            ImageView iv;
            iv = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(3, 3, 3, 3);
            if (i == position) {
                iv.setImageResource(R.drawable.point_red);
            } else {
                iv.setImageResource(R.drawable.point_black);
            }
            llPointer.addView(iv, params);
        }
    }

    @Override
    public void initListener() {
        guidePagers.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setPoint(position);
                if (position == 3) {
                    go.setVisibility(View.VISIBLE);
                    go.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            HomeActivity.start(GuideActivity.this);
                            GuideActivity.this.finish();
                        }
                    });
                } else {
                    go.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setPoint(int position) {
        if (llPointer.getChildCount() > 0) {
            for (int i = 0; i < llPointer.getChildCount(); i++) {
                ImageView iv = (ImageView) llPointer.getChildAt(i);
                if (i == position) {
                    iv.setImageResource(R.drawable.point_red);
                } else {
                    iv.setImageResource(R.drawable.point_black);
                }
            }
        }
    }

    class GuideAdapter extends PagerAdapter {
        private final List<View> mViews;

        public GuideAdapter(List<View> mViews) {
            this.mViews = mViews;
        }

        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViews.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViews.get(position));

            return mViews.get(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        protected void destory() {
            if (guidePagers != null) {
                guidePagers.destroyDrawingCache();
                guidePagers.removeAllViews();
            }
            if (mViews != null) {
                mViews.clear();
            }

        }
    }

    @Override
    protected void onDestroy() {
        if (mAdapter != null) {
            mAdapter.destory();
        }
        super.onDestroy();
    }
}
