package lins.com.myspace.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lins.com.myspace.R;
import lins.com.myspace.databinding.ActivityMyinfoBinding;
import lins.com.myspace.entity.PickerViewBean;
import lins.com.myspace.util.URL;
import lins.com.myspace.util.piccut.SelectPhotoDialog;
import lins.com.myspace.util.piccut.SelectPicUtil;

/**
 * Created by LINS on 2017/3/1.
 */

public class EditMyInfoActivity extends AppCompatActivity{
    private static final int    REQUEST_NICKNAME = 0;
    private static final int    REQUEST_MOTTO    = 1;
    private static final String RESULT           = "result";
    private static final int REQUEST_CHANGE_AVATAR=2;

    ActivityMyinfoBinding binding;
    private SelectPhotoDialog selectPhotoDialog;
    String                       avatar;
    ArrayList<String>            constellationList;
    ArrayList<PickerViewBean>    provinces;
    ArrayList<ArrayList<String>> cities;
    OptionsPickerView locationPicker, constellationPicker;
    String sex = "1";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_myinfo);
        binding.topBar.tvTopRight.setText("保存");
        binding.topBar.tvTopTitle.setText("个人资料");
        selectPhotoDialog=new SelectPhotoDialog(EditMyInfoActivity.this, R.style.CustomDialog);
       // initInfo();
        initLocation();
        initConstellation();
        initPicker();
        initListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_NICKNAME:
                    binding.tvNickName.setText(data.getStringExtra(RESULT));
                    break;
                case REQUEST_MOTTO:
                    binding.tvMotto.setText(data.getStringExtra(RESULT));
                    break;
                case REQUEST_CHANGE_AVATAR:
                    // Glide.with(EditMyInfoActivity.this).load(new File(URL.PATH_SELECT_AVATAR)).into(binding.ciAvatar);
                    Bitmap bitmap = BitmapFactory.decodeFile(URL.PATH_SELECT_AVATAR);
                    binding.ciAvatar.setImageBitmap(bitmap);
                    /*Qiniu.uploadFile(URL.PATH_SELECT_AVATAR, new Qiniu.Callback() {
                        @Override
                        public void uploadResult(String remoteUrl, boolean ok, String error) {
                            if (!ok) {
                                Log.e("EditmyInfoActivity-----",error);
                                return;
                            }else{
                                Log.e("EditMyInfoActivity-----","成功上传。。。。");
                            }
                            selectPhotoDialog.hide();
                            avatar = remoteUrl;
                        }
                    });*/
                    break;
            }
        }
    }
    private void initListener() {
        binding.topBar.ivTopArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.topBar.tvTopRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.ciAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectPicUtil.showSelectPhoto(EditMyInfoActivity.this,REQUEST_CHANGE_AVATAR, URL.PATH_SELECT_AVATAR,selectPhotoDialog);
            }
        });

        binding.tvNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeNameActivity.start(EditMyInfoActivity.this, REQUEST_NICKNAME, binding.tvNickName.getText().toString());
            }
        });

        binding.tvMotto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeMottoActivity.start(EditMyInfoActivity.this, REQUEST_MOTTO, binding.tvMotto.getText().toString());
            }
        });

        binding.tvFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvFemale.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.female), null, null, null);
                setUnselected("0");
            }
        });

        binding.tvMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvMale.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.male), null, null, null);
                setUnselected("1");
            }
        });

        binding.tvKeepSecret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvKeepSecret.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.keep_secret_selected), null, null, null);
                setUnselected("2");
            }
        });

        binding.llLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationPicker.show();
            }
        });

        binding.llConstellation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constellationPicker.show();
            }
        });


        constellationPicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                binding.tvConstellation.setText(constellationList.get(options1));
            }
        });

        locationPicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                binding.tvLocation.setText(provinces.get(options1).getStr() + cities.get(options1).get(option2));
            }
        });
    }
    void initConstellation() {
        constellationList = new ArrayList<>();
        constellationList.add(getString(R.string.aries));
        constellationList.add(getString(R.string.taurus));
        constellationList.add(getString(R.string.gemini));
        constellationList.add(getString(R.string.cancer));
        constellationList.add(getString(R.string.leo));
        constellationList.add(getString(R.string.virgo));
        constellationList.add(getString(R.string.libra));
        constellationList.add(getString(R.string.scorpio));
        constellationList.add(getString(R.string.sagittarius));
        constellationList.add(getString(R.string.capricorn));
        constellationList.add(getString(R.string.aquarius));
        constellationList.add(getString(R.string.pisces));
    }

    void initLocation() {
        provinces = new ArrayList<>();
        cities = new ArrayList<>();
        List<String> provincesStrList = Arrays.asList(getString(R.string.provinces).split(","));
        for (String prov : provincesStrList) {
            provinces.add(new PickerViewBean(prov));
        }

        String[] citiesStrList = getResources().getStringArray(R.array.cities);
        for (String c : citiesStrList) {
            List<String> p_cities = Arrays.asList(c.split(","));
            ArrayList<String> pickerViewBeen = new ArrayList<>();
            for (String city : p_cities) {
                pickerViewBeen.add(city);
            }
            cities.add(pickerViewBeen);
        }
    }

    private void initPicker() {
        // 星座
        constellationPicker = new OptionsPickerView(this);
        constellationPicker.setPicker(constellationList);
        constellationPicker.setCyclic(false);
        constellationPicker.setSelectOptions(0);

        // 所在地
        locationPicker = new OptionsPickerView(this);
        locationPicker.setPicker(provinces, cities, true);
        locationPicker.setCyclic(false, false, false);
        locationPicker.setSelectOptions(0, 0);
    }

    void setUnselected(String sexNow){
        if (sex.equals(sexNow)) {
            return;
        }
        switch (sex) {
            case "0":
                // female
                binding.tvFemale.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.female_unselected),null,null,null);
                break;
            case "1":
                // male
                binding.tvMale.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.male_unselected),null,null,null);
                break;
            case "2":
                // keep secret
                binding.tvKeepSecret.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.keep_secret),null,null,null);
                break;
        }
        sex = sexNow;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (locationPicker.isShowing()) {
                locationPicker.dismiss();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, EditMyInfoActivity.class);
        context.startActivity(starter);
    }
}
