package zhangwei.mycook.view.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import zhangwei.mycook.R;
import zhangwei.mycook.common.NiftyListAdapter;
import zhangwei.mycook.model.CookDetail;
import zhangwei.mycook.view.activity.SearchResultActivity;

/**
 * Created by zhangwei25 on 2016/4/8.
 */
public class CookDetailAdapter extends NiftyListAdapter<CookDetail> {
    private static final String TAG = "CookDetailAdapter";

    public CookDetailAdapter(Activity context) {
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

        final CookDetail detail = getList().get(position);

        holder.mName.setText(detail.title);
        Log.d(TAG, "getView() called with: " + "detail.title = [" + detail.title + "], position = [" + position + "]");
        holder.mName.append(" + " + detail.id);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchResultActivity.start(getContext(), detail.title);
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
