package lins.com.myspace.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;



import java.util.ArrayList;
import java.util.List;

import lins.com.myspace.util.LogUtil;

/**
 * 所有适配器的父类
 * Created by LINS on 2016/12/20.
 * Please Try Hard
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {

    //上下文
    private Context context;
    //定义布局过滤器
    protected LayoutInflater inflater;
    protected List<T> myList = new ArrayList<T>();//定义数据集合，并初始化

    //泛型集合
    protected ArrayList<T> list = new ArrayList<T>();

    public MyBaseAdapter(Context context){
        //初始化context，inflate对象
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    //清除所有数据
    public void clear(){
        myList.clear();
    }
    //查找所有数据
    public List<T> getAdapterData(){
        return list;
    }

    /**
     * 封装添加一条记录方法
     * @param t 一条数据的对象
     * @param isClearOld 是否清楚原数据
     */

    public void appendData(T t,boolean isClearOld){
        if (t ==null) {//非空验证
            return;
        }
        if (isClearOld) {//如果true清空原数据
            list.clear();
        }//添加一条新数据到最后
        list.add(t);
    }
    public void appendData(List<T> t,boolean isClearOld){
        if (t ==null) {//非空验证
            return;
        }
        if (isClearOld) {//如果true清空原数据
            list.clear();
        }//添加一条新数据到最后
        list.addAll(t);
    }
    /**
     *  添加多条记录
     * @param alist 数据集合
     * @param isClearOld 是否清空原数据
     */

    public void addendData(ArrayList<T> alist,boolean isClearOld){
        if (alist == null) {
            LogUtil.d("----无数据");
            return;
        }
        if (isClearOld) {
            list.clear();
        }
        list.addAll(alist);
    }

    /**
     * 添加一条记录到第一条
     * @param t
     * @param isClearOld
     */
    public void appendDataTop(T t,boolean isClearOld){
        if (t ==null) {//非空验证
            return;
        }
        if (isClearOld) {//如果true清空原数据
            list.clear();
        }//添加一条新数据到第一条
        list.add(0,t);
    }


    /**
     * 添加多条数据到顶部
     * @param alist 数据集合
     * @param isClearOld 是否清空原数据
     */
    public void addendDataTop(ArrayList<T> alist,boolean isClearOld){
        if (alist == null) {
            return;
        }
        if (isClearOld) {
            list.clear();
        }
        list.addAll(0,alist);
    }

    public void update(){
        //刷新适配器
        this.notifyDataSetChanged();
    }



    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }else{
            return list.size();
        }
    }

    @Override
    public T getItem(int position) {
        if (list==null) {
            return null;
        }
        //如果已经没有数据了返回空
        if (position>list.size()-1) {
            return null;
        }
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    //调用本身的抽象方法
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getMyView(position,convertView,parent);
    }
    //作为预留方法，定义为抽象方法，要求子类继承该基础类时，必须重写该方法
    public abstract View getMyView(int postion,View convertView,ViewGroup parent);

}
