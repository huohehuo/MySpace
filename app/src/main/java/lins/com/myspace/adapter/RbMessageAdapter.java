package lins.com.myspace.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import lins.com.myspace.R;
import lins.com.myspace.entity.ChatMessageInfo;
import lins.com.myspace.io.LoadImageIO;
import lins.com.myspace.util.ImageUtil;
import lins.com.myspace.util.LoadImage;
import lins.com.myspace.util.LogUtil;

/**
 * Created by LINS on 2016/12/26.
 * Please Try Hard
 */
public class RbMessageAdapter extends BaseAdapter {

    private Bitmap bitmap2;
    private ListView listView;
    private LayoutInflater mInflater;
    private List<ChatMessageInfo> mDatas;
    private ImageUtil imageUtil;
    private LoadImage loadImage;
    private ViewHolder viewHolder = null;
    public RbMessageAdapter(Context context, List<ChatMessageInfo> mDatas,ListView lv) {
        this.mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
        listView = lv;
       // imageUtil = new ImageUtil(context,listener);
        loadImage = new LoadImage(context,loadImageIO);
    }

    @Override
    public int getCount() {
        // TODO 自动生成的方法存根
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO 自动生成的方法存根
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO 自动生成的方法存根
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessageInfo chatMessage = mDatas.get(position);
        if (chatMessage.getType() == ChatMessageInfo.Type.INCOMING) {
            return 0;
        }
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        // TODO 自动生成的方法存根
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            //通过itemType设置不同的布局
            if (getItemViewType(position) == 0) {
                convertView = mInflater.inflate(R.layout.item_rb_left, parent,
                        false);
                viewHolder = new ViewHolder();
                viewHolder.mDate = (TextView) convertView
                        .findViewById(R.id.txt_time_left);
                viewHolder.mMsg = (TextView) convertView
                        .findViewById(R.id.txt_lefttxt);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_rb_image);
            } else {
                convertView = mInflater.inflate(R.layout.item_rb_right, parent,
                        false);
                viewHolder = new ViewHolder();
                viewHolder.mDate = (TextView) convertView
                        .findViewById(R.id.txt_time_right);
                viewHolder.mMsg = (TextView) convertView
                        .findViewById(R.id.txt_righttxt);
            }
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置数据
        ChatMessageInfo chatMessage = mDatas.get(position);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        viewHolder.mDate.setText(df.format(chatMessage.getDate()));
        String text= chatMessage.getMsg();


        String urlImage = chatMessage.getUrl();
      //  if (urlImage!=null&&!urlImage.equals("")){
        if (chatMessage.getMsg().contains("http://")){
            viewHolder.mMsg.setVisibility(View.GONE);
            viewHolder.imageView.setVisibility(View.VISIBLE);
            viewHolder.imageView.setTag("asdfasdf"+text);
            loadImage.getImage(chatMessage.getMsg(), viewHolder.imageView,loadImageIO);
        }else{
            viewHolder.mMsg.setVisibility(View.VISIBLE);
            viewHolder.mMsg.setText(chatMessage.getMsg());
            if (viewHolder.imageView!=null){
                viewHolder.imageView.setVisibility(View.GONE);
            }
        }
//        loadImage.getImage(chatMessage.getMsg(), viewHolder.imageView,loadImageIO);
//            viewHolder.imageView.setVisibility(View.VISIBLE);
           // viewHolder.imageView.setTag("http://www.baicu.com"+urlImage);
            //LogUtil.d("适配器中获取到图片链接：",urlImage);
            //Bitmap bitmap = imageUtil.getBitmap(urlImage);
//            if (bitmap !=null){
//                viewHolder.imageView.setVisibility(View.VISIBLE);
//                viewHolder.imageView.setImageBitmap(bitmap);
//                LogUtil.d("获得图片");
//            }
   //     }


        return convertView;
    }

    private LoadImageIO loadImageIO = new LoadImageIO() {
        @Override
        public void getImage(Bitmap bitmap,String url) {
           // if (viewHolder.imageView !=null){
          //  viewHolder.imageView.setImageBitmap(bitmap);

            //}else{
                //viewHolder.imageView.setVisibility(View.VISIBLE);
                setPic(bitmap);
               ImageView iv = (ImageView)listView.findViewWithTag(url);
               if (iv != null){

                iv.setImageBitmap(getPic());
               }
            //}
        }
    };
//    private ImageUtil.ImageLoadListener listener = new ImageUtil.ImageLoadListener() {
//
//        @Override
//        public void imageLoadOk(Bitmap bitmap) {
//            //得到某个listview的图片，通过一部加载显示图片
//                setPic(bitmap);
//
//        }
//    };
    private void setPic(Bitmap bitmap){
        this.bitmap2 = bitmap;
    }
    private Bitmap getPic(){
        return bitmap2;
    }
    private final class ViewHolder {
        TextView mDate;
        TextView mMsg;
        ImageView imageView;
    }
}
