package zhangwei.mycook.view.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import zhangwei.mycook.R;
import zhangwei.mycook.common.NiftyListAdapter;
import zhangwei.mycook.model.Category;
import zhangwei.mycook.view.activity.SearchResultActivity;

/**
 * Created by zhangwei25 on 2016/4/8.
 */
public class SubcategoryAdapter extends NiftyListAdapter<Category.Subcategory> {
    private static final String TAG = "SubcategoryAdapter";
    public SubcategoryAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = getInflater().inflate(R.layout.item_categorylist, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Category.Subcategory subcategory = getList().get(position);
        Log.d(TAG, "getView() called with: " + "position = [" + position + "], category.name = [" + subcategory.name + "]");
        holder.mName.setText(subcategory.name);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchResultActivity.start(getContext(),Integer.parseInt(subcategory.id));
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView mName;
        View root;

        public ViewHolder(View root) {
            mName = (TextView) root.findViewById(R.id.tvName);
            this.root = root;
        }
    }
}
