package com.ljr.shoppingmall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ljr.shoppingmall.base.BaseConfigKey;
import com.ljr.shoppingmall.home.bean.GoodsBean;
import com.ljr.shoppingmall.shoppingcart.util.CartStorage;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderActivity extends AppCompatActivity {

    @Bind(R.id.ib_good_info_back)
    ImageButton ibGoodInfoBack;
    @Bind(R.id.ll_toolbar)
    LinearLayout llToolbar;
    @Bind(R.id.iv_address)
    ImageView ivAddress;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_phone_num)
    TextView tvPhoneNum;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.rl_address)
    RelativeLayout rlAddress;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.tv_total_price)
    TextView tvTotalPrice;
    @Bind(R.id.btn_pay)
    TextView btnPay;
    private GoodsBean mGoodsBean = null;
    private List<GoodsBean> goodsBeanList;
    private double totalPrice = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        mGoodsBean = (GoodsBean) getIntent().getSerializableExtra(BaseConfigKey.GOOD);
        if (mGoodsBean != null) {
            //从商品详情点击进入
            goodsBeanList = new ArrayList<>();
            goodsBeanList.add(mGoodsBean);
        } else {
            //从购物车进入
          goodsBeanList = CartStorage.getInstance().getAllData();
        }
        totalPrice = getTotalPrice();
        tvTotalPrice.setText("合计￥"+ totalPrice);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        AdapterOrder adapterOrder = new AdapterOrder(R.layout.item_order, goodsBeanList);
        adapterOrder.openLoadAnimation();
        recyclerview.setAdapter(adapterOrder);

    }
    /**
     * 计算总价格
     *
     * @return
     */
    public double getTotalPrice() {
        double mTotalPrice = 0;
        if (goodsBeanList != null && goodsBeanList.size() > 0) {
            for (int i = 0; i < goodsBeanList.size(); i++) {
                GoodsBean goodsBean = goodsBeanList.get(i);
                if (goodsBean.isSelected()) {
                    mTotalPrice = mTotalPrice + Double.valueOf(goodsBean.getNumber()) * Double.valueOf(goodsBean.getCover_price());
                }
            }
        }
        return mTotalPrice;
    }
    @OnClick({R.id.ib_good_info_back, R.id.rl_address, R.id.btn_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_good_info_back:
                finish();
                break;
            case R.id.rl_address:
                break;
            case R.id.btn_pay:
                Intent intent = new Intent(this, PayActivity.class);
                intent.putExtra(BaseConfigKey.PRICE_TOTAL, String.valueOf(totalPrice));
                startActivity(intent);
                break;
        }
    }
}
