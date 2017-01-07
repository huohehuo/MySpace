package lins.com.myspace.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import lins.com.myspace.R;
import lins.com.myspace.entity.TextBoxInfo;

/**
 * Created by LINS on 2016/10/14.
 * Please Try Hard
 */
public class MySpAdapter extends BaseAdapter {
    private List<TextBoxInfo> list;
    private LayoutInflater inflater;
    public MySpAdapter(){}
    public MySpAdapter(Context context,List<TextBoxInfo> list){
        this.list = list;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
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
        ViewHolder viewHolder=null;
        if(convertView == null){
            convertView=inflater.inflate(R.layout.item_spinner,null);
            viewHolder = new ViewHolder();
            viewHolder.txt = (TextView) convertView.findViewById(R.id.tv_spinner);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TextBoxInfo textBoxInfo = list.get(position);
        viewHolder.txt.setText(textBoxInfo.getName());
        return convertView;
    }
    class ViewHolder{
        private TextView txt;
    }
}
