package lins.com.myspace.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import lins.com.myspace.R;
import lins.com.myspace.base.LinsApp;
import lins.com.myspace.base.MyBaseFragment;
import lins.com.myspace.ui.fragment.FragmentLeft;
import lins.com.myspace.ui.fragment.FragmentMain;
import lins.com.myspace.ui.fragment.FragmentMsg;
import lins.com.myspace.ui.fragment.map.MapFragment;
import lins.com.myspace.ui.fragment.FragmentUser;
import lins.com.myspace.view.SlidingMenu;


public class MainActivity extends MyBaseFragment {
    // 按两次退出
    private boolean isFirstExit = true;
    //声明各个碎片，并用父类Fragment接收
    private Fragment fragmentMain, fragmentLocation, fragmentLeft, fragmentMsg,fragmentUser;
    public static SlidingMenu slidingMenu;

    private ImageView iv_left, iv_right;
    private TextView tv_title;
    private TextView tv_one, tv_two, tv_thr;
    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initActionBar("首页", R.drawable.bank,
                -1, clickLisener);
        initSlidingMenu();
        showFragmentMain();
    }

    // 展示主页面新闻列表
    // 在左右菜单中调用 ((ActivityMain)getActivity()).showFragmentMain();方法可以达到切换碎片的目的
    public void showFragmentMain() {
        setTitle("主页");
        slidingMenu.showContent();
        // 先判断新闻列表界面是否需要创建
        if (fragmentMain == null) {
            fragmentMain = new FragmentMain();
        }
        // 将当前主界面替换为新闻列表界面
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragmentMain).commit();
    }
    public void showLocation() {
        setTitle("地图");
        slidingMenu.showContent();
        if (fragmentLocation == null) {
            fragmentLocation = new MapFragment();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragmentLocation).commit();
    }

    public void showMenuOne() {
        showFragmentMain();

    }

    public void showMenuTwo() {
        setTitle("消息");
        slidingMenu.showContent();
        if (fragmentMsg == null) {
            fragmentMsg = new FragmentMsg();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragmentMsg).commit();
    }
    public void showMenuThr() {
        setTitle("消息");
        slidingMenu.showContent();
        if (fragmentUser == null) {
            fragmentUser = new FragmentUser();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragmentUser).commit();
    }

    private void initSlidingMenu() {
        // TODO 自动生成的方法存根
        slidingMenu = new SlidingMenu(this);
        // 设置侧拉栏为双向侧拉栏
        slidingMenu.setMode(SlidingMenu.LEFT);
        // 设置全屏范围都可以打开菜单栏
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        // 设置菜单栏显示时偏移量，可定义在res/values/dimens文件中
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);// 偏移量
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        // 设置左右菜单栏的布局
        slidingMenu.setMenu(R.layout.left);
        slidingMenu.setSecondaryMenu(R.layout.right);
        // 设置左右菜单栏具体的
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fgm_left, fragmentLeft).commit();
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fgm_right, fragmentRight).commit();
    }
    // 设置标题
    private void setTitle(String txt) {
        //tv_title.setText(txt);
    }

    //初始化控件等
    private void init() {
        fragmentLeft = new FragmentLeft();
        fragmentLocation = new MapFragment();
        fragmentMain = new FragmentMain();
        fragmentMsg = new FragmentMsg();
        fragmentUser = new FragmentUser();

//        iv_left = (ImageView) findViewById(R.id.iv_left);
//        iv_right = (ImageView) findViewById(R.id.iv_right);
//        tv_title = (TextView) findViewById(R.id.tv_title);


    }

    private void showPup(View view) {
        View popView = LayoutInflater.from(LinsApp.getContext()).inflate(R.layout.item_pop, null);
        Button add = (Button) popView.findViewById(R.id.btn_main_add);
        Button find = (Button) popView.findViewById(R.id.btn_main_find);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    openActivity(AddActivity.class);

            }
        });
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        final PopupWindow popupWindow = new PopupWindow(popView, GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupWindow.setBackgroundDrawable(LinsApp.getContext().getResources().getDrawable(R.drawable.res8));
        popupWindow.setAnimationStyle(R.anim.loading_animation);
        popupWindow.setWidth(400);
        popupWindow.setHeight(200);
        popupWindow.showAsDropDown(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 调用双击退出函数
            if (slidingMenu.isMenuShowing()) {
                slidingMenu.showContent();
            } else {
                // 按两次退出
                exitBy2Click();
            }
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
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }


    private View.OnClickListener clickLisener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            // TODO 自动生成的方法存根
            switch (arg0.getId()) {
                case R.id.tv_item_menu_one:
                    showFragmentMain();
                    break;
                case R.id.tv_item_menu_two:
                    showMenuTwo();
                    break;
                case R.id.tv_item_menu_three:
                    showMenuThr();
                    break;
                case R.id.iv_left:
                    slidingMenu.showMenu();
                    break;
                case R.id.iv_right:
                    openActivity(ChatActivity.class);
                    break;
                default:
                    break;
            }
        }
    };




}
