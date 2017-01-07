package lins.com.myspace.entity;

import java.util.Date;

/**
 * Created by LINS on 2016/12/26.
 * Please Try Hard
 */
public class ChatMessageInfo {
    private String name;
    private String msg;
    private String url;
    private Type type;
    private Date date;
    public enum Type{
        INCOMING,OUTCOMING
    }
    public ChatMessageInfo(){}

    public ChatMessageInfo(String msg, Type type, Date date) {
        this.msg = msg;
        this.type = type;
        this.date = date;
    }
    public ChatMessageInfo(String msg,String url, Type type, Date date) {
        this.msg = msg;
        this.url = url;
        this.type = type;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ChatMessageInfo{" +
                "name='" + name + '\'' +
                ", msg='" + msg + '\'' +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", date=" + date +
                '}';
    }
}
