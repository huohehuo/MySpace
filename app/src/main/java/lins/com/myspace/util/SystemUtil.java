package lins.com.myspace.util;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LINS on 2016/12/20.
 * Please Try Hard
 */
public class SystemUtil {
    private static Context context;
    private static SystemUtil systemUtils;
    private static LocationManager locationManager;
    private ConnectivityManager connManager;// 用于判断联网信息
    // private TelephonyManager telManager;

    private String provider;

    private SystemUtil(Context context) {
        this.context=context;
        // telManager =
        // (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
    }

    public static SystemUtil getSystemUtils(Context context) {
        if (systemUtils == null) {
            systemUtils = new SystemUtil(context);
        }
        return systemUtils;
    }

    /**
     * 判断网络是否连接
     */
    public boolean isOnLine() {
        NetworkInfo info = connManager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前时间
     */
    public static String getSystime() {
        String systime;
        // 对应的时间格式
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss EEEE");
        // 获取当前的时间并格式化
        systime = dateFormat.format(new Date(System.currentTimeMillis()));
        return systime;
    }

    /**
     * 获取当前时间
     */
    public static String getDate() {
        Date date = new Date(System.currentTimeMillis());
        String strs = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            strs = sdf.format(date);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return strs;
    }

    /**
     * 验证邮箱格式
     *
     * @param email
     * @return 格式是否正确
     */
    public static boolean checkEmail(String email) {
        Pattern pattern = Pattern
                .compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)"
                        + "|(([a-zA-Z0-9||-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * 验证密码格式
     *
     * @param password
     * @return
     */
    public static boolean checkPassword(String password) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{6,16}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static LocationManager getLocationManager(){
        return locationManager;
    }

}

