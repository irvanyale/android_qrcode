package app.proyek.qrcode.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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

    private static final String TAG = "DaftarBelanjaActivity";

    private RelativeLayout rlly_cart;
    private RelativeLayout rlly_no_cart;
    private ImageView imgv_back;
    private TextView txvw_total;
    private Button btn_checkout;
    private RecyclerView rv_listItem;
    private ItemCartAdapter itemCartAdapter;
    private List<Item> cart;
    private List<ItemChart.Item> itemTransaksi = new ArrayList<>();
    private AlertDialog alertDialog;
    private ApiInterface client;
    private int totalharga = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_belanja);

        rlly_cart = (RelativeLayout) findViewById(R.id.rlly_cart);
        rlly_no_cart = (RelativeLayout) findViewById(R.id.rlly_no_cart);
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

        getTotalHarga();

        txvw_total.setText("Rp " + Util.convertToCurrency(String.valueOf(totalharga)));

        ItemCartAdapter.ViewHolder.setOnClickItemListener(new ItemCartAdapter.ViewHolder.setOnClickItemListener() {
            @Override
            public void OnClickItemListener(int position) {
                showDialogDetailItem(position);
            }
        });

        imgv_back.setOnClickListener(_handler);
        btn_checkout.setOnClickListener(_handler);

        if (cart.size() == 0){
            rlly_no_cart.setVisibility(View.VISIBLE);
            rlly_cart.setVisibility(View.GONE);
        }
    }

    private String getUserId(){
        SessionManagement session = new SessionManagement(this);
        HashMap<String, String> user = session.getUserDetails();
        return user.get(SessionManagement.KEY_ID_USER);
    }

    private void getTotalHarga(){
        int total = 0;

        for (Item item : cart){
            total += Integer.parseInt(item.getHarga());
        }

        totalharga = total;
    }

    private ItemChart getDataTransaksi(){

        for (Item item : cart){
            ItemChart.Item data = new ItemChart.Item();
            data.setId_barang(item.getId_barang());
            data.setHarga_barang(item.getHarga());
            data.setJumlah_barang(String.valueOf(item.getQty()));
            itemTransaksi.add(data);
        }

        getTotalHarga();

        ItemChart itemChart = new ItemChart();
        itemChart.setId_user(getUserId());
        itemChart.setTotal_harga(String.valueOf(totalharga));
        itemChart.setItems(itemTransaksi);

        return itemChart;
    }

    private int qty = 1;
    private int qtyItem = 1;
    private int totalHarga = 0;
    private Item item;
    private Dialog dialogItem = null;
    private void showDialogDetailItem(final int position){
        dialogItem = new Dialog(this, R.style.Theme_Dialog_Fullscreen_Margin);
        dialogItem.setContentView(R.layout.dialog_detail_item);

        Window window = dialogItem.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);

        item = cart.get(position);

        ImageView imgv_close = (ImageView)dialogItem.findViewById(R.id.imgv_close);
        ImageView imgv_item = (ImageView)dialogItem.findViewById(R.id.imgv_item);
        ImageView imgv_up = (ImageView)dialogItem.findViewById(R.id.imgv_up);
        ImageView imgv_down = (ImageView)dialogItem.findViewById(R.id.imgv_down);
        final TextView txvw_qty = (TextView) dialogItem.findViewById(R.id.txvw_qty);
        final TextView item_harga = (TextView) dialogItem.findViewById(R.id.item_harga);
        final TextView item_nama = (TextView) dialogItem.findViewById(R.id.item_nama);
        final TextView item_distributor = (TextView) dialogItem.findViewById(R.id.item_distributor);
        TextView item_masa_berlaku = (TextView) dialogItem.findViewById(R.id.item_masa_berlaku);
        TextView item_tanggal_masuk_barang = (TextView) dialogItem.findViewById(R.id.item_tanggal_masuk_barang);
        TextView item_berat = (TextView) dialogItem.findViewById(R.id.item_berat);
        TextView item_stok = (TextView) dialogItem.findViewById(R.id.item_stok);
        Button btn_add = (Button) dialogItem.findViewById(R.id.btn_add);
        Button btn_delete = (Button) dialogItem.findViewById(R.id.btn_cart);

        btn_add.setText("SIMPAN");
        btn_delete.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.rounded_button_red));
        btn_delete.setText("HAPUS");
        btn_delete.setTextColor(ContextCompat.getColor(this, android.R.color.white));

        item_nama.setText(item.getNama_barang());
        item_harga.setText("Rp " + Util.convertToCurrency(item.getHarga()));
        item_berat.setText(item.getBerat());
        item_tanggal_masuk_barang.setText(item.getTanggal_masuk_barang());
        item_masa_berlaku.setText(item.getMasa_berlaku());
        item_distributor.setText(item.getDistributor());
        txvw_qty.setText(String.valueOf(item.getQty()));

        final int harga = Integer.parseInt(item.getHarga()) / item.getQty();
        qty = item.getQty();

        Picasso.with(this)
                .load(ApiClient.BASE_URL_GAMBAR + item.getGambar())
                .error(R.drawable.ic_image)
                .fit()
                .into(imgv_item);

        imgv_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty++;
                totalHarga = harga * qty;
                totalharga += totalHarga - (harga*qtyItem);
                txvw_qty.setText(String.valueOf(qty));
                item_harga.setText("Rp " + Util.convertToCurrency(String.valueOf(totalHarga)));
                qtyItem = qty;
            }
        });

        imgv_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty--;
                if (qty <= 0){
                    qty = 1;
                }
                totalHarga = harga * qty;
                totalharga += totalHarga - (harga*qtyItem);
                txvw_qty.setText(qty <= 0 ? "1" : String.valueOf(qty));
                item_harga.setText("Rp " + Util.convertToCurrency(String.valueOf(totalHarga)));
                qtyItem = qty;
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setHarga(item.getHarga());
                setItemCart(item, item);
                itemCartAdapter.notifyDataSetChanged();
                getTotalHarga();
                txvw_total.setText("Rp " + Util.convertToCurrency(String.valueOf(totalharga)));
                dialogItem.dismiss();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.remove(position);
                itemCartAdapter.notifyItemRemoved(position);
                itemCartAdapter.notifyItemRangeChanged(position, cart.size());
                totalharga = totalharga - Integer.parseInt(item.getHarga());
                txvw_total.setText("Rp " + Util.convertToCurrency(String.valueOf(totalharga)));
                dialogItem.dismiss();
            }
        });

        imgv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogItem.dismiss();
            }
        });

        dialogItem.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialogItem = null;
            }
        });

        dialogItem.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialogItem = null;
            }
        });

        dialogItem.show();
    }

    private Item setItemCart(Item item, Item newItem){
        item.setId_barang(newItem.getId_barang());
        item.setNama_barang(newItem.getNama_barang());
        item.setHarga(String.valueOf((Integer.parseInt(newItem.getHarga())/item.getQty())*qty));
        item.setBerat(newItem.getBerat());
        item.setTanggal_masuk_barang(newItem.getTanggal_masuk_barang());
        item.setMasa_berlaku(newItem.getMasa_berlaku());
        item.setDistributor(newItem.getDistributor());
        item.setStok(newItem.getStok());
        item.setGambar(newItem.getGambar());
        item.setQty(qty);
        return item;
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

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        Call<Antrian> call = client.doTransaction(getDataTransaksi());
        call.enqueue(new Callback<Antrian>() {
            @Override
            public void onResponse(Call<Antrian> call, Response<Antrian> response) {
                if (response.body() != null && response.isSuccessful()){
                    Antrian antrian = response.body();
                    startActivity(new Intent(DaftarBelanjaActivity.this,
                            PembayaranActivity.class).putExtra("id_transaksi", antrian.getId_transaksi()));
                    cart.clear();
                    finish();
                } else {
                    Toast.makeText(DaftarBelanjaActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<Antrian> call, Throwable t) {
                Toast.makeText(DaftarBelanjaActivity.this, "Koneksi bermasalah", Toast.LENGTH_SHORT).show();
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
                case R.id.btn_checkout:
                    showDialogTransaksi();
                    break;
            }
        }
    };
}
