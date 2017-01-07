package lins.com.myspace.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import lins.com.myspace.R;

/**
 * 用于页面顶部的bar
 * Created by LINS on 2016/12/27.
 * Please Try Hard
 */
public class ActionBarView extends LinearLayout {
    private ImageView iv_actionbar_left;// 左边按钮
    private ImageView iv_actionbar_right;// 右边按钮
    private TextView tv_actionbar_title;// 中间文本

    public ActionBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub

        //inflate将Layout形成一个以view类实现的对象，从指定的XML资源文件中填充一个新的视图层次结构
        inflate(context, R.layout.activity_actionbar, this);

        //初始化控件
        tv_actionbar_title = (TextView) findViewById(R.id.tv_title);
        iv_actionbar_left = (ImageView) findViewById(R.id.iv_left);
        iv_actionbar_right = (ImageView) findViewById(R.id.iv_right);
    }

    public void initActionBar(String title, int leftResID, int rightResID,
                              OnClickListener listenner) {
        //设置标题
        tv_actionbar_title.setText(title);
        if (leftResID == -1) {
            //setVisibility设置控件的可见性。View.INVISIBLE：不可见，但这个View仍然会占用在xml文件中所分配的布局空间。
            iv_actionbar_left.setVisibility(View.INVISIBLE);
        } else {
            iv_actionbar_left.setImageResource(leftResID);
            iv_actionbar_left.setOnClickListener(listenner);
        }
        if (rightResID == -1) {
            iv_actionbar_right.setVisibility(View.INVISIBLE);
        } else {
            iv_actionbar_right.setImageResource(rightResID);
            iv_actionbar_right.setOnClickListener(listenner);
        }
    }

}
