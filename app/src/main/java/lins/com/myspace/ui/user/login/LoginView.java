package lins.com.myspace.ui.user.login;

/**
 * Created by LINS on 2017/2/11.
 * Please Try Hard
 */
public interface LoginView {
    void showProgress();
    void hideProgress();
    void showMessage(String msg);
    void navigationToHome();
}
