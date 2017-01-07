package lins.com.myspace.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于管理Activity的管理类。
 * 可在继承了Activity的类中的onCreate方法中使用ActivityCollector.addActivity(this);来添加进该集合，
 * 并且可在任意位置调用ActivityCollector.removeActivity(this);方法一次性结束掉集合中所有的Activity。
 * Created by LINS on 2016/12/20.
 * Please Try Hard
 */
public class ActivityUtil {
    //用于存放每个Activity的集合
    public static List<Activity> activities = new ArrayList<Activity>();

    /**
     * 添加Activity到集合中
     * @param activity	可传入当前Activity对象
     */
    public static void addActivity(Activity activity){
        activities.add(activity);
    }
    /**
     * 从集合中移除Activity
     * @param activity	可传入当前Activity对象
     */
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }
    /**
     * 结束所有Activity
     */
    public static void finishAll(){
        for(Activity activity:activities){
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}

