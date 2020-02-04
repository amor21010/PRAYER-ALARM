package com.example.prayer.data;

import com.example.prayer.Pojo.Responce;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PrayerInterface {
    @GET("timingsByAddress")
    Single<Responce> getPrayerTimes(@Query("address") String City, @Query("method") int method);
}
