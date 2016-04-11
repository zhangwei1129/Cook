package zhangwei.mycook.common;

/**
 * Created by zhangwei25 on 2016/3/29.
 */
public interface SimpleListener<T> {
    void onFinish(T t);

    void onError(String message);
}
