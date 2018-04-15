package lins.com.myspace.bean;

/**
 * Created by Administrator on 2018/4/5.
 */

public class MarkBean {

    public String name;
    public String wrold;

    public MarkBean(String name, String wrold) {
        this.name = name;
        this.wrold = wrold;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWrold() {
        return wrold;
    }

    public void setWrold(String wrold) {
        this.wrold = wrold;
    }
}
