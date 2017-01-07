package lins.com.myspace.entity;

/**
 * Created by LINS on 2016/12/22.
 * Please Try Hard
 */
public class TextBoxInfo {
    private String tbid;
    private String name;

    public TextBoxInfo(){}
    public TextBoxInfo(String name) {
        this.name = name;
    }
    public TextBoxInfo(String tbid,String name) {
        this.tbid = tbid;
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTbid() {
        return tbid;
    }

    public void setTbid(String tbid) {
        this.tbid = tbid;
    }

    @Override
    public String toString() {
        return "TextBoxInfo{" +
                "tbid='" + tbid + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
