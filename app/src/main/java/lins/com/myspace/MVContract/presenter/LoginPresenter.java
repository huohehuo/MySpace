package lins.com.myspace.MVContract.presenter;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import lins.com.myspace.MVContract.LoginContract;
import lins.com.myspace.entity.User;
import lins.com.myspace.util.UserPrefs;

/**
 * Created by LINS on 2017/2/11.
 * Please Try Hard
 */
public class LoginPresenter implements LoginContract.LoginPresenter{
    private LoginContract.LoginView loginView;
    public LoginPresenter(LoginContract.LoginView loginView) {
        this.loginView = loginView;
    }
    public LoginPresenter() {

    }
    public void login(String name,String pwd){
        loginView.showProgress();
        BmobUser.loginByAccount(name, pwd, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (user !=null){
                    loginView.hideProgress();
                    loginView.showMessage("登录成功");
                    UserPrefs.getInstance().setObjectId(user.getObjectId());//保存用户的ObjectId
                    loginView.successLogin();
                }else{
                    loginView.showMessage("error。。。。");
                    //loginView.errorFinish();
                    loginView.hideProgress();
                }
            }
        });
    }
    public void registerToLogin(String name,String pwd){
        BmobUser.loginByAccount(name, pwd, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (user !=null){
                    UserPrefs.getInstance().setObjectId(user.getObjectId());//保存用户的ObjectId
                }else{
                    loginView.showMessage("error。。。。");
                    loginView.hideProgress();
                   // loginView.errorFinish();
                }
            }
        });
    }

}
