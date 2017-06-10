package app.proyek.qrcode;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by WINDOWS 10 on 10/06/2017.
 */

public class SessionManagement {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    private int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "BelanjaQRCode";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String KEEP_LOGIN = "KeepLogin";
    public static final String KEY_ID_USER = "id_member";
    public static final String KEY_NAMA = "nama";
    public static final String KEY_ALAMAT = "password";
    public static final String KEY_TTL = "email";
    public static final String KEY_NO_HP = "telepon";
    public static final String KEY_NO_KTP = "kelamin";
    public static final String KEY_USERNAME = "nomor_id";
    public static final String KEY_PASSWORD = "status";

    public SessionManagement(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    // Login Session
    public void createLoginSession(String id_user,
                                   String nama,
                                   String alamat,
                                   String ttl,
                                   String no_hp,
                                   String no_ktp,
                                   String username,
                                   String password){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_ID_USER, id_user);
        editor.putString(KEY_NAMA, nama);
        editor.putString(KEY_ALAMAT, alamat);
        editor.putString(KEY_TTL, ttl);
        editor.putString(KEY_NO_HP, no_hp);
        editor.putString(KEY_NO_KTP, no_ktp);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.commit();
    }

    // Get Session
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_ID_USER, pref.getString(KEY_ID_USER, null));
        user.put(KEY_NAMA, pref.getString(KEY_NAMA, null));
        user.put(KEY_ALAMAT, pref.getString(KEY_ALAMAT, null));
        user.put(KEY_TTL, pref.getString(KEY_TTL, null));
        user.put(KEY_NO_HP, pref.getString(KEY_NO_HP, null));
        user.put(KEY_NO_KTP, pref.getString(KEY_NO_KTP, null));
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        return user;
    }

    public void checkLogin(){
        if (this.isLoggedIn()){
            Intent i = new Intent(context, MainMenuActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        } else {
            Intent i = new Intent(context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    // Clear Session
    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
