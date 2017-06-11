package app.proyek.qrcode;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.zxing.integration.android.IntentIntegrator;

import app.proyek.qrcode.R;
import app.proyek.qrcode.activity.DaftarBelanjaActivity;
import app.proyek.qrcode.activity.PengaturanActivity;
import app.proyek.qrcode.activity.ScanActivity;
import app.proyek.qrcode.activity.TransaksiActivity;

public class MainMenuActivity extends AppCompatActivity {

    private LinearLayout lnly_scan;
    private LinearLayout lnly_daftarbelanja;
    private LinearLayout lnly_pengaturan;
    private LinearLayout lnly_bill;
    private LinearLayout lnly_logout;
    private SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        lnly_scan = (LinearLayout)findViewById(R.id.lnly_scan);
        lnly_daftarbelanja = (LinearLayout)findViewById(R.id.lnly_daftarbelanja);
        lnly_pengaturan = (LinearLayout)findViewById(R.id.lnly_pengaturan);
        lnly_bill = (LinearLayout)findViewById(R.id.lnly_bill);
        lnly_logout = (LinearLayout)findViewById(R.id.lnly_logout);

        session = new SessionManagement(MainMenuActivity.this);

        lnly_scan.setOnClickListener(_handler);
        lnly_daftarbelanja.setOnClickListener(_handler);
        lnly_pengaturan.setOnClickListener(_handler);
        lnly_bill.setOnClickListener(_handler);
        lnly_logout.setOnClickListener(_handler);
    }

    private View.OnClickListener _handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.lnly_scan:
                    startActivity(new Intent(MainMenuActivity.this, ScanActivity.class));
                    break;
                case R.id.lnly_daftarbelanja:
                    startActivity(new Intent(MainMenuActivity.this, DaftarBelanjaActivity.class));
                    break;
                case R.id.lnly_pengaturan:
                    startActivity(new Intent(MainMenuActivity.this, PengaturanActivity.class));
                    break;
                case R.id.lnly_bill:
                    startActivity(new Intent(MainMenuActivity.this, TransaksiActivity.class));
                    break;
                case R.id.lnly_logout:
                    showDialogLogout();
                    break;
            }
        }
    };

    private void showScanner(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(ScanActivity.class);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Letakkan QRCode di area ini untuk melakukan scanning");
        integrator.setOrientationLocked(true);
        integrator.setBeepEnabled(true);
        integrator.initiateScan();
    }

    private void showDialogLogout(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainMenuActivity.this);

        alertDialogBuilder.setTitle("Anda Yakin Akan\nKeluar dari Akun Anda?");

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        session.logoutUser();
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }
}
