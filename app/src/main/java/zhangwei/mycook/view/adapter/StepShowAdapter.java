package zhangwei.mycook.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import zhangwei.mycook.model.CookDetail;
import zhangwei.mycook.view.fragment.StepShowFragment;

/**
 * Created by Administrator on 2016.04.17.
 */
public class StepShowAdapter extends FragmentStatePagerAdapter {
    ArrayList<CookDetail.Step> stps;

    public StepShowAdapter(FragmentManager fm, ArrayList<CookDetail.Step> stps) {
        super(fm);
        this.stps = stps;
    }

    @Override
    public Fragment getItem(int position) {
        return StepShowFragment.newInstance(stps.get(position));
    }

    @Override
    public int getCount() {
        return stps.size();
    }
}
