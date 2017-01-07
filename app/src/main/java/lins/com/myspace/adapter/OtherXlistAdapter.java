package lins.com.myspace.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.util.List;

import lins.com.myspace.R;
import lins.com.myspace.base.MyBaseAdapter;

/**
 * Created by LINS on 2016/12/20.
 * Please Try Hard
 */
public class OtherXlistAdapter extends MyBaseAdapter<String> {
    private LayoutInflater layoutInflater;
    private List<String> list;
    public OtherXlistAdapter(Context context){
        // this.list = list;
        super(context);
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public View getMyView(int postion, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.item,null);
        TextView tv = (TextView) convertView.findViewById(R.id.tv_rc_msg);
        tv.setText(list.get(postion));
        return convertView;
    }

}
