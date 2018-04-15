package lins.com.myspace.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import java.util.HashMap;

import cn.bmob.v3.Bmob;
import lins.com.myspace.util.LogUtil;
import lins.com.myspace.util.UserPrefs;

/**
 * Created by LINS on 2016/12/20.
 * Please Try Hard
 */
public class App extends Application {
    private static Context context;

    //private static UploadManager mQiniuUploadManager;

    // 用来保存整个应用程序数据
    private HashMap<String, Object> allData = new HashMap<String, Object>();
    // 单例模式
    private static App application;
    public static App getInstance() {
        LogUtil.d(LogUtil.TAG, "MyApplication onCreate");
        return application;
    }



    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        application = this;
        context = getApplicationContext();

        // 初始化BmobSDK
        Bmob.initialize(this, "82a6a0cab0e91a5be765dc2ba05b2719");

        UserPrefs.init(getApplicationContext());
        LogUtil.d(LogUtil.TAG, "application onCreate");


    }

    // 用于给需要Context的方法提供context，可直接写LinsApp.getContext()即可获取到当前context上下文
    public static Context getContext() {
        return context;
    }
/*
    public static UploadManager getQiniuUploadManager() {
        return mQiniuUploadManager;
    }
    // 存数据*/
    public void addAllData(String key, Object value) {
        allData.put(key, value);
    }

    // 取数据
    public Object getAllData(String key) {
        if (allData.containsKey(key)) {
            return allData.get(key);
        }
        return null;
    }

    // 删除一条数据
    public void delAllDataBykey(String key) {
        if (allData.containsKey(key)) {
            allData.remove(key);
        }
    }

    // 删除所有数据
    public void delAllData() {
        allData.clear();
    }

    // 内存不足的时候
    @Override
    public void onLowMemory() {
        // TODO Auto-generated method stub
        super.onLowMemory();
        LogUtil.d(LogUtil.TAG, "application onLowMemory");
    }
    public String getUUID(){
        return java.util.UUID.randomUUID().toString().replaceAll("-", "");
    }


    // 结束的时候
    @Override
    public void onTerminate() {
        // TODO Auto-generated method stub
        super.onTerminate();
        LogUtil.d(LogUtil.TAG, "application onTerminate");
    }

    // 配置改变的时候
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        LogUtil.d(LogUtil.TAG, "application onConfigurationChanged");
    }

}
