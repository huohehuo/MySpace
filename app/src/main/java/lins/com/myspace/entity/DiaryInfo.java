package lins.com.myspace.entity;

/**
 * Created by LINS on 2016/12/27.
 * Please Try Hard
 */
public class DiaryInfo {
    private String diaryId;//笔记ID
    private String title;//笔记标题
    private String diary;//笔记正文
    private String time;//笔记时间
    private String belong;//笔记所属文字包

    public DiaryInfo(String title, String diary) {
        this.title = title;
        this.diary = diary;
    }
    public DiaryInfo(String diaryId,String title, String diary,String time) {
        this.diaryId= diaryId;
        this.title = title;
        this.diary = diary;
        this.time = time;
    }
    public DiaryInfo(String did,String title, String diary,String time,String belong) {
        this.diaryId = did;
        this.title = title;
        this.diary = diary;
        this.time = time;
        this.belong = belong;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiary() {
        return diary;
    }

    public void setDiary(String diary) {
        this.diary = diary;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    /**
     * 获取笔记ID
     * @return
     */
    public String getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(String diaryId) {
        this.diaryId = diaryId;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    @Override
    public String toString() {
        return "DiaryInfo{" +
                "diaryId='" + diaryId + '\'' +
                ", title='" + title + '\'' +
                ", diary='" + diary + '\'' +
                ", time='" + time + '\'' +
                ", belong='" + belong + '\'' +
                '}';
    }
}
