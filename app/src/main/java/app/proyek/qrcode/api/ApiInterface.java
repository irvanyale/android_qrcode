package app.proyek.qrcode.api;

import app.proyek.qrcode.model.Antrian;
import app.proyek.qrcode.model.ItemChart;
import app.proyek.qrcode.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by WINDOWS 10 on 09/06/2017.
 */

public interface ApiInterface {

    @POST("User/login")
    Call<User> doLogin(@Body User user);

    @POST("Transaksi/Post")
    Call<Antrian> doTransaction(@Body ItemChart itemChart);

    @POST("User/patch_user/{id}")
    Call<User> doEditUser(@Path("id") String id, @Body User user);

    @GET("transaksi/history/{id}")
    Call<User> getHistoryTransaksi(@Path("id") String id);

    @GET("transaksi/detail_transaksi/{id}")
    Call<User> getHistoryTransaksiDetail(@Path("id") String id);

}
