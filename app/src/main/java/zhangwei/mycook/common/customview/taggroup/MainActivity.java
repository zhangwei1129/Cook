package zhangwei.mycook.common.customview.taggroup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import zhangwei.mycook.R;


public class MainActivity extends Activity {

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    private TextView mPromptText;
    private TagGroup mDefaultTagGroup;
    private TagsManager mTagsManager;
    private TagGroup.OnTagClickListener mTagClickListener = new TagGroup.OnTagClickListener() {
        @Override
        public void onTagClick(String tag) {
            Toast.makeText(MainActivity.this, tag, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTagsManager = TagsManager.getInstance(getApplicationContext());
        String[] tags = mTagsManager.getTags();

        mPromptText = (TextView) findViewById(R.id.tv_prompt);
        mPromptText.setVisibility((tags == null || tags.length == 0) ? View.VISIBLE : View.GONE);
        mPromptText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchTagEditorActivity();
            }
        });

        mDefaultTagGroup = (TagGroup) findViewById(R.id.tag_group);
        if (tags != null && tags.length > 0) {
            mDefaultTagGroup.setTags(tags);
        }

//        MyTagGroupOnClickListener tgClickListener = new MyTagGroupOnClickListener();
//
//        mDefaultTagGroup.setOnClickListener(tgClickListener);

        mDefaultTagGroup.setOnTagClickListener(mTagClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String[] tags = mTagsManager.getTags();
        mPromptText.setVisibility((tags == null || tags.length == 0) ? View.VISIBLE : View.GONE);
        mDefaultTagGroup.setTags(tags);
    }



    protected void launchTagEditorActivity() {
        Intent intent = new Intent(MainActivity.this, TagEditorActivity.class);
        startActivity(intent);
    }

    class MyTagGroupOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            launchTagEditorActivity();
        }
    }
}