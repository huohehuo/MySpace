package lins.com.myspace.ui.user.account;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobUser;
import lins.com.myspace.R;
import lins.com.myspace.entity.User;

public class AccountActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.btn_account_out)
    Button btnAccountOut;

    private Unbinder mUnbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mUnbinder=ButterKnife.bind(this);

        //BmobUser bmobUser = BmobUser.getCurrentUser();
        User user = BmobUser.getCurrentUser(User.class);

        tvMsg.setText(user.getUsername()+"---"+user.getObjectId());

    }

    @OnClick(R.id.btn_account_out)
    public void onClick() {
        BmobUser.logOut();//清楚缓存用户对象
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
