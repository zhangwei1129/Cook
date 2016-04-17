package zhangwei.mycook.view.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import zhangwei.mycook.R;
import zhangwei.mycook.common.NiftyListAdapter;
import zhangwei.mycook.common.util.ToastUtil;
import zhangwei.mycook.model.CookDetail;
import zhangwei.mycook.view.activity.CookDetailActivity;
import zhangwei.mycook.view.activity.StepShowActivity;
import zhangwei.mycook.volleyutil.VolleyUtil;

/**
 * Created by Administrator on 2016.04.16.
 */
public class CookStepAdapter extends NiftyListAdapter<CookDetail.Step> {
    CookDetail detail;
    public CookStepAdapter(Activity context, CookDetail detail) {
        super(context);
        this.detail = detail;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = getInflater().inflate(R.layout.item_cooklist, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final CookDetail.Step step = getList().get(position);
        VolleyUtil.load(step.img, holder.ivPhoto, R.drawable.image);
        holder.tvStep.setText(step.step);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showLongToast(getContext(), "CookStepAdapter " + position);
                StepShowActivity.start(getContext(),detail,position);
            }
        });

        return convertView;
    }

    class ViewHolder {
        ImageView ivPhoto;
        TextView tvStep;

        View root;

        public ViewHolder(View root) {
            ivPhoto = (ImageView) root.findViewById(R.id.ivPhoto);
            tvStep = (TextView) root.findViewById(R.id.tvContent);
            this.root = root;
        }
    }
}
