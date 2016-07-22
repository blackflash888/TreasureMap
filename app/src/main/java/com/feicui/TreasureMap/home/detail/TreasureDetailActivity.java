package com.feicui.TreasureMap.home.detail;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.feicui.TreasureMap.R;
import com.feicui.TreasureMap.commons.ActivityUtils;
import com.feicui.TreasureMap.home.Treasure;
import com.feicui.TreasureMap.home.TreasureView;

import butterknife.Bind;
import butterknife.ButterKnife;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * 宝藏详情页面
 */
public class TreasureDetailActivity extends AppCompatActivity {

    private ActivityUtils activityUtils;
    @Bind(R.id.toolbar)Toolbar toolbar;
    private Treasure treasure;
    private static final String KEY_TREASURE = "key_treasure";
    @Bind(R.id.treasureView)TreasureView treasureView;
    @Bind(R.id.frameLayout)FrameLayout frameLayout;
    @Bind(R.id.tv_detail_description)TextView tvDetailDescription;

    public static void open(@NonNull Context context,@NonNull Treasure treasure){
        Intent intent = new Intent(context,TreasureDetailActivity.class);
        intent.putExtra(KEY_TREASURE,treasure);
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
    }
}
