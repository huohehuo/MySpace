package lins.com.myspace.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.util.List;

/**
 * Created by LINS on 2016/12/20.
 * Please Try Hard
 */
public class LocationUtil {
    /**
     *
     * @param context 上下文
     * @param time    监听位置变化的时间间隔（毫秒）
     * @param move    监听位置变化的距离间隔（米）
     * @param locationManager    LocationManager对象
     * @param locationListener    LocationListener监听器
     *   位置提供器的三种类型：
     *   		GPS_PROVIDER(GPS定位，精度高，耗电高),
     *   		NETWORK_PROVIDER(网络定位，精度稍差，耗电少)
     *   		PASSIVE_PROVIDER
     * 下面可获得一个Location对象，但是获取的是当前位置不移动时的位置
     * String provider = LocationManager.NETWORK_PROVIDER;
     * Location location =locationManager.getLastKnownLocation(provider);
     *
     *   if (locationManager !=null) {
     * 		locationManager.removeUpdates(locationListener);
     * }
     * Activity调用的时候记得把locationListener监听器移除
     *
     */
    public static void loadLocation(Context context, int time, int move,
                                    LocationManager locationManager, LocationListener locationListener) {
        // 查找有哪些位置提供器类型
        List<String> providerList = locationManager.getProviders(true);
        String provider = null;
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(context, "当前没有可用定位，请确定是否打开GPS或网络",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        // 获取当前位置的位置信息
        // Location location = locationManager.getLastKnownLocation(provider);
        // if (location != null) {
        // //showLocation(location);
        // }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(provider, time, move,
                locationListener);
    }

}
