package com.feicui.TreasureMap.user.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.feicui.TreasureMap.R;
import com.feicui.TreasureMap.commons.ActivityUtils;
import com.feicui.TreasureMap.commons.RegexUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView{

    private ActivityUtils activityUtils;
    private String userName,userPassword;

    @Bind(R.id.et_Password) EditText etPassword;
    @Bind(R.id.et_Username) EditText etUsername;
    @Bind(R.id.btn_Login) Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        etPassword.addTextChangedListener(mTextWatcher);
        etUsername.addTextChangedListener(mTextWatcher);
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
            userName = etUsername.getText().toString();
            userPassword = etPassword.getText().toString();
            boolean canLogin = !(TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPassword));
            // 默认情况下Login按钮是未激活，不可点的
            btnLogin.setEnabled(canLogin);
        }
    };

    @OnClick(R.id.btn_Login)
    public void login(){
        // 用户名是否有效
        if (RegexUtils.verifyUsername(userName) != RegexUtils.VERIFY_SUCCESS) {
            activityUtils.showToast(R.string.username_rules);
            return;
        }
        // 密码是否有效
        if (RegexUtils.verifyPassword(userPassword) != RegexUtils.VERIFY_SUCCESS) {
            activityUtils.showToast(R.string.password_rules);
            return;
        }
        //执行业务
        new LoginPresenter(this).login();
    }


    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void navigateToHome() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
