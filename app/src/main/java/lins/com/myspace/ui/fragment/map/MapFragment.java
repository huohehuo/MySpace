package lins.com.myspace.ui.fragment.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import lins.com.myspace.R;
import lins.com.myspace.base.LinsApp;
import lins.com.myspace.util.ActivityUtil;


/**
 * Created by LINS on 2016/12/20.
 * Please Try Hard
 */
public class MapFragment extends AppCompatActivity implements MapMvpView {
    private static final int ACCESS_LOCATION = 100;
    @BindView(R.id.map_frame)
    FrameLayout mMapFrame;
    @BindView(R.id.iv_located)
    ImageView mIvLocated;
    @BindView(R.id.btn_HideHere)
    Button mBtnHideHere;
    @BindView(R.id.centerLayout)
    RelativeLayout mCenterLayout;
    @BindView(R.id.tv_located)
    TextView mTvLocated;
    @BindView(R.id.tv_currentLocation)
    TextView mTvCurrentLocation;
    @BindView(R.id.iv_toTreasureInfo)
    ImageView mIvToTreasureInfo;
    @BindView(R.id.et_treasureTitle)
    EditText mEtTreasureTitle;
    @BindView(R.id.layout_bottom)
    FrameLayout mLayoutBottom;
    @BindView(R.id.treasureView)
    TreasureView mTreasureView;
    @BindView(R.id.hide_treasure)
    RelativeLayout mHideTreasure;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_write_loc)
    ImageButton ivWriteLoc;

    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private static LatLng mCurrentLocation;
    private LatLng mCurrentStatus;
    private Marker mCurrentMarker;
    private MapView mMapView;

    private boolean mIsFirst = true;

    private ActivityUtil mActivityUtils;
    // private MapPresenter mPresenter;
    private GeoCoder mGeoCoder;
    private String mCurrentAddr;
    private static String mLocationAddr;

    private Unbinder unbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);
        //        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_DENIED){
