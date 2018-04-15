package lins.com.myspace.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import lins.com.myspace.R;
import lins.com.myspace.base.App;
import lins.com.myspace.base.MyBaseActivity;
import lins.com.myspace.entity.DiaryInfo;
import lins.com.myspace.model.DiaryManager;
import lins.com.myspace.model.TextBoxManager;
import lins.com.myspace.util.LogUtil;
import lins.com.myspace.util.SystemUtil;
import lins.com.myspace.view.ActionBarView;

/**
 * Created by LINS on 2016/12/30.
 * Please Try Hard
 */
public class ShowActivity extends MyBaseActivity {
    @BindView(R.id.actionBar)
    ActionBarView actionBar;
    @BindView(R.id.tv_show_title)
    TextView tvShowTitle;
    @BindView(R.id.tv_show_diary)
    TextView tvShowDiary;
    @BindView(R.id.tv_show_time)
    TextView tvShowTime;
    @BindView(R.id.tv_show_belong)
    TextView tvShowBelong;
    @BindView(R.id.rl_show)
    RelativeLayout rlShow;
    @BindView(R.id.ed_show_title)
    EditText edShowTitle;
    @BindView(R.id.ed_show_time)
    TextView edShowTime;
    @BindView(R.id.ed_show_diary)
    EditText edShowDiary;
    @BindView(R.id.rl_show_change)
    RelativeLayout rlShowChange;
    @BindView(R.id.ed_show_belong)
    TextView edShowBelong;
    private Intent intent;
    private String diaryId;
    private DiaryManager diaryManager;
    private TextBoxManager textBoxManager;
    private DiaryInfo diaryInfo;
    private boolean isChange = false;
    private int ishas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_show);
        ButterKnife.bind(this);
        initActionBar("笔记", R.drawable.back, R.drawable.edit, clickListener);
        init();
        intent = getIntent();
        diaryId = intent.getStringExtra("diaryid");
        //根据笔记ID获得笔记对应的数据
        diaryInfo = diaryManager.findById(diaryId);
        LogUtil.d(diaryInfo.toString());
        showData();
    }

    private void showData() {
        init();
        tvShowTitle.setText(diaryInfo.getTitle());
        tvShowDiary.setText(diaryInfo.getDiary());
        tvShowTime.setText(diaryInfo.getTime());
        String time = textBoxManager.findById(diaryInfo.getBelong());
        tvShowBelong.setText(" @" + time);
    }

    private void changeData() {
        edShowTitle.setText(diaryInfo.getTitle());
        edShowDiary.setText(diaryInfo.getDiary());
        edShowTime.setText(diaryInfo.getTime());
        String belong = textBoxManager.findById(diaryInfo.getBelong());
        edShowBelong.setText(" @" + belong);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_left:
                    myFinish();
                    showToast("返回");

                    break;
                case R.id.iv_right:
                    rlShow.setVisibility(View.GONE);
                    rlShowChange.setVisibility(View.VISIBLE);
                    if (!isChange) {
                        changeData();
                        isChange = true;
                    } else {
                        if (isTextNull()) {
                            DiaryInfo diaryInfo = new DiaryInfo(diaryId, edShowTitle.getText().toString(), edShowDiary.getText().toString(), SystemUtil.getSystime());
                            diaryManager.updateById(diaryInfo);
                            showToast("修改成功");
                            isChange = false;
                            myFinish();
                        }
                    }
                    break;
            }
        }
    };

    private boolean isTextNull() {
        //如果俩输入框不为空
        return (!TextUtils.isEmpty(edShowTitle.getText().toString())) && (!TextUtils.isEmpty(edShowDiary.getText().toString()));
    }

    private void init() {
        diaryManager = new DiaryManager(App.getContext());
        textBoxManager = new TextBoxManager(App.getContext());
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 按两次退出
            exitBy2Click();
        }
        return false;
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "注意！再按一次将关闭页面", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            this.overridePendingTransition(R.anim.anim_alpha_come, R.anim.anim_alpha_gone);
        }
    }
}
