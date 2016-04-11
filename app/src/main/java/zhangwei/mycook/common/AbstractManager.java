package zhangwei.mycook.common;

import android.os.Handler;
import android.os.Looper;

import org.json.JSONObject;

public abstract class AbstractManager {


    protected Handler handler;

    public AbstractManager() {
        handler = new Handler(Looper.getMainLooper());
    }

    public Handler getHandler() {
        return handler;
    }

    public static String errorMessage(boolean timeout) {
        return timeout ? "网络不好,请稍后重试" : "暂无网络,请稍后重试";
    }

    /**
     * @param listener   UI 回调的监听
     * @param jsonObject 服务器返回的json 数据
     */
    protected <T> void handleError(final SimpleListener<T> listener, final JSONObject jsonObject) {
        if (listener == null) {
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (jsonObject != null) {
                    listener.onError(jsonObject.optString("message"));
                }
            }
        });

    }


    protected <T> void handleError(final SimpleListener<T> listener, final boolean timeout) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (listener != null) {
                    listener.onError(errorMessage(timeout));
                }
            }
        });
    }

    protected <T> void handleError(final SimpleListener<T> listener, final String message) {
        if (listener == null) {
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.onError(message);
            }
        });
    }

    /**
     * @param listener UI 回调的监听
     * @param t        json 解析后的数据模型
     */
    protected <T> void handleSuccess(final SimpleListener<T> listener, final T t) {
        if (listener == null) {
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.onFinish(t);
            }
        });

    }
}
