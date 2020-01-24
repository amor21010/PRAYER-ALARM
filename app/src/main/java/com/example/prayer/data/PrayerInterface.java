package com.example.prayer.data;

import com.example.prayer.Pojo.Responce;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PrayerInterface {
    @GET("timingsByAddress")
    Flowable<Responce> getPrayerTimes(@Query("address") String City, @Query("method") int method);
}
