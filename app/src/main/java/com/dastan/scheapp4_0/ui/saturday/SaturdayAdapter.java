package com.dastan.scheapp4_0.ui.saturday;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dastan.scheapp4_0.R;
import com.dastan.scheapp4_0.Schedule;

import java.util.List;

public class SaturdayAdapter extends RecyclerView.Adapter<SaturdayAdapter.SaturdayViewHolder> {

    private List<Schedule> saturdayList;

    public SaturdayAdapter(List<Schedule> saturdayList) {
        this.saturdayList = saturdayList;
    }

    @NonNull
    @Override
    public SaturdayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_saturday, parent, false);
        return new SaturdayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaturdayViewHolder holder, int position) {
        holder.bind(saturdayList.get(position));
    }

    @Override
    public int getItemCount() {
        return saturdayList.size();
    }

    public class SaturdayViewHolder extends RecyclerView.ViewHolder{

        private TextView textTime;
        private TextView textLesson;
        private TextView textType;
        private TextView textRoom;

        public SaturdayViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(View view) {
            textTime = view.findViewById(R.id.tvTimeSat);
            textLesson = view.findViewById(R.id.tvLessonSat);
            textType = view.findViewById(R.id.tvTypeSat);
            textRoom = view.findViewById(R.id.tvRoomSat);
        }

        public void bind(Schedule schedule) {
            textTime.setText(schedule.getTime());
            textLesson.setText(schedule.getLesson());
            textType.setText(schedule.getType());
            textRoom.setText(schedule.getRoom());
        }
    }
}
