package com.example.prayer.ui.dashboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.prayer.FireStoreDataBase.FireStoreUser;
import com.example.prayer.R;
import com.example.prayer.Util.AdHandler;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class DashboardFragment extends Fragment {
    private String TAG = "OnAdDash";
    private DashboardViewModel dashboardViewModel;
    private View root;
    AdView mAdView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider.NewInstanceFactory().create(DashboardViewModel.class);
        root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        TextView textView = root.findViewById(R.id.text_dashboard);
        //dashboardViewModel.getText().observe(this, s -> textView.setText(s));
        ViewAds();
        String userID = getUserInfo();
        Log.d(TAG, "onCreateView: " + userID);
        if (userID != null)
            textView.setText(userID);

        //   MediationTestSuite.launch(root.getContext());

        return root;
    }

    private void ViewAds() {


        mAdView = root.findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        new AdHandler(mAdView, root.getContext(), DashboardFragment.this).AdRequest();


    }


    private String getUserInfo() {

        @SuppressLint("HardwareIds") String userID = Settings.Secure.getString(root.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        FireStoreUser fireStoreUser = new FireStoreUser(root.getContext());
        return String.valueOf(fireStoreUser.CreateDataDocument(userID).get("userID"));
    }

    @Override
    public void onPause() {

        super.onPause();
        mAdView.pause();

    }

    @Override
    public void onResume() {
        super.onResume();
        mAdView.resume();
    }


}