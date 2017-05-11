package com.irvanyale.app.qrcode.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.irvanyale.app.qrcode.R;
import com.irvanyale.app.qrcode.activity.adapter.ItemCartAdapter;
import com.irvanyale.app.qrcode.helper.CartHelper;
import com.irvanyale.app.qrcode.model.Item;

import java.util.List;

public class DaftarBelanjaActivity extends AppCompatActivity {

    private ImageView imgv_back;
    private RecyclerView rv_listItem;
    private ItemCartAdapter itemCartAdapter;
    private List<Item> cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_belanja);

        imgv_back = (ImageView)findViewById(R.id.imgv_back);
        rv_listItem = (RecyclerView)findViewById(R.id.rv_listItem);

        cart = CartHelper.getOrder();

        itemCartAdapter = new ItemCartAdapter(this, cart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_listItem.setLayoutManager(linearLayoutManager);
        rv_listItem.setAdapter(itemCartAdapter);

        imgv_back.setOnClickListener(_handler);
    }

    private View.OnClickListener _handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imgv_back:
                    finish();
                    break;
            }
        }
    };
}
