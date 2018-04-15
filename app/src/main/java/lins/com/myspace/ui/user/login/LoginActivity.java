package lins.com.myspace.ui.user.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import lins.com.myspace.MVContract.LoginContract;
import lins.com.myspace.MVContract.presenter.LoginPresenter;
import lins.com.myspace.R;
import lins.com.myspace.ui.MarkActivity;
import lins.com.myspace.ui.user.register.RegisterActivity;
import lins.com.myspace.util.ActivityUtil;

public class LoginActivity extends AppCompatActivity implements LoginContract.LoginView{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_Username)
    EditText etUsername;
    @BindView(R.id.et_Password)
    EditText etPassword;
    @BindView(R.id.tv_forgetPassword)
    TextView tvForgetPassword;
    @BindView(R.id.btn_Login)
    Button btnLogin;
    private ProgressDialog mDialog;
    private ActivityUtil mActivityUtils;
    private String mUsername,mPassword;

    private Unbinder mUnbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUnbinder= ButterKnife.bind(this);

        mActivityUtils = new ActivityUtil(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.login);
        }
        etUsername.addTextChangedListener(textWatcher);
        etPassword.addTextChangedListener(textWatcher);
    }
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mUsername = etUsername.getText().toString();
            mPassword = etPassword.getText().toString();
            boolean canLogin=!(TextUtils.isEmpty(mUsername)||
                    TextUtils.isEmpty(mPassword));
            btnLogin.setEnabled(canLogin);
        }
    };
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.btn_Login,R.id.btn_regis})
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_Login:
//                if (RegexUtils.verifyUsername(mUsername)!=RegexUtils.VERIFY_SUCCESS){
//                    AlertDialogFragment.getInstances(
//                            getString(R.string.username_error),
//                            getString(R.string.username_rules)
//                    ).show(getSupportFragmentManager(),"usernameError");
//                    return;
//                }
//                if (RegexUtils.verifyPassword(mPassword)!=RegexUtils.VERIFY_SUCCESS){
//                    AlertDialogFragment.getInstances(getString(R.string.password_error),
//                            getString(R.string.password_rules)).show(getSupportFragmentManager(),"passwordError");
//                    return;
//                }
                new LoginPresenter(this).login(mUsername,mPassword);
                break;
            case R.id.btn_regis:
                mActivityUtils.startActivity(RegisterActivity.class);
                //finish();
                break;
        }

        //去做业务逻辑的处理
       // new LoginPresenter(this).login(new User(mUsername,mPassword));
    }
    //提供给别的Activity调用来启动该Activity
    public static void start(Context context){
        Intent intent = new Intent();
        intent.setClass(context,RegisterActivity.class);
        context.startActivity(intent);
    }
    //广播类，用于给别的Activity关闭当前Activity
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            unregisterReceiver(this);
            ((Activity)context).finish();
        }
    };


    //-------------实现接口----------------
    @Override
    public void showProgress() {
        mDialog = ProgressDialog.show(this,"登录","正在登录，请稍候~");
    }

    @Override
    public void hideProgress() {
        if (mDialog !=null){
            mDialog.dismiss();
        }
    }

    @Override
    public void showMessage(String msg) {
        mActivityUtils.showToast(msg);
    }

    @Override
    public void successLogin() {
        mActivityUtils.startActivity(MarkActivity.class);
        finish();
//        //发送广播，关闭MainActivity界面
//        Intent intent = new Intent(MainActivity.MAIN_ACTION);
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void errorFinish() {
        //finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册广播，让别的Activity能够关闭当前Activity
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("delLoginActivity");
        registerReceiver(this.broadcastReceiver,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.broadcastReceiver);
        mUnbinder.unbind();
    }

    @Override
    public void showToast(String msg) {

    }
}
