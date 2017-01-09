package lins.com.myspace.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import lins.com.myspace.R;
import lins.com.myspace.base.MyBaseActivity;

public class WelcomeActivity extends MyBaseActivity {

    @BindView(R.id.img_bg)
    ImageView imgBg;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.ln_logo_text)
    LinearLayout ln_logotext;

    private Animation animation,am_logo_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        animation= AnimationUtils.loadAnimation(this,R.anim.logo_and_text);
        //为动画设置监听事件
        animation.setAnimationListener(animationListener);
        //给logo图片设置渐变动画效果
        ln_logotext.startAnimation(animation);
    }
    //设置动画监听器AnimationListener,需要重写三个方法
    private Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            //当动画开始的时候执行
            ln_logotext.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            //当动画结束的时候执行

            openActivity(MainActivity.class);
            finish();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            //当动画重复的时候执行
        }
    };

    private void ShowlogoAndText(){

        AnimationSet amtSet = new AnimationSet(true);
        Animation amt = AnimationUtils.loadAnimation(this,R.anim.logo_and_text);
        amtSet.addAnimation(amt);
        ln_logotext.startAnimation(amt);
//        startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
//        finish();


    }
}
