package com.example.prayer.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prayer.FireStoreDataBase.FireStoreUser;
import com.example.prayer.Pojo.Pray;
import com.example.prayer.Pojo.Responce;
import com.example.prayer.R;
import com.example.prayer.Util.DateOprations;
import com.example.prayer.ui.AdHandler;
import com.google.android.gms.ads.AdView;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment {
    @BindView(R.id.progress)
    ProgressBar spinner;
    @BindView(R.id.adView)
    AdView mAdView;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.hijri)
    TextView Hijri;
    @BindView(R.id.next_time)
    TextView nextTime;
    @BindView(R.id.date_card)
    CardView DateCardView;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.reload)
    TextView online;


    private List<Pray> prayTimes = new ArrayList<>();
    private String TAG = "onAdHome";
    // private LinearLayout disconnected;


    private HomeViewModel homeViewModel;


    private TimesRecyclerAdapter adapter;
    private View root;


    private FireStoreUser fireStoreUser;
    private DateOprations dateOprations;


    private String UserID;

    private AdHandler adHandler = new AdHandler();

    private String[] PrayNames = {
            "Fajr"
            , "Sun Rise"
            , "Duhr"
            , "Asr"
            , "Sun Set"
            , "Maghrib"
            , "Isha"
            , "Imsak"};
    private int[] backgroundColor = {
            R.color.Fahr
            , R.color.rise
            , R.color.duhr
            , R.color.asr
            , R.color.set
            , R.color.maghrib
            , R.color.Ishaa
            , R.color.Imsak};


    @SuppressLint("HardwareIds")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,root);

        homeViewModel = new ViewModelProvider.NewInstanceFactory().create(HomeViewModel.class);
        dateOprations = new DateOprations();

        adHandler.AdRequest(TAG, HomeFragment.this, mAdView);

        UserID = Settings.Secure.getString(root.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);


        fireStoreUser = new FireStoreUser();


        adapter = new TimesRecyclerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);


        if (prayTimes.size() > 7) {
            prayTimes.removeAll(prayTimes);
            adapter.notifyDataSetChanged();
            homeViewModel.getData();

            homeViewModel.mResponse.observe(getViewLifecycleOwner(), this::setAdapter);

        } else {
            homeViewModel.getData();
            homeViewModel.mResponse.observe(getViewLifecycleOwner(), this::setAdapter);

        }


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


    private void getData2View(Responce responce) {


        //get date Info
        String DateText = responce.getData().getDate().getReadable();
        String HijriText = responce.getData().getDate().getHijri().getDate();

        //set date Info To View
        date.setText(DateText);
        Hijri.setText(HijriText);


//get Times
        List<String> Times = getTimes(responce);


        String hhmm = dateOprations.nextPray(Times);
        Log.d("getCurrentHHmm", "getData2View: " + hhmm);

        for (int i = 0; i < Times.size(); i++) {
            prayTimes.add(new Pray(Times.get(i), 100, PrayNames[i]));
        }


        for (int i = 0; i < prayTimes.size(); i++) {
            getLiveProgress(i, prayTimes.get(i).getName());
        }
        String Name = prayTimes.get(Times.indexOf(hhmm)).getName();
        nextTime.setText(Name + " (" + hhmm + ")");
        DateCardView.setCardBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(HomeFragment.this.getContext()), backgroundColor[Times.indexOf(hhmm)]));


    }

    private List<String> getTimes(Responce responce) {
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
        return new ArrayList<>(Arrays.asList(fajrTime, sunRiseTime, DuhrTime, AsrTime, sunSetTime, MaghribTime, IshaTime, IImsakTime));

    }


    private void setAdapter(Responce responce) {
        if (responce == null)
            online.setVisibility(View.VISIBLE);

        else {
            recyclerView.setVisibility(View.VISIBLE);
            if (prayTimes.size() == 0)
                getData2View(responce);

            adapter.setList(root.getContext(), prayTimes);
            DateCardView.setVisibility(View.VISIBLE);
            online.setVisibility(View.GONE);


        }
        spinner.setVisibility(View.GONE);

        // conneciting.setVisibility(View.INVISIBLE);
    }


    private void getLiveProgress(int index, String Name) {

        String prayTime = prayTimes.get(index).getTime();


        fireStoreUser.getStartTime(UserID).observe(HomeFragment.this, startDay ->

        {
            if (startDay != null) {

                fireStoreUser.getTimesDone(UserID, Name).observe(HomeFragment.this, timesDone -> {
                    if (timesDone != null) {
                        float porgress = dateOprations.getProgress(startDay, timesDone);

                        prayTimes.set(index, new Pray(prayTime, porgress, Name));
                        Log.d(TAG, "getLiveProgress: Times = " + timesDone);
                        Log.d(TAG, "getLiveProgress: StartDay = " + startDay);
                        adapter.notifyItemChanged(index);
                    } else {
                        prayTimes.set(index, new Pray(prayTime, 100, Name));
                        adapter.notifyItemChanged(index);


                    }
                });
            }
        });

    }

    private void getTime() {

    }


}
