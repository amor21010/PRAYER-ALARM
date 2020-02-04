package com.example.prayer.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prayer.Pojo.Pray;
import com.example.prayer.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TimesRecyclerAdapter extends RecyclerView.Adapter<TimesRecyclerAdapter.TimesVH> {
    private List<Pray> TimesList = new ArrayList<>();
    private int[] backgroundColor = {
            R.color.Fahr
            , R.color.rise
            , R.color.duhr
            , R.color.asr
            , R.color.maghrib
            , R.color.set
            , R.color.Ishaa
            , R.color.Imsak};

    private Context context;
    private OnItemClicked onItemClick;
    private List<String> Times = new ArrayList<>();

    @NonNull
    @Override
    public TimesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new TimesVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.pray_item, parent, false));
    }


    @SuppressLint("HardwareIds")
    @Override
    public void onBindViewHolder(@NonNull TimesVH holder, int position) {


        if (position == 1 || position == 4 || position == 7) {
            holder.cardView.setVisibility(View.GONE);
            holder.disable();
        }
        Pray pray = TimesList.get(position);

        for (int i = 0; i < TimesList.size(); i++)
            try {
                Times.set(position, pray.getTime());
                Log.d("NextPray", "onBindViewHolder: set " + i + "=" + Times.get(i));

            } catch (Exception e) {
                Times.add(position, pray.getTime());
                Log.d("NextPray", "onBindViewHolder: add" + i + "=" + Times.get(i));
            }

        float x = pray.getProgress();


        holder.PrayTime.setText(pray.getTime());
        holder.PrayerName.setText(pray.getName());


        holder.left.setLayoutParams(new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.MATCH_PARENT, x));

        holder.right.setLayoutParams(new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.MATCH_PARENT, 100f - x));

        if (position < backgroundColor.length)
            holder.left.setBackgroundColor(ContextCompat
                    .getColor(context, backgroundColor[position]));

        if (x < 45) {

            holder.right.setBackground(ContextCompat
                    .getDrawable(context, R.drawable.right_list));
            AnimationDrawable animationDrawable = (AnimationDrawable) holder.right.getBackground();
            animationDrawable.setEnterFadeDuration(1000);
            animationDrawable.setExitFadeDuration(2000);
            animationDrawable.start();


        } else {
            holder.right.setBackgroundColor(Color.parseColor("#ffffff"));
        }


        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);

//check if checked once a day
        String lastTimeStarted = settings.getString("last_time_started" + TimesList.get(position).getName(), null);
        Calendar calendar = Calendar.getInstance();
        String today = String.valueOf(calendar.get(Calendar.DAY_OF_YEAR));
        if (today.equals(lastTimeStarted)) {
            Log.d("last_time_started", "TimesVH: if " + lastTimeStarted + " =" + position);
            holder.disable();
        }
//disable to mark prays
        String nextPrayTime = settings.getString("last_next_Pray", null);
        Log.d("NextPray", "onBindViewHolder next: " + nextPrayTime);
        if (position >= Times.lastIndexOf(nextPrayTime) && Times.lastIndexOf(nextPrayTime) != 0) {
            Log.d("NextPray", "onBindViewHolder pose: " + position + "=" + Times.lastIndexOf(nextPrayTime));

            holder.disable();
        }
        //     Log.d("NextPray", "onBindViewHolder pose: " + position + "=" + Times.lastIndexOf(nextPrayTime) + "hour" + Integer.valueOf(Times.get(0).split(":")[0]) + "=" + calendar.get(Calendar.HOUR_OF_DAY));


        // check if next pray is fajr and if now is before or after midnight
        if (Times.lastIndexOf(nextPrayTime) == 0//if next pray is fajr
                &&
                calendar.get(Calendar.HOUR_OF_DAY)//now hour
                        <
                        Integer.valueOf(Times.get(0).split(":")[0])) //fajr hour
        {
            Log.d("NextPray", "onBindViewHolder f: " + position + "=" + Times.lastIndexOf(nextPrayTime) + "hour" + Integer.valueOf(Times.get(0).split(":")[0]) + "=" + calendar.get(Calendar.HOUR_OF_DAY));


            holder.disable();
        }
    }

    @Override
    public int getItemCount() {
        return TimesList.size();
    }

    void setInfo(Context context, List<Pray> TimesList, OnItemClicked onItemClicked) {
        this.TimesList = TimesList;
        this.context = context;
        this.onItemClick = onItemClicked;
        notifyDataSetChanged();
    }


    interface OnItemClicked {

        void onItemClick(int pos);
    }

    class TimesVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView PrayerName, PrayTime;
        View left, right;
        CheckBox done;
        CardView cardView;

        TimesVH(@NonNull View itemView) {
            super(itemView);
            PrayerName = itemView.findViewById(R.id.prayName);
            PrayTime = itemView.findViewById(R.id.prayTime);
            done = itemView.findViewById(R.id.done);
            cardView = itemView.findViewById(R.id.done_card);
            left = itemView.findViewById(R.id.left);
            right = itemView.findViewById(R.id.right);
            done.setOnClickListener(this);
            itemView.setOnClickListener(this);


        }


        @Override
        public void onClick(View v) {


            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);

            String lastTimeStarted = settings.getString("last_time_started" + TimesList.get(getAdapterPosition()).getName(), null);
            Calendar calendar = Calendar.getInstance();
            String today = String.valueOf(calendar.get(Calendar.DAY_OF_YEAR));
            Log.d("last_time_started", "TimesVH: " + lastTimeStarted + " =" + getAdapterPosition());
            if (!today.equals(lastTimeStarted)) {
                Log.d("last_time_started", "TimesVH: if " + lastTimeStarted + " =" + getAdapterPosition());
                onItemClick.onItemClick(getAdapterPosition());
            }


        }

        void disable() {
            Log.d("tttttt", "disable: dis" + getAdapterPosition() + "=" + TimesList.size());
            done.setChecked(true);
            done.setEnabled(false);
            if (itemView.hasOnClickListeners()) {
                itemView.setOnClickListener(null);
                done.setOnClickListener(null);
            }
        }


    }
}