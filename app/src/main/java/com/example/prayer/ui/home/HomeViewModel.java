package com.example.prayer.ui.home;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prayer.Pojo.Responce;
import com.example.prayer.data.ResponceClient;

import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {
    private final String TAG = "onFailure";
    MutableLiveData<Responce> mResponse = new MutableLiveData<>();


    @SuppressLint("CheckResult")
    void getData() {

        ResponceClient.getINICTANCE().getData("cairo", 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responce -> mResponse.setValue(responce)
                        , e -> Log.d(TAG, "on " + e));

    }


}