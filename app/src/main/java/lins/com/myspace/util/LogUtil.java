package lins.com.myspace.util;

/**
 * Created by LINS on 2016/12/20.
 * Please Try Hard
 */

import android.util.Log;

/**
 * 日志管理
 * @author LINS
 *
 */
public class LogUtil {
    public static final String TAG = "----测试信息---：";
    //调试日志的开关
    public static boolean isDebug = true;

    public static void d(String tag,String msg){
        if (isDebug) {
            Log.d(tag,msg);
        }
    }
    public static void d(String msg){
        if (isDebug) {
            Log.d(LogUtil.TAG,msg);
        }
    }

}
