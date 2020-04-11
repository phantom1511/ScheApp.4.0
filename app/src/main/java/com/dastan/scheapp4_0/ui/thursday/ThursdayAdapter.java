package com.dastan.scheapp4_0.ui.thursday;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dastan.scheapp4_0.R;
import com.dastan.scheapp4_0.Schedule;

import java.util.List;

public class ThursdayAdapter extends RecyclerView.Adapter<ThursdayAdapter.ThursdayViewHolder> {

    private List<Schedule> thursdayList;

    public ThursdayAdapter(List<Schedule> thursdayList) {
        this.thursdayList = thursdayList;
    }

    @NonNull
    @Override
    public ThursdayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_thursday, parent, false);
        return new ThursdayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThursdayViewHolder holder, int position) {
        holder.bind(thursdayList.get(position));
    }

    @Override
    public int getItemCount() {
        return thursdayList.size();
    }

    public class ThursdayViewHolder extends RecyclerView.ViewHolder{

        private TextView textTime;
        private TextView textLesson;
        private TextView textType;
        private TextView textRoom;

        public ThursdayViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(View view) {
            textTime = view.findViewById(R.id.tvTimeThu);
            textLesson = view.findViewById(R.id.tvLessonThu);
            textType = view.findViewById(R.id.tvTypeThu);
            textRoom = view.findViewById(R.id.tvRoomThu);
        }

        public void bind(Schedule schedule) {
            textTime.setText(schedule.getTime());
            textLesson.setText(schedule.getLesson());
            textType.setText(schedule.getType());
            textRoom.setText(schedule.getRoom());
        }
    }
}
