package com.irvanyale.app.qrcode.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
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
    private RelativeLayout rlly_qrcode;
    private TextView txvw_nama_item;
    private Button bttn_scan;
    private DecoratedBarcodeView scanner;
    private BeepManager beepManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        imgv_back = (ImageView)findViewById(R.id.imgv_back);
        rlly_qrcode = (RelativeLayout)findViewById(R.id.rlly_qrcode);
        scanner = (DecoratedBarcodeView)findViewById(R.id.zxing_barcode_scanner);
        txvw_nama_item = (TextView)findViewById(R.id.txvw_nama_item);
        bttn_scan = (Button)findViewById(R.id.bttn_scan);
        beepManager = new BeepManager(this);

        imgv_back.setOnClickListener(_handler);
        bttn_scan.setOnClickListener(_handler);

        scanner.decodeContinuous(callback);
        scanner.setStatusText("");

        scanner.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scanner.resume();
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

                        txvw_nama_item.setText(obj.getString("nama"));
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
                        scanner.pause();
                    }

                    break;
            }
        }
    };
}
