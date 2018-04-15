package lins.com.myspace.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.AdapterView;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import lins.com.myspace.MVContract.MarkContract;
import lins.com.myspace.R;
import lins.com.myspace.adapter.MarkAdapter;
import lins.com.myspace.adapter.MySpAdapter;
import lins.com.myspace.base.MyBaseActivity;
import lins.com.myspace.bean.MarkBean;
import lins.com.myspace.bean.ZoneBean;
import lins.com.myspace.databinding.ActivityMarkBinding;
import lins.com.myspace.MVContract.presenter.MarkPresenter;

public class MarkActivity extends MyBaseActivity implements MarkContract.MarkIO{

    ActivityMarkBinding binding;
    private MarkAdapter adapter;
    private MySpAdapter spAdapter;
    private MarkContract.MarkPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_mark);
        binding.topBar.tvTopLeft.setText("添加");
        binding.topBar.tvTopTitle.setText("主页");
        binding.ryMark.setAdapter(adapter = new MarkAdapter(this));
//            binding.ryMark.setLayoutManager(new LinearLayoutManager(getActivity()));
//            binding.ryMark.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        binding.ryMark.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        presenter = new MarkPresenter(this);

        spAdapter = new MySpAdapter(this);
        binding.spZone.setAdapter(spAdapter);

        initEven();
        presenter.getMarkData();
//        getSpList();
    }

    private void getSpList(){
        BmobQuery<ZoneBean> query = new BmobQuery<>();
//        query.addWhereEqualTo("author",user);// 查询当前用户的所有帖子
        query.order("-updatedAt");
//        query.include("author");// 希望在查询帖子信息的同时也把发布人的信息查询出来
        query.findObjects(new FindListener<ZoneBean>() {
            @Override
            public void done(List<ZoneBean> list, BmobException e) {
                if (e==null){
                    showToast("获取sp列表成功");
                    spAdapter.setDatas(list);
//                    for (ZoneBean zone:list) {
//                        zoneBeanList.add(zone);
//                    }
                    adapter.notifyDataSetChanged();
//                    spAdapter.addAll(list);
                }else{
                    showToast("获取sp列表-----失败");
//                    Toast.makeText(getActivity(), "获取信息失败", Toast.LENGTH_SHORT).show();
                }
//                binding.rySquare.setRefreshing(false);
            }
        });

    }

    private void initEven(){
        binding.topBar.tvTopLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(AddZoneActivity.class);
            }
        });
        binding.ryMark.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                ShowPlanActivity.start(
//                        getActivity(),
//                        adapter.getAllData().get(position).getObjectId(),
//                        adapter.getAllData().get(position).getEssay(),
//                        adapter.getAllData().get(position).getCreatedAt());
            }
        });
        /**选项选择监听*/
        binding.spZone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                showToast("选择："+parent.getAdapter().getItem(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //获取数据
    protected void getData() {
        binding.ryMark.setRefreshing(true);

//        adapter.clear();
        List<MarkBean> list =new ArrayList<>();
        list.add(new MarkBean("asdf","asdf"));
        list.add(new MarkBean("asdf","asdf"));
        list.add(new MarkBean("asdf","asdf"));
        list.add(new MarkBean("asdf","asdf"));
        adapter.addAll(list);


//        User user = BmobUser.getCurrentUser(User.class);
//        BmobQuery<PlanBean> query = new BmobQuery<>();
//        query.addWhereEqualTo("author",user);// 查询当前用户的所有帖子
//        query.order("-updatedAt");
//        query.include("author");// 希望在查询帖子信息的同时也把发布人的信息查询出来
//        query.findObjects(new FindListener<PlanBean>() {
//            @Override
//            public void done(List<PlanBean> list, BmobException e) {
//                if (e==null){
//                    adapter.clear();
//                    adapter.addAll(list);
//                }else{
//                    Toast.makeText(getActivity(), "获取信息失败", Toast.LENGTH_SHORT).show();
//                }
//                binding.ryMark.setRefreshing(false);
//            }
//        });
                binding.ryMark.setRefreshing(false);
        //删除帖子
//        PlanBean p = new PlanBean();
//        p.remove("author");
//        p.update("ESIt3334", new UpdateListener() {
//
//            @Override
//            public void done(BmobException e) {
//                if(e==null){
//                    Log.i("bmob","成功");
//                }else{
//                    Log.i("bmob","失败："+e.getMessage());
//                }
//            }
//        });
    }

    @Override
    public void showToast(String error) {

    }

    @Override
    public void showMarkData(List<ZoneBean> list) {
        showToast("获取sp列表成功");
        spAdapter.setDatas(list);
        adapter.notifyDataSetChanged();
    }
}
