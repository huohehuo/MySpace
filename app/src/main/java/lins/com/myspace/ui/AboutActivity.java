package lins.com.myspace.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lins.com.myspace.R;
import lins.com.myspace.base.MyBaseActivity;
import lins.com.myspace.view.KeywordsFlow;

/**
 * Created by LINS on 2017/1/3.
 * Please Try Hard
 */
public class AboutActivity extends MyBaseActivity {

    @BindView(R.id.keyflow_about)
    KeywordsFlow keyflowAbout;
    @BindView(R.id.imageView2)
    ImageView iv_icon;
    public static final String[] keywords = {"软件就那样啊", "就是哦", "心疼流量",
            "流量多啊？", "放狗咬作者啊",//
            "别用流量下OK?", "没地方差评哦 (=.=", "所以说你想怎样", "WIFI下下载蟹蟹", "今天写笔记了吗",//
            "今天有没有心情很棒", "你好啊 (o_o", "how ？", "I don't cares", "哦没写~",//
            "呵呵", "软件好烂哦但是作者没给地方你吐槽啊嘻嘻", "谢谢使用", "嗯~", "Thanks",//
            "明天见", "嘘~我要超进化了", "别动，我要卡机了", "哟嚯~", "有bug正常啊(*_*",//
            "嘘~", "发邮件给我啊反正发了我也不会看", "别点了，去写点东西吧", "如果作者有台帮一点的电脑", "版本就会更新得很漂亮",//
            "你说什么？(=_0", "嗯哼？", "谁？是雨荷吗", "低碳点，别乱卸载", "版本就会更新得很漂亮",//
            "哎", "明骚难防 (v_v", "那谁，你首胜掉了", "发邮件给作者有一分钱红包拿", "蟹蟹~",//
            "我不听我不听","无广告，不忽悠","作者不穷，只是没钱","作者是无业良民"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        initActionBar("关于", R.drawable.back,
                -1, clickListener);

        keyflowAbout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                keyflowAbout.rubKeywords();
                // keywordsFlow.rubAllViews();//清楚所有文字
                feedKeywordsFlow(keyflowAbout, keywords);
                //                keyflowAbout.go2Show(KeywordsFlow.ANIMATION_IN);//由外至内的动画
                keyflowAbout.go2Show(KeywordsFlow.ANIMATION_OUT);//由内至外的动画
                return false;
            }
        });
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_left:
                    finish();
                    overridePendingTransition(R.anim.anim_alpha_come, R.anim.anim_alpha_gone);
                    break;
            }
        }
    };

    @OnClick(R.id.imageView2)
    public void onClick() {
        keyflowAbout.rubKeywords();
        // keywordsFlow.rubAllViews();//清楚所有文字
        feedKeywordsFlow(keyflowAbout, keywords);
        keyflowAbout.go2Show(KeywordsFlow.ANIMATION_IN);//由外至内的动画
//            keywordsFlow.go2Show(KeywordsFlow.ANIMATION_OUT);//由内至外的动画
    }

    private static void feedKeywordsFlow(KeywordsFlow keywordsFlow, String[] arr) {
        Random random = new Random();
        for (int i = 0; i < KeywordsFlow.MAX; i++) {
            int ran = random.nextInt(arr.length);
            String tmp = arr[ran];
            keywordsFlow.feedKeyword(tmp);
        }
    }
}
