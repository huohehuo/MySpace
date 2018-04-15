package lins.com.myspace.MVContract;

import java.util.List;

import lins.com.myspace.MVContract.base.IPresenter;
import lins.com.myspace.MVContract.base.IView;
import lins.com.myspace.bean.ZoneBean;

/**
 * Created by Administrator on 2018/4/15.
 */

public interface MarkContract {

     interface MarkIO extends IView{

        void showMarkData(List<ZoneBean> list);

    }
    interface MarkPresenter extends IPresenter{
         void getMarkData();
    }
}
