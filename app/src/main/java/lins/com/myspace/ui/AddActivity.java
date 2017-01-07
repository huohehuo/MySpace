package lins.com.myspace.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lins.com.myspace.R;
import lins.com.myspace.adapter.MySpAdapter;
import lins.com.myspace.base.LinsApp;
import lins.com.myspace.base.MyBaseActivity;
import lins.com.myspace.entity.DiaryInfo;
import lins.com.myspace.entity.TextBoxInfo;
import lins.com.myspace.model.DiaryManager;
import lins.com.myspace.model.TextBoxManager;
import lins.com.myspace.util.LogUtil;
import lins.com.myspace.util.SystemUtil;


public class AddActivity extends MyBaseActivity {

    private Button btn_add;
    private EditText et_title, et_diary;
    private TextView tv_time;
    private ImageButton iv_back;
    private DiaryInfo tb_diary;
    private DiaryManager diaryManager;
    private TextBoxManager textBoxManager;
    private Intent intent;
    private String diaryId;
    private DiaryInfo diaryInfo;
    private Timer timer;
    private TimerTask timerTask;
    private Spinner niceSpinner;
    private List<TextBoxInfo> spList;
    private MySpAdapter spAdapter;
    private String getBeLongId;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                new Thread(runnable).start();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add);
        initView();
        spAdapter = new MySpAdapter(this, getData());
        niceSpinner.setAdapter(spAdapter);
        timer = new Timer();
        timerTask = new TimerTask() {

            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };
        timer.schedule(timerTask, 0, 1 * 1000);
        //下拉框点击事件
        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextBoxInfo textBoxInfo = (TextBoxInfo) parent.getItemAtPosition(position);
                getBeLongId = textBoxInfo.getTbid();//设置选中的文字包的id
                //Toast.makeText(LinsApp.getContext(), "选择了：" + textBoxInfo.getName() + getBeLongId, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //查找Box中的文字包，并赋给Spinner下拉控件
    public List<TextBoxInfo> getData() {
        spList = new ArrayList<TextBoxInfo>();
        spList = TextBoxManager.query();
        return spList;
    }
    //不停更新日期的显示
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String str = SystemUtil.getSystime();
                    tv_time.setText(str);
                }
            });

        }
    };

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_add_add:
                    if (!et_title.getText().toString().equals("") && !et_diary.getText().toString().equals("")) {
                        String time = SystemUtil.getSystime();
                        LogUtil.d(time);
                        tb_diary = new DiaryInfo(LinsApp.getInstance().getUUID(), et_title.getText().toString(), et_diary.getText().toString(),
                                time, getBeLongId);
                        diaryManager.insert(tb_diary);
                        LogUtil.d(tb_diary.toString());
                        showToast("添加成功");
                        Message message = Message.obtain();
                        message.what = 1;
                        handler.sendMessage(message);
                        myFinish();
                    } else {
                        showToast("添加失败");
                    }
                    break;
                case R.id.ib_add_back:
                    myFinish();
                    break;
            }
        }
    };

    private void initView() {
        btn_add = (Button) findViewById(R.id.btn_add_add);
        et_diary = (EditText) findViewById(R.id.et_diary);
        et_title = (EditText) findViewById(R.id.et_title);
        iv_back = (ImageButton) findViewById(R.id.ib_add_back);
        tv_time = (TextView) findViewById(R.id.tv_add_time);
        niceSpinner = (Spinner) findViewById(R.id.nice_spinner);

        btn_add.setOnClickListener(clickListener);
        iv_back.setOnClickListener(clickListener);

        diaryManager = new DiaryManager(LinsApp.getContext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null)
            timer.cancel();
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
