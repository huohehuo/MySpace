package lins.com.myspace.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lins.com.myspace.R;
import lins.com.myspace.bean.ZoneBean;

/**
 * Created by LINS on 2016/10/14.
 * Please Try Hard
 */
public class MySpAdapter extends BaseAdapter {
    List<ZoneBean> datas = new ArrayList<>();
    Context mContext;
    public MySpAdapter(Context context) {
        this.mContext = context;
    }

    public void setDatas(List<ZoneBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas==null?0:datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas==null?null:datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler hodler = null;
        if (convertView == null) {
            hodler = new ViewHodler();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_zone, null);
            hodler.mTextView = (TextView) convertView;
            convertView.setTag(hodler);
        } else {
            hodler = (ViewHodler) convertView.getTag();
        }

        hodler.mTextView.setText(datas.get(position).getZ_title());

        return convertView;
    }

    private static class ViewHodler{
        TextView mTextView;
    }
}
