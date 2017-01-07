package lins.com.myspace.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

import lins.com.myspace.R;

/**
 * Created by LINS on 2016/12/20.
 * Please Try Hard
 */
public class PuBuAdapter extends RecyclerView.Adapter<PuBuAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    private Context context;
    private List<String> mDatas;
    private List<Integer> mHeight;
    public PuBuAdapter(Context context, List<String> datas){
        this.context = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(context);

        mHeight = new ArrayList<Integer>();
        for (int i = 0; i < mDatas.size(); i++) {
            mHeight.add((int) (100+Math.random()*300));
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ViewGroup.LayoutParams layoutParams=  holder.itemView.getLayoutParams();
        layoutParams.height = mHeight.get(position);
        holder.itemView.setLayoutParams(layoutParams);
        holder.tv.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public MyViewHolder(View v) {
            super(v);
            tv = (TextView) v.findViewById(R.id.tv_rc_msg);
        }
    }
}
