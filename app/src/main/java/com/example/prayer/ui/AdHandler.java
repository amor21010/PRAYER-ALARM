package com.example.prayer.ui;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class AdHandler {

    MutableLiveData<Boolean> checker = new MutableLiveData<>();

    public void AdRequest(String TAG, LifecycleOwner owner, AdView adView) {

        adView.loadAd(new AdRequest.Builder().build());
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                //   Toast.makeText(context, "ad  not shows : " + i, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onAdLoaded: false " + i);
                checker.setValue(false);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
               // Toast.makeText(context, "ad shows : successfully ", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onAdLoaded: success");

                checker.setValue(true);
            }


        });
        checker.observe(owner, aBoolean -> {
            if (!aBoolean){
                adView.loadAd(new AdRequest.Builder().build());
            }else Log.d(TAG, "onChanged: loaded");
        });

    }



}
