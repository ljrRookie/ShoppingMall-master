package com.ljr.shoppingmall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.ljr.shoppingmall.base.BaseConfigKey;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaySuccessActivity extends AppCompatActivity {

    @Bind(R.id.ib_good_info_back)
    ImageButton ibGoodInfoBack;
    @Bind(R.id.ll_toolbar)
    LinearLayout llToolbar;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.btn_finish)
    SuperButton btnFinish;
    private String totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        totalPrice = intent.getStringExtra(BaseConfigKey.PRICE_TOTAL);
        tvPrice.setText("付款金额：¥"+ totalPrice);
    }

    @OnClick({R.id.ib_good_info_back, R.id.btn_finish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_good_info_back:
                finish();
                break;
            case R.id.btn_finish:
                finish();
                break;
        }
    }
}
