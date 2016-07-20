package com.feicui.TreasureMap.home.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.feicui.TreasureMap.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 16-7-19.
 */
public class MapFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        //初始化百度地图
        initBaiduMap();
        // 初始定位相关
        initLocation();
    }
    @Bind(R.id.map_frame)FrameLayout frameLayout;
    private MapView mapView;//地图视图
    private BaiduMap baiduMap;//地图视图操作类
    private void initBaiduMap() {
        //地图的状态
        MapStatus mapStatus = new MapStatus.Builder()
                .zoom(15)//缩放大小
                .overlook(-20)
                .build();
        BaiduMapOptions baiduMapOptions = new BaiduMapOptions()
                .mapStatus(mapStatus)//地图相关状态
                .zoomControlsEnabled(false);//缩放是否激活
        //地图视图
        mapView = new MapView(getActivity(),baiduMapOptions);
        //拿到当前MapView的控制器
        baiduMap = mapView.getMap();
        //在当前布局添加mapView
        frameLayout.addView(mapView,0);
    }

    // 定位核心API
    private LocationClient locationClient;
    // 我的位置(通过定位得到的当前位置经纬度)
    private LatLng myLocation;

    private void initLocation() {
        // 激活定位图层
        baiduMap.setMyLocationEnabled(true);
        // 定位实例化
        locationClient = new LocationClient(getActivity().getApplicationContext());
        // 进行一些定位的一般常规性设置
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开GPS
        option.setScanSpan(60000);// 扫描周期
        option.setCoorType("bd09ll");// 百度坐标类型
        locationClient.setLocOption(option);
        // 注册定位监听
        locationClient.registerLocationListener(locationListener);
        // 开始定位
        locationClient.start();
        locationClient.requestLocation(); // 请求位置(解决部分机器,初始定位不成功问题)
    }

    // 定位监听
    private final BDLocationListener locationListener = new BDLocationListener() {
        @Override public void onReceiveLocation(BDLocation bdLocation) {
            // 定位不成功 -- 最好UI上有表现
            if (bdLocation == null) {
                locationClient.requestLocation();
                return;
            }
            double lon = bdLocation.getLongitude();// 经度
            double lat = bdLocation.getLatitude();// 纬度
            myLocation = new LatLng(lat, lon);
            MyLocationData myLocationData = new MyLocationData.Builder()
                    .longitude(lon)
                    .latitude(lat)
                    .accuracy(100f) // 精度
                    .build();
            // 设置定位图层“我的位置”
            baiduMap.setMyLocationData(myLocationData);
            // 移动到我的位置上去
            animateMoveToMyLocation();
        }
    };

    @OnClick(R.id.tv_located)
    public void animateMoveToMyLocation() {
        MapStatus mapStatus = new MapStatus.Builder()
                .target(myLocation)// 当前位置
                .rotate(0)// 地图摆正
                .zoom(19)
                .build();
        MapStatusUpdate update = MapStatusUpdateFactory.newMapStatus(mapStatus);
        baiduMap.animateMapStatus(update);
    }

    // 地图缩放操作
    @OnClick({R.id.iv_scaleDown, R.id.iv_scaleUp})
    public void scaleMap(View view) {
        switch (view.getId()) {
            case R.id.iv_scaleUp:
                baiduMap.setMapStatus(MapStatusUpdateFactory.zoomIn());
                break;
            case R.id.iv_scaleDown:
                baiduMap.setMapStatus(MapStatusUpdateFactory.zoomOut());
                break;
        }
    }

    // 地图类别更新
    @OnClick(R.id.tv_satellite)
    public void switchMapType() {
        int type = baiduMap.getMapType();
        type = type == BaiduMap.MAP_TYPE_NORMAL ? BaiduMap.MAP_TYPE_SATELLITE : BaiduMap.MAP_TYPE_NORMAL;
        baiduMap.setMapType(type);
    }

    // 指南针更新
    @OnClick(R.id.tv_compass)
    public void switchCompass() {
        boolean isCompass = baiduMap.getUiSettings().isCompassEnabled();
        baiduMap.getUiSettings().setCompassEnabled(!isCompass);
    }
}
