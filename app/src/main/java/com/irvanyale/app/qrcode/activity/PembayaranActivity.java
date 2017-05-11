package com.irvanyale.app.qrcode.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.irvanyale.app.qrcode.R;
import com.irvanyale.app.qrcode.activity.adapter.PaymentAdapter;
import com.irvanyale.app.qrcode.helper.CartHelper;
import com.irvanyale.app.qrcode.model.Item;
import com.irvanyale.app.qrcode.util.Util;

import java.util.List;

public class PembayaranActivity extends AppCompatActivity {

    private ImageView imgv_back;
    private TextView txvw_total;
    private Button bttn_back;
    private RecyclerView rv_listItem;
    private PaymentAdapter paymentAdapter;
    private List<Item> cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        imgv_back = (ImageView)findViewById(R.id.imgv_back);
        txvw_total = (TextView) findViewById(R.id.txvw_total);
        bttn_back = (Button) findViewById(R.id.bttn_back);
        rv_listItem = (RecyclerView)findViewById(R.id.rv_listItem);

        cart = CartHelper.getOrder();

        paymentAdapter = new PaymentAdapter(this, cart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_listItem.setLayoutManager(linearLayoutManager);
        rv_listItem.setAdapter(paymentAdapter);

        txvw_total.setText("Total : Rp " + Util.convertToCurrency(getTotalHarga()));

        imgv_back.setOnClickListener(_handler);
        bttn_back.setOnClickListener(_handler);
    }

    private String getTotalHarga(){
        int total = 0;

        for (Item item : cart){
            total += Integer.parseInt(item.getHarga());
        }

        return String.valueOf(total);
    }

    private View.OnClickListener _handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imgv_back:
                    finish();
                    break;
                case R.id.bttn_back:
                    startActivity(new Intent(PembayaranActivity.this, ScanActivity.class));
                    break;
            }
        }
    };
}
