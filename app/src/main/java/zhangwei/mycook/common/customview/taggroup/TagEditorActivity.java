package zhangwei.mycook.common.customview.taggroup;

import android.app.Activity;
import android.os.Bundle;

import zhangwei.mycook.R;


public class TagEditorActivity extends Activity {
    private TagGroup mTagGroup;
    private TagsManager mTagsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_editor);

        mTagsManager = TagsManager.getInstance(getApplicationContext());
        String[] tags = mTagsManager.getTags();

        mTagGroup = (TagGroup) findViewById(R.id.tag_group);
        mTagGroup.setTags(tags);
    }


    @Override
    public void onBackPressed() {
        mTagsManager.updateTags(mTagGroup.getTags());
        super.onBackPressed();
    }
}