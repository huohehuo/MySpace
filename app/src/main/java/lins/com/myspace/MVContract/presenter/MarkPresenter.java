package lins.com.myspace.MVContract.presenter;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import lins.com.myspace.MVContract.MarkContract;
import lins.com.myspace.bean.ZoneBean;

/**
 * Created by Administrator on 2018/4/11.
 */

public class MarkPresenter implements MarkContract.MarkPresenter{
    private MarkContract.MarkIO markIO;

    public MarkPresenter(MarkContract.MarkIO markIO){
        this.markIO = markIO;
    }


    @Override
    public void getMarkData() {
        BmobQuery<ZoneBean> query = new BmobQuery<>();
//        query.addWhereEqualTo("author",user);// 查询当前用户的所有帖子
        query.order("-updatedAt");
//        query.include("author");// 希望在查询帖子信息的同时也把发布人的信息查询出来
        query.findObjects(new FindListener<ZoneBean>() {
            @Override
            public void done(List<ZoneBean> list, BmobException e) {
                if (e==null){
                    markIO.showMarkData(list);
//                    showToast("获取sp列表成功");
//                    spAdapter.setDatas(list);
//                    for (ZoneBean zone:list) {
//                        zoneBeanList.add(zone);
//                    }
//                    adapter.notifyDataSetChanged();
//                    spAdapter.addAll(list);
                }else{
                    markIO.showToast("请求失败："+e.toString());
//                    showToast("获取sp列表-----失败");
//                    Toast.makeText(getActivity(), "获取信息失败", Toast.LENGTH_SHORT).show();
                }
//                binding.rySquare.setRefreshing(false);
            }
        });
    }
}
