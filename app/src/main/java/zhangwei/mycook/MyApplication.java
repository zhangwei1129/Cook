package zhangwei.mycook;

import android.app.Application;
import android.content.Context;

import com.thinkland.sdk.android.JuheSDKInitializer;

import zhangwei.mycook.manager.App;
import zhangwei.mycook.volleyutil.VolleyUtil;


/**
 * Created by zhangwei25 on 2016/3/28.
 */
public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        App.setApp(this);
        VolleyUtil.initialize(this);
        JuheSDKInitializer.initialize(this);
    }


}
