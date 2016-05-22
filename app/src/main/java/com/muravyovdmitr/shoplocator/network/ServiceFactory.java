package com.muravyovdmitr.shoplocator.network;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Dima Muravyov on 21.05.2016.
 */
public class ServiceFactory {
    private static final String API_ENDPOINT = "https://api.backendless.com/v1/data/";
    private static final String API_APP_ID = "D8A06A9A-E3D2-2DE5-FF6C-373D612DAA00";
    private static final String API_SECRET_KEY = "F07B0A00-D46D-28A4-FF92-A697DFE60C00";

    private static final int TIMEOUT = 60;
    private static final int WRITE_TIMEOUT = 120;
    private static final int CONNECT_TIMEOUT = 10;

    private static final OkHttpClient CLIENT = new OkHttpClient();

    static {
        CLIENT.setConnectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        CLIENT.setWriteTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        CLIENT.setReadTimeout(TIMEOUT, TimeUnit.SECONDS);
        CLIENT.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                request = request.newBuilder()
                        .addHeader("application-id", stringToAscii(API_APP_ID))
                        .addHeader("secret-key", stringToAscii(API_SECRET_KEY))
                        .method(request.method(), request.body())
                        .build();
                return chain.proceed(request);
            }
        });
    }

    public static <T> T createRetrofitService(final Class<T> serviceClass) {
        Retrofit retrofit = getRetrofit();

        return retrofit.create(serviceClass);
    }

    private static Retrofit getRetrofit() {
        Retrofit ret = new Retrofit.Builder()
                .baseUrl(API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(CLIENT)
                .build();
        return ret;
    }

    private static String stringToAscii(String line) {
        //TODO supported only API > 19
        return new String(line.getBytes(StandardCharsets.US_ASCII), StandardCharsets.US_ASCII);
    }
}
