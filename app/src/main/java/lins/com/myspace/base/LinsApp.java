package lins.com.myspace.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Random;

import lins.com.myspace.util.LogUtil;

/**
 * Created by LINS on 2016/12/20.
 * Please Try Hard
 */
public class LinsApp extends Application {
    private static Context context;
    // 用来保存整个应用程序数据
    private HashMap<String, Object> allData = new HashMap<String, Object>();
    // 单例模式
    private static LinsApp application;

    public static LinsApp getInstance() {
        LogUtil.d(LogUtil.TAG, "MyApplication onCreate");
        return application;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        application = this;
        context = getApplicationContext();
        // 对极光推送功能进行初始化
        // JPushInterface.setDebugMode(true);
        // JPushInterface.init(this);
        LogUtil.d(LogUtil.TAG, "application onCreate");
    }

    // 用于给需要Context的方法提供context，可直接写LinsApp.getContext()即可获取到当前context上下文
    public static Context getContext() {
        return context;
    }

    // 存数据
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

    /**
     * 对字符串md5加密
     * @param str
     * @return
     */
    public static String toBeMD5(String str) {
        String str5=null;
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            str5 =new BigInteger(1, md.digest()).toString(16);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return str5;
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
