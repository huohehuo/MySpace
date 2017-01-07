package lins.com.myspace.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import lins.com.myspace.R;
import lins.com.myspace.entity.DiaryInfo;
import lins.com.myspace.model.DiaryManager;
import lins.com.myspace.model.TextBoxManager;

/**
 * Created by LINS on 2016/12/22.
 * Please Try Hard
 */
public class DealDiaryAdapter extends RecyclerView.Adapter<DealDiaryAdapter.MainViewHolder>{
    private LayoutInflater inflater;
    private Context context;
    private List<DiaryInfo> mDatas;

    private OnItemMainClick onItemMainClick;
    private TextBoxManager textBoxManager;
    public interface OnItemMainClick{
        void onItemClick(View view, int position, DiaryInfo tb_diary);
        boolean onItemLongClick(View view,int position, DiaryInfo tb_diary);
    }
    public void setOnItemMainClick(OnItemMainClick listener){
        this.onItemMainClick = listener;
    }

    public DealDiaryAdapter(Context context, List<DiaryInfo> datas){
        this.context = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(context);
        textBoxManager = new TextBoxManager(context);
    }
    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view = inflater.inflate(R.layout.item_main_rc,parent,false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deal_diary,parent,false);
        MainViewHolder mainViewHolder = new MainViewHolder(view);
        return mainViewHolder;
    }

    @Override
    public void onBindViewHolder(final MainViewHolder holder, int position) {
       final DiaryInfo tb_diary =  mDatas.get(position);

        if (!tb_diary.toString().equals("")){
            holder.tv_title.setText(tb_diary.getTitle());
        }
        if (onItemMainClick != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //如果用参数position，会在调用addData添加item数据的时候会出现position值都是一样的情况
                    int layoutPosition = holder.getLayoutPosition();
                    onItemMainClick.onItemClick(holder.itemView,layoutPosition,tb_diary);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int layoutPosition = holder.getLayoutPosition();
                    onItemMainClick.onItemLongClick(holder.itemView,layoutPosition,tb_diary);
                    return true;
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    public void updata(){
        this.mDatas = DiaryManager.query();

        notifyDataSetChanged();
        //notifyItemInserted(pos);
        // notifyItemChanged(pos);注意不是这个方法，不然不会有动画效果
    }

    public void addData(int pos){
        //mDatas.add(pos,"add a text");
        //notifyItemInserted(pos);
        // notifyItemChanged(pos);注意不是这个方法，不然不会有动画效果
    }
    public void delData(DiaryInfo diaryInfo){
        DiaryManager.deleteById(diaryInfo);
        notifyDataSetChanged();
    }

    class MainViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title;
        public MainViewHolder(View view){
            super(view);
            tv_title = (TextView) view.findViewById(R.id.tv_deal_diary_title);

        }
    }
}
