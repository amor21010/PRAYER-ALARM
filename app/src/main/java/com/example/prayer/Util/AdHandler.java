package com.example.prayer.Util;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.prayer.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;

public class AdHandler {
    private AdView adView;
    private Context context;
    private Fragment owner;

    public AdHandler(AdView adView, Context context, Fragment owner) {
        this.adView = adView;
        this.context = context;
        this.owner = owner;
    }

    public void AdRequest() {


        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        adView.setAdListener(new AdListener() {

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Log.d("shoIntAd", "onAdFailedToLoad: onDestroy " + i);
                AdRequest();

            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                //     Log.d("dddddd", "onAdFailedToLoad: onDestroy " + adView.getMediationAdapterClassName());

                new Handler().postDelayed(() -> showIntAd(), 10000);
            }
        });

        // initializeUnity();

    }

    private void showIntAd() {
        Log.d("shoInterAd", "onDestroy: ");
        InterstitialAd mInterstitialAd;
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(context.getString(R.string.int_ad_unit_id3));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mInterstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                new Handler().postDelayed(() -> initializeUnity(), 10000);
            }
        });

    }

    private void initializeUnity() {

        UnityAds.initialize(owner.getActivity()
                , context.getString(R.string.uinty_game_id)
                , new IUnityAdsListener() {
                    @Override
                    public void onUnityAdsReady(String placementId) {
                        UnityAds.show(owner.getActivity(), placementId);
                    }

                    @Override
                    public void onUnityAdsStart(String placementId) {
                        adView.pause();
                    }

                    @Override
                    public void onUnityAdsFinish(String placementId, UnityAds.FinishState result) {
                        adView.resume();
                    }

                    @Override
                    public void onUnityAdsError(UnityAds.UnityAdsError error, String message) {
                        Log.d("instadd", "unity" + error + "=" + message);
                        new Handler().postDelayed(() -> AdRequest(), 10000);
                    }

                });
    }
}
