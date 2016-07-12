package com.feicui.TreasureMap.user.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.feicui.TreasureMap.MainActivity;
import com.feicui.TreasureMap.R;
import com.feicui.TreasureMap.commons.ActivityUtils;
import com.feicui.TreasureMap.commons.RegexUtils;
import com.feicui.TreasureMap.home.HomeActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册视图
 */
public class RegisterActivity extends AppCompatActivity implements RegisterView{

    @Bind(R.id.et_Username) EditText etUsername;
    @Bind(R.id.et_Password) EditText etPassword;
    @Bind(R.id.et_Confirm) EditText etConfirm;
    @Bind(R.id.btn_Register) Button btnRegister;
    @Bind(R.id.toolbar)Toolbar toolbar;

    private String username; // 用来保存编辑框内的用户名
    private String password; // 用来保存编辑框内的密码

    private ActivityUtils activityUtils;// Activity常用工具集

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_register);
    }

    @Override public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        etConfirm.addTextChangedListener(mTextWatcher); // EditText监听
        etPassword.addTextChangedListener(mTextWatcher); // EditText监听
        etUsername.addTextChangedListener(mTextWatcher); // EditText监听
        // 用toolbar来更换以前的actionBar
        setSupportActionBar(toolbar);
        // 激活Home(左上角,内部使用的选项菜单处理的),设置其title
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getTitle());
        }
    }
    //选项菜单处理,返回键的监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private final TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            username = etUsername.getText().toString();
            password = etPassword.getText().toString();
            String confirm = etConfirm.getText().toString();
            boolean canRegister = !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)
                    && password.equals(confirm);
            btnRegister.setEnabled(canRegister);// 注意：在布局内注册按钮默认是不可用的
        }
    };

    @OnClick(R.id.btn_Register)
    public void register() {
        // 正则进行判断输入的用户名是否有效
        if (RegexUtils.verifyUsername(username) != RegexUtils.VERIFY_SUCCESS) {
            activityUtils.showToast(R.string.username_rules);
            return;
        }
        // 正则进行判断输入的密码是否有效
        if (RegexUtils.verifyPassword(password) != RegexUtils.VERIFY_SUCCESS) {
            activityUtils.showToast(R.string.username_rules);
            return;
        }
        // 执行注册业务逻辑
        new RegisterPresenter(this).regiser();
    }
    private ProgressDialog progressDialog;
    @Override public void navigateToHome() {
        activityUtils.startActivity(HomeActivity.class);
        // 关闭当前页面
        finish();
        // 关闭入口Main页面 (发送一个广播出去,是本地广播)
        Intent intent = new Intent(MainActivity.ACTION_ENTER_HOME);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override public void showProgress() {
        activityUtils.hideSoftKeyboard();
        progressDialog = ProgressDialog.show(this,"","注册中,请稍后...");
    }

    @Override public void hideProgress() {
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }

    @Override public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
