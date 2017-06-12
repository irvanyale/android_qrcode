package app.proyek.qrcode.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.proyek.qrcode.LoginActivity;
import app.proyek.qrcode.R;
import app.proyek.qrcode.SessionManagement;
import app.proyek.qrcode.activity.adapter.TransaksiAdapter;
import app.proyek.qrcode.api.ApiClient;
import app.proyek.qrcode.api.ApiInterface;
import app.proyek.qrcode.model.Transaksi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiActivity extends AppCompatActivity {

    private ImageView imgv_back;
    private TransaksiAdapter transaksiAdapter;
    private List<Transaksi> listTransaksi = new ArrayList<>();
    private RecyclerView rv_listItem;
    private ApiInterface client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);

        rv_listItem = (RecyclerView)findViewById(R.id.rv_listItem);
        imgv_back = (ImageView)findViewById(R.id.imgv_back);

        client = ApiClient.createService(ApiInterface.class);
        transaksiAdapter = new TransaksiAdapter(this, listTransaksi);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_listItem.setLayoutManager(linearLayoutManager);
        rv_listItem.setAdapter(transaksiAdapter);
        rv_listItem.addItemDecoration(new DividerItemDecoration(this, linearLayoutManager.getOrientation()));

        imgv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        transaksiAdapter.setOnClickListener(new TransaksiAdapter.setOnClickListener() {
            @Override
            public void onClickListener(String id) {
                startActivity(new Intent(TransaksiActivity.this,
                        PembayaranActivity.class).putExtra("id_transaksi", id));
            }
        });

        loadTransaksi();
    }

    private String getUserId(){
        SessionManagement session = new SessionManagement(this);
        HashMap<String, String> user = session.getUserDetails();
        return user.get(SessionManagement.KEY_ID_USER);
    }

    private void loadTransaksi(){

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        Call<List<Transaksi>> call = client.getHistoryTransaksi(getUserId());
        call.enqueue(new Callback<List<Transaksi>>() {
            @Override
            public void onResponse(Call<List<Transaksi>> call, Response<List<Transaksi>> response) {
                if (response.body() != null && response.isSuccessful()){
                    listTransaksi = response.body();
                    transaksiAdapter.setList(listTransaksi);
                } else {
                    Toast.makeText(TransaksiActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Transaksi>> call, Throwable t) {
                Toast.makeText(TransaksiActivity.this, "Koneksi bermasalah", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}
