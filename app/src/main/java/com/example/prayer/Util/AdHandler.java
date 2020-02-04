package com.example.prayer.Util;

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

import java.util.Objects;

public class AdHandler {


    private Fragment owner;

    public AdHandler(Fragment owner) {
        this.owner = owner;
    }

    public void AdRequest(AdView adView) {

        adView.loadAd(new AdRequest.Builder().build());
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Log.d("shoIntAd", "onAdFailedToLoad: onDestroy " + i);
                AdRequest(adView);

            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d("dddddd", "onAdFailedToLoad: onDestroy " + adView.getMediationAdapterClassName());
                showIntAd();
            }
        });

        // initilizeUnity();

    }

    public void showIntAd() {
        Log.d("shoIntAd", "onDestroy: ");
        InterstitialAd mInterstitialAd;
        mInterstitialAd = new InterstitialAd(Objects.requireNonNull(owner.getContext()));
        mInterstitialAd.setAdUnitId(owner.getString(R.string.banner_ad_unit_id3));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.show();
        mInterstitialAd.setAdListener(new AdListener() {
                                          @Override
                                          public void onAdFailedToLoad(int i) {
                                              Log.d("shoIntAd", "onAdFailedToLoad: onDestroy " + i);
                                              new Handler().postDelayed(() -> {
                                                  mInterstitialAd.loadAd(new AdRequest.Builder().build());
                                                  mInterstitialAd.show();
                                              }, 100);

                                          }
                                      }
        );
    }

    public void initilizeUnity() {

        UnityAds.initialize(owner.getActivity(), "3437179", new IUnityAdsListener() {
            @Override
            public void onUnityAdsReady(String placementId) {
                UnityAds.show(owner.getActivity());
            }

            @Override
            public void onUnityAdsStart(String placementId) {

            }

            @Override
            public void onUnityAdsFinish(String placementId, UnityAds.FinishState result) {

            }

            @Override
            public void onUnityAdsError(UnityAds.UnityAdsError error, String message) {
                Log.d("UnityADs", "initilizeUnity: " + error + "=" + message);


            }
        });

        Log.d("UnityADs", "initilizeUnity: " + UnityAds.isReady());
        new Handler().
                postDelayed(this::initilizeUnity, 6000);
    }
}
