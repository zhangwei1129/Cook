package zhangwei.mycook.view.adapter;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import zhangwei.mycook.R;
import zhangwei.mycook.common.NiftyListAdapter;
import zhangwei.mycook.common.util.ToastUtil;
import zhangwei.mycook.common.util.Util;
import zhangwei.mycook.model.CookDetail;
import zhangwei.mycook.view.activity.CookDetailActivity;
import zhangwei.mycook.volleyutil.VolleyUtil;

/**
 * Created by zhangwei25 on 2016/4/14.
 */
public class CookListAdapter extends NiftyListAdapter<CookDetail> {
    private static final String TAG = "CookListAdapter";

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
        int dimension = Util.getDisplayWidth();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dimension, dimension);
        params.setMargins(Util.dp2px( 16), 0, 0, 0);
        holder.ivPhoto.setLayoutParams(params);
        holder.ivPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
        VolleyUtil.load(detail.albums.get(0), holder.ivPhoto, R.drawable.image);
        holder.tvCookName.setText(detail.title);
        holder.tvIngredients.setText(detail.ingredients);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showLongToast(getContext(), "CookListAdapter " + position);
                CookDetailActivity.start(getContext(), detail);
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
            tvCookName = (TextView) root.findViewById(R.id.tvTitle);
            tvIngredients = (TextView) root.findViewById(R.id.tvContent);
            this.root = root;
        }
    }

}
