package lins.com.myspace.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import cn.bmob.v3.BmobUser;
import lins.com.myspace.R;
import lins.com.myspace.base.MyBaseActivity;
import lins.com.myspace.databinding.ActivityWelcomeBinding;
import lins.com.myspace.ui.user.login.LoginActivity;

public class WelcomeActivity extends MyBaseActivity {
    private ActivityWelcomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding =DataBindingUtil.setContentView(WelcomeActivity.this,R.layout.activity_welcome);

        checkUser();
    }


    private void checkUser(){
        BmobUser bmobUser = BmobUser.getCurrentUser();
        if(bmobUser != null){
            openActivity(MarkActivity.class);
            finish();
        }else{
            openActivity(LoginActivity.class);
            finish();
            //缓存用户对象为空时， 可打开用户注册界面…
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
