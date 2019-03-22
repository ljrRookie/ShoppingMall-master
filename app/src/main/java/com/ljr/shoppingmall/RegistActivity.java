package com.ljr.shoppingmall;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ljr.shoppingmall.Utils.SPUtils;
import com.ljr.shoppingmall.base.BaseConfigKey;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistActivity extends Activity {


    @Bind(R.id.ib_login_back)
    ImageButton ibLoginBack;
    @Bind(R.id.username)
    EditText username;
    @Bind(R.id.til_username)
    TextInputLayout tilUsername;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.til_password)
    TextInputLayout tilPassword;
    @Bind(R.id.password_agree)
    EditText passwordAgree;
    @Bind(R.id.til_password_agree)
    TextInputLayout tilPasswordAgree;
    @Bind(R.id.login)
    Button login;
    @Bind(R.id.llayout)
    LinearLayout llayout;
    @Bind(R.id.home_container)
    RelativeLayout homeContainer;
    private ZLoadingDialog zLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
        initUploadDialog();
    }

    private void initUploadDialog() {
        zLoadingDialog = new ZLoadingDialog(this);
        zLoadingDialog.setLoadingBuilder(Z_TYPE.ROTATE_CIRCLE)//设置类型
                .setLoadingColor(Color.GRAY)//颜色
                .setHintText("注册中，请稍等")
                .setHintTextSize(13) // 设置字体大小 dp
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .setHintTextColor(Color.BLACK)  // 设置字体颜色
                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                .setDialogBackgroundColor(Color.TRANSPARENT);
    }
    @OnClick({R.id.ib_login_back, R.id.login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_login_back:
                finish();
                break;
            case R.id.login:
                final String account = username.getText().toString().trim();
                final String pwd = password.getText().toString().trim();
                String pwdAgree = passwordAgree.getText().toString().trim();
                if(TextUtils.isEmpty(account) || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(pwdAgree)){
                    Toast.makeText(RegistActivity.this, "输入的内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!TextUtils.equals(pwd,pwdAgree)){
                    Toast.makeText(RegistActivity.this, "输入的密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                zLoadingDialog.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        zLoadingDialog.dismiss();
                        Toast.makeText(RegistActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        SPUtils.put(BaseConfigKey.Account, account);
                        SPUtils.put(BaseConfigKey.pwd,pwd);
                        Intent intent = new Intent();
                        intent.putExtra(BaseConfigKey.Account, account);
                        intent.putExtra(BaseConfigKey.pwd,pwd);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }, 1500);
                break;
        }
    }
}
