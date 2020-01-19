package com.example.prayer.ui.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prayer.Pojo.Pray;
import com.example.prayer.R;

import java.util.ArrayList;
import java.util.List;

public class TimesRecyclerAdapter extends RecyclerView.Adapter<TimesRecyclerAdapter.TimesVH> {
    private List<Pray> TimesList = new ArrayList<>();
//    int COLOR;

    @NonNull
    @Override
    public TimesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TimesVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.pray_item, parent, false));
    }

    //String TAG = "wwwww";


    @Override
    public void onBindViewHolder(@NonNull TimesVH holder, int position) {

//         holder.left.setBackgroundColor(COLOR);


        Pray pray = TimesList.get(position);


        float x = pray.getProgress();


        holder.PrayTime.setText(pray.getTime());
        holder.PrayerName.setText(pray.getName());


        holder.PrayerName.setTextColor(pray.getTextCOLOR());
        holder.dots.setTextColor(pray.getTextCOLOR());
        holder.PrayTime.setTextColor(pray.getTextCOLOR());


        holder.left.setLayoutParams(new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.MATCH_PARENT, x));

        holder.right.setLayoutParams(new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.MATCH_PARENT, 100f - x));


        holder.left.setBackgroundColor(pray.getBackgroundCOLOR());

        if (x <45){
            AnimationDrawable animationDrawable= (AnimationDrawable) holder.right.getBackground();
            animationDrawable.setEnterFadeDuration(1000);
            animationDrawable.setExitFadeDuration(2000);
            animationDrawable.start();
        }else {
            holder.right.setBackgroundColor(Color.parseColor("#ffffff"));
        }


    }

    @Override
    public int getItemCount() {
        return TimesList.size();
    }

    void setList(List<Pray> TimesList) {
        this.TimesList = TimesList;
//        this.COLOR=COLOR;
        notifyDataSetChanged();
    }

    class TimesVH extends RecyclerView.ViewHolder {
        TextView PrayerName, PrayTime, dots;
        View left, right;

        TimesVH(@NonNull View itemView) {
            super(itemView);
            PrayerName = itemView.findViewById(R.id.prayName);
            PrayTime = itemView.findViewById(R.id.prayTime);
            dots = itemView.findViewById(R.id.dots);


            left = itemView.findViewById(R.id.left);
            right = itemView.findViewById(R.id.right);


        }
    }
}