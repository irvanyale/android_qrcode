package app.proyek.qrcode.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import app.proyek.qrcode.R;
import app.proyek.qrcode.activity.adapter.ItemCartAdapter;
import app.proyek.qrcode.helper.CartHelper;
import app.proyek.qrcode.model.Item;
import app.proyek.qrcode.util.Util;

import java.util.List;

public class DaftarBelanjaActivity extends AppCompatActivity {

    private ImageView imgv_back;
    private TextView txvw_total;
    private Button btn_checkout;
    private RecyclerView rv_listItem;
    private ItemCartAdapter itemCartAdapter;
    private List<Item> cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_belanja);

        imgv_back = (ImageView)findViewById(R.id.imgv_back);
        txvw_total = (TextView) findViewById(R.id.txvw_total);
        btn_checkout = (Button) findViewById(R.id.btn_checkout);
        rv_listItem = (RecyclerView)findViewById(R.id.rv_listItem);

        cart = CartHelper.getOrder();

        itemCartAdapter = new ItemCartAdapter(this, cart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_listItem.setLayoutManager(linearLayoutManager);
        rv_listItem.setAdapter(itemCartAdapter);

        txvw_total.setText("Rp " + Util.convertToCurrency(getTotalHarga()));

        imgv_back.setOnClickListener(_handler);
        btn_checkout.setOnClickListener(_handler);
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
                case R.id.btn_checkout:
                    startActivity(new Intent(DaftarBelanjaActivity.this, PembayaranActivity.class));
                    break;
            }
        }
    };
}
