package com.feicui.TreasureMap.user.register;

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

public class RegisterActivity extends AppCompatActivity {

    private ActivityUtils activityUtils;
    private String userName,userPassword,confirm;

    @Bind(R.id.et_Username) EditText etUsername;
    @Bind(R.id.et_Password) EditText etPassword;
    @Bind(R.id.et_Confirm) EditText etConfirm;
    @Bind(R.id.btn_Register) Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_register);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        etUsername.addTextChangedListener(mTextWatcher);
        etPassword.addTextChangedListener(mTextWatcher);
        etConfirm.addTextChangedListener(mTextWatcher);
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
            confirm = etConfirm.getText().toString();
            boolean canRengister = !TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPassword)
                    && !TextUtils.isEmpty(confirm);
            btnRegister.setEnabled(canRengister);

        }
    };

    @OnClick(R.id.btn_Register)
    public void register(){
        // 正则进行判断输入的用户名是否有效
        if (RegexUtils.verifyUsername(userName) != RegexUtils.VERIFY_SUCCESS) {
            activityUtils.showToast(R.string.username_rules);
            return;
        }
        // 正则进行判断输入的密码是否有效
        if (RegexUtils.verifyPassword(userPassword) != RegexUtils.VERIFY_SUCCESS) {
            activityUtils.showToast(R.string.username_rules);
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
