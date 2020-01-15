package com.example.prayer.ui.home;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prayer.Pojo.Responce;
import com.example.prayer.data.ResponceClient;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    private final String  TAG="onFailure";
    public MutableLiveData<Responce> mResponse=new MutableLiveData<>();

    @SuppressLint("CheckResult")
    public void getData(String City){
        ResponceClient.getINICTANCE().getData(City).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<Responce>() {
                    @Override
                    public void onNext(Responce responce) {
                        mResponse.setValue(responce);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }
}