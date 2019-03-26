package com.ljr.shoppingmall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ljr.shoppingmall.Utils.SPUtils;
import com.ljr.shoppingmall.base.BaseConfigKey;
import com.ljr.shoppingmall.home.bean.GoodsBean;
import com.ljr.shoppingmall.shoppingcart.util.CartStorage;
import com.ljr.shoppingmall.user.AddressActivty;

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
    @Bind(R.id.iv_address_add)
    ImageView ivAddressAdd;
    @Bind(R.id.tv_address_empty)
    TextView tvAddressEmpty;
    @Bind(R.id.add_address)
    RelativeLayout addAddress;
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
        tvTotalPrice.setText(String.format("合计￥%s", totalPrice));
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        AdapterOrder adapterOrder = new AdapterOrder(R.layout.item_order, goodsBeanList);
        adapterOrder.openLoadAnimation();
        recyclerview.setAdapter(adapterOrder);

    }

    @Override
    protected void onResume() {
        super.onResume();
        String addressName = (String) SPUtils.get(BaseConfigKey.AddressName, "");
        String addressPhone = (String) SPUtils.get(BaseConfigKey.AddressPhone, "");
        String addressInfo = (String) SPUtils.get(BaseConfigKey.AddressInfo, "");
        if (TextUtils.isEmpty(addressPhone) || TextUtils.isEmpty(addressName) || TextUtils.isEmpty(addressInfo)) {
            addAddress.setVisibility(View.VISIBLE);
            rlAddress.setVisibility(View.GONE);

        } else {
            addAddress.setVisibility(View.GONE);
            rlAddress.setVisibility(View.VISIBLE);
            tvName.setText(addressName);
            tvPhoneNum.setText(addressPhone);
            tvAddress.setText(addressInfo);
        }
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

    @OnClick({R.id.ib_good_info_back, R.id.rl_address, R.id.btn_pay, R.id.add_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_good_info_back:
                finish();
                break;

            case R.id.add_address:
                startActivityForResult(new Intent(this, AddressActivty.class), BaseConfigKey.REQUEST_ADDRESS);
                break;
            case R.id.rl_address:

                break;
            case R.id.btn_pay:
                if(addAddress.getVisibility() == View.VISIBLE){
                    Toast.makeText(this,"请添加收货地址...",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(this, PayActivity.class);
                    intent.putExtra(BaseConfigKey.PRICE_TOTAL, String.valueOf(totalPrice));
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == BaseConfigKey.REQUEST_ADDRESS) {
                String addressName = (String) SPUtils.get(BaseConfigKey.AddressName, "");
                String addressPhone = (String) SPUtils.get(BaseConfigKey.AddressPhone, "");
                String addressInfo = (String) SPUtils.get(BaseConfigKey.AddressInfo, "");
                if (TextUtils.isEmpty(addressPhone) || TextUtils.isEmpty(addressName) || TextUtils.isEmpty(addressInfo)) {
                    addAddress.setVisibility(View.VISIBLE);
                    rlAddress.setVisibility(View.GONE);
                } else {
                    addAddress.setVisibility(View.GONE);
                    rlAddress.setVisibility(View.VISIBLE);
                    tvName.setText(addressName);
                    tvPhoneNum.setText(addressPhone);
                    tvAddress.setText(addressInfo);
                }
            }
        }

    }
}
