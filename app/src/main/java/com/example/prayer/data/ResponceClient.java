package com.example.prayer.data;

import com.example.prayer.Pojo.Responce;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Flowable;
import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResponceClient {

    private static final String BaseUrl = "http://api.aladhan.com/v1/";
    private PrayerInterface prayerInterface;
    private static ResponceClient INICTANCE;

    public ResponceClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        prayerInterface = retrofit.create(PrayerInterface.class);
    }

    public static ResponceClient getINICTANCE() {
        if (null == INICTANCE)
            INICTANCE = new ResponceClient();
        return INICTANCE;
    }

    public Flowable<Responce> getData(String City, int method) {
        return prayerInterface.getPrayerTimes(City,method);
    }
}
