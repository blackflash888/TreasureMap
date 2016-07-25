package com.feicui.TreasureMap.home.detail;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.feicui.TreasureMap.R;
import com.feicui.TreasureMap.commons.ActivityUtils;
import com.feicui.TreasureMap.commons.LogUtils;
import com.feicui.TreasureMap.home.Treasure;
import com.feicui.TreasureMap.home.TreasureView;
import com.feicui.TreasureMap.home.map.MapFragment;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.List;

/**
 * 宝藏详情页面
 */
public class TreasureDetailActivity extends MvpActivity<TreasureDetailView, TreasureDetailPresenter> implements TreasureDetailView{

    private ActivityUtils activityUtils;
    @Bind(R.id.toolbar)Toolbar toolbar;
    private Treasure treasure;
    private static final String KEY_TREASURE = "key_treasure";
    @Bind(R.id.treasureView)TreasureView treasureView;
    @Bind(R.id.frameLayout)FrameLayout frameLayout;
    @Bind(R.id.tv_detail_description)TextView tvDetailDescription;
    private final BitmapDescriptor mBitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.treasure_expanded);

    public static void open(@NonNull Context context,@NonNull Treasure treasure){
        Intent intent = new Intent(context,TreasureDetailActivity.class);
        intent.putExtra(KEY_TREASURE, treasure);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treasure_detail);
        activityUtils = new ActivityUtils(this);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        treasure = (Treasure) getIntent().getSerializableExtra(KEY_TREASURE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(treasure.getTitle());

        treasureView.bindTreasure(treasure);

        initMap();
    }
    //初始化地图
    private void initMap() {
        LatLng latLng = new LatLng(treasure.getLatitude(), treasure.getLongitude());
        MapStatus mapStatus = new MapStatus.Builder()
                .target(latLng)
                .overlook(-20)
                .zoom(18)
                .build();
        BaiduMapOptions options = new BaiduMapOptions()
                .mapStatus(mapStatus)// 地图相关状态
                .compassEnabled(false)// 指南针
                .zoomGesturesEnabled(false) // 设置是否允许缩放手势
                .scrollGesturesEnabled(false) // 设置是否允许拖拽手势，默认允许
                .scaleControlEnabled(false) // 设置是否显示比例尺控件
                .overlookingGesturesEnabled(false) // 设置是否允许俯视手势，默认允许
                .zoomControlsEnabled(false); // 设置是否显示缩放控件
        MapView mapView = new MapView(this, options);
        frameLayout.addView(mapView);
        BaiduMap baiduMap = mapView.getMap();
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .anchor(0.5f, 0.5f)
                .icon(mBitmapDescriptor);
        // 添加覆盖物
        baiduMap.addOverlay(markerOptions);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // 开始导航
    @OnClick(R.id.iv_navigation)
    public void showPopupMenu(View view) {
        // 弹出菜单
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(menuItemClickListener);
        // menu项
        popupMenu.inflate(R.menu.menu_navigation);
        popupMenu.show();
    }

    // PopupMenu弹出菜单的监听
    private final PopupMenu.OnMenuItemClickListener menuItemClickListener = new PopupMenu.OnMenuItemClickListener() {
        @Override public boolean onMenuItemClick(MenuItem item) {
            LatLng startPt = MapFragment.getMyLocation();
            String startAdr = MapFragment.getMyAddress();
            LogUtils.d("startAdr:" + startAdr);
            LatLng endPt = new LatLng(treasure.getLatitude(), treasure.getLongitude());
            String endAdr = treasure.getLocation();
            LogUtils.d("endAdr:" + endAdr);
            switch (item.getItemId()) {
                case R.id.walking_navi:
                    startWalkingNavi(startPt,startAdr,endPt,endAdr);
                    break;
                case R.id.biking_navi:
                    startBikingNavi(startPt, startAdr, endPt, endAdr);
                    break;
                case R.id.jiking_navi:
                    startCarNavi(startPt, startAdr, endPt, endAdr);
                    break;
            }
            return false;
        }
    };

    @NonNull @Override public TreasureDetailPresenter createPresenter() {
        return new TreasureDetailPresenter();
    }

    @Override public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override public void setData(List<TreasureDetailResult> results) {
        if (results.size() >= 1) {
            TreasureDetailResult result = results.get(0);
            tvDetailDescription.setText(result.description);
            return;
        }
        activityUtils.showToast("没有记录");
    }

    /**
     * 启动百度地图导航(Web)
     */
    public void startWebNavi(LatLng startPt, String startAdr, LatLng endPt, String endAdr) {
        // 构建 导航参数
        NaviParaOption para = new NaviParaOption()
                .startPoint(startPt).endPoint(endPt)
                .startName(startAdr).endName(endAdr);

        BaiduMapNavigation.openWebBaiduMapNavi(para, this);
    }

    /**
     * 启动百度地图步行导航(Native)
     */
    public void startWalkingNavi(LatLng startPt, String startAdr, LatLng endPt, String endAdr) {
        // 构建 导航参数
        NaviParaOption para = new NaviParaOption()
                .startPoint(startPt).endPoint(endPt)
                .startName(startAdr).endName(endAdr);
//        try {
//            BaiduMapNavigation.openBaiduMapWalkNavi(para, this);
//        } catch (BaiduMapAppNotSupportNaviException e) {
//            e.printStackTrace();
//            showDialog();
//        }
        if (!BaiduMapNavigation.openBaiduMapWalkNavi(para, this)) {
            //启动网页版地图
            startWebNavi(startPt, startAdr, endPt, endAdr);
            //直接显示对话框
//            showDialog();
        }
    }

    /**
     * 启动百度地图骑行导航(Native)
     */
    public void startBikingNavi(LatLng startPt, String startAdr, LatLng endPt, String endAdr) {
        NaviParaOption option = new NaviParaOption()
                .startPoint(startPt).endPoint(endPt)
                .startName(startAdr).endName(endAdr);

//        try {
//            BaiduMapNavigation.openBaiduMapBikeNavi(para, this);
//        } catch (BaiduMapAppNotSupportNaviException e) {
//            e.printStackTrace();
//            showDialog();
//        }
        if (!BaiduMapNavigation.openBaiduMapBikeNavi(option, this)) {
            showDialog();
        }
    }

    /**
     * 启动百度地图驾车导航
     */
    private void startCarNavi(LatLng startLatlng, String startAddress, LatLng endLatlng, String endAddress) {
        NaviParaOption option = new NaviParaOption()
                .startPoint(startLatlng)
                .startName(startAddress)
                .endPoint(endLatlng)
                .endName(endAddress);
//        try {
//            BaiduMapNavigation.openBaiduMapNavi(option, this);
//        } catch (Exception e) {
//            e.printStackTrace();
//            showDialog();
//        }
        if (!BaiduMapNavigation.openBaiduMapBikeNavi(option, this)) {
            showDialog();
        }
    }

    /**
     * 提示未安装百度地图app或app版本过低
     */
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("您尚未安装百度地图app或app版本过低，点击确认安装？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                OpenClientUtil.getLatestBaiduMapApp(TreasureDetailActivity.this);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
