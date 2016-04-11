package zhangwei.mycook.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zhangwei25 on 2016/4/7.
 */
public class Category implements Serializable {
    public String parentId;
    public String name;
    public ArrayList<Subcategory> subcategories;

    public static class Subcategory implements Serializable {
        public String parentId;
        public String id;
        public String name;
    }
}
