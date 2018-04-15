package lins.com.myspace.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;
import lins.com.myspace.entity.User;

/**
 * Created by Administrator on 2018/4/7.
 */

public class ZoneBean extends BmobObject{

    public String z_title;
    public String z_location;
    public String z_command;
    public String z_time;

    private User author;//帖子的发布者，这里体现的是一对一的关系，该帖子属于某个用户
    private BmobRelation favour;//多对多关系：用于存储喜欢该帖子的所有用户


    public ZoneBean(String z_title, String z_location, String z_command, String z_time) {
        this.z_title = z_title;
        this.z_location = z_location;
        this.z_command = z_command;
        this.z_time = z_time;
    }

    public String getZ_title() {
        return z_title;
    }

    public void setZ_title(String z_title) {
        this.z_title = z_title;
    }

    public String getZ_location() {
        return z_location;
    }

    public void setZ_location(String z_location) {
        this.z_location = z_location;
    }

    public String getZ_command() {
        return z_command;
    }

    public void setZ_command(String z_command) {
        this.z_command = z_command;
    }

    public String getZ_time() {
        return z_time;
    }

    public void setZ_time(String z_time) {
        this.z_time = z_time;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public BmobRelation getFavour() {
        return favour;
    }

    public void setFavour(BmobRelation favour) {
        this.favour = favour;
    }

    @Override
    public String toString() {
        return "ZoneBean{" +
                "z_title='" + z_title + '\'' +
                ", z_location='" + z_location + '\'' +
                ", z_command='" + z_command + '\'' +
                ", z_time='" + z_time + '\'' +
                '}';
    }
}
