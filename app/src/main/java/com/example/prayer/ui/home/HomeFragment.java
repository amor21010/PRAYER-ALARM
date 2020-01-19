package com.example.prayer.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prayer.Pojo.Pray;
import com.example.prayer.Pojo.Responce;
import com.example.prayer.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeFragment extends Fragment {
    private TextView Date, Hijri;
    private List<Pray> prayTimes = new ArrayList<>();
    private String TAG = "onAd";
    private Boolean isGranted = false;
    ImageView conneciting;
    ImageView disconnected;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        ViewAds(root);

        conneciting = root.findViewById(R.id.image);
        disconnected = root.findViewById(R.id.image_des);

        Date = root.findViewById(R.id.date);
        Hijri = root.findViewById(R.id.hijri);


        AnimationDrawable animationDrawable = (AnimationDrawable) conneciting.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

        LogFireBaseToken();
        homeViewModel.getData("cairo");
        RecyclerView recyclerView = root.findViewById(R.id.recycler);
        final TimesRecyclerAdapter adapter = new TimesRecyclerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
        homeViewModel.mResponse.observe(this, responce -> {

            //set Data To View
            if (responce == null)
                disconnected.setVisibility(View.VISIBLE);
            else {
                recyclerView.setVisibility(View.VISIBLE);
                getData2View(root, responce);
                adapter.setList(prayTimes);
            }
            conneciting.setVisibility(View.INVISIBLE);

        });


        return root;
    }

    private void LogFireBaseToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "getInstanceId failed", task.getException());
                        return;
                    }

                    // Get new Instance ID token
                    String token = Objects.requireNonNull(task.getResult()).getToken();

                    // Log and toast

                    Log.d(TAG, token);

                });
    }


    private void getData2View(View root, Responce responce) {


        //get Date Info
        String DateText = responce.getData().getDate().getReadable();
        String HijriText = responce.getData().getDate().getHijri().getDate();

        //set Date Info To View
        Date.setText(DateText);
        Hijri.setText(HijriText);


//get Times
        String fajrTime = responce.getData().getTimings().getFajr();
        String sunRiseTime = responce.getData().getTimings().getSunrise();
        String DuhrTime = responce.getData().getTimings().getDhuhr();
        String AsrTime = responce.getData().getTimings().getAsr();
        String sunSetTime = responce.getData().getTimings().getSunset();
        String MaghribTime = responce.getData().getTimings().getMaghrib();
        String IshaTime = responce.getData().getTimings().getIsha();
        String IImsakTime = responce.getData().getTimings().getImsak();

//TODO update progress
        //set times to the list
        prayTimes.add(new Pray("Fajr", fajrTime
                , ContextCompat.getColor(root.getContext(), R.color.Fahr)
                , ContextCompat.getColor(root.getContext(), R.color.white), 20));
        prayTimes.add(new Pray("Sun Rise", sunRiseTime,
                ContextCompat.getColor(root.getContext(), R.color.rise)
                , ContextCompat.getColor(root.getContext(), R.color.white), 50));
        prayTimes.add(new Pray("Duhr", DuhrTime,
                ContextCompat.getColor(root.getContext(), R.color.duhr)
                , ContextCompat.getColor(root.getContext(), R.color.white), 10));
        prayTimes.add(new Pray("Asr", AsrTime,
                ContextCompat.getColor(root.getContext(), R.color.asr)
                , ContextCompat.getColor(root.getContext(), R.color.white), 3));
        prayTimes.add(new Pray("Sun Set", sunSetTime,
                ContextCompat.getColor(root.getContext(), R.color.maghrib)
                , ContextCompat.getColor(root.getContext(), R.color.white), 45));
        prayTimes.add(new Pray("Maghrib", MaghribTime,
                ContextCompat.getColor(root.getContext(), R.color.set)
                , ContextCompat.getColor(root.getContext(), R.color.white), 45.5f));
        prayTimes.add(new Pray("Isha", IshaTime,
                ContextCompat.getColor(root.getContext(), R.color.Ishaa)
                , ContextCompat.getColor(root.getContext(), R.color.white), 30));
        prayTimes.add(new Pray("Imsak", IImsakTime,
                ContextCompat.getColor(root.getContext(), R.color.Imsak)
                , ContextCompat.getColor(root.getContext(), R.color.white), 50));


    }


    private void ViewAds(View root) {
        PublisherAdView mAdView;

        MobileAds.initialize(root.getContext(), getString(R.string.app_ad_unit_id));

        mAdView = root.findViewById(R.id.adView);


        ActivityCompat.requestPermissions(Objects.requireNonNull(HomeFragment.this.getActivity()),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                1);
        if (isGranted) {
            Location location = LocationServices
                    .getFusedLocationProviderClient(Objects.requireNonNull(HomeFragment.this.getActivity()))
                    .getLastLocation().getResult();
            Log.d(TAG, "ViewAds: " + location);
            PublisherAdRequest adRequest = new PublisherAdRequest.Builder().setLocation(location).build();
            mAdView.loadAd(adRequest);

        }
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.d(TAG, "onAdLoaded: ");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.d(TAG, "onAdFailedToLoad: " + errorCode);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == 1) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isGranted = true;
                Toast.makeText(HomeFragment.this.getContext(), "Permission granted", Toast.LENGTH_SHORT).show();

            } else {


                Toast.makeText(HomeFragment.this.getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}