package lins.com.myspace.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import lins.com.myspace.R;
import lins.com.myspace.base.App;
import lins.com.myspace.bean.MarkBean;

/**
 * Created by Administrator on 2018/4/5.
 */

public class MarkAdapter extends RecyclerArrayAdapter<MarkBean> {
    public MarkAdapter(Context context) {
        super(context);
    }
//
//    public MarkAdapter(Context context, MarkBean[] objects) {
//        super(context, objects);
//    }
//
//    public MarkAdapter(Context context, List<MarkBean> objects) {
//        super(context, objects);
//    }
//    @Override
//    public int getViewType(int position) {
//        return Integer.valueOf(getAllData().get(position).getType());
//
//    }
    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MarkHolder(parent);
//        if (viewType==2){
//            Log.e("holder","返回图文布局");
//            return new MarkHolder(parent);
//        }else{
//            Log.e("holder","返回--文字--布局");
//            return new MainHolderForTxt(parent);
//        }

    }


    class MarkHolder extends BaseViewHolder<MarkBean> {

        private TextView time;
        private TextView eesay;
        private ImageView img_bg;
        private ImageView favour;
        private TextView num;
        public MarkHolder(ViewGroup parent) {
            super(parent, R.layout.item_mark);
            time = $(R.id.tv_time);
            eesay = $(R.id.tv_main_essay);
            num = $(R.id.tv_favour);
            favour = $(R.id.iv_favour);
            img_bg = $(R.id.iv_main_bg);
        }

        @Override
        public void setData(MarkBean data) {
            super.setData(data);
            eesay.setText(data.getName());
            time.setText(data.getWrold());
//            num.setText(data.getFavour().get__op());

            favour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(App.getContext(), "喜欢+1", Toast.LENGTH_SHORT).show();
                }
            });

//            Glide.with(getContext())
//                    .load(data.getBg_pic())
//                    .placeholder(R.drawable.dog)
//                    .centerCrop()
//                    .into(img_bg);

        }
    }




//    //纯文字布局
//    class MainHolderForTxt extends BaseViewHolder<PlanBean> {
//
//        private TextView time;
//        private TextView eesay;
//        private ImageView favour;
//        private TextView num;
//        public MainHolderForTxt(ViewGroup parent) {
//            super(parent, R.layout.item_plan_for_txt);
//            time = $(R.id.tv_time);
//            eesay = $(R.id.tv_main_essay);
//            num = $(R.id.tv_favour);
//            favour = $(R.id.iv_favour);
//        }
//
//        @Override
//        public void setData(PlanBean data) {
//            super.setData(data);
//            eesay.setText(data.getEssay());
//            time.setText(data.getCreatedAt());
////            num.setText(data.getFavour().get__op());
//
//            favour.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(App.getContext(), "喜欢+1", Toast.LENGTH_SHORT).show();
//                }
//            });
//
////            Glide.with(getContext())
////                    .load(data.getPic())
////                    .placeholder(R.mipmap.ic_launcher)
////                    .centerCrop()
////                    .into(imageView);
//
//        }
//    }
}
