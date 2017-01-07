package lins.com.myspace.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by LINS on 2016/12/27.
 * Please Try Hard
 */
public class ImageUtil {
    //定义回调接口对象
    private ImageLoadListener listener;
    /**
     * 自定义回调接口传递图片和图片路径
     * @author LINS
     *
     */
    public interface ImageLoadListener{
        void imageLoadOk(Bitmap bitmap);
    }
    /**
     * 软引用存内存只要不关闭该程序一直存放(原先)
     */
	/*private static Map<String,SoftReference<Bitmap>> softReferences =new
			HashMap<String,SoftReference<Bitmap>>();
	*/
    private static LruCache<String,Bitmap> cache = new
            LruCache<String,Bitmap>(1024*1024*3);
    private Context context;

    //构造方法赋值
    public ImageUtil(Context context,ImageLoadListener listener){
        this.context = context;
        this.listener = listener;
    }
    /**
     * 最终调用的方法：先看内存中有没有，再看缓存文件中有没有，咀咒只能想网络中请求图片
     * @param url	图片路径
     * @return	图片
     */
    public Bitmap getBitmap(String url){
        Bitmap bitmap = null;
        if (url == null || url.length() <=0) {
            return bitmap;
        }
        //先看内存中
        bitmap = getBitmapFromReferences(url);
        if (bitmap !=null) {
            return bitmap;
        }
        //是不是缓存文件的
        bitmap = getBitmapFromCache(url);
        if (bitmap !=null) {
            return bitmap;
        }
        getBitmapAsync(url);
        return bitmap;
    }


    /**
     * 保存图片到软引用缓存中
     * @param url	图片的原路径网络 http://aa/t.jpg
     * @param bitmap	来自文件的图片
     */
	/*public void saveSoftReferences(String url,Bitmap bitmap){
		//存在内存中的一个图片
		SoftReference<Bitmap> softbitmap = new SoftReference<Bitmap>(bitmap);
		//存在集合中
		softReferences.put(url, softbitmap);
	}*/
    /**
     * 得到内存的图片软引用
     * @param url
     * @return
     */
    public Bitmap getBitmapFromReferences(String url){
        Bitmap bitmap = null;
        //如果内存集合中根据key得到values
		/*if (softReferences.containsKey(url)) {
			//得到软引用中的图片
			bitmap = softReferences.get(url).get();
		}*/
        bitmap = cache.get(url);
        return bitmap;
    }
    /**
     * 保存图片到缓存路径中
     * @param url	图片的原路径网络 http://aa/t.jpg
     * @param bitmap	来自网络得到图片
     */
    public void saveCachFile(String url,Bitmap bitmap){
        //http://aa/t.jpg 获取文件名字
        String name = url.substring(url.lastIndexOf("/")+1);
        //返回的路径目录应用程序缓存文件
        File cacheDir = context.getCacheDir();
        if (!cacheDir.exists()) {
            cacheDir.mkdir();
        }
        //建立输出流
        OutputStream outStream = null;

        try {
            outStream = new FileOutputStream(new File(cacheDir,name));
            //存图片到文件
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                if (outStream !=null) {
                    outStream.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    /**
     * 从缓存文件中获取图片
     * @param url	图片路径 com.app.azy.news.cache/xxx.png
     * @return	缓存文件中的图片
     */
    private Bitmap getBitmapFromCache(String url){
        String name = url.substring(url.lastIndexOf("/")+1);
        //获取当前包下的缓存文件路径
        File cacheDir = context.getCacheDir();
        //得到该文件夹下所有文件
        File[] files = cacheDir.listFiles();
        if (files == null) {
            return null;
        }
        //图片文件
        File bitFile = null;
        //如有名字和传入的文件名一致的则找到图片
        for (File file : files) {
            if (file.getName().equals(name)) {
                bitFile = file;
                break;
            }
        }
        //如果没有找到，则返回空
        if (bitFile == null) {
            return null;
        }
        /**
         * 把找的文件转换成Bitmap
         */
        Bitmap bitmap = BitmapFactory.decodeFile(bitFile.getAbsolutePath());
        return bitmap;
    }
    /**
     * 异步加载类
     * 1.Url处理的网络逻辑
     * 2.无Void 当加载一条时传递的类型
     * 3.返回的加载后的数据：Bitmap
     */
    private class ImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
        private String url;
        //执行之前ui线程
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }
        //后台运行新县城的代码不能修改ui
        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            //单条加载更新ui
            //publishProgress(values);
            url = params[0];
            Bitmap bitmap = null;
            try {
                //建立网络连接
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //得到输入字节流
                InputStream is = conn.getInputStream();
                //得到图片
                bitmap = BitmapFactory.decodeStream(is);
                //存入软引用中
                //saveSoftReferences(params[0], bitmap);
                //使用Lrc优化缓存方式存储
                cache.put(params[0], bitmap);
                //存在缓存文件中
                saveCachFile(params[0], bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return bitmap;
        }
        //如果新县城中执行了publishProgress(values);
        //就会执行此方法实现一条一条的更新ui
        @Override
        protected void onProgressUpdate(Void... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
        }
        //doInBackground执行return后马上执行ui线程
        //并把结果传递给此方法execute(url)
        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (listener !=null) {
                //线程结束后返回图片和路径
                listener.imageLoadOk(result);
            }
        }
    }
    /**
     * 异步加载网络图片
     * @param url	图片在网络中的路径
     */
    private void getBitmapAsync(String url){
        //自定义的异步加载类
        ImageAsyncTask imageAsyncTask = new ImageAsyncTask();
        //执行加载
        imageAsyncTask.execute(url);
    }
}
