package lins.com.myspace.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lins.com.myspace.db.DBOpenHelper;
import lins.com.myspace.entity.DiaryInfo;
import lins.com.myspace.entity.TextBoxInfo;
import lins.com.myspace.util.LogUtil;

/**
 * Created by LINS on 2016/12/22.
 * Please Try Hard
 */
public class DiaryManager {
    private static DBOpenHelper helper;
    private SQLiteDatabase db;

    public DiaryManager(Context context) {
        helper = new DBOpenHelper(context);
    }

    //    public void add(DiaryInfo tb_diary) {
//        db = helper.getWritableDatabase();
//        db.execSQL("insert into tb_diary (title,diary) values (?,?)",
//                new Object[]{
//                        tb_diary.getTitle(),
//                        tb_diary.getDiary(),
//                });
//        LogUtil.d(tb_diary.getTitle());
//    }
    //添加
    public void insert(DiaryInfo diaryInfo) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("diaryid", diaryInfo.getDiaryId());
        values.put("title", diaryInfo.getTitle());
        values.put("diary", diaryInfo.getDiary());
        values.put("time", diaryInfo.getTime());
        values.put("belong", diaryInfo.getBelong());
        db.insert("tb_diary", null, values);
        db.close();
    }

    /**
     * 获取新闻分类
     */
    //查询数据
    public static ArrayList<DiaryInfo> query() {
        ArrayList<DiaryInfo> newsList = new ArrayList<DiaryInfo>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from tb_diary order by _id desc";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("diaryid"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String diary = cursor.getString(cursor.getColumnIndex("diary"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                String belong = cursor.getString(cursor.getColumnIndex("belong"));
                DiaryInfo subType = new DiaryInfo(id, title, diary, time, belong);
                newsList.add(subType);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        } else {
            DiaryInfo ss = new DiaryInfo("404", "无数据", "写点什么把", "2012-12-25", "007");
            newsList.add(ss);
            return newsList;
        }
        return newsList;
    }

    public void updateById(DiaryInfo diaryInfo) {
        db = helper.getWritableDatabase();
//        db.execSQL("update tb_diary set title = ?,diary = ?,time = ?,belong = ? where diaryid = ?",
        db.execSQL("update tb_diary set title = ?,diary = ?,time = ? where diaryid = ?",
                new Object[]{
                        diaryInfo.getTitle(),
                        diaryInfo.getDiary(),
                        diaryInfo.getTime(),
                        diaryInfo.getDiaryId(),

                });
    }

    public static void deleteById(DiaryInfo diaryInfo) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from tb_diary where diaryid = ?",
                new Object[]{
                        diaryInfo.getDiaryId()
                });
        db.close();
    }

    /**
     * 根据Id找到数据库中的对象
     *
     * @param id
     * @return
     */
    public DiaryInfo findById(String id) {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select diaryid,title,diary,time,belong from tb_diary where diaryid = ?",
                new String[]{
                        id
                });
        if (cursor.moveToNext()) {
            return new DiaryInfo(
                    cursor.getString(cursor.getColumnIndex("diaryid")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("diary")),
                    cursor.getString(cursor.getColumnIndex("time")),
                    cursor.getString(cursor.getColumnIndex("belong"))

            );
        }
        return null;
    }

    public DiaryInfo checkCountByBelongId(String id) {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select diaryid,title,diary,time,belong from tb_diary where belong = ?",
                new String[]{
                        id
                });
        if (cursor.moveToNext()) {
            return new DiaryInfo(
                    cursor.getString(cursor.getColumnIndex("diaryid")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("diary")),
                    cursor.getString(cursor.getColumnIndex("time")),
                    cursor.getString(cursor.getColumnIndex("belong"))

            );
        }
        return null;
    }

    public static ArrayList<DiaryInfo> findByBelong(String belongid) {
        ArrayList<DiaryInfo> newsList = new ArrayList<DiaryInfo>();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select diaryid,title,diary,time,belong from tb_diary where belong = ?",
                new String[]{
                        belongid
                });
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("diaryid"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String diary = cursor.getString(cursor.getColumnIndex("diary"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                String belong = cursor.getString(cursor.getColumnIndex("belong"));
                DiaryInfo subType = new DiaryInfo(id, title, diary, time, belong);
                newsList.add(subType);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }else {
            DiaryInfo ss = new DiaryInfo("404", "无数据", "写点什么把", "2012-12-25", "007");
            newsList.add(ss);
            return newsList;
        }
        return newsList;
    }

    public DiaryInfo getData() {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select title,diary from tb_diary", null);
        if (cursor.moveToNext()) {
            return new DiaryInfo(
                    cursor.getString(cursor.getColumnIndex("diaryid")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("diary")),
                    cursor.getString(cursor.getColumnIndex("time")),
                    cursor.getString(cursor.getColumnIndex("belong"))
            );
        }
        return null;
    }

    //返回数据的总条数
    public long getTextCount() {
        db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象
        Cursor cursor = db
                .rawQuery("select count(_id) from tb_diary", null);// 获取收入信息的记录数
        if (cursor.moveToNext())// 判断Cursor中是否有数据
        {
            return cursor.getLong(0);// 返回总记录数
        }
        return 0;// 如果没有数据，则返回0
    }

    //    public List<DiaryInfo> getScrollDate(int start, int count){
//        List<DiaryInfo> tb_diary = new ArrayList<DiaryInfo>();
//        db = helper.getWritableDatabase();
//        Cursor cursor = db.rawQuery("select * from tb_diary limit ?,?",
//                new String[]{
//                        String.valueOf(start), String.valueOf(count)
//                });
//        while (cursor.moveToNext())
//        {
//            tb_diary.add(new DiaryInfo
//                    (
//                            cursor.getString(cursor.getColumnIndex("diaryid")),
//                            cursor.getString(cursor.getColumnIndex("title")),
//                            cursor.getString(cursor.getColumnIndex("diary")),
//                            cursor.getString(cursor.getColumnIndex("time"))
//                    ));
//        }
//        return tb_diary;
//    }


    public int getMaxId() {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select max(_id) from tb_diary", null);
        while (cursor.moveToLast()) {
            return cursor.getInt(0);
        }
        return 0;
    }

    public void detele(Integer... ids) {
        if (ids.length > 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < ids.length; i++) {
                sb.append('?').append(',');
            }
            sb.deleteCharAt(sb.length() - 1);
            db = helper.getWritableDatabase();
            db.execSQL("delete from tb_diary where _id in (" + sb + ")",
                    (Object[]) ids);
        }
    }
//    public DiaryInfo findbyid(int id)
//    {
//        db = helper.getWritableDatabase();
//        Cursor cursor = db.rawQuery("select _id,title,diary from tb_diary where _id = ?",
//                new String[] { String.valueOf(id) });
//        if(cursor.moveToNext())
//        {
//            return new DiaryInfo(
//                    cursor.getString(cursor.getColumnIndex("diaryid")),
//                    cursor.getString(cursor.getColumnIndex("title")),
//                    cursor.getString(cursor.getColumnIndex("diary")),
//                    cursor.getString(cursor.getColumnIndex("time"))
//            );
//        }
//        return  null;
//    }
  /*  public boolean chick(){
    db = helper.getWritableDatabase();

    }*/

   /* public boolean tabbleIsExist(String tableName){
        boolean result = false;
        if(tableName == null){
            return false;
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.helper.getWritableDatabase();
            String sql = "select count(*) as c from tb_diary where type ='table' and name ='"+tableName.trim()+"' ";
            cursor = db.rawQuery(sql, null);
            if(cursor.moveToNext()){
                int count = cursor.getInt(0);
                if(count>0){
                    result = true;
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;
    }
*/

}
