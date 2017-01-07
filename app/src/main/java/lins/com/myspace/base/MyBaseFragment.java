package lins.com.myspace.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import lins.com.myspace.R;
import lins.com.myspace.util.LogUtil;
import lins.com.myspace.view.ActionBarView;


/**
 * 继承FragmentActivity
 * 父类activity用来调试打印activity生命周期
 * Created by LINS on 2016/12/20.
 * Please Try Hard
 */
public class MyBaseFragment extends FragmentActivity {
    public static int screen_w,screen_h;
    private Toast toast;
    private Dialog dialog;
    //public RequestQueue mQueue;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        screen_w = getWindowManager().getDefaultDisplay().getWidth();
        screen_h = getWindowManager().getDefaultDisplay().getHeight();

//		 if (mQueue == null) {
//				mQueue = Volley.newRequestQueue(this);
//			}

    }

    public void myFinish(){
        super.finish();
        //设置切换的动画，两个动画在res/anim中定义
        overridePendingTransition(R.anim.anim_alpha_come, R.anim.anim_alpha_gone);
    }
    public void openActivity(Class<?> pClass,Bundle bundle,Uri uri){
        Intent intent = new Intent(this,pClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (uri != null) {
            intent.setData(uri);
        }
        startActivity(intent);
        //增加动画
        overridePendingTransition(R.anim.anim_alpha_come, R.anim.anim_alpha_gone);

    }
    //通过class名字进行界面跳转，只带Bundle数据
    public void openActivity(Class<?> pClass,Bundle bundle){
        openActivity(pClass,bundle,null);
    }
    //通过class名字进行界面跳转
    public void openActivity(Class<?> pClass){
        openActivity(pClass,null,null);
    }
    //通过action字符串进行界面跳转
    public void openActivity(String action){
        openActivity(action,null,null);
    }
    //通过action字符串进行页面跳转，只带Bundle数据
    public void openActivity(String action,Bundle bundle){
        openActivity(action,bundle,null);
    }
    //通过action字符串进行界面跳转，并带Bundle和Uri数据
    public void openActivity(String action,Bundle bundle,Uri uri){
        Intent intent = new Intent(action);
        if (bundle !=null) {
            intent.putExtras(bundle);
        }
        if (uri !=null) {
            intent.setData(uri);
        }
        startActivity(intent);
        //增加动画
        overridePendingTransition(R.anim.anim_alpha_come, R.anim.anim_alpha_gone);
    }

    //------------------公共功能封装

    public void showToast(int resId){
        showToast(getString(resId));
    }
    /**
     * 自定义Toast消息展示
     * @param msg
     */
    public void showToast(String msg){
        if (toast == null) {
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        }
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }

    /**
     * 显示一个等待对话框
     * @param context	上下文
     * @param msg	消息
     * @param cancelable	是否可以取消
     */
    public void showLoadingDialog(Context context, String msg, boolean cancelable){
        LayoutInflater inflater = LayoutInflater.from(context);
        //得到加载view
        View v = inflater.inflate(R.layout.dialog_loading, null);
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);
        //自定义图片
        ImageView iv_img = (ImageView) v.findViewById(R.id.iv_dialogloading_img);

        //提示文字
        TextView tv_msg = (TextView) v.findViewById(R.id.tv_dialogloading_msg);
        //加载动画

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.loading_animation);
        //使用ImageView显示动画
        iv_img.startAnimation(animation);
        //使用ImageView显示动画
        if (msg !=null) {
            //设置加载信息
            tv_msg.setText(msg);
        }
        //创建自定义样式dialog
        dialog = new Dialog(context,R.style.loading_dialog);
        //创建dialog样式，不可以用返回键取消
        dialog.setCancelable(cancelable);
        //设置布局
        dialog.setContentView(layout,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        //显示dialog
        dialog.show();
    }
    public void cancelDialog(){
        if (null !=dialog) {
            dialog.dismiss();
        }
    }

    protected void initActionBar(String title, int leftResID, int rightResID,
                                 View.OnClickListener listenner) {
        try {
            ActionBarView actionBar = (ActionBarView) findViewById(R.id.actionBar);
            actionBar.initActionBar(title, leftResID, rightResID, listenner);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    /*
     * 生命周期方法（非 Javadoc）
     * @see android.support.v4.app.FragmentActivity#onStart()
     */
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        LogUtil.d(LogUtil.TAG,"application onStart");
    }
    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        LogUtil.d(LogUtil.TAG,"application onRestart");
    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //JPushInterface.onResume(this);
        LogUtil.d(LogUtil.TAG,"application onResume");
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        //JPushInterface.onPause(this);
        LogUtil.d(LogUtil.TAG,"application onPause");
    }
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        LogUtil.d(LogUtil.TAG,"application onStop");
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        LogUtil.d(LogUtil.TAG,"application onDestroy");
    }
    /**
     * 用于避免需要的信息在Activity被回收后被清空
     * (如：原Activity中输入框有数据了，如果activity不小心被系统回收，可用该方法保存相应数据，
     * 并且在原activity中的判断
     * if(savedInstanceState !=null){
     * 	String tempData=savedInstanceState.getString("data");
     * }
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
//		String tempData = "save data";
//		outState.putString("data", tempData);
        LogUtil.d(LogUtil.TAG,getClass().getSimpleName()+"application onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onRestoreInstanceState(savedInstanceState);
        LogUtil.d(LogUtil.TAG,getClass().getSimpleName()+"application onRestoreInstanceState");
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        LogUtil.d(LogUtil.TAG,getClass().getSimpleName()+"application onConfigurationChanged");
    }

}