package zhangwei.mycook.model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zhangwei25 on 2016/4/7.
 */
public class CookDetail implements Serializable {
    public String id;
    public String title;
    public String tags;
    public String intro;
    public String ingredients;
    public String burden;
    public ArrayList<String> albums;
    public ArrayList<Step> steps;

    public static class Step implements Serializable {
        public String img;
        public String step;
    }
}
