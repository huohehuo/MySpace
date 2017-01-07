package lins.com.myspace.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LINS on 2016/12/20.
 * Please Try Hard
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    private String CREATE_BOOK = "create table tb_textbox (_id integer primary key autoincrement,tbid text,name text,belong text)   ";

    private String CREATE_TEMP_BOOK = "alter table tb_textbox rename to _temp_book";

    private String INSERT_DATA = "insert into tb_textbox select *,'a' from _temp_book";

    private String DROP_BOOK = "drop table _temp_book";
    public DBOpenHelper(Context context) {
        //创建数据库
        super(context,"newsdb2.db",null,1);
        // TODO Auto-generated constructor stub
    }

    //创建数据表
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
//        db.execSQL("create table news (_id integer primary key autoincrement,nid integer," +
//                "title text,stamp text,summary text,icon text,link text,type integer)");
//        db.execSQL("create table type (_id integer primary key autoincrement,subid integer,subgroup text)");
//        db.execSQL("create table lovenews (_id integer primary key autoincrement,nid integer," +
//                "title text,stamp text,summary text,icon text,link text,type integer)");
        db.execSQL("create table tb_diary (_id integer primary key autoincrement,diaryid text,title text,diary text,time text,belong text)");
        db.execSQL("create table tb_textbox (_id integer primary key autoincrement,tbid text,name text)");

    }

    //当数据库版本更新时，调用此方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        // TODO Auto-generated method stub
//        db.execSQL(CREATE_TEMP_BOOK);
//
//        db.execSQL(CREATE_BOOK);
//
//        db.execSQL(INSERT_DATA);
//
//        db.execSQL(DROP_BOOK);
    }


}
