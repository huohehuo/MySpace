package lins.com.myspace.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



import java.util.List;

import lins.com.myspace.R;

/**
 * 下拉上拉ListView适配器
 * Created by LINS on 2016/12/20.
 * Please Try Hard
 */
public class XlistAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<String> list;
    public XlistAdapter(Context context, List<String> list){
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    public void updata(){
        notifyDataSetChanged();
    }
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.item,null);
        TextView tv = (TextView) convertView.findViewById(R.id.tv_rc_msg);
        tv.setText(list.get(position));
        return convertView;
    }

}
