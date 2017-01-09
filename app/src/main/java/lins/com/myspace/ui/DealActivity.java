package lins.com.myspace.ui;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.oushangfeng.marqueelayout.MarqueeLayout;
import com.oushangfeng.marqueelayout.MarqueeLayoutAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lins.com.myspace.R;
import lins.com.myspace.adapter.DealDiaryAdapter;
import lins.com.myspace.adapter.DealTBAdapter;
import lins.com.myspace.base.LinsApp;
import lins.com.myspace.base.MyBaseActivity;
import lins.com.myspace.entity.DiaryInfo;
import lins.com.myspace.entity.TextBoxInfo;
import lins.com.myspace.model.DiaryManager;
import lins.com.myspace.model.TextBoxManager;
import lins.com.myspace.ui.fragment.FragmentLeft;

/**
 * Created by LINS on 2017/1/6.
 * Please Try Hard
 */
public class DealActivity extends MyBaseActivity {

    @BindView(R.id.rc_deal_diary)
    RecyclerView rvDealDiary;
    @BindView(R.id.rv_deal_textbox)
    RecyclerView rvDealTextbox;
    @BindView(R.id.ml_warning)
    MarqueeLayout ml_Warning;

    private List<TextBoxInfo> mDatas;//文字包的数据
    private DealTBAdapter textBoxAdapter;

    private List<DiaryInfo> diaryDatas;
    private DealDiaryAdapter dealDiaryAdapter;

    private TextBoxManager textBoxManager;
    private DiaryManager diaryManager;
    private List<String> mList;
    private MarqueeLayoutAdapter<String> mSrcAdapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                TextBoxInfo textBoxInfo = (TextBoxInfo) msg.obj;
                rvDealDiary.setVisibility(View.VISIBLE);
                initDiary(textBoxInfo.getTbid());

            } else if (msg.what == 2) {
                textBoxAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal);
        ButterKnife.bind(this);
        initActionBar("管理", R.drawable.back, -1, listener);
        init();
        initMLayout();
        initTextBox();
    }

    private void initTextBox() {
        //查找并设置文字包的数据
        mDatas = TextBoxManager.query();
        textBoxAdapter = new DealTBAdapter(this, mDatas);
        rvDealTextbox.setAdapter(textBoxAdapter);
        //GridView形式
        // recycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvDealTextbox.setLayoutManager(new LinearLayoutManager(this));
        rvDealTextbox.setItemAnimator(new DefaultItemAnimator());

        textBoxAdapter.setmOnItemClickListener(new DealTBAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, TextBoxInfo textBoxInfo) {
                if (textBoxManager.getTextBoxCount() <= 0) {
                    Toast.makeText(LinsApp.getContext(), "文字包无数据，请在左侧菜单添加", Toast.LENGTH_SHORT).show();
                } else {
                    sendChange(textBoxInfo);
                }
               // Toast.makeText(LinsApp.getContext(), "" + textBoxInfo.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position, TextBoxInfo textBoxInfo) {
                if (diaryManager.checkCountByBelongId(textBoxInfo.getTbid())!=null) {
                    showToast("文字包中有数据，不能整个删除");
                    sendChange(textBoxInfo);
                } else{
                    textBoxAdapter.delData(textBoxInfo);
                    Message message = Message.obtain();
                    message.what = 2;
                    handler.sendMessage(message);
                    showToast("删除成功");
                }

//                textBoxAdapter.notifyItemRemoved(position);
                // PopupWindowUtil.getSystemUtils(LinsApp.getContext()).WarningDialog(isFinishIO);
            }
        });
    }

    private void initDiary(String belong) {
        //查找并设置文字包的数据
        diaryDatas = DiaryManager.findByBelong(belong);
        dealDiaryAdapter = new DealDiaryAdapter(this, diaryDatas);
        rvDealDiary.setAdapter(dealDiaryAdapter);
        //GridView形式
        // recycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvDealDiary.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        rvDealDiary.setItemAnimator(new DefaultItemAnimator());

        dealDiaryAdapter.setOnItemMainClick(new DealDiaryAdapter.OnItemMainClick() {
            @Override
            public void onItemClick(View view, int position, DiaryInfo tb_diary) {
                Intent intent = new Intent(LinsApp.getContext(), ShowActivity.class);
                intent.putExtra("diaryid", tb_diary.getDiaryId() + "");
                startActivity(intent);
                overridePendingTransition(R.anim.anim_alpha_come, R.anim.anim_alpha_gone);
                //Toast.makeText(LinsApp.getContext(), "获取标题：" + tb_diary.getDiaryId(), Toast.LENGTH_SHORT).show();
                rvDealDiary.setVisibility(View.GONE);
            }

            @Override
            public boolean onItemLongClick(View view, int position, DiaryInfo tb_diary) {
                dealDiaryAdapter.delData(tb_diary);
                rvDealDiary.setVisibility(View.GONE);
                Toast.makeText(LinsApp.getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void initMLayout() {
        mSrcAdapter = new MarqueeLayoutAdapter<String>(setMlayoutData()) {
            @Override
            protected int getItemLayoutId() {
                return R.layout.notice_item;
            }
            @Override
            protected void initView(View view, int position, String item) {
                ((TextView) view).setText(item);
            }
        };
        ml_Warning.setAdapter(mSrcAdapter);
        ml_Warning.start();
    }


    //发送Handler通知主线程更新UI
    private void sendChange(TextBoxInfo diaryInfo) {
        Message message = Message.obtain();
        message.what = 1;
        message.obj = diaryInfo;
        handler.sendMessage(message);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_left:
                    myFinish();
                    break;
            }
        }
    };

    //初始化
    private void init() {
        textBoxManager = new TextBoxManager(this);
        diaryManager = new DiaryManager(this);
    }
    private List<String> setMlayoutData() {
        mList = new ArrayList<String>();
        mList.add("Please Attention ！Long press will be erased ");
        mList.add("请注意！长按即删除");
        return mList;
    }
}
