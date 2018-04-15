package lins.com.myspace.MVContract;

import lins.com.myspace.MVContract.base.IPresenter;
import lins.com.myspace.MVContract.base.IView;

/**
 * Created by Administrator on 2018/4/15.
 */

public interface LoginContract {

     interface LoginView extends IView{
         void showProgress();
         void hideProgress();
         void showMessage(String msg);
         void successLogin();
         void errorFinish();

    }
    interface LoginPresenter extends IPresenter{
//         void getMarkData();
    }
}
