package zhangwei.mycook.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
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
    FrameLayout flDashLine, flBack, flPhotoContainer;

    private ScalingRunnalable mScalingRunnalable;
    int mHeaderHeight;
    float mLastMotionY = -1.0F;
    float mLastScale = -1.0F;
    float mMaxScale = -1.0F;
    int mActivePointerId = -1;
    int mScreenHeight;
    private static final Interpolator sInterpolator = new Interpolator() {
        public float getInterpolation(float paramAnonymousFloat) {
            float f = paramAnonymousFloat - 1.0F;
            return 1.0F + f * (f * (f * (f * f)));
        }
    };

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
        flPhotoContainer = (FrameLayout) headerView.findViewById(R.id.flPhotoContainer);
        ivPhoto = (ImageView) headerView.findViewById(R.id.ivPhoto);
        tvIngredients = (TextView) headerView.findViewById(R.id.tvIngredients);
        tvBurden = (TextView) headerView.findViewById(R.id.tvBurden);
        tvIntro = (TextView) headerView.findViewById(R.id.tvIntro);
        cookDetailList.addHeaderView(headerView);
        flDashLine = (FrameLayout) findViewById(R.id.flDashLine);
        flDashLine.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        initPhotoContainer();

    }

    private void initPhotoContainer() {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        mScreenHeight = localDisplayMetrics.heightPixels;
        int widthPixels = localDisplayMetrics.widthPixels;
        int heightPixels = (int) (9.0F * (widthPixels / 16.0F));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthPixels, heightPixels);
        params.width = widthPixels;
        params.height = heightPixels;
        flPhotoContainer.setLayoutParams(params);
        mHeaderHeight = heightPixels;
        mScalingRunnalable = new ScalingRunnalable();
    }

    @Override
    public void initData() {
        detail = (CookDetail) getIntent().getSerializableExtra("detail");
        tvTitle.setText(detail.title);
        VolleyUtil.load(detail.albums.get(0), ivPhoto, R.drawable.image, 0);
        tvIngredients.setText(detail.ingredients);
        tvBurden.setText(detail.burden);
        tvIntro.setText(detail.intro);
        adapter = new CookStepAdapter(CookDetailActivity.this, detail);
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

        cookDetailList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (0xFF & event.getAction()) {
                    case 4:
                    case 0:
                        if (!mScalingRunnalable.mIsFinished) {
                            mScalingRunnalable.abortAnimation();
                        }
                        mLastMotionY = event.getY();
                        mActivePointerId = event.getPointerId(0);
                        mMaxScale = (mScreenHeight / mHeaderHeight);
                        mLastScale = (flPhotoContainer.getBottom() / mHeaderHeight);
                        break;
                    case 2:
                        int j = event.findPointerIndex(mActivePointerId);
                        if (j == -1) {
                        } else {
                            if (mLastMotionY == -1.0F)
                                mLastMotionY = event.getY(j);
                            if (flPhotoContainer.getBottom() >= mHeaderHeight) {
                                ViewGroup.LayoutParams localLayoutParams = flPhotoContainer.getLayoutParams();
                                float f = ((event.getY(j) - mLastMotionY + flPhotoContainer.getBottom()) / mHeaderHeight - mLastScale) / 2.0F + mLastScale;
                                if ((mLastScale <= 1.0D) && (f < mLastScale)) {
                                    localLayoutParams.height = mHeaderHeight;
                                    flPhotoContainer.setLayoutParams(localLayoutParams);
                                    return onTouchEvent(event);
                                }
                                mLastScale = Math.min(Math.max(f, 1.0F), mMaxScale);
                                localLayoutParams.height = ((int) (mHeaderHeight * mLastScale));
                                if (localLayoutParams.height < mScreenHeight)
                                    flPhotoContainer.setLayoutParams(localLayoutParams);
                                mLastMotionY = event.getY(j);
                                return true;
                            }
                            mLastMotionY = event.getY(j);
                        }
                        break;
                    case 1:
                        reset();
                        endScraling();
                        break;
                    case 3:
                        int i = event.getActionIndex();
                        mLastMotionY = event.getY(i);
                        mActivePointerId = event.getPointerId(i);
                        break;
                    case 5:
                        onSecondaryPointerUp(event);
                        mLastMotionY = event.getY(event.findPointerIndex(mActivePointerId));
                        break;
                    case 6:
                }
                return onTouchEvent(event);
            }
        });

        cookDetailList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                float f = mHeaderHeight - flPhotoContainer.getBottom();
                if ((f > 0.0F) && (f < mHeaderHeight)) {
                    int i = (int) (0.65D * f);
                    ivPhoto.scrollTo(0, -i);
                } else if (ivPhoto.getScrollY() != 0) {
                    ivPhoto.scrollTo(0, 0);
                }
            }
        });


    }

    class ScalingRunnalable implements Runnable {
        long mDuration;
        boolean mIsFinished = true;
        float mScale;
        long mStartTime;

        ScalingRunnalable() {
        }

        public void abortAnimation() {
            this.mIsFinished = true;
        }

        public boolean isFinished() {
            return this.mIsFinished;
        }

        public void run() {
            float f2;
            ViewGroup.LayoutParams localLayoutParams;
            if ((!mIsFinished) && (mScale > 1.0D)) {
                float f1 = ((float) SystemClock.currentThreadTimeMillis() - (float) mStartTime) / (float) mDuration;
                f2 = mScale - (mScale - 1.0F) * sInterpolator.getInterpolation(f1);
                localLayoutParams = flPhotoContainer.getLayoutParams();
                if (f2 > 1.0F) {
                    localLayoutParams.height = mHeaderHeight;
                    localLayoutParams.height = ((int) (f2 * mHeaderHeight));
                    flPhotoContainer.setLayoutParams(localLayoutParams);
                    cookDetailList.post(this);
                    return;
                }
                mIsFinished = true;
            }
        }

        public void startAnimation(long paramLong) {
            mStartTime = SystemClock.currentThreadTimeMillis();
            mDuration = paramLong;
            mScale = ((float) (flPhotoContainer.getBottom()) / mHeaderHeight);
            mIsFinished = false;
            cookDetailList.post(this);
        }
    }

    private void reset() {
        this.mActivePointerId = -1;
        this.mLastMotionY = -1.0F;
        this.mMaxScale = -1.0F;
        this.mLastScale = -1.0F;
    }

    private void endScraling() {
        if (flPhotoContainer.getBottom() >= mHeaderHeight)
            mScalingRunnalable.startAnimation(200);
    }

    private void onSecondaryPointerUp(MotionEvent paramMotionEvent) {
        int i = (paramMotionEvent.getAction()) >> 8;
        if (paramMotionEvent.getPointerId(i) == this.mActivePointerId)
            if (i != 0) {
                this.mLastMotionY = paramMotionEvent.getY(0);
                this.mActivePointerId = paramMotionEvent.getPointerId(0);
                return;
            }
    }


}
