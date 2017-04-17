package com.irvanyale.app.qrcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText edtx_email;
    private EditText edtx_password;
    private Button bttn_masuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtx_email = (EditText)findViewById(R.id.edtx_email);
        edtx_password = (EditText)findViewById(R.id.edtx_password);
        bttn_masuk = (Button) findViewById(R.id.bttn_masuk);

        bttn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainMenuActivity.class));
            }
        });
    }
}
