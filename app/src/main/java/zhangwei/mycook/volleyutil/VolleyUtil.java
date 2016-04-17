package zhangwei.mycook.volleyutil;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import zhangwei.mycook.common.BitmapCache;

public class VolleyUtil {

    private static RequestQueue mRequestQueue;

    public static void initialize(Context context) {
        if (mRequestQueue == null) {
            synchronized (VolleyUtil.class) {
                if (mRequestQueue == null) {
                    mRequestQueue = Volley.newRequestQueue(context);
                }
            }
        }
        mRequestQueue.start();
    }

    public static RequestQueue getRequestQueue() {
        if (mRequestQueue == null)
            throw new RuntimeException("请先初始化mRequestQueue");
        return mRequestQueue;
    }

    public static void load(String url, ImageView view, int defaultImageResId, int errorImageResId) {
        ImageLoader mImageLoader = new ImageLoader(getRequestQueue(), BitmapCache.getInstance());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(view, defaultImageResId, errorImageResId);
        mImageLoader.get(url, listener);
    }

    public static void load(String url, ImageView view, int defaultImageResId) {
        ImageLoader mImageLoader = new ImageLoader(getRequestQueue(), BitmapCache.getInstance());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(view, defaultImageResId, 0);
        mImageLoader.get(url, listener);
    }

}
