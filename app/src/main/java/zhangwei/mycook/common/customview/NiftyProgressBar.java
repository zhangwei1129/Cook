package zhangwei.mycook.common.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import zhangwei.mycook.R;


public class NiftyProgressBar extends Dialog {

    private View mDialogView;
    private TextView tvMsg;
    private ProgressBar pbRoate;
    private OnCancelListener listener;

    public static NiftyProgressBar newInstance(Context context) {
        return new NiftyProgressBar(context, R.style.dialog_untran);

    }

    private NiftyProgressBar(Context context) {
        super(context);
        init(context);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (listener != null) {
            listener.onCancel();
        }
    }

    private NiftyProgressBar(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes((WindowManager.LayoutParams) params);

    }

    private void init(Context context) {
        mDialogView = View.inflate(context, R.layout.customview_progress_hud, null);
        tvMsg = (TextView) mDialogView.findViewById(R.id.message);
        tvMsg.setVisibility(View.GONE);
        pbRoate = (ProgressBar) mDialogView.findViewById(R.id.pbRotate);
        setContentView(mDialogView);

    }

    public NiftyProgressBar withMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            tvMsg.setVisibility(View.VISIBLE);
            tvMsg.setText(message);
        } else {
            tvMsg.setVisibility(View.GONE);
        }
        return this;
    }

    public NiftyProgressBar withCancelListener(OnCancelListener listener) {
        this.listener = listener;
        return this;
    }

    public interface OnCancelListener {
        void onCancel();
    }
}
