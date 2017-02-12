package lins.com.myspace.ui.user.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobUser;
import lins.com.myspace.R;
import lins.com.myspace.base.LinsApp;
import lins.com.myspace.entity.User;
import lins.com.myspace.ui.fragment.map.MapFragment;

public class AccountActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.btn_account_out)
    Button btnAccountOut;
    @BindView(R.id.iv_account_icon)
    ImageView ivAccountIcon;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mUnbinder = ButterKnife.bind(this);
        // toolBar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("个人信息");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //BmobUser bmobUser = BmobUser.getCurrentUser();
        User user = BmobUser.getCurrentUser(User.class);

        tvMsg.setText(user.getUsername() + "---" + user.getObjectId());

    }

    // toolbar上返回箭头的处理
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.btn_account_out,R.id.iv_account_icon})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_account_out:
                BmobUser.logOut();//清楚缓存用户对象
                finish();
                break;
            case R.id.iv_account_icon:
                startActivity(new Intent(LinsApp.getContext(), MapFragment.class));
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
