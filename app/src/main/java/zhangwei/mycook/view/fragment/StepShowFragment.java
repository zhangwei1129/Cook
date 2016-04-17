package zhangwei.mycook.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import zhangwei.mycook.R;
import zhangwei.mycook.model.CookDetail;
import zhangwei.mycook.volleyutil.VolleyUtil;

/**
 * Created by Administrator on 2016.04.17.
 */
public class StepShowFragment extends Fragment {

    CookDetail.Step step;

    public static StepShowFragment newInstance(CookDetail.Step step) {
        StepShowFragment fragment = new StepShowFragment();
        fragment.step = step;
        return fragment;
    }

    ImageView ivStepPhoto;
    TextView tvStepContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_pager, container, false);
        ivStepPhoto = (ImageView) view.findViewById(R.id.ivStepPhoto);
        ivStepPhoto.setScaleType(ImageView.ScaleType.FIT_CENTER);
        VolleyUtil.load(step.img, ivStepPhoto, 0);
        tvStepContent = (TextView) view.findViewById(R.id.tvStepContent);
        tvStepContent.setText(step.step);
        return view;
    }
}
