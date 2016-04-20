package zhangwei.mycook.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import zhangwei.mycook.Config;
import zhangwei.mycook.common.AbstractManager;
import zhangwei.mycook.common.SimpleListener;
import zhangwei.mycook.common.customview.taggroup.TagsTable;
import zhangwei.mycook.model.Category;
import zhangwei.mycook.model.CookDetail;
import zhangwei.mycook.parser.AllParser;
import zhangwei.mycook.view.activity.SearchResultActivity;


/**
 * Created by zhangwei25 on 2016/3/28.
 */
public class Manager extends AbstractManager {
    private static final String TAG = "Manager";
    private static Manager instance;
    private static SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(App.getContext());

    public static synchronized Manager getInstance() {
        if (instance == null) {
            instance = new Manager();
        }
        return instance;
    }

    /**
     * 搜索历史
     */
    public String[] getSearchHistory() {
        String s = sp.getString("SEARCH_HISTORY", "");
        String[] stringArr = s.split(",");
        return stringArr;
    }

    /**
     * 设置历史
     */
    public void setSearchHistory(String s) {
        sp.edit().putString("SEARCH_HISTORY", s).apply();

    }

    /**
     * 菜名搜索
     */
    public void getCookDetailFromQuery(Context mContext, String menu, String pn, final SimpleListener<ArrayList<CookDetail>> listener) {
        Parameters params = new Parameters();
        params.add("menu", menu);
        if (TextUtils.isEmpty(pn)) {
            pn = "0";
        }
        params.add("pn", pn);
        params.add("rn", 10);
        JuheData.executeWithAPI(mContext, 46, "http://apis.juhe.cn/cook/query.php", JuheData.GET, params, new DataCallBack() {

            @Override
            public void onSuccess(int statusCode, String responseString) {
                Log.d(TAG, "onSuccess() called with: " + "statusCode = [" + statusCode + "], responseString = [" + responseString + "]");
                if (!TextUtils.isEmpty(responseString)) {
                    try {
                        JSONObject jsonObject = new JSONObject(responseString);
                        JSONObject jsonResult = jsonObject.optJSONObject("result");
                        if (AllParser.success(jsonObject)) {
                            ArrayList<CookDetail> details = new ArrayList<>();
                            JSONArray jsonArray = jsonResult.optJSONArray("data");
                            if (jsonArray != null) {
                                int size = jsonArray.length();
                                for (int i = 0; i < size; i++) {
                                    details.add(AllParser.parserCookDetail(jsonArray.optJSONObject(i)));
                                }
                                handleSuccess(listener, details);
                            } else {
                                handleError(listener, jsonObject);
                            }
                        } else {
                            handleError(listener, jsonObject);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int statusCode, String responseString, Throwable throwable) {
                if (!TextUtils.isEmpty(responseString)) {
                    handleError(listener, responseString);
                }

            }
        });
    }

    /**
     * 菜谱类别
     */
    public void getCookCategory(Context mContext, final SimpleListener<ArrayList<Category>> listener) {
        String json = sp.getString("COOK_CATEGORY", "");
        if (TextUtils.isEmpty(json)) {
            JuheData.executeWithAPI(mContext, 46, "http://apis.juhe.cn/cook/category", JuheData.GET, null, new DataCallBack() {
                @Override
                public void onSuccess(int statusCode, String responseString) {
                    if (!TextUtils.isEmpty(responseString)) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseString);
                            if (AllParser.success(jsonObject)) {
                                ArrayList<Category> details = new ArrayList<>();
                                JSONArray jsonArray = jsonObject.optJSONArray("result");
                                sp.edit().putString("COOK_CATEGORY", jsonArray.toString()).apply();
                                int size = jsonArray.length();
                                for (int i = 0; i < size; i++) {
                                    details.add(AllParser.parserCookCategory(jsonArray.optJSONObject(i)));
                                }
                                handleSuccess(listener, details);
                            } else {
                                handleError(listener, jsonObject);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFinish() {

                }

                @Override
                public void onFailure(int statusCode, String responseString, Throwable throwable) {
                    if (!TextUtils.isEmpty(responseString)) {
                        handleError(listener, responseString);
                    }
                }
            });
        } else {
            try {
                JSONArray jsonArray = new JSONArray(json);
                ArrayList<Category> details = new ArrayList<>();
                int size = jsonArray.length();
                for (int i = 0; i < size; i++) {
                    details.add(AllParser.parserCookCategory(jsonArray.optJSONObject(i)));
                }
                handleSuccess(listener, details);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * 菜id搜索
     */
    public void getCookDetailFromCid(Context mContext, int cid, String pn, final SimpleListener<ArrayList<CookDetail>> listener) {
        Parameters params = new Parameters();
        params.add("cid", cid);
        if (TextUtils.isEmpty(pn)) {
            pn = "0";
        }
        params.add("pn", pn);
        JuheData.executeWithAPI(mContext, 46, "http://apis.juhe.cn/cook/index", JuheData.GET, params, new DataCallBack() {
            @Override
            public void onSuccess(int statusCode, String responseString) {
                if (!TextUtils.isEmpty(responseString)) {
                    try {
                        JSONObject jsonObject = new JSONObject(responseString);
                        JSONObject jsonResult = jsonObject.optJSONObject("result");
                        if (AllParser.success(jsonObject)) {
                            ArrayList<CookDetail> details = new ArrayList<>();
                            JSONArray jsonArray = jsonResult.optJSONArray("data");
                            int size = jsonArray.length();
                            for (int i = 0; i < size; i++) {
                                details.add(AllParser.parserCookDetail(jsonArray.optJSONObject(i)));
                            }
                            handleSuccess(listener, details);
                        } else {
                            handleError(listener, jsonObject);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int statusCode, String responseString, Throwable throwable) {
                if (!TextUtils.isEmpty(responseString)) {
                    handleError(listener, responseString);
                }

            }
        });

    }

    /**
     * 菜类别搜索
     */
    public void getCookDetailFromId(Context mContext, int id, final SimpleListener<ArrayList<CookDetail>> listener) {
        Parameters params = new Parameters();
        params.add("id", id);
        JuheData.executeWithAPI(mContext, 46, "http://apis.juhe.cn/cook/queryid", JuheData.GET, params, new DataCallBack() {

            @Override
            public void onSuccess(int statusCode, String responseString) {
                Log.d(TAG, "onSuccess() called with: " + "statusCode = [" + statusCode + "], responseString = [" + responseString + "]");
                if (!TextUtils.isEmpty(responseString)) {
                    try {
                        JSONObject jsonObject = new JSONObject(responseString);
                        if (AllParser.success(jsonObject)) {
                            ArrayList<CookDetail> details = new ArrayList<>();
                            JSONArray jsonArray = jsonObject.optJSONArray("data");
                            int size = jsonArray.length();
                            for (int i = 0; i < size; i++) {
                                details.add(AllParser.parserCookDetail(jsonArray.optJSONObject(i)));
                            }
                            handleSuccess(listener, details);
                        } else {
                            handleError(listener, jsonObject);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int statusCode, String responseString, Throwable throwable) {
                if (!TextUtils.isEmpty(responseString)) {
                    handleError(listener, responseString);
                }

            }
        });
    }


}
