package zhangwei.mycook.common.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import zhangwei.mycook.R;


/**
 * Created by zhangwei25 on 2016/4/14.
 */
public class CustomTitleBar extends RelativeLayout {

    private TextView title;

    private String titleText;
    private float titleTextSize;
    private int titleTextColor;
    private int titleBackground;
    private LayoutParams titleParams;


    public CustomTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleBar);
        titleText = typedArray.getString(R.styleable.CustomTitleBar_customTitleText);
        titleTextSize = typedArray.getDimension(R.styleable.CustomTitleBar_customTitleTextSize, 0);
        titleTextColor = typedArray.getColor(R.styleable.CustomTitleBar_customTitleTextColor, 0);
        titleBackground = typedArray.getColor(R.styleable.CustomTitleBar_customTitleBackground, 0);
        typedArray.recycle();

        title = new TextView(context);

        title.setText(titleText);
        title.setTextSize(titleTextSize);
        title.setTextColor(titleTextColor);
        title.setBackgroundColor(titleBackground);
        title.setGravity(Gravity.CENTER);
//        title.setGravity(CENTER_IN_PARENT);

        setBackgroundColor(getResources().getColor(R.color.tomato));

        titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titleParams.addRule(CENTER_IN_PARENT,TRUE);
        addView(title,titleParams);

    }
}
