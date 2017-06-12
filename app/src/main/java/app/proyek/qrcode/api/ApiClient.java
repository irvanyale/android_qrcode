package app.proyek.qrcode.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by WINDOWS 10 on 09/06/2017.
 */

public class ApiClient {

    private static final String BASE_URL = "http://192.168.1.8/belanjaqr/";
    public static final String BASE_URL_GAMBAR = "http://192.168.1.8/belanjaqr/gambar_produk/";

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();

    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

    public static <S> S createService(Class<S> serviceClass) {

        httpClient.interceptors().add(logging);

        Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build();

        return retrofit.create(serviceClass);
    }
}
