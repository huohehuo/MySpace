package lins.com.myspace.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import lins.com.myspace.R;
import lins.com.myspace.adapter.TextBoxAdapter;
import lins.com.myspace.base.LinsApp;
import lins.com.myspace.entity.TextBoxInfo;
import lins.com.myspace.io.ChangeView;
import lins.com.myspace.io.IsFinishIO;
import lins.com.myspace.model.DiaryManager;
import lins.com.myspace.model.TextBoxManager;
import lins.com.myspace.ui.AboutActivity;
import lins.com.myspace.ui.DealActivity;
import lins.com.myspace.ui.TextBoxActivity;
import lins.com.myspace.ui.user.account.AccountActivity;
import lins.com.myspace.ui.user.login.LoginActivity;
import lins.com.myspace.util.LogUtil;
import lins.com.myspace.util.PopupWindowUtil;


/**
 * Created by LINS on 2016/12/20.
 * Please Try Hard
 */
public class FragmentLeft extends Fragment {

    @BindView(R.id.iv_left_icon)
    ImageView iv_icon;
    @BindView(R.id.tv_left_textbox_num)
    TextView tv_textbox_num;
    @BindView(R.id.tv_left_text_num)
    TextView tv_text_num;
    @BindView(R.id.btn_thr_ud)
    Button btn;
    @BindView(R.id.rc_left)
    RecyclerView recycler;
    @BindView(R.id.btn_left_data)
    Button btnData;
    @BindView(R.id.tv_btn_thr)
    TextView tvBtnThr;
    @BindView(R.id.tv_btn_left_data)
    TextView tvBtnLeftData;
    private View view;
    private List<TextBoxInfo> mDatas;//文字包的数据
    private TextBoxAdapter textBoxAdapter;
    private TextBoxManager textBoxManager;
    private DiaryManager diaryManager;
    private BmobUser bmobUser;
    private TextBoxInfo isDelTb;
    private int isDelTbNum;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                setDataNumber();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_left, container, false);
        ButterKnife.bind(this, view);
        init();
        setDataNumber();
        //查找并设置文字包的数据
        mDatas = TextBoxManager.query();
        textBoxAdapter = new TextBoxAdapter(getActivity(), mDatas);
        recycler.setAdapter(textBoxAdapter);
        //GridView形式
        // recycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        recycler.setItemAnimator(new DefaultItemAnimator());

        textBoxAdapter.setmOnItemClickListener(new TextBoxAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, TextBoxInfo textBoxInfo) {
                Intent intent = new Intent(LinsApp.getContext(), TextBoxActivity.class);
                intent.putExtra("belong", textBoxInfo.getTbid() + "");
                intent.putExtra("tbname", textBoxInfo.getName());
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_alpha_come, R.anim.anim_alpha_gone);
                Toast.makeText(LinsApp.getContext(), "" + textBoxInfo.getName(), Toast.LENGTH_SHORT).show();
            }

//            @Override
//            public void onItemLongClick(View view, int position, TextBoxInfo textBoxInfo) {
//                FragmentLeft.this.isDelTb = textBoxInfo;
//                FragmentLeft.this.isDelTbNum = position;
//                // PopupWindowUtil.getSystemUtils(LinsApp.getContext()).WarningDialog(isFinishIO);
//            }
        });

        return view;
    }

    //初始化
    private void init() {
        textBoxManager = new TextBoxManager(getActivity());
        diaryManager = new DiaryManager(getActivity());
    }

    //设置数据：有多少个文字包和文字
    private void setDataNumber() {
        init();
        tv_textbox_num.setText(textBoxManager.getTextBoxCount() + "");
        tv_text_num.setText(diaryManager.getTextCount() + "");
    }

    //发送Handler通知主线程更新UI
    private void sendChange() {
        Message message = Message.obtain();
        message.what = 1;
        handler.sendMessage(message);
    }

    final ChangeView changeView = new ChangeView() {
        @Override
        public void changeData(String string) {
            TextBoxInfo whoIS = new TextBoxInfo();
            whoIS.setTbid(LinsApp.getInstance().getUUID());
            whoIS.setName(string);
            textBoxManager.insert(whoIS);
            textBoxAdapter.addData(0, whoIS);
            sendChange();
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d("左侧栏onResume");
        bmobUser = BmobUser.getCurrentUser();
        if (bmobUser !=null){
            iv_icon.setImageResource(R.mipmap.ic_launcher);
        }else{
            iv_icon.setImageResource(R.drawable.user2);
        }
        //mDatas = TextBoxManager.query();
        textBoxAdapter.updata();
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d("左边onPause");
    }

    @OnClick({R.id.iv_left_icon, R.id.btn_thr_ud, R.id.btn_left_data, R.id.tv_btn_thr, R.id.tv_btn_left_data, R.id.iv_lefttop_about, R.id.iv_lefttop_loc})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left_icon:
                if (bmobUser!=null){
                    startActivity(new Intent(getActivity(), AccountActivity.class));
                    getActivity().overridePendingTransition(R.anim.anim_alpha_come, R.anim.anim_alpha_gone);
                }else{
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().overridePendingTransition(R.anim.anim_alpha_come, R.anim.anim_alpha_gone);
                }

                break;
            case R.id.btn_thr_ud:
            case R.id.tv_btn_thr:
                PopupWindowUtil.getSystemUtils(getActivity()).popupWindow(btn, changeView);
                break;
            case R.id.btn_left_data:
            case R.id.tv_btn_left_data:
                startActivity(new Intent(getActivity(), DealActivity.class));
                getActivity().overridePendingTransition(R.anim.anim_alpha_come, R.anim.anim_alpha_gone);

//                Intent intent = new Intent(LinsApp.getContext(), ChatShowActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                LinsApp.getContext().startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.anim_alpha_come, R.anim.anim_alpha_gone);
                break;
            case R.id.iv_lefttop_about:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                getActivity().overridePendingTransition(R.anim.anim_alpha_come, R.anim.anim_alpha_gone);
                break;
            case R.id.iv_lefttop_loc:
                break;
        }
    }

}
