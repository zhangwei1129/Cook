package zhangwei.mycook.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import zhangwei.mycook.model.Category;
import zhangwei.mycook.model.CookDetail;


/**
 * Created by zhangwei25 on 2016/3/28.
 */
public class AllParser {

    public static boolean success(JSONObject jsonObject) {
        return jsonObject.optInt("error_code", 1) == 0;
    }

    public static CookDetail parserCookDetail(JSONObject jsonObject) {
        CookDetail detail = new CookDetail();
        detail.id = jsonObject.optString("id");
        detail.title = jsonObject.optString("title");
        detail.tags = jsonObject.optString("tags");
        detail.intro = jsonObject.optString("imtro");
        detail.ingredients = jsonObject.optString("ingredients");
        detail.burden = jsonObject.optString("burden");

        ArrayList<String> albums = new ArrayList<>();
        JSONArray albumsArray = jsonObject.optJSONArray("albums");
        int size = albumsArray.length();
        for (int i = 0; i < size; i++) {
            try {
                albums.add(albumsArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        detail.albums = albums;

        ArrayList<CookDetail.Step> steps = new ArrayList<>();
        JSONArray stepsArray = jsonObject.optJSONArray("steps");
        size = stepsArray.length();
        for (int i = 0; i < size; i++) {
            CookDetail.Step step = new CookDetail.Step();
            JSONObject j = stepsArray.optJSONObject(i);
            step.img = j.optString("img");
            step.step = j.optString("step");
            steps.add(step);
        }
        detail.steps = steps;

        return detail;
    }

    public static Category parserCookCategory(JSONObject jsonObject) {
        Category category = new Category();
        category.parentId = jsonObject.optString("parentId");
        category.name = jsonObject.optString("name");

        JSONArray jsonArray = jsonObject.optJSONArray("list");
        ArrayList<Category.Subcategory> subcategories = new ArrayList<>();
        int size = jsonArray.length();
        for (int i = 0; i < size; i++) {
            Category.Subcategory subcategory = new Category.Subcategory();
            JSONObject j = jsonArray.optJSONObject(i);
            subcategory.id = j.optString("id");
            subcategory.name = j.optString("name");
            subcategory.parentId = j.optString("parentId");
            subcategories.add(subcategory);
        }
        category.subcategories = subcategories;
        return category;
    }




}