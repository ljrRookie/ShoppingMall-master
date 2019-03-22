package com.ljr.shoppingmall;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ljr.shoppingmall.base.Constants;
import com.ljr.shoppingmall.home.bean.GoodsBean;

import java.util.List;

public class AdapterOrder extends BaseQuickAdapter<GoodsBean,BaseViewHolder>{
    public AdapterOrder(int layouId, List<GoodsBean> goodsBeanList) {
        super(layouId,goodsBeanList);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean item) {
        helper.setText(R.id.tv_goods_name, item.getName());
        helper.setText(R.id.tv_item_price,"￥"+item.getCover_price());
        helper.setText(R.id.tv_num, "X" + item.getNumber());
        ImageView view = helper.getView(R.id.iv_img);
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + item.getFigure()).into(view);
    }
}
