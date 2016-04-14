package zhangwei.mycook.view.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import zhangwei.mycook.common.NiftyListAdapter;
import zhangwei.mycook.model.CookDetail;

/**
 * Created by zhangwei25 on 2016/4/14.
 */
public class CookListAdapter extends NiftyListAdapter<CookDetail>{

    public CookListAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
