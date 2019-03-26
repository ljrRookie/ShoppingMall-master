package com.ljr.shoppingmall;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.ljr.shoppingmall.Utils.SPUtils;
import com.ljr.shoppingmall.base.BaseConfigKey;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayActivity extends AppCompatActivity {

    @Bind(R.id.ib_good_info_back)
    ImageButton ibGoodInfoBack;
    @Bind(R.id.ll_toolbar)
    LinearLayout llToolbar;
    @Bind(R.id.tv_order_money)
    SuperTextView tvOrderMoney;
    @Bind(R.id.wechat_pay)
    ImageView wechatPay;
    @Bind(R.id.pay_c)
    TextView payC;
    @Bind(R.id.rb_wechat_pay)
    RadioButton rbWechatPay;
    @Bind(R.id.rl_wechat)
    RelativeLayout rlWechat;
    @Bind(R.id.ali_pay)
    ImageView aliPay;
    @Bind(R.id.pay_b)
    TextView payB;
    @Bind(R.id.rb_ali_pay)
    RadioButton rbAliPay;
    @Bind(R.id.rl_ali)
    RelativeLayout rlAli;
    @Bind(R.id.money_pay)
    ImageView moneyPay;
    @Bind(R.id.rb_money_pay)
    RadioButton rbMoneyPay;
    @Bind(R.id.rl_money)
    RelativeLayout rlMoney;
    @Bind(R.id.btn_commit)
    TextView btnCommit;
    private ZLoadingDialog zLoadingDialog;
    private int payType = 3;
    public static final int PAY_MONEY = 1;
    public static final int PAY_Ali = 2;
    public static final int PAY_WECHAT = 3;
    private String totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        zLoadingDialog = new ZLoadingDialog(this);
        Intent intent = getIntent();
        totalPrice = intent.getStringExtra(BaseConfigKey.PRICE_TOTAL);
        tvOrderMoney.setRightString("¥"+ totalPrice);
    }

    private void initUploadDialog(String title) {
        zLoadingDialog.setLoadingBuilder(Z_TYPE.ROTATE_CIRCLE)//设置类型
                .setLoadingColor(Color.GRAY)//颜色
                .setHintText(title)
                .setHintTextSize(13) // 设置字体大小 dp
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .setHintTextColor(Color.BLACK)  // 设置字体颜色
                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                .setDialogBackgroundColor(Color.WHITE).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                zLoadingDialog.dismiss();
                Intent intent = new Intent(PayActivity.this, PaySuccessActivity.class);
                intent.putExtra(BaseConfigKey.PRICE_TOTAL, String.valueOf(totalPrice));
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    @OnClick({R.id.ib_good_info_back, R.id.rl_wechat, R.id.rl_ali, R.id.rl_money, R.id.btn_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_good_info_back:
                finish();
                break;
            case R.id.rl_money:
                payType = PAY_MONEY;
                rbAliPay.setChecked(false);
                rbMoneyPay.setChecked(true);
                rbWechatPay.setChecked(false);
                break;
            case R.id.rl_ali:
                payType = PAY_Ali;
                rbAliPay.setChecked(true);
                rbMoneyPay.setChecked(false);
                rbWechatPay.setChecked(false);
                break;
            case R.id.rl_wechat:
                payType = PAY_WECHAT;
                rbAliPay.setChecked(false);
                rbMoneyPay.setChecked(false);
                rbWechatPay.setChecked(true);
                break;
            case R.id.btn_commit:
                if (payType == PAY_MONEY) {
                    initUploadDialog("余额支付中,请稍等...");
                } else if (payType == PAY_Ali) {
                    initUploadDialog("支付宝支付中,请稍等...");
                } else {
                    initUploadDialog("微信支付中,请稍等...");
                }
                break;
        }
    }
}
