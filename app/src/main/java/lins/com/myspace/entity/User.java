package lins.com.myspace.entity;

import cn.bmob.v3.BmobUser;

/**
 * Created by LINS on 2016/12/20.
 * Please Try Hard
 */
public class User extends BmobUser{

    private String pic;
    private String signature;
    private String from;
    private String sex;
    private String age;

    public User(){}

    public User(String pic, String signature, String from, String sex, String age) {
        this.pic = pic;
        this.signature = signature;
        this.from = from;
        this.sex = sex;
        this.age = age;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "User{" +
                "pic='" + pic + '\'' +
                ", signature='" + signature + '\'' +
                ", from='" + from + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
