package app.proyek.qrcode.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import app.proyek.qrcode.R;
import app.proyek.qrcode.SessionManagement;
import app.proyek.qrcode.activity.adapter.PaymentAdapter;
import app.proyek.qrcode.api.ApiClient;
import app.proyek.qrcode.api.ApiInterface;
import app.proyek.qrcode.helper.CartHelper;
import app.proyek.qrcode.model.DetailTransaksi;
import app.proyek.qrcode.model.Item;
import app.proyek.qrcode.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PembayaranActivity extends AppCompatActivity {

    private ImageView imgv_back;
    private TextView txvw_total;
    private TextView txvw_antrian;
    private Button bttn_back;
    private RecyclerView rv_listItem;
    private PaymentAdapter paymentAdapter;
    private List<Item> cart;
    private List<DetailTransaksi> detailTransaksi = new ArrayList<>();
    private List<DetailTransaksi.Detail> listDetailTransaksi = new ArrayList<>();
    private ApiInterface client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        imgv_back = (ImageView)findViewById(R.id.imgv_back);
        txvw_total = (TextView) findViewById(R.id.txvw_total);
        txvw_antrian = (TextView) findViewById(R.id.txvw_antrian);
        bttn_back = (Button) findViewById(R.id.bttn_back);
        rv_listItem = (RecyclerView)findViewById(R.id.rv_listItem);

        cart = CartHelper.getOrder();
        client = ApiClient.createService(ApiInterface.class);

        String id_transaksi = getIntent().getStringExtra("id_transaksi");

        paymentAdapter = new PaymentAdapter(this, listDetailTransaksi);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_listItem.setLayoutManager(linearLayoutManager);
        rv_listItem.setAdapter(paymentAdapter);

        imgv_back.setOnClickListener(_handler);
        bttn_back.setOnClickListener(_handler);

        loadDataDetailTransaksi(id_transaksi);
    }

    private String getTotalHarga(){
        int total = 0;

        for (Item item : cart){
            total += Integer.parseInt(item.getHarga());
        }

        return String.valueOf(total);
    }

    private String getUserId(){
        SessionManagement session = new SessionManagement(this);
        HashMap<String, String> user = session.getUserDetails();
        return user.get(SessionManagement.KEY_ID_USER);
    }

    private void loadDataDetailTransaksi(String id_transaksi){

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        Call<DetailTransaksi> call = client.getHistoryTransaksiDetail(id_transaksi);
        call.enqueue(new Callback<DetailTransaksi>() {
            @Override
            public void onResponse(Call<DetailTransaksi> call, Response<DetailTransaksi> response) {
                if (response.body() != null && response.isSuccessful()){
                    txvw_antrian.setText(response.body().getContent().getNo_antrian());
                    txvw_total.setText("Total : Rp " + Util.convertToCurrency(response.body().getContent().getTotal_harga()));
                    paymentAdapter.setList(response.body().getDetail());
                } else {
                    Toast.makeText(PembayaranActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<DetailTransaksi> call, Throwable t) {
                Toast.makeText(PembayaranActivity.this, "Koneksi bermasalah", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
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
                    finish();
                    break;
            }
        }
    };
}
