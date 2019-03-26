package com.ljr.shoppingmall;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.ljr.shoppingmall.Utils.SPUtils;
import com.ljr.shoppingmall.base.BaseConfigKey;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends Activity {

    @Bind(R.id.ib_login_back)
    ImageButton mIbLoginBack;
    @Bind(R.id.username)
    EditText mUsername;
    @Bind(R.id.til_username)
    TextInputLayout mTilUsername;
    @Bind(R.id.password)
    EditText mPassword;
    @Bind(R.id.til_password)
    TextInputLayout mTilPassword;
    @Bind(R.id.login)
    Button mLogin;
    @Bind(R.id.tv_login_register)
    TextView mTvLoginRegister;
    @Bind(R.id.tv_login_forget_pwd)
    TextView mTvLoginForgetPwd;
    @Bind(R.id.ib_weibo)
    ImageButton mIbWeibo;
    @Bind(R.id.ib_qq)
    ImageButton mIbQq;
    @Bind(R.id.ib_wechat)
    ImageButton mIbWechat;
    @Bind(R.id.llayout)
    LinearLayout mLlayout;
    @Bind(R.id.home_container)
    RelativeLayout mHomeContainer;
    private MediaPlayer mp = null;
    private ZLoadingDialog zLoadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
       // initView();
        String userAccount = (String) SPUtils.get(BaseConfigKey.Account, "");
        String userPwd = (String) SPUtils.get(BaseConfigKey.pwd, "");
        mUsername.setText(userAccount);
        mPassword.setText(userPwd);
        initUploadDialog();
    }


    @OnClick({R.id.ib_login_back, R.id.login
            , R.id.tv_login_register, R.id.tv_login_forget_pwd, R.id.ib_weibo
            , R.id.ib_qq, R.id.ib_wechat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_login_back:
                finish();
                break;
            case R.id.login:
                login();
                break;
            case R.id.tv_login_register:
                startActivityForResult(new Intent(this,RegistActivity.class), BaseConfigKey.REQUEST_REGIST);
                break;
            case R.id.tv_login_forget_pwd:
                break;
            case R.id.ib_weibo:
                break;
            case R.id.ib_qq:
                break;
            case R.id.ib_wechat:
                break;
        }
    }
    private void initUploadDialog() {
        zLoadingDialog = new ZLoadingDialog(this);
        zLoadingDialog.setLoadingBuilder(Z_TYPE.ROTATE_CIRCLE)//设置类型
                .setLoadingColor(Color.GRAY)//颜色
                .setHintText("正在登录...")
                .setHintTextSize(13) // 设置字体大小 dp
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .setHintTextColor(Color.BLACK)  // 设置字体颜色
                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                .setDialogBackgroundColor(Color.TRANSPARENT);
    }
    private void login() {
        final String account = mUsername.getText().toString().trim();
        final String pwd = mPassword.getText().toString().trim();

        String userAccount = (String) SPUtils.get(BaseConfigKey.Account, "");
        String userPwd = (String) SPUtils.get(BaseConfigKey.pwd, "");
        if(TextUtils.equals(account,userAccount) && TextUtils.equals(userPwd,pwd)){
            zLoadingDialog.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    zLoadingDialog.dismiss();
                    SPUtils.put(BaseConfigKey.Account, account);
                    SPUtils.put(BaseConfigKey.pwd,pwd);
                    SPUtils.put(BaseConfigKey.IS_LOGIN,true);
                    finish();
                }
            }, 1500);

        }else{
            Toast.makeText(this, "账号密码错误", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == BaseConfigKey.REQUEST_REGIST) {
                String account = data.getStringExtra(BaseConfigKey.Account);
                String pwd = data.getStringExtra(BaseConfigKey.pwd);
                mUsername.setText(account);
                mPassword.setText(pwd);
            }
            }
    }
}
