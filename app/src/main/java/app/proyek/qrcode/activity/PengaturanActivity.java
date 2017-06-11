package app.proyek.qrcode.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;

import app.proyek.qrcode.LoginActivity;
import app.proyek.qrcode.R;
import app.proyek.qrcode.SessionManagement;
import app.proyek.qrcode.api.ApiClient;
import app.proyek.qrcode.api.ApiInterface;
import app.proyek.qrcode.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengaturanActivity extends AppCompatActivity {

    private ImageView imgv_back;
    private Button bttn_profile;
    private Button bttn_password;
    private Button bttn_submit;
    private LinearLayout lnly_profile;
    private LinearLayout lnly_password;
    private TextInputEditText edtx_nama;
    private TextInputEditText edtx_alamat;
    private TextInputEditText edtx_ttl;
    private TextInputEditText edtx_no_hp;
    private TextInputEditText edtx_email;
    private TextInputEditText edtx_no_ktp;
    private TextInputEditText edtx_username;
    private TextInputEditText edtx_password;
    private TextInputEditText edtx_repassword;
    private ApiInterface client;
    private SessionManagement session;
    private String id_user;
    private String nama;
    private String alamat;
    private String ttl;
    private String no_hp;
    private String email;
    private String no_ktp;
    private String username;
    private String password;
    private String nama_e;
    private String alamat_e;
    private String ttl_e;
    private String no_hp_e;
    private String email_e;
    private String no_ktp_e;
    private String username_e;
    private String password_e;
    private int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);

        initComponents();

        client = ApiClient.createService(ApiInterface.class);
        session = new SessionManagement(PengaturanActivity.this);

        imgv_back.setOnClickListener(_handler);
        bttn_profile.setOnClickListener(_handler);
        bttn_password.setOnClickListener(_handler);
        bttn_submit.setOnClickListener(_handler);

        getUserDetail();
    }

    private void initComponents(){
        imgv_back = (ImageView)findViewById(R.id.imgv_back);
        bttn_profile = (Button)findViewById(R.id.bttn_profile);
        bttn_password = (Button)findViewById(R.id.bttn_password);
        bttn_submit = (Button)findViewById(R.id.bttn_submit);
        lnly_profile = (LinearLayout) findViewById(R.id.lnly_profile);
        lnly_password = (LinearLayout) findViewById(R.id.lnly_password);
        edtx_nama = (TextInputEditText) findViewById(R.id.edtx_nama);
        edtx_alamat = (TextInputEditText) findViewById(R.id.edtx_alamat);
        edtx_ttl = (TextInputEditText) findViewById(R.id.edtx_ttl);
        edtx_no_hp = (TextInputEditText) findViewById(R.id.edtx_no_hp);
        edtx_email = (TextInputEditText) findViewById(R.id.edtx_email);
        edtx_no_ktp = (TextInputEditText) findViewById(R.id.edtx_no_ktp);
        edtx_username = (TextInputEditText) findViewById(R.id.edtx_username);
        edtx_password = (TextInputEditText) findViewById(R.id.edtx_password);
        edtx_repassword = (TextInputEditText) findViewById(R.id.edtx_repassword);
    }

    private void getUserDetail(){
        SessionManagement session = new SessionManagement(this);
        HashMap<String, String> user = session.getUserDetails();
        id_user = user.get(SessionManagement.KEY_ID_USER);
        nama = user.get(SessionManagement.KEY_NAMA);
        alamat = user.get(SessionManagement.KEY_ALAMAT);
        ttl = user.get(SessionManagement.KEY_TTL);
        no_hp = user.get(SessionManagement.KEY_NO_HP);
        email = user.get(SessionManagement.KEY_EMAIL);
        no_ktp = user.get(SessionManagement.KEY_NO_KTP);
        username = user.get(SessionManagement.KEY_USERNAME);
        password = user.get(SessionManagement.KEY_PASSWORD);
    }

    private void EditProfile(){
        status = 1;
        bttn_profile.setVisibility(View.GONE);
        bttn_password.setVisibility(View.GONE);
        lnly_profile.setVisibility(View.VISIBLE);
        bttn_submit.setVisibility(View.VISIBLE);
    }

    private void EditPassword(){
        status = 2;
        bttn_profile.setVisibility(View.GONE);
        bttn_password.setVisibility(View.GONE);
        lnly_password.setVisibility(View.VISIBLE);
        bttn_submit.setVisibility(View.VISIBLE);
    }

    private boolean checkData(){

        boolean check = false;

        nama_e = edtx_nama.getText().toString();
        alamat_e = edtx_alamat.getText().toString();
        ttl_e = edtx_ttl.getText().toString();
        no_hp_e = edtx_no_hp.getText().toString();
        email_e = edtx_email.getText().toString();
        no_ktp_e = edtx_no_ktp.getText().toString();
        username_e = edtx_username.getText().toString();
        password_e = edtx_password.getText().toString();
        String repassword = edtx_repassword.getText().toString();

        if (status == 1){
            if (nama_e.trim().isEmpty()
                    || alamat_e.trim().isEmpty()
                    || ttl_e.trim().isEmpty()
                    || no_hp_e.trim().isEmpty()
                    || email_e.trim().isEmpty()
                    || no_ktp_e.trim().isEmpty()){
                Toast.makeText(PengaturanActivity.this, "Silahkan lengkapi data", Toast.LENGTH_SHORT).show();
                check = false;
            } else {
                check = true;
            }
        } else {
            if (username_e.trim().isEmpty() || password_e.trim().isEmpty()){
                Toast.makeText(PengaturanActivity.this, "Silahkan lengkapi data", Toast.LENGTH_SHORT).show();
                check = false;
            } else if (!password_e.equals(repassword)){
                Toast.makeText(PengaturanActivity.this, "Ulangi password tidak sama", Toast.LENGTH_SHORT).show();
                check = false;
            } else {
                check = true;
            }
        }
        return check;
    }

    private User getUserData(){
        User user = new User();

        nama_e = edtx_nama.getText().toString();
        alamat_e = edtx_alamat.getText().toString();
        ttl_e = edtx_ttl.getText().toString();
        no_hp_e = edtx_no_hp.getText().toString();
        email_e = edtx_email.getText().toString();
        no_ktp_e = edtx_no_ktp.getText().toString();
        username_e = edtx_username.getText().toString();
        password_e = edtx_password.getText().toString();

        if (status == 1){
            user.setNama(nama_e);
            user.setAlamat(alamat_e);
            user.setTtl(ttl_e);
            user.setNo_hp(no_hp_e);
            user.setEmail(email_e);
            user.setNo_ktp(no_ktp_e);
            user.setUsername(username);
            user.setPassword(password);
        } else {
            user.setNama(nama);
            user.setAlamat(alamat);
            user.setTtl(ttl);
            user.setNo_hp(no_hp);
            user.setEmail(email);
            user.setNo_ktp(no_ktp);
            user.setUsername(username_e);
            user.setPassword(password_e);
        }

        return user;
    }

    private void doSubmit(User user){

        Call<User> call = client.doEditUser(id_user, user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() != null && response.isSuccessful()){
                    Toast.makeText(PengaturanActivity.this, "Data berhasil diubah", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PengaturanActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(PengaturanActivity.this, "Koneksi bermasalah", Toast.LENGTH_SHORT).show();
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
                case R.id.bttn_profile:
                    EditProfile();
                    break;
                case R.id.bttn_password:
                    EditPassword();
                    break;
                case R.id.bttn_submit:
                    if (checkData()){
                        doSubmit(getUserData());
                    }
                    break;
            }
        }
    };
}
