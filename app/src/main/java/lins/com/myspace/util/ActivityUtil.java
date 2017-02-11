package lins.com.myspace.util;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.lang.ref.WeakReference;
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


    // 弱引用
    private WeakReference<Activity> activityWeakReference;
    private WeakReference<Fragment> fragmentWeakReference;

    private Toast toast;

    public ActivityUtil(Activity activity) {
        activityWeakReference = new WeakReference<>(activity);
    }

    public ActivityUtil(Fragment fragment){
        fragmentWeakReference = new WeakReference<>(fragment);
    }

    private @Nullable
    Activity getActivity() {

        if (activityWeakReference != null) return activityWeakReference.get();
        if (fragmentWeakReference != null) {
            Fragment fragment = fragmentWeakReference.get();
            return fragment == null? null : fragment.getActivity();
        }
        return null;
    }

    // 封装的弹吐司的方法
    public void showToast(CharSequence msg){
        Activity activity = getActivity();
        if (activity != null){
            if (toast == null) toast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT);
            toast.setText(msg);
            toast.show();
        }
    }

    @SuppressWarnings("SameParameterValue")
    public void showToast(int resId){
        Activity activity = getActivity();
        if (activity != null) {
            String msg = activity.getString(resId);
            showToast(msg);
        }
    }

    // 跳转页面
    public void startActivity(Class<? extends Activity> clazz){
        Activity activity = getActivity();
        if (activity == null) return;
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
    }
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

