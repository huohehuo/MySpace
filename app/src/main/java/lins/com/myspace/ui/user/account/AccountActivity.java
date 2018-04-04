package lins.com.myspace.ui.user.account;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import lins.com.myspace.R;
import lins.com.myspace.adapter.OnPageSelected;
import lins.com.myspace.adapter.ViewPagerAdapter;
import lins.com.myspace.databinding.ActivityAccountBinding;
import lins.com.myspace.entity.User;
import lins.com.myspace.ui.EditMyInfoActivity;
import lins.com.myspace.ui.fragment.map.MapFragment;
import lins.com.myspace.util.URL;
import lins.com.myspace.util.piccut.SelectPhotoDialog;
import lins.com.myspace.util.piccut.SelectPicUtil;

public class AccountActivity extends AppCompatActivity {

    private ActivityAccountBinding binding;
    public static final int REQUEST_CHANGE_AVATAR=0;
    List<Fragment> fragments;
    private SelectPhotoDialog selectPhotoDialog;
    View oldView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_account);
        // toolBar
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("个人信息");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initViewPager();
        initListener();
        oldView = binding.btnFriends;
        oldView.setSelected(true);
        selectPhotoDialog=new SelectPhotoDialog(AccountActivity.this, R.style.MyDialog);
        //BmobUser bmobUser = BmobUser.getCurrentUser();
        User user = BmobUser.getCurrentUser(User.class);
        binding.tvAccoutName.setText(BmobUser.getCurrentUser(User.class).getUsername());
        binding.ivLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, MapFragment.class));
            }
        });
        binding.ivOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser.logOut();//清楚缓存用户对象
                finish();
            }
        });
        binding.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditMyInfoActivity.start(AccountActivity.this);
            }
        });

    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();

    }

    private void initListener() {
        binding.btnFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTab(binding.btnFriends);
                binding.viewPager.setCurrentItem(0);
            }
        });
        binding.btnChats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTab(binding.btnChats);
                binding.viewPager.setCurrentItem(1);
            }
        });
        binding.viewPager.addOnPageChangeListener(pageChangeListener);
        binding.ivMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectPicUtil.showSelectPhoto(AccountActivity.this,REQUEST_CHANGE_AVATAR, URL.PATH_SELECT_AVATAR,selectPhotoDialog);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case REQUEST_CHANGE_AVATAR:
                    // Glide.with(EditMyInfoActivity.this).load(new File(URL.PATH_SELECT_AVATAR)).into(binding.ciAvatar);
                    Bitmap bitmap = BitmapFactory.decodeFile(URL.PATH_SELECT_AVATAR);
                    binding.ivMine.setImageBitmap(bitmap);
                    break;
            }
        }

    }

    private void initViewPager() {
        fragments = new ArrayList<>();
        fragments.add(new FriendFragment());
        fragments.add(new MsgFragment());
        binding.viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),fragments));
    }

    private void changeTab(View v){
        oldView.setSelected(false);
        oldView = v;
        oldView.setSelected(true);
    }

    OnPageSelected pageChangeListener = new OnPageSelected(){

        @Override
        public void onPageSelected(int position) {
            switch (position){
                case 0:
                    changeTab(binding.btnFriends);
                    break;
                case 1:
                    changeTab(binding.btnChats);
                    break;
            }
        }
    };
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

}
