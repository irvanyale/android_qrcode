package com.irvanyale.app.qrcode.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.irvanyale.app.qrcode.R;
import com.irvanyale.app.qrcode.helper.CartHelper;
import com.irvanyale.app.qrcode.model.Item;
import com.irvanyale.app.qrcode.util.Util;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ScanActivity extends AppCompatActivity {

    private static final String TAG = "ScanActivity";
    private ImageView imgv_back;
    private TextView counter_cart;
    private RelativeLayout rlly_cart;
    private RelativeLayout rlly_qrcode;
    private TextView txvw_nama_item;
    private Button bttn_scan;
    private Button bttn_add;
    private DecoratedBarcodeView scanner;
    private BeepManager beepManager;
    private String idItem;
    private String namaItem;
    private String hargaItem;
    private String beratItem;
    private String tglProduksiItem;
    private String masaBerlakuItem;
    private String distributorItem;
    private String stokItem;
    private int qtyItem = 1;
    private String imgItem;
    private List<Item> cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        imgv_back = (ImageView)findViewById(R.id.imgv_back);
        counter_cart = (TextView)findViewById(R.id.counter_cart);
        rlly_cart = (RelativeLayout)findViewById(R.id.rlly_cart);
        rlly_qrcode = (RelativeLayout)findViewById(R.id.rlly_qrcode);
        scanner = (DecoratedBarcodeView)findViewById(R.id.zxing_barcode_scanner);
        txvw_nama_item = (TextView)findViewById(R.id.txvw_nama_item);
        bttn_scan = (Button)findViewById(R.id.bttn_scan);
        bttn_add = (Button)findViewById(R.id.bttn_add);
        beepManager = new BeepManager(this);

        imgv_back.setOnClickListener(_handler);
        rlly_cart.setOnClickListener(_handler);
        bttn_scan.setOnClickListener(_handler);
        bttn_add.setOnClickListener(_handler);

        cart = CartHelper.getOrder();

        scanner.decodeContinuous(callback);
        scanner.setStatusText("");

        scanner.pause();
    }

    private ImageView imgv_close;
    private ImageView imgv_item;
    private ImageView imgv_up;
    private ImageView imgv_down;
    private TextView txvw_qty;
    private TextView item_harga;
    private TextView item_nama;
    private TextView item_distributor;
    private TextView item_masa_berlaku;
    private TextView item_tanggal_masuk_barang;
    private TextView item_berat;
    private TextView item_stok;
    private Button btn_add;
    private Button btn_cart;
    private int qty = 1;
    private int totalHarga = 0;
    private Dialog dialogItem = null;
    private void showDialogDetailItem(){
        dialogItem = new Dialog(ScanActivity.this, R.style.Theme_Dialog_Fullscreen_Margin);
        dialogItem.setContentView(R.layout.dialog_detail_item);

        Window window = dialogItem.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);

        imgv_close = (ImageView)dialogItem.findViewById(R.id.imgv_close);
        imgv_item = (ImageView)dialogItem.findViewById(R.id.imgv_item);
        imgv_up = (ImageView)dialogItem.findViewById(R.id.imgv_up);
        imgv_down = (ImageView)dialogItem.findViewById(R.id.imgv_down);
        txvw_qty = (TextView) dialogItem.findViewById(R.id.txvw_qty);
        item_harga = (TextView) dialogItem.findViewById(R.id.item_harga);
        item_nama = (TextView) dialogItem.findViewById(R.id.item_nama);
        item_distributor = (TextView) dialogItem.findViewById(R.id.item_distributor);
        item_masa_berlaku = (TextView) dialogItem.findViewById(R.id.item_masa_berlaku);
        item_tanggal_masuk_barang = (TextView) dialogItem.findViewById(R.id.item_tanggal_masuk_barang);
        item_berat = (TextView) dialogItem.findViewById(R.id.item_berat);
        item_stok = (TextView) dialogItem.findViewById(R.id.item_stok);
        btn_add = (Button) dialogItem.findViewById(R.id.btn_add);
        btn_cart = (Button) dialogItem.findViewById(R.id.btn_cart);

        item_nama.setText(namaItem);
        item_harga.setText("Rp " + Util.convertToCurrency(hargaItem));
        item_berat.setText(beratItem);
        item_tanggal_masuk_barang.setText(tglProduksiItem);
        item_masa_berlaku.setText(masaBerlakuItem);
        item_distributor.setText(distributorItem);

        final int harga = Integer.parseInt(hargaItem);

        imgv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogItem.dismiss();
            }
        });

        imgv_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty++;
                qtyItem = qty;
                totalHarga = harga * qty;
                txvw_qty.setText(String.valueOf(qty));
                item_harga.setText("Rp " + Util.convertToCurrency(String.valueOf(totalHarga)));
            }
        });

        imgv_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty--;
                if (qty <= 0){
                    qty = 1;
                }
                qtyItem = qty;
                totalHarga = harga * qty;
                txvw_qty.setText(qty <= 0 ? "1" : String.valueOf(qty));
                item_harga.setText("Rp " + Util.convertToCurrency(String.valueOf(totalHarga)));
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cart.add(setItemCart())){
                    counter_cart.setText(String.valueOf(cart.size()));
                    txvw_nama_item.setVisibility(View.GONE);
                    bttn_add.setVisibility(View.GONE);
                    dialogItem.dismiss();
                }
            }
        });

        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScanActivity.this, DaftarBelanjaActivity.class));
            }
        });

        bttn_scan.setText("SCAN");
        scanner.setVisibility(View.GONE);
        rlly_qrcode.setVisibility(View.VISIBLE);
        scanner.pause();

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

        dialogItem.setCanceledOnTouchOutside(true);
        dialogItem.show();
    }

    private Item setItemCart(){
        Item item = new Item();
        item.setId_barang(idItem);
        item.setNama_barang(namaItem);
        item.setHarga(String.valueOf(Integer.parseInt(hargaItem)*qty));
        item.setBerat(beratItem);
        item.setTanggal_masuk_barang(tglProduksiItem);
        item.setMasa_berlaku(masaBerlakuItem);
        item.setDistributor(distributorItem);
        item.setStok(stokItem);
        item.setQty(qty);
        return item;
    }

    @Override
    protected void onResume() {
        super.onResume();
        scanner.resume();
        cart = CartHelper.getOrder();
        counter_cart.setText(cart.size()+"");
    }

    @Override
    protected void onPause() {
        super.onPause();
        scanner.pause();
    }

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scanner.resume();
                }
            }, 1000);

            beepManager.setVibrateEnabled(true);
            beepManager.playBeepSoundAndVibrate();

            if (result != null){
                if (result.getResult() == null){
                    Toast.makeText(ScanActivity.this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try{
                        JSONObject obj = new JSONObject(result.getResult().toString());

                        qty = 1;

                        idItem = obj.getString("id_barang");
                        namaItem = obj.getString("nama_barang");
                        hargaItem = obj.getString("harga");
                        beratItem = obj.getString("berat");
                        tglProduksiItem = obj.getString("tanggal_masuk_barang");
                        masaBerlakuItem = obj.getString("masa_berlaku");
                        distributorItem = obj.getString("distributor");
                        stokItem = obj.getString("stok");
                        //imgItem = obj.getString("img");

                        txvw_nama_item.setVisibility(View.VISIBLE);
                        bttn_add.setVisibility(View.VISIBLE);
                        txvw_nama_item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showDialogDetailItem();
                            }
                        });

                        txvw_nama_item.setText(namaItem);
                        Log.d(TAG, "barcodeResult: "+obj.toString());

                    } catch (JSONException e){
                        e.printStackTrace();
                        Log.d(TAG, "Result "+result.getResult().toString());
                        Toast.makeText(ScanActivity.this, "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {

        }
    };

    private View.OnClickListener _handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imgv_back:
                    finish();
                    break;
                case R.id.rlly_cart:
                    startActivity(new Intent(ScanActivity.this, DaftarBelanjaActivity.class));
                    break;
                case R.id.bttn_scan:
                    if (bttn_scan.getText().equals("SCAN")){
                        bttn_scan.setText("STOP");
                        scanner.setVisibility(View.VISIBLE);
                        rlly_qrcode.setVisibility(View.GONE);
                        scanner.resume();
                    } else {
                        bttn_scan.setText("SCAN");
                        scanner.setVisibility(View.GONE);
                        rlly_qrcode.setVisibility(View.VISIBLE);
                        bttn_add.setVisibility(View.GONE);
                        txvw_nama_item.setVisibility(View.GONE);
                        scanner.pause();
                    }
                    break;
                case R.id.bttn_add:
                    if (cart.add(setItemCart())){
                        counter_cart.setText(String.valueOf(cart.size()));
                        txvw_nama_item.setVisibility(View.GONE);
                        bttn_add.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };
}
