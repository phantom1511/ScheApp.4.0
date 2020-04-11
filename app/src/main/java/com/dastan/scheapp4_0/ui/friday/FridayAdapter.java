package com.dastan.scheapp4_0.ui.friday;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dastan.scheapp4_0.R;
import com.dastan.scheapp4_0.Schedule;

import java.util.List;

public class FridayAdapter extends RecyclerView.Adapter<FridayAdapter.FridayViewHolder> {

    private List<Schedule> fridayList;

    public FridayAdapter(List<Schedule> fridayList) {
        this.fridayList = fridayList;
    }

    @NonNull
    @Override
    public FridayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_friday, parent, false);
        return new FridayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FridayViewHolder holder, int position) {
        holder.bind(fridayList.get(position));
    }

    @Override
    public int getItemCount() {
        return fridayList.size();
    }

    public class FridayViewHolder extends RecyclerView.ViewHolder{

        private TextView textTime;
        private TextView textLesson;
        private TextView textType;
        private TextView textRoom;

        public FridayViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(View view) {
            textTime = view.findViewById(R.id.tvTimeFri);
            textLesson = view.findViewById(R.id.tvLessonFri);
            textType = view.findViewById(R.id.tvTypeFri);
            textRoom = view.findViewById(R.id.tvRoomFri);
        }

        public void bind(Schedule schedule) {
            textTime.setText(schedule.getTime());
            textLesson.setText(schedule.getLesson());
            textType.setText(schedule.getType());
            textRoom.setText(schedule.getRoom());
        }
    }
}
