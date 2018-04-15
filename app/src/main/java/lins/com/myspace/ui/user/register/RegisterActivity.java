package lins.com.myspace.ui.user.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lins.com.myspace.R;
import lins.com.myspace.entity.User;
import lins.com.myspace.ui.MarkActivity;
import lins.com.myspace.util.ActivityUtil;

public class RegisterActivity extends AppCompatActivity implements RegisterView{
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_Username_reg)
    EditText etUsername;
    @BindView(R.id.et_Password_reg)
    EditText etPassword;
    @BindView(R.id.et_Confirm_reg)
    EditText etConfirm;
    @BindView(R.id.btn_Register_reg)
    Button btnRegister;

    private String mUsername;
    private String mPassword;
    private ActivityUtil mActivityUtils;
    private ProgressDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        mActivityUtils = new ActivityUtil(this);
        //toolbar的展示和返回箭头的监听
        setSupportActionBar(toolbar);
        if (getSupportActionBar() !=null){
            //激活左上角的返回图标（内部使用选项菜单处理）
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.register);
        }
        //EditText的输入监听
        etUsername.addTextChangedListener(textWatcher);
        etPassword.addTextChangedListener(textWatcher);
        etConfirm.addTextChangedListener(textWatcher);
    }
    //文本输入监听
    private TextWatcher textWatcher = new TextWatcher() {
        //文本变化前
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        //文本输入变化
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
        //文本输入之后，在这里处理
        @Override
        public void afterTextChanged(Editable s) {
            //处理文本输入之后的按钮事件
            mUsername = etUsername.getText().toString();
            mPassword = etPassword.getText().toString();
            String confirm = etConfirm.getText().toString();
            boolean canregister = !(TextUtils.isEmpty(mUsername)||
                    TextUtils.isEmpty(mPassword)||
                    TextUtils.isEmpty(confirm)
                            &&mPassword.equals(confirm));
            btnRegister.setEnabled(canregister);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            // 处理ActionBar的返回箭头事件
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_Register_reg)
    public void onClick() {
        //注册的视图和业务处理
//        if (RegexUtils.verifyUsername(mUsername)!=RegexUtils.VERIFY_SUCCESS){
//            // 显示一个错误的对话框
//            AlertDialogFragment.getInstances(
//                    getString(R.string.username_error),
//                    getString(R.string.username_rules)
//            ).show(getSupportFragmentManager(),"usernameError");
//            return;
//        }
//        if (RegexUtils.verifyPassword(mPassword)!=RegexUtils.VERIFY_SUCCESS){
//            // 显示一个错误的对话框
//            AlertDialogFragment.getInstances(
//                    getString(R.string.password_error),
//                    getString(R.string.password_rules)
//            ).show(getSupportFragmentManager(),"passwordError");
//            return;
//        }
        //提交注册信息
        User user = new User();
        user.setUsername(mUsername);
        user.setPassword(mPassword);
        new RegisterPresenter(this).register(user,mUsername,mPassword);
    }

    @Override
    public void showProgress() {
        mDialog = ProgressDialog.show(this,"注册","正在注册中。。。");
    }

    @Override
    public void hideProgress() {
        if (mDialog!=null){
            mDialog.dismiss();
        }
    }

    @Override
    public void showMessage(String msg) {
        mActivityUtils.showToast(msg);
    }

    @Override
    public void navigationToHome() {
        mActivityUtils.startActivity(MarkActivity.class);
        //发送广播，关闭LoginActivity
        Intent intent = new Intent();
        intent.setAction("delLoginActivity");
        sendBroadcast(intent);

        finish();
//        Intent intent = new Intent(MainActivity.MAIN_ACTION);
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
