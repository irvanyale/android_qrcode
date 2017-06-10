package app.proyek.qrcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.proyek.qrcode.R;
import app.proyek.qrcode.api.ApiClient;
import app.proyek.qrcode.api.ApiInterface;
import app.proyek.qrcode.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText edtx_email;
    private EditText edtx_password;
    private Button bttn_masuk;
    private ApiInterface client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtx_email = (EditText)findViewById(R.id.edtx_email);
        edtx_password = (EditText)findViewById(R.id.edtx_password);
        bttn_masuk = (Button) findViewById(R.id.bttn_masuk);

        client = ApiClient.createService(ApiInterface.class);

        bttn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtx_email.getText().toString();
                String password = edtx_password.getText().toString();

                if (username.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Username atau Password kosong", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    login(user);
                }
            }
        });
    }

    private void login(User user){

        Call<User> call = client.doLogin(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() != null && response.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this, MainMenuActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Username atau Password salah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Koneksi bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
