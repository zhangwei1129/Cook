package zhangwei.mycook.view.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import zhangwei.mycook.R;
import zhangwei.mycook.common.NiftyListAdapter;
import zhangwei.mycook.common.util.ToastUtil;
import zhangwei.mycook.model.CookDetail;
import zhangwei.mycook.volleyutil.VolleyUtil;

/**
 * Created by zhangwei25 on 2016/4/14.
 */
public class CookListAdapter extends NiftyListAdapter<CookDetail> {

    ImageLoader mImageLoader;

    public CookListAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = getInflater().inflate(R.layout.item_cooklist, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final CookDetail detail = getList().get(position);
        VolleyUtil.load(detail.albums.get(0), holder.ivPhoto, R.drawable.image);
        holder.tvCookName.setText(detail.title);
        holder.tvIngredients.setText(detail.ingredients);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showLongToast(getContext(), "CookListAdapter " + position);
            }
        });

        return convertView;
    }

    class ViewHolder {
        ImageView ivPhoto;
        TextView tvCookName;
        TextView tvIngredients;

        View root;

        public ViewHolder(View root) {
            ivPhoto = (ImageView) root.findViewById(R.id.ivPhoto);
            tvCookName = (TextView) root.findViewById(R.id.tvName);
            tvIngredients = (TextView) root.findViewById(R.id.tvIngredients);
            this.root = root;
        }
    }

}
