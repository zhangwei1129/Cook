package zhangwei.mycook.common.customview.tag;

import android.content.Context;
import android.nfc.Tag;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import zhangwei.mycook.R;
import zhangwei.mycook.common.customview.FlowLayout;

/**
 * Created by zhangwei25 on 2016/4/21.
 */
public class TagGroup extends FlowLayout {

    private tagClickListener listener;

    public interface tagClickListener {
        void tagClick(String string);
    }

    public void setTagClickListener(tagClickListener listener) {
        this.listener = listener;
    }


    public TagGroup(Context context) {
        super(context);
    }

    public TagGroup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TagGroup(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }

    private void addTag(TextView textView) {
        final String s = (String) textView.getTag();
        textView.setText(s);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.tagClick(s);
            }
        });
        addView(textView);
    }

    public void setTags(String[] tags) {
        removeAllViews();
        String[] s = new String[7];
        if (tags == null || TextUtils.isEmpty(tags[0])) {
            s = new String[]{"爸爸去哪儿", "奇葩再多一点说", "案例是肯定发"};
        } else {
            if (tags.length > 7) {
                for (int i = 0; i < 7; i++) {
                    s[i] = tags[i];
                }
            } else {
                s = tags;
            }
        }

        for (int i = 0; i < s.length; i++) {
            TextView textView = new TextView(getContext());
            final String str = s[i];
            textView.setText(str);
            textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.item_tag_selector));
            textView.setTextSize(16);
            textView.setTextColor(getResources().getColor(R.color.white));
            textView.setPadding(10, 5, 10, 5);

            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.tagClick(str);
                }
            });
            addView(textView);
        }
    }


}
