package com.feicui.TreasureMap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.feicui.TreasureMap.commons.ActivityUtils;
import com.feicui.TreasureMap.user.login.LoginActivity;
import com.feicui.TreasureMap.user.register.RegisterActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private ActivityUtils activityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_Login, R.id.btn_Register})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_Login:
                activityUtils.startActivity(LoginActivity.class);
                break;
            case R.id.btn_Register:
                activityUtils.startActivity(RegisterActivity.class);
                break;
        }
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
