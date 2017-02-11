package lins.com.myspace.ui.fragment.map;

import java.io.Serializable;

/**
 * Created by LINS on 2017/2/11.
 * Please Try Hard
 */
public class Treasure implements Serializable{
    private int id;
    private String title;//标题
    private double latitude;//纬度
    private double longitude;//经度
    private double altitude;//海拔
    private String location;//位置信息

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
