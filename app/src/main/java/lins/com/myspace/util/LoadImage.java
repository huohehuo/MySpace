package lins.com.myspace.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import lins.com.myspace.R;
import lins.com.myspace.io.LoadImageIO;

/**
 * Created by LINS on 2016/12/28.
 * Please Try Hard
 */
public class LoadImage {

    private LoadImageIO loadImageIO;
    private Context context;
    public LoadImage(Context context,LoadImageIO loadImageIO){
        this.context = context;
        this.loadImageIO = loadImageIO;
    }
    public void getImage(String url, final ImageView imageView, final LoadImageIO loadImageIO){
        final RequestQueue queue = Volley.newRequestQueue(context);
        final LruCache<String,Bitmap> cache = new LruCache<>(1024*1024*3);
        ImageLoader imageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String url) {
                //获取图片
                Bitmap bitmap = cache.get(url);
                return bitmap;
            }
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                //把图片存入缓存中
                loadImageIO.getImage(bitmap,url);
                cache.put(url,bitmap);
            }
        });
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,
                0,0);
        imageLoader.get(url,listener,1024,1024);
    }


}