//            // 需要动态获取权限的
//            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},ACCESS_LOCATION);
//        }else {
//            // 不需要去动态获取权限
//        }

        // toolBar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

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

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        unbinder=ButterKnife.bind(this);

        //mPresenter = new MapPresenter(this);

        mActivityUtils = new ActivityUtil(this);

        //初始化百度地图
        initMapView();
        //初始化定位相关
        initLocation();
        // 地理编码的初始化相关
        initGeoCoder();
    }

    // 地理编码的初始化相关
    private void initGeoCoder() {
        // 初始化：创建出一个地理编码查询的对象
        mGeoCoder = GeoCoder.newInstance();
        // 设置查询结果的监听:地理编码的监听
        mGeoCoder.setOnGetGeoCodeResultListener(mGeoCoderResultListener);
    }

    // 地理编码的监听
    private OnGetGeoCoderResultListener mGeoCoderResultListener = new OnGetGeoCoderResultListener() {
        // 得到地理编码的结果：地址-->经纬度
        @Override
        public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
        }

        // 得到反向地理编码的结果：经纬度-->地址
        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
            // 当前是拿到结果以后给标题录入的卡片上面的文本设置上
            if (reverseGeoCodeResult == null) {
                mCurrentAddr = "未知的位置";
                return;
            }
            // 拿到反地理编码得到的位置信息
            mCurrentAddr = reverseGeoCodeResult.getAddress();
            // 将地址信息给TextView设置上
            mTvCurrentLocation.setText(mCurrentAddr);
        }
    };

    // 初始化定位相关
    private void initLocation() {
        // 前置：激活定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 第一步，初始化LocationClient类:LocationClient类必须在主线程中声明，需要Context类型的参数。
        mLocationClient = new LocationClient(LinsApp.getContext());

        // 第二步，配置定位SDK参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开GPS
        option.setCoorType("bd09ll");// 设置百度坐标类型，默认gcj02，会有偏差，bd9ll百度地图坐标类型，将无偏差的展示到地图上
        option.setIsNeedAddress(true);// 需要地址信息
        mLocationClient.setLocOption(option);

        // 第三步，实现BDLocationListener接口
        mLocationClient.registerLocationListener(mBDLocationListener);

        // 第四步，开始定位
        mLocationClient.start();
    }

    // 定位监听
    private BDLocationListener mBDLocationListener = new BDLocationListener() {

        // 获取到定位结果
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            // 如果没有拿到结果，重新请求：部分机型会失败
            if (bdLocation == null) {
                mLocationClient.requestLocation();
                return;
            }
            // 定位结果的经纬度
            double latitude = bdLocation.getLatitude();
            double longitude = bdLocation.getLongitude();
            //定位的经纬度的类
            mCurrentLocation = new LatLng(latitude, longitude);
            String currentAddr = bdLocation.getAddrStr();

            Log.i("TAG", "定位的位置：" + currentAddr + "，经纬度：" + latitude + "," + longitude);
            MyLocationData data = new MyLocationData.Builder()
                    .latitude(latitude)//定位数据展示的经纬度
                    .longitude(longitude)
                    .accuracy(100f) //定位精度大小
                    .build();
            //定位数据展示到地图上
            mBaiduMap.setMyLocationData(data);
            // 移动到定位的地方，在地图上展示定位的信息：位置
            // 做一个判断：第一次进入页面自动移动，其他时候点击按钮移动
            if (mIsFirst) {
                moveToLocation();
                mIsFirst = false;
            }
        }
    };

    // 初始化百度地图
    private void initMapView() {
        // 设置地图状态
        MapStatus mapStatus = new MapStatus.Builder()
                .zoom(19)// 3--21：默认的是12
                .overlook(0)// 俯仰的角度
                .rotate(0)// 旋转的角度
                .build();
        // 设置百度地图的设置信息
        BaiduMapOptions options = new BaiduMapOptions()
                .mapStatus(mapStatus)
                .compassEnabled(true)// 是否显示指南针
                .zoomGesturesEnabled(true)// 是否允许缩放手势
                .scaleControlEnabled(false)// 不显示比例尺
                .zoomControlsEnabled(true)// 显示缩放的控件
                ;
        // 创建
        mMapView = new MapView(this, options);

        // 在布局上添加地图控件：0，代表第一位
        mMapFrame.addView(mMapView, 0);
        // 拿到地图的操作类(控制器：操作地图等都是使用这个)
        mBaiduMap = mMapView.getMap();
        // 设置地图状态的监听
        mBaiduMap.setOnMapStatusChangeListener(mStatusChangeListener);
        // 设置地图上标注物的点击监听
        mBaiduMap.setOnMarkerClickListener(mMarkerClickListener);
    }

    // 标注物的点击监听
    private BaiduMap.OnMarkerClickListener mMarkerClickListener = new BaiduMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            if (mCurrentMarker != null) {
                if (mCurrentMarker != marker) {
                    mCurrentMarker.setVisible(true);
                }
                mCurrentMarker.setVisible(true);
            }
            mCurrentMarker = marker;
            // 点击Marker展示InfoWindow，当前的覆盖物不可见
            mCurrentMarker.setVisible(false);

            // 创建一个InfoWindow
            InfoWindow infoWindow = new InfoWindow(dot_expand, marker.getPosition(), 0, new InfoWindow.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick() {
                    // 切换回普通的视图
                    changeUIMode(UI_MODE_NORMAL);
//                    if (mCurrentMarker!=null){
//                        mCurrentMarker.setVisible(true);
//                    }
//                    // 隐藏InfoWindow
//                    mBaiduMap.hideInfoWindow();
                }
            });
            // 地图上显示一个InfoWindow
            mBaiduMap.showInfoWindow(infoWindow);

            //宝藏信息的取出和展示
            int id = marker.getExtraInfo().getInt("id");
            //Treasure treasure = TreasureRepo.getInstance().getTreasure(id);
            // mTreasureView.bindTreasure(treasure);

            // 切换到宝藏选中视图
            changeUIMode(UI_MODE_SECLECT);
            return false;
        }
    };

    // 地图状态的监听
    private BaiduMap.OnMapStatusChangeListener mStatusChangeListener = new BaiduMap.OnMapStatusChangeListener() {
        // 变化前
        @Override
        public void onMapStatusChangeStart(MapStatus mapStatus) {
        }

        // 变化中
        @Override
        public void onMapStatusChange(MapStatus mapStatus) {
        }

        // 变化结束后
        @Override
        public void onMapStatusChangeFinish(MapStatus mapStatus) {
            // 当前地图的位置
            LatLng target = mapStatus.target;
            // 确实地图的状态发生变化了
            if (target != MapFragment.this.mCurrentStatus) {
                // 地图状态发生变化以后实时获取当前区域内的宝藏
                updateMapArea();
                // 在埋藏宝藏的情况下
                if (mUIMode == UI_MODE_HIDE) {
                    // 设置反地理编码的位置
                    ReverseGeoCodeOption option = new ReverseGeoCodeOption();
                    option.location(target);
                    // 发起反地理编码
                    mGeoCoder.reverseGeoCode(option);
                }
                MapFragment.this.mCurrentStatus = target;
            }
        }
    };

    // 定位的按钮：移动到定位的地方
    @OnClick(R.id.tv_located)
    public void moveToLocation() {
        //地图状态的设置：设置到定位的地方
        MapStatus mapStatus = new MapStatus.Builder()
                .target(mCurrentLocation)//定位的位置
                .rotate(0)
                .overlook(0)
                .zoom(19)
                .build();
        //更新状态
        MapStatusUpdate update = MapStatusUpdateFactory.newMapStatus(mapStatus);
        //更新展示的地图的状态
        mBaiduMap.animateMapStatus(update);
    }
    //埋藏宝藏按钮
    @OnClick(R.id.iv_write_loc)
    public void writeLoc(){
        // MapFragment里面视图的普通的视图，可以退出
        if (clickbackPrssed()) {
            changeUIMode(2);
        }else{
            changeUIMode(0);
        }
    }

    // 宝藏显示的卡片的点击事件
    @OnClick(R.id.treasureView)
    public void clickTreasureView() {
        // 跳转到详情页,拿到当前的Marker的宝藏，并传递过去
        int id = mCurrentMarker.getExtraInfo().getInt("id");
        Treasure treasure = TreasureRepo.getInstance().getTreasure(id);
        //TreasureDetailActivity.open(getContext(),treasure);
    }

    // 点击宝藏标题录入的卡片，跳转埋藏宝藏的详细页面
    @OnClick(R.id.hide_treasure)
    public void hideTreasure() {
        String title = mEtTreasureTitle.getText().toString();
        if (TextUtils.isEmpty(title)) {
            mActivityUtils.showToast("请输入宝藏标题");
            return;
        }
        // 跳转到埋藏宝藏的详细页面
        LatLng latLng = mBaiduMap.getMapStatus().target;
        // HideTreasureActivity.open(getContext(),title,mCurrentAddr,latLng,0);
        Toast.makeText(MapFragment.this, "位置为："+latLng+mCurrentAddr, Toast.LENGTH_SHORT).show();
    }

    // 根据位置的变化，区域也发生了变化
    private void updateMapArea() {

        // 当前地图的状态
        MapStatus mapStatus = mBaiduMap.getMapStatus();
        // 当前的经纬度
        double longitude = mapStatus.target.longitude;
        double latitude = mapStatus.target.latitude;

        // 根据地图的位置拿到的一个区域
        Area area = new Area();
        area.setMaxLat(Math.ceil(latitude));
        area.setMaxLng(Math.ceil(longitude));
        area.setMinLat(Math.floor(latitude));
        area.setMinLng(Math.floor(longitude));

        // 区域拿到了，要根据区域获取宝藏数据了
        //mPresenter.getTreasure(area);

    }

    private BitmapDescriptor dot = BitmapDescriptorFactory.fromResource(R.mipmap.treasure_dot);
    private BitmapDescriptor dot_expand = BitmapDescriptorFactory.fromResource(R.mipmap.treasure_expanded);

    // 添加覆盖物
    private void addMarker(LatLng latLng, int treasureId) {

        MarkerOptions options = new MarkerOptions();
        options.position(latLng);// 覆盖物的位置
        options.icon(dot);// 覆盖物的图标
        options.anchor(0.5f, 0.5f);// 锚点位置：居中

        // 将宝藏的id信息保存到marker
        Bundle bundle = new Bundle();
        bundle.putInt("id", treasureId);
        options.extraInfo(bundle);
        // 添加覆盖物
        mBaiduMap.addOverlay(options);
    }

    // 将定位的地址供其它调用获取
    public static LatLng getMyLocation() {
        return mCurrentLocation;
    }

    // 将定位的地址供其它调用获取
    public static String getLocationAddr() {
        return mLocationAddr;
    }

    private static final int UI_MODE_NORMAL = 0;// 普通的视图
    private static final int UI_MODE_SECLECT = 1;// 宝藏选中的视图
    private static final int UI_MODE_HIDE = 2;// 埋藏宝藏的视图

    private static int mUIMode = UI_MODE_NORMAL;

    // 把所有视图的变化都统一到一个方法里面:视图的切换是根据布局控件或其他(marker、infowindow)显示和隐藏来实现
    public void changeUIMode(int uiMode) {

        if (mUIMode == uiMode) return;
        mUIMode = uiMode;

        switch (uiMode) {
            // 普通的视图
            case UI_MODE_NORMAL:
                if (mCurrentMarker != null) {
                    mCurrentMarker.setVisible(true);
                }
                mBaiduMap.hideInfoWindow();
                mLayoutBottom.setVisibility(View.GONE);
                mCenterLayout.setVisibility(View.GONE);
                break;

            // 宝藏选中(信息卡片展示的视图)
            case UI_MODE_SECLECT:
                mLayoutBottom.setVisibility(View.VISIBLE);
                mTreasureView.setVisibility(View.VISIBLE);
                mCenterLayout.setVisibility(View.GONE);
                mHideTreasure.setVisibility(View.GONE);
                break;

            // 宝藏埋藏的视图
            case UI_MODE_HIDE:
                if (mCurrentMarker != null) {
                    mCurrentMarker.setVisible(true);
                }
                mBaiduMap.hideInfoWindow();
                mCenterLayout.setVisibility(View.VISIBLE);
                mLayoutBottom.setVisibility(View.GONE);
                mBtnHideHere.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mLayoutBottom.setVisibility(View.VISIBLE);
                        mTreasureView.setVisibility(View.GONE);
                        mHideTreasure.setVisibility(View.VISIBLE);
                    }
                });
                break;
        }
    }

    //---------------------数据请求的视图方法-------------------------
    @Override
    public void showMessage(String msg) {
        mActivityUtils.showToast(msg);
    }

    @Override
    public void setData(List<Treasure> list) {

        // 再次网络请求拿到数据添加覆盖物之前，清理之前的覆盖物
        mBaiduMap.clear();// 清空地图上所有的覆盖物和infoWindow

        for (Treasure treasure : list) {

            LatLng latLng = new LatLng(treasure.getLatitude(), treasure.getLongitude());
            addMarker(latLng, treasure.getId());
        }
    }

    // 对外提供一个方法：什么时候可以退出了
    public boolean clickbackPrssed() {

        // 如果不是普通视图，切换成普通视图
        if (mUIMode != UI_MODE_NORMAL) {
            changeUIMode(UI_MODE_NORMAL);
            return false;
        }
        // 是普通的视图：告诉HomeActivity，可以退出了
        return true;
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode){
//            case ACCESS_LOCATION:
//                if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                    // 获取到，做相应的处理
//                    mLocationClient.requestLocation();
//                }else {
//
//                }
//                break;
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
