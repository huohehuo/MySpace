package lins.com.myspace.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

import lins.com.myspace.R;
import lins.com.myspace.adapter.TextBoxAdapter;

/**
 * Created by LINS on 2016/12/20.
 * Please Try Hard
 */
public class FragmentMsg extends Fragment {
    private View view;
    private EditText et_find;
    private TextView tv_shownothing;
    private RecyclerView recycler;
    private List<String> mDatas;
    private TextBoxAdapter fragmentMsgAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO 自动生成的方法存根
        view = inflater.inflate(R.layout.fragment_msg, null);
        et_find= (EditText) view.findViewById(R.id.et_find);
        recycler = (RecyclerView) view.findViewById(R.id.rc_msg);

        initDatas();

//        //fragmentMsgAdapter = new FragmentMsgAdapter(getActivity(),mDatas);
//        recycler.setAdapter(fragmentMsgAdapter);

        //设置RecyclerView的布局管理
        LinearLayoutManager lins = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        recycler.setLayoutManager(lins);
        //设置默认动画
        recycler.setItemAnimator(new DefaultItemAnimator());
//        //listView普通形式
//        recycler.setLayoutManager(new LinearLayoutManager(this));
//        //GridView形式
//        recycler.setLayoutManager(new GridLayoutManager(this,3));
//        //横向GridView
//        recycler.setLayoutManager(new StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.HORIZONTAL));
        //设置瀑布流ListVIew（注：适配器有些许不同，）
//        mAdapter = new PuBuAdapter(this,mDatas);
//        recycler.setAdapter(mAdapter);
        //recycler.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
//        mAdapter.delData(0);
//        mAdapter.addData(1);
        return view;
    }


    private void initDatas() {
        mDatas = new ArrayList<String>();
        for (int i =0;i<20;i++){
            mDatas.add("hello....");
        }
    }
}
