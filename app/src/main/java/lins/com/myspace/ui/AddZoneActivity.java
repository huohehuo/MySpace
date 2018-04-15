package lins.com.myspace.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import lins.com.myspace.R;
import lins.com.myspace.base.MyBaseActivity;
import lins.com.myspace.bean.ZoneBean;
import lins.com.myspace.databinding.ActivityAddZoneBinding;
import lins.com.myspace.entity.User;

public class AddZoneActivity extends MyBaseActivity {

    ActivityAddZoneBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(AddZoneActivity.this,R.layout.activity_add_zone);

        binding.topBar.tvTopTitle.setText("添加zone");
        binding.topBar.tvTopRight.setText("确定");

        binding.topBar.tvTopRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushData();
            }
        });
    }

    private void pushData(){
        final User user = BmobUser.getCurrentUser(User.class);
        final ZoneBean zone = new ZoneBean(
                "标题",
                "china",
                "口令",
                "时间"
        );
        zone.setAuthor(user);

        zone.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    showToast("发布成功");
                    finish();
                }else{
                    showToast("发布失败");
                }
            }
        });
    }

//    /**
//     * 菜单、返回键响应
//     */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        // TODO Auto-generated method stub
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            // 按两次退出
//            exitBy2Click();
//        }
//        return false;
//    }
//    /**
//     * 双击退出函数
//     */
//    private static Boolean isExit = false;
//
//    private void exitBy2Click() {
//        Timer tExit = null;
//        if (isExit == false) {
//            isExit = true; // 准备退出
//            Toast.makeText(this, "注意！再按一次将关闭页面", Toast.LENGTH_SHORT).show();
//            tExit = new Timer();
//            tExit.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    isExit = false; // 取消退出
//                }
//            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
//
//        } else {
//            finish();
//            this.overridePendingTransition(R.anim.anim_alpha_come, R.anim.anim_alpha_gone);
//        }
//    }
}
