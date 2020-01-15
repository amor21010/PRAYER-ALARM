package com.example.prayer.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prayer.Pojo.Pray;
import com.example.prayer.Pojo.Responce;
import com.example.prayer.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    TextView Date, Hijri;
    View left;
    private HomeViewModel homeViewModel;
    private List<Pray> prayTimes = new ArrayList<>();
    Fragment fragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        final View root = inflater.inflate(R.layout.fragment_home, container, false);




        Date = root.findViewById(R.id.date);
        Hijri = root.findViewById(R.id.hijri);
        left = root.findViewById(R.id.left);

        homeViewModel.getData("cairo");
        RecyclerView recyclerView = root.findViewById(R.id.recycler);
        final TimesRecyclerAdapter adapter = new TimesRecyclerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(adapter);
        homeViewModel.mResponse.observe(this, new Observer<Responce>() {
            @Override
            public void onChanged(Responce responce) {
                String DateText = responce.getData().getDate().getReadable();
                String HijriText = responce.getData().getDate().getHijri().getDate();

                Date.setText(DateText);
                Hijri.setText(HijriText);
                String fajrTime = responce.getData().getTimings().getFajr();
                String sunRiseTime = responce.getData().getTimings().getSunrise();
                String DuhrTime = responce.getData().getTimings().getDhuhr();
                String AsrTime = responce.getData().getTimings().getAsr();
                String sunSetTime = responce.getData().getTimings().getSunset();
                String MaghribTime = responce.getData().getTimings().getMaghrib();
                String IshaTime = responce.getData().getTimings().getIsha();
                String IImsakTime = responce.getData().getTimings().getImsak();


                prayTimes.add(new Pray("Fajr", fajrTime
                        , ContextCompat.getColor(root.getContext(), R.color.Fahr)
                        , ContextCompat.getColor(root.getContext(), R.color.black), 20));
                prayTimes.add(new Pray("Sun Rise", sunRiseTime,
                        ContextCompat.getColor(root.getContext(), R.color.rise)
                        , ContextCompat.getColor(root.getContext(), R.color.black), 50));
                prayTimes.add(new Pray("Duhr", DuhrTime,
                        ContextCompat.getColor(root.getContext(), R.color.duhr)
                        , ContextCompat.getColor(root.getContext(), R.color.black), 10));

                prayTimes.add(new Pray("Asr", AsrTime,
                        ContextCompat.getColor(root.getContext(), R.color.asr)
                        , ContextCompat.getColor(root.getContext(), R.color.black), 3));
                prayTimes.add(new Pray("Sun Set", sunSetTime,
                        ContextCompat.getColor(root.getContext(), R.color.maghrib)
                        , ContextCompat.getColor(root.getContext(), R.color.black), 45));
                prayTimes.add(new Pray("Maghrib", MaghribTime,
                        ContextCompat.getColor(root.getContext(), R.color.set)
                        , ContextCompat.getColor(root.getContext(), R.color.black), 45.5f));
                prayTimes.add(new Pray("Isha", IshaTime,
                        ContextCompat.getColor(root.getContext(), R.color.Ishaa)
                        , ContextCompat.getColor(root.getContext(), R.color.white), 30));
                prayTimes.add(new Pray("Imsak", IImsakTime,
                        ContextCompat.getColor(root.getContext(), R.color.Imsak)
                        , ContextCompat.getColor(root.getContext(), R.color.Imsak_con), 50));


                adapter.setList(prayTimes);

            }
        });


        return root;
    }

    @Override
    public void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's state here


    }

}