package lins.com.myspace.db;

import android.content.Context;

import lins.com.myspace.db.DBOpenHelper;

/**
 * 数据库操作类例子，查询，增加，删除等操作
 * Created by LINS on 2016/12/20.
 * Please Try Hard
 */
public class DBManager {
    //单例模式
    private static DBManager dbManager;
    private DBOpenHelper helper;
    private Context context;
    //private NewsAdapter adapter;

    public DBManager(Context context){
        this.context = context;
        helper = new DBOpenHelper(context);
    }
    //同步锁
    public static DBManager getNewsDBManager(Context context){
        if (dbManager == null) {
            synchronized (DBManager.class) {
                if (dbManager == null) {
                    dbManager = new DBManager(context);
                }
            }
        }
        return dbManager;
    }

    //添加
//	public void insertNews(News news){
//		SQLiteDatabase db = helper.getWritableDatabase();
//		ContentValues values = new ContentValues();
//
//		values.put("nid", news.getNid());
//		values.put("title", news.getTitle());
//		values.put("stamp", news.getStamp());
//		values.put("summary", news.getSummary());
//		values.put("icon", news.getIcon());
//		values.put("link", news.getLink());
//		values.put("type", news.getType());
//		db.insert("news", null, values);
//		db.close();
//	}
//
//	//数据数量
//	public long getCount(){
//		SQLiteDatabase db = helper.getReadableDatabase();
//		Cursor cursor = db.rawQuery("select count(*) from news", null);
//		long len = 0;
//		if (cursor.moveToFirst()) {
//			len=cursor.getLong(0);
//		}
//		cursor.close();
//		db.close();
//		return len;
//	}
//
//	//查询数据
//	public ArrayList<News> queryNews(int count,int offset){
//		ArrayList<News> newsList = new ArrayList<News>();
//		SQLiteDatabase db = helper.getWritableDatabase();
//		String sql = "select * from news order by _id desc limit "+
//		count+" offset "+offset;
//		Cursor cursor = db.rawQuery(sql, null);
//		if (cursor.moveToFirst()) {
//			do {
//				int nid = cursor.getInt(cursor.getColumnIndex("nid"));
//				String stamp = cursor.getString(cursor.getColumnIndex("stamp"));
//				String title = cursor.getString(cursor.getColumnIndex("title"));
//				String summary = cursor.getString(cursor.getColumnIndex("summary"));
//				String icon = cursor.getString(cursor.getColumnIndex("icon"));
//				String link = cursor.getString(cursor.getColumnIndex("link"));
//				int type = cursor.getInt(cursor.getColumnIndex("type"));
//				News news = new News(nid,stamp,title,summary,icon,link,type);
//				newsList.add(news);
//			} while (cursor.moveToNext());
//			cursor.close();
//			db.close();
//		}
//		return newsList;
//	}
//	/**
//	 * 保存新闻分类
//	 */
//	public boolean saveNewsType(List<SubType> types){
//		for(SubType type:types){
//			try {
//				SQLiteDatabase db = helper.getWritableDatabase();
//				//判断有没有相应的新闻类
//				Cursor cursor = db.rawQuery("select * from type where subid="+type.getSubid(), null);
//				if (cursor.moveToFirst()) {
//					cursor.close();
//					return false;
//				}
//				cursor.close();
//				//保存新闻分类数据
//				ContentValues values = new ContentValues();
//				values.put("subid", type.getSubid());
//				values.put("subgroup", type.getSubgroup());
//				LogUtil.d("TGP",type.getSubgroup()+"");
//				db.insert("type", null, values);
//				db.close();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				return false;
//			}
//
//		}
//		return true;
//	}
//
//	/**
//	 * 获取新闻分类
//	 */
//	//查询数据
//		public ArrayList<SubType> queryNewsType(){
//			ArrayList<SubType> newsList = new ArrayList<SubType>();
//			SQLiteDatabase db = helper.getReadableDatabase();
//			String sql = "select * from type order by _id desc";
//			Cursor cursor = db.rawQuery(sql, null);
//			if (cursor.moveToFirst()) {
//				do {
//					int subId = cursor.getInt(cursor.getColumnIndex("subid"));
//					String subGroup = cursor.getString(cursor.getColumnIndex("subgroup"));
//
//					SubType subType = new SubType(subId,subGroup);
//					newsList.add(subType);
//				} while (cursor.moveToNext());
//				cursor.close();
//				db.close();
//			}
//			return newsList;
//		}
//
//		/**
//		 * 收藏新闻
//		 */
//		public boolean saveLoveNews(News news){
//			try {
//				SQLiteDatabase db = helper.getWritableDatabase();
//				Cursor cursor = db.rawQuery("select * from lovenews where nid="+news.getNid(), null);
//				if (cursor.moveToFirst()) {
//					cursor.close();
//					return false;
//				}
//				cursor.close();
//				ContentValues values = new ContentValues();
//				values.put("type", news.getType());
//				values.put("nid", news.getNid());
//				values.put("stamp", news.getStamp());
//				values.put("icon", news.getIcon());
//				values.put("title", news.getTitle());
//				values.put("summary", news.getSummary());
//				values.put("link", news.getLink());
//
//				db.insert("lovenews", null, values);
//				db.close();
//				return true;
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				return false;
//			}
//
//		}
//		/**
//		 * 获取收藏新闻的列表
//		 */
//		public ArrayList<News> queryLoveNews(){
//			ArrayList<News> newsList = new ArrayList<News>();
//			SQLiteDatabase db = helper.getReadableDatabase();
//			String sql = "select * from lovenews order by _id desc";
//			Cursor cursor = db.rawQuery(sql, null);
//			if (cursor.moveToFirst()) {
//				do {
//					int type = cursor.getInt(cursor.getColumnIndex("type"));
//					int nid = cursor.getInt(cursor.getColumnIndex("nid"));
//					String stamp = cursor.getString(cursor.getColumnIndex("stamp"));
//					String icon = cursor.getString(cursor.getColumnIndex("icon"));
//					String title = cursor.getString(cursor.getColumnIndex("title"));
//					String summary = cursor.getString(cursor.getColumnIndex("summary"));
//					String link = cursor.getString(cursor.getColumnIndex("link"));
//
//					News news = new News(nid,stamp,title,summary,icon,link,type);
//
//					newsList.add(news);
//				} while (cursor.moveToNext());
//				cursor.close();
//				db.close();
//			}
//			return newsList;
//		}

//		/**
//		 * 从数据库中加载保存的新闻
//		 */
//		public void loadLoveNews(){
//			ArrayList<News> data = new NewsDBManager(context).queryLoveNews();
//			adapter.appendData(data,true);
//		}

}

