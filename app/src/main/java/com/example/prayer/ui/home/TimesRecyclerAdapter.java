package com.example.prayer.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
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

    @NonNull
    @Override
    public TimesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new TimesVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.pray_item, parent, false));
    }


    @SuppressLint("HardwareIds")
    @Override
    public void onBindViewHolder(@NonNull TimesVH holder, int position) {


        if (position == 1 || position == 4 || position == 7)
            holder.cardView.setVisibility(View.GONE);

        Pray pray = TimesList.get(position);


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
            onItemClick.onItemClick(getAdapterPosition());
            done.setChecked(true);
            done.setEnabled(false);
            if (itemView.hasOnClickListeners()) {
                itemView.setOnClickListener(null);
                done.setOnClickListener(null);
                notifyItemChanged(getAdapterPosition());
            }
        }
    }

    interface OnItemClicked {
        void onItemClick(int pos);
    }
}