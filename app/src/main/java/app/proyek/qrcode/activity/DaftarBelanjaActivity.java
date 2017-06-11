package app.proyek.qrcode.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import app.proyek.qrcode.LoginActivity;
import app.proyek.qrcode.R;
import app.proyek.qrcode.SessionManagement;
import app.proyek.qrcode.activity.adapter.ItemCartAdapter;
import app.proyek.qrcode.api.ApiClient;
import app.proyek.qrcode.api.ApiInterface;
import app.proyek.qrcode.helper.CartHelper;
import app.proyek.qrcode.model.Antrian;
import app.proyek.qrcode.model.Item;
import app.proyek.qrcode.model.ItemChart;
import app.proyek.qrcode.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DaftarBelanjaActivity extends AppCompatActivity {

    private ImageView imgv_back;
    private TextView txvw_total;
    private Button btn_checkout;
    private RecyclerView rv_listItem;
    private ItemCartAdapter itemCartAdapter;
    private List<Item> cart;
    private List<ItemChart.Item> itemTransaksi = new ArrayList<>();
    private AlertDialog alertDialog;
    private ApiInterface client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_belanja);

        imgv_back = (ImageView)findViewById(R.id.imgv_back);
        txvw_total = (TextView) findViewById(R.id.txvw_total);
        btn_checkout = (Button) findViewById(R.id.btn_checkout);
        rv_listItem = (RecyclerView)findViewById(R.id.rv_listItem);

        cart = CartHelper.getOrder();
        client = ApiClient.createService(ApiInterface.class);

        itemCartAdapter = new ItemCartAdapter(this, cart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_listItem.setLayoutManager(linearLayoutManager);
        rv_listItem.setAdapter(itemCartAdapter);

        txvw_total.setText("Rp " + Util.convertToCurrency(getTotalHarga()));

        imgv_back.setOnClickListener(_handler);
        btn_checkout.setOnClickListener(_handler);
    }
    private String getUserId(){
        SessionManagement session = new SessionManagement(this);
        HashMap<String, String> user = session.getUserDetails();
        return user.get(SessionManagement.KEY_ID_USER);
    }

    private String getTotalHarga(){
        int total = 0;

        for (Item item : cart){
            total += Integer.parseInt(item.getHarga());
        }

        return String.valueOf(total);
    }

    private ItemChart getDataTransaksi(){

        for (Item item : cart){
            ItemChart.Item data = new ItemChart.Item();
            data.setId_barang(item.getId_barang());
            data.setHarga_barang(item.getHarga());
            data.setJumlah_barang(String.valueOf(item.getQty()));
            itemTransaksi.add(data);
        }

        ItemChart itemChart = new ItemChart();
        itemChart.setId_user(getUserId());
        itemChart.setTotal_harga(getTotalHarga());
        itemChart.setItems(itemTransaksi);

        return itemChart;
    }

    private void showDialogTransaksi(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Lanjutkan ke Proses Pembayaran ("+ cart.size() +") barang ?");

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        doTransaksi();
                        alertDialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    private void doTransaksi(){

        Call<Antrian> call = client.doTransaction(getDataTransaksi());
        call.enqueue(new Callback<Antrian>() {
            @Override
            public void onResponse(Call<Antrian> call, Response<Antrian> response) {
                if (response.body() != null && response.isSuccessful()){
                    Antrian antrian = response.body();
                    startActivity(new Intent(DaftarBelanjaActivity.this,
                            PembayaranActivity.class).putExtra("antrian", antrian.getNomor_antrian()));
                } else {
                    Toast.makeText(DaftarBelanjaActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Antrian> call, Throwable t) {
                Toast.makeText(DaftarBelanjaActivity.this, "Koneksi bermasalah", Toast.LENGTH_SHORT).show();
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
                case R.id.btn_checkout:
                    showDialogTransaksi();
                    break;
            }
        }
    };
}
