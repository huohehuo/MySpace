package lins.com.myspace.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oushangfeng.marqueelayout.MarqueeLayout;
import com.oushangfeng.marqueelayout.MarqueeLayoutAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import lins.com.myspace.R;
import lins.com.myspace.adapter.FragmentMainAdapter;
import lins.com.myspace.base.LinsApp;
import lins.com.myspace.entity.DiaryInfo;
import lins.com.myspace.model.DiaryManager;
import lins.com.myspace.model.TextBoxManager;
import lins.com.myspace.ui.AddActivity;
import lins.com.myspace.ui.ChatShowActivity;
import lins.com.myspace.ui.MainActivity;
import lins.com.myspace.ui.ShowActivity;
import lins.com.myspace.util.LogUtil;
import lins.com.myspace.util.SystemUtil;
import lins.com.myspace.view.KeywordsFlow;
import lins.com.xrecyclerview.ProgressStyle;
import lins.com.xrecyclerview.XRecyclerView;

/**
 * Created by LINS on 2016/12/20.
 * Please Try Hard
 */
public class FragmentMain extends Fragment {

    @BindView(R.id.iv_main_rb)
    ImageView ivMainRb;
    @BindView(R.id.rl_main_rb)
    RelativeLayout rlRb;
    @BindView(R.id.tv_main_rb_txt)
    MarqueeLayout tvMainRbTxt;
    private View view;
    private MarqueeLayoutAdapter<String> mSrcAdapter;
    private KeywordsFlow keywordsFlow;
    private XRecyclerView recyclerView;
    private FragmentMainAdapter fragmentMainAdapter;
    private List<DiaryInfo> mDatas;
    private DiaryManager diaryManager;
    private TextBoxManager textBoxManager;
    private ImageButton imageButton;
    private List<String> tips;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mSrcAdapter.notifyDataSetChanged();
            fragmentMainAdapter.updata();
            tvMainRbTxt.start();
        }
    };

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, null);
        initView(view);
        LogUtil.d(diaryManager.query().toString());

        dealRecyclerView(container);//设置RecyclerView的相关事件

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textBoxManager.getTextBoxCount() <= 0) {
                    Toast.makeText(LinsApp.getContext(), "文字包无数据，请在左侧菜单添加", Toast.LENGTH_SHORT).show();
                    MainActivity.slidingMenu.showMenu();
                } else {
                    LogUtil.d("获取UUID", LinsApp.getInstance().getUUID());
                    Intent intent = new Intent(LinsApp.getContext(), AddActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    LinsApp.getContext().startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.anim_alpha_come, R.anim.anim_alpha_gone);
                }
            }
        });
        return view;
    }

    private void dealRecyclerView(ViewGroup container) {
        //LinearLayoutManager lins = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        //recyclerView.setLayoutManager(lins);
        //recyclerView.setLayoutManager(new LinearLayoutManager(LinsApp.getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        recyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        //为RecyclerView添加头部布局
        View header = LayoutInflater.from(LinsApp.getContext()).inflate(R.layout.item_rc_head, (ViewGroup) container.findViewById(android.R.id.content), false);
        ButterKnife.bind(this, header);

        //设置滚动的标语
        mSrcAdapter = new MarqueeLayoutAdapter<String>(setMFData()) {
            @Override
            protected int getItemLayoutId() {
                return R.layout.notice_item;
            }

            @Override
            protected void initView(View view, int position, String item) {
                ((TextView) view).setText(item);
            }
        };
        tvMainRbTxt.setAdapter(mSrcAdapter);
        tvMainRbTxt.start();
        //图标的点击跳转
        ivMainRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LinsApp.getContext(), ChatShowActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                LinsApp.getContext().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_alpha_come, R.anim.anim_alpha_gone);
            }
        });
        recyclerView.addHeaderView(header);//为列表添加头部
        //设置列表的上拉下拉刷新
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LinsApp.getContext(), "下拉刷新", Toast.LENGTH_SHORT).show();
                        tips.add(0, "刷新了一下心情");
                        setHandler();
                        recyclerView.refreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LinsApp.getContext(), "下拉刷新", Toast.LENGTH_SHORT).show();
                        recyclerView.loadMoreComplete();
                        // recyclerView.setNoMore(true);//刷新完毕后会有“没有了”文字在底部
                    }
                }, 1000);
            }
        });
        fragmentMainAdapter = new FragmentMainAdapter(getActivity(), initData());
        recyclerView.setAdapter(fragmentMainAdapter);
        //设置列表的点击事件
        fragmentMainAdapter.setOnItemMainClick(new FragmentMainAdapter.OnItemMainClick() {
            @Override
            public void onItemClick(View view, int position, DiaryInfo diaryInfo) {
                if (diaryInfo.getDiaryId().equals("404")){
                    Toast.makeText(LinsApp.getContext(), "文字包无数据，请在左侧菜单添加", Toast.LENGTH_SHORT).show();
                    MainActivity.slidingMenu.showMenu();
                }else{
                    Intent intent = new Intent(LinsApp.getContext(), ShowActivity.class);
                    intent.putExtra("diaryid", diaryInfo.getDiaryId() + "");
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.anim_alpha_come, R.anim.anim_alpha_gone);
                    //Toast.makeText(getActivity(), "获取标题：" + diaryInfo.getDiaryId(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public List<String> setMFData() {
        tips = new ArrayList<String>();
        tips.add("今天心情怎样呢？");
        tips.add("无聊的话跟我聊聊天吧。。。我算挺聪明的");
        return tips;
    }

    private void initView(View view) {
        recyclerView = (XRecyclerView) view.findViewById(R.id.rc_main);
        imageButton = (ImageButton) view.findViewById(R.id.ib_main_add);
        diaryManager = new DiaryManager(LinsApp.getContext());
        textBoxManager = new TextBoxManager(LinsApp.getContext());
        mDatas = new ArrayList<DiaryInfo>();
        // mDatas.add(diaryManager.query());
    }

    private List<DiaryInfo> initData() {
        mDatas = diaryManager.query();
        return mDatas;
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d("碎片onResume", "更新listview");
        setHandler();
    }

    //发送通知Handler更新UI
    private void setHandler() {
        Message message = Message.obtain();
        message.what = 1;
        handler.sendMessage(message);
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d("碎片onPause", "aaaaaaa");

    }
}
