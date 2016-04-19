package zhangwei.mycook.common.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import zhangwei.mycook.manager.App;

/**
 * Created by zhangwei25 on 2016/4/14.
 */
public class SoftInput {

    public static void hideSoftInput() {
        InputMethodManager imm = ((InputMethodManager) App.getCurrentActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
        View focus = App.getCurrentActivity().getCurrentFocus();
        if (focus != null) {
            imm.hideSoftInputFromWindow(focus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

    public static void showSoftInput(Activity context, View view) {
        view.setFocusable(true);
        view.requestFocus();
        InputMethodManager imm = ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE));
        imm.showSoftInput(view, 0);
    }
}
