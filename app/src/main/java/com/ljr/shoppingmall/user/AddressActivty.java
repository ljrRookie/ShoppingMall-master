package com.ljr.shoppingmall.user;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ljr.shoppingmall.R;
import com.ljr.shoppingmall.Utils.SPUtils;
import com.ljr.shoppingmall.base.BaseConfigKey;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressActivty extends AppCompatActivity {
    @Bind(R.id.ib_good_info_back)
    ImageButton ibGoodInfoBack;
    @Bind(R.id.ll_toolbar)
    LinearLayout llToolbar;
    @Bind(R.id.edit_name)
    EditText editName;
    @Bind(R.id.name)
    TextInputLayout name;
    @Bind(R.id.edit_phone)
    EditText editPhone;
    @Bind(R.id.phone)
    TextInputLayout phone;
    @Bind(R.id.edit_address)
    EditText editAddress;
    @Bind(R.id.address)
    TextInputLayout address;
    @Bind(R.id.btn_save)
    TextView btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
initView();
    }

    private void initView() {
        String addressName = (String) SPUtils.get(BaseConfigKey.AddressName, "");
        String addressPhone = (String) SPUtils.get(BaseConfigKey.AddressPhone, "");
        String addressInfo = (String) SPUtils.get(BaseConfigKey.AddressInfo, "");
        editName.setText(addressName);
        editAddress.setText(addressInfo);
        editPhone.setText(addressPhone);
    }

    @OnClick({R.id.ib_good_info_back, R.id.btn_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_good_info_back:
                finish();
                break;
            case R.id.btn_save:
                String photo = editPhone.getText().toString().trim();
                String name = editName.getText().toString().trim();
                String addressInfo = editAddress.getText().toString().trim();
                if(TextUtils.isEmpty(photo) || TextUtils.isEmpty(name) || TextUtils.isEmpty(addressInfo)){
                    Toast.makeText(this,"输入的内容不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    SPUtils.put(BaseConfigKey.AddressName, name);
                    SPUtils.put(BaseConfigKey.AddressPhone, photo);
                    SPUtils.put(BaseConfigKey.AddressInfo, addressInfo);
                    Toast.makeText(this,"成功保存收获地址",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }
}
