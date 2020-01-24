package com.example.prayer.ui.home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prayer.Pojo.Responce;
import com.example.prayer.data.ResponceClient;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {
    private final String TAG = "onFailure";
    MutableLiveData<Responce> mResponse = new MutableLiveData<>();


    void getData() {
        ResponceClient.getINICTANCE().getData("cairo", 5)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Responce>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Responce responce) {
                        mResponse.setValue(responce);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                        mResponse.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}