package lins.com.myspace.entity;

/**
 * Created by LINS on 2016/12/20.
 * Please Try Hard
 */
public class User {
    private String name;
    private String pwd;
    private Type type;
    //用于判断ListView所传进的数据的类型
    public enum Type{
        INCOMING,OUTCOMING
    }


    public User(String name, String pwd, Type type) {
        super();
        this.name = name;
        this.pwd = pwd;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User [name=" + name + ", pwd=" + pwd + ", type=" + type + "]";
    }





}
