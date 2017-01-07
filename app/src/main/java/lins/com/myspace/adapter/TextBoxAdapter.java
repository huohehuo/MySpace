package lins.com.myspace.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import lins.com.myspace.R;
import lins.com.myspace.entity.TextBoxInfo;
import lins.com.myspace.model.DiaryManager;
import lins.com.myspace.model.TextBoxManager;

/**
 * Created by LINS on 2016/12/20.
 * Please Try Hard
 */
public class TextBoxAdapter extends RecyclerView.Adapter<TextBoxAdapter.MsgViewHolder>{
    private LayoutInflater inflater;
    private Context context;
    private List<TextBoxInfo> mDatas;


    public interface OnItemClickListener{
        void onItemClick(View view, int position,TextBoxInfo textBoxInfo);
       // void onItemLongClick(View view,int position,TextBoxInfo textBoxInfo);
    }
    private OnItemClickListener mOnItemClickListener;
    public void setmOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    public TextBoxAdapter(Context context, List<TextBoxInfo> datas){
        this.context = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MsgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item,parent,false);
        MsgViewHolder myViewHolder = new MsgViewHolder(view);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(final MsgViewHolder holder, int position) {
        final TextBoxInfo str = mDatas.get(position);
        holder.tv.setText(str.getName());
        if (mOnItemClickListener !=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //如果用参数position，会在调用addData添加item数据的时候会出现position值都是一样的情况
                    int layoutPosition = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView,layoutPosition,str);
                }
            });
//            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    int layoutPosition = holder.getLayoutPosition();
//                    mOnItemClickListener.onItemLongClick(holder.itemView,layoutPosition,str);
//                    return false;
//                }
//            });
        }
    }
    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void updata(){
        this.mDatas = TextBoxManager.query();
        notifyDataSetChanged();
    }

    public void addData(int pos,TextBoxInfo whoIS){
        mDatas.add(pos,whoIS);
        notifyItemInserted(pos);
        // notifyItemChanged(pos);注意不是这个方法，不然不会有动画效果
    }
    public void delData(int pos){
        mDatas.remove(pos);
        notifyItemRemoved(pos);
    }

    class MsgViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public MsgViewHolder(View v) {
            super(v);
            tv = (TextView) v.findViewById(R.id.tv_rc_msg);
        }
    }
}
