package zhangwei.mycook.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import zhangwei.mycook.R;
import zhangwei.mycook.common.util.ToastUtil;
import zhangwei.mycook.common.util.Util;

/**
 * Created by zhangwei25 on 2016/4/26.
 */
public class HomeFragment extends Fragment {

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    private View rootView;
    TextView tv;
    ArrayList<View> mViews = null;
    ViewPager loopPager;
    int[] imgId = new int[]{R.drawable.imgpager1, R.drawable.imgpager2, R.drawable.imgpager3, R.drawable.imgpager4};
    private Handler timeHandler = new Handler();
    private boolean isFlip = false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home, null);
            initWidget(rootView);
            initData();
            initListener();
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    private void initWidget(View rootView) {
        tv = (TextView) rootView.findViewById(R.id.tv);
        loopPager = (ViewPager) rootView.findViewById(R.id.loopPager);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, Util.getDisplayWidth() / 3);
        loopPager.setLayoutParams(params);

    }

    private void initData() {
        mViews = new ArrayList<View>();
        ImageView mImage;
        for (int i = 0; i < imgId.length; i++) {
            mImage = new ImageView(getContext());
            mImage.setImageResource(imgId[i]);
            mImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            final int finalI = i;
            mImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showLongToast(getContext(), "我是第 " + finalI + " 张图片");
                }
            });
            mViews.add(mImage);
        }

        loopPager.setAdapter(new PagerAdapter() {
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                int pager = position % mViews.size();
                container.removeView(mViews.get(pager));

            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                int pager = position % mViews.size();
                container.addView(mViews.get(pager));
                return mViews.get(pager);
            }

            @Override
            public int getCount() {
                if (mViews == null || mViews.size() == 0) {
                    return 0;
                } else if (mViews.size() == 1) {
                    return 1;
                } else {
                    return Integer.MAX_VALUE;
                }
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });

//        loopPager.setCurrentItem(pager,true);
    }

    private Runnable loopTask = new Runnable() {
        @Override
        public void run() {
            if (loopPager != null) {
                int page = loopPager.getCurrentItem() + 1;
                loopPager.setCurrentItem(page, true);
                timeHandler.postDelayed(loopTask, 2000);
            }

        }
    };

    private void initListener() {
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showLongToast(getContext(), "首页");
            }
        });

        loopPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position >= Integer.MAX_VALUE - 1) {
                    loopPager.setCurrentItem(0);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        startFlip();

    }

    @Override
    public void onPause() {
        super.onPause();
        timeHandler.removeCallbacks(loopTask);
        isFlip = false;
    }

    private void startFlip() {
        if (!isFlip) {
            timeHandler.removeCallbacks(loopTask);
            timeHandler.postDelayed(loopTask, 2000);
            isFlip = true;
        }
    }
}
