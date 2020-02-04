package com.example.prayer.ui.home;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.prayer.Util.AdHandler;
import com.example.prayer.Util.AlarmHandler;
import com.example.prayer.Util.DateOprations;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment implements TimesRecyclerAdapter.OnItemClicked {
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


    private HomeViewModel homeViewModel;


    private TimesRecyclerAdapter adapter;
    private View root;


    private FireStoreUser fireStoreUser;
    private DateOprations dateOprations;


    private String UserID;

    private AdHandler adHandler = new AdHandler(HomeFragment.this);

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
            , R.color.maghrib
            , R.color.set
            , R.color.Ishaa
            , R.color.Imsak};


    @SuppressLint("HardwareIds")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);

        MobileAds.initialize(root.getContext(),
                getString(R.string.app_ad_id));
        MobileAds.initialize(root.getContext(), initializationStatus -> {
            Log.d("initializationStatus", initializationStatus.toString());
            Toast.makeText(root.getContext(), initializationStatus.toString(), Toast.LENGTH_SHORT).show();
        });

        homeViewModel = new ViewModelProvider.NewInstanceFactory().create(HomeViewModel.class);
        dateOprations = new DateOprations();
        MobileAds.initialize(root.getContext(), getString(R.string.app_ad_id));
        MobileAds.initialize(root.getContext(),
                getString(R.string.banner_ad_unit_id));
        adHandler.AdRequest(mAdView);

        UserID = Settings.Secure.getString(root.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);


        fireStoreUser = new FireStoreUser();


        adapter = new TimesRecyclerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
        getDataFromApi();
        // MediationTestSuite.launch(root.getContext());
        //  adHandler.initilizeUnity();

        // adHandler.showIntAd();
        AlarmHandler alarmHandler = new AlarmHandler(root.getContext());
        alarmHandler.createAlarm("21:45");

        return root;
    }

    @Override
    public void onPause() {
        super.onPause();

    }


    @SuppressLint("SetTextI18n")
    private void getData2View(Responce responce) {

        setDateText(responce);
//get Times
        List<String> Times = getTimes(responce);


        for (int i = 0; i < Times.size(); i++) {
            prayTimes.add(new Pray(Times.get(i), 100, PrayNames[i]));
        }


        for (int i = 0; i < prayTimes.size(); i++) {
            getLiveProgress(i);
        }
        setNextPrayText(Times);

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

            adapter.setInfo(root.getContext(), prayTimes, HomeFragment.this);
            DateCardView.setVisibility(View.VISIBLE);
            online.setVisibility(View.GONE);


        }
        spinner.setVisibility(View.GONE);

        // conneciting.setVisibility(View.INVISIBLE);
    }


    private void getLiveProgress(int index) {

        String prayTime = prayTimes.get(index).getTime();
        String Name = prayTimes.get(index).getName();


        fireStoreUser.getStartTime(UserID).observe(getViewLifecycleOwner(), startDay ->

        {
            if (startDay != null) {

                fireStoreUser.getTimesDone(UserID, Name).observe(getViewLifecycleOwner(), timesDone -> {
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


    @Override
    public void onItemClick(int index) {
        Log.d("onItemClick", String.valueOf((index)));

        String Name = prayTimes.get(index).getName();
        Log.d("onItemClick", prayTimes.get(index).getName());

        //Checking ShardPreference if Once a day
        //if d
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(root.getContext());
        String lastTimeStarted = settings.getString("last_time_started" + Name, null);
        Calendar calendar = Calendar.getInstance();
        String today = String.valueOf(calendar.get(Calendar.DAY_OF_YEAR));
        Log.d("last_time_started", "onItem: " + lastTimeStarted + "=" + index);
        if (!today.equals(lastTimeStarted)) {
            //startSomethingOnce();
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("last_time_started" + Name, today);

            editor.apply();
            fireStoreUser.getStartTime(UserID).observe(HomeFragment.this, startDay ->

            {
                if (startDay != null) {
                    fireStoreUser.getTimesDone(UserID, Name).observe(HomeFragment.this, timesDone -> {
                        if (timesDone != null) {
                            float porgress = dateOprations.getProgress(startDay, timesDone);
                            if (porgress < 100) {
                                Log.d("onItemClick", "onItemClick: update");
                                fireStoreUser.markAsDone(UserID, Name);
                                getLiveProgress(index);
                            } else {
                                Log.d("onItemClick", "onItemClick: notUpdate");
                            }
                        }
                    });
                }
            });
        }
    }

    private String storeNextpray(List<String> Times) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(root.getContext());
        String lastTimeStarted = settings.getString("last_next_Pray", null);
        String newNxtPray = dateOprations.nextPray(Times);
        if (!newNxtPray.equals(lastTimeStarted)) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("last_next_Pray", newNxtPray);
            editor.apply();

            return newNxtPray;
        } else
            return lastTimeStarted;
    }


    private String todayInMeladi(Responce responce) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(root.getContext());
        String lastTimeStarted = settings.getString("last_Day", null);
        String DateText = responce.getData().getDate().getReadable();
        if (!DateText.equals(lastTimeStarted)) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("last_Day", DateText);
            editor.apply();
            return DateText;
        } else
            return lastTimeStarted;
    }

    private String todayInHijri(Responce responce) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(root.getContext());
        String lastTimeHijri = settings.getString("last_Day_Hijri", null);
        String HijriText = responce.getData().getDate().getHijri().getDate();

        if (!HijriText.equals(lastTimeHijri)) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("last_Day", HijriText);
            editor.apply();
            return HijriText;
        } else
            return lastTimeHijri;
    }

    @SuppressLint("SetTextI18n")
    private void setNextPrayText(List<String> Times) {
        String hhmm = storeNextpray(Times);
        String Name = prayTimes.get(Times.lastIndexOf(hhmm)).getName();
        Log.d("setNextPrayText", "setNextPrayText: " + Name + "==" + hhmm);
        if (!String.valueOf(nextTime.getText()).contains(Name)) {
            nextTime.setText(Name + " (" + hhmm + ")");
            DateCardView.setCardBackgroundColor(ContextCompat
                    .getColor(Objects.requireNonNull(HomeFragment.this.getContext())
                            , backgroundColor[Times.lastIndexOf(hhmm)]));

        }

        new Handler().postDelayed(() -> setNextPrayText(Times), 30000);
    }

    @SuppressLint("SetTextI18n")
    private void setDateText(Responce responce) {
        String DateText = todayInMeladi(responce);
        String HijriText = todayInHijri(responce);


        Log.d("setNextPrayText", "setNextPrayText: " + DateText + "==" + HijriText);
        if (!String.valueOf(date.getText()).contains(DateText)) {
            date.setText(DateText);
            Hijri.setText(HijriText);
        }

        new Handler().postDelayed(() -> {
            getDataFromApi();
            setDateText(responce);

        }, 60000);
    }

    @SuppressLint("FragmentLiveDataObserve")
    private void getDataFromApi() {
        homeViewModel.getData();
        Log.d(TAG, "getDataFromApi: ");
        homeViewModel.mResponse.observe(HomeFragment.this, response -> {
            if (prayTimes.size() != 0) {

                if (!response.getData().getTimings().getFajr().equals(prayTimes.get(0).getTime())) {
                    Log.d(TAG, "getDataFromApi: changing");
                    setAdapter(response);
                    adapter.notifyDataSetChanged();
                }

            } else {
                setAdapter(response);
            }
        });
    }
}
