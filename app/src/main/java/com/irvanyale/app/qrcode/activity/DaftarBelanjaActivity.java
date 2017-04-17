package com.irvanyale.app.qrcode.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.irvanyale.app.qrcode.R;

public class DaftarBelanjaActivity extends AppCompatActivity {

    private ImageView imgv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_belanja);

        imgv_back = (ImageView)findViewById(R.id.imgv_back);

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
