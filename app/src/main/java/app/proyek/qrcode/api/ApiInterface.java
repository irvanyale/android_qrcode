package app.proyek.qrcode.api;

import app.proyek.qrcode.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by WINDOWS 10 on 09/06/2017.
 */

public interface ApiInterface {

    @POST("User/login")
    Call<User> doLogin(@Body User user);


}
