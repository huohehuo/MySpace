package lins.com.myspace.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lins.com.myspace.R;
import lins.com.myspace.adapter.FragmentMainAdapter;
import lins.com.myspace.base.LinsApp;
import lins.com.myspace.base.MyBaseActivity;
import lins.com.myspace.entity.DiaryInfo;
import lins.com.myspace.model.DiaryManager;
import lins.com.myspace.model.TextBoxManager;

public class TextBoxActivity extends MyBaseActivity {

    @BindView(R.id.rv_tb_show_diary)
    RecyclerView recyclerView;

    private DiaryManager diaryManager;
    private FragmentMainAdapter fragmentMainAdapter;
    private TextBoxManager textBoxManager;
    private String belongid;
    private List<DiaryInfo> listDiary;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_box);
        ButterKnife.bind(this);
        intent = getIntent();
        initActionBar(intent.getStringExtra("tbname"), R.drawable.back, R.drawable.calendar, clickListener);
        belongid = intent.getStringExtra("belong");
        listDiary = DiaryManager.findByBelong(belongid);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        fragmentMainAdapter = new FragmentMainAdapter(LinsApp.getContext(), listDiary);
        recyclerView.setAdapter(fragmentMainAdapter);
        //设置列表的点击事件
        fragmentMainAdapter.setOnItemMainClick(new FragmentMainAdapter.OnItemMainClick() {
            @Override
            public void onItemClick(View view, int position, DiaryInfo diaryInfo) {
                if (diaryInfo.getDiaryId().equals("404")) {
                    Toast.makeText(LinsApp.getContext(), "文字包无数据，请在左侧菜单添加", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(LinsApp.getContext(), ShowActivity.class);
                    intent.putExtra("diaryid", diaryInfo.getDiaryId() + "");
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_alpha_come, R.anim.anim_alpha_gone);
                    //Toast.makeText(LinsApp.getContext(), "获取标题：" + diaryInfo.getDiaryId(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_left:
                    myFinish();
                    break;
                case R.id.iv_right:
                    startActivity(new Intent(LinsApp.getContext(), DealActivity.class));
                    overridePendingTransition(R.anim.anim_alpha_come, R.anim.anim_alpha_gone);
                    break;
            }
        }
    };
}
