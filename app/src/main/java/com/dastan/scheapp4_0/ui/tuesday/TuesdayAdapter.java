package com.dastan.scheapp4_0.ui.tuesday;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dastan.scheapp4_0.R;
import com.dastan.scheapp4_0.Schedule;

import java.util.List;

public class TuesdayAdapter extends RecyclerView.Adapter<TuesdayAdapter.TuesdayViewHolder> {

    private List<Schedule> tuesdayList;

    public TuesdayAdapter(List<Schedule> tuesdayList) {
        this.tuesdayList = tuesdayList;
    }

    @NonNull
    @Override
    public TuesdayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_tuesday, parent, false);
        return new TuesdayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TuesdayViewHolder holder, int position) {
        holder.bind(tuesdayList.get(position));
    }

    @Override
    public int getItemCount() {
        return tuesdayList.size();
    }

    public class TuesdayViewHolder extends RecyclerView.ViewHolder{

        private TextView textTime;
        private TextView textLesson;
        private TextView textType;
        private TextView textRoom;

        public TuesdayViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(View view){
            textTime = view.findViewById(R.id.tvTimeTue);
            textLesson = view.findViewById(R.id.tvLessonTue);
            textType = view.findViewById(R.id.tvTypeTue);
            textRoom = view.findViewById(R.id.tvRoomTue);
        }

        public void bind(Schedule schedule) {
            textTime.setText(schedule.getTime());
            textLesson.setText(schedule.getLesson());
            textType.setText(schedule.getType());
            textRoom.setText(schedule.getRoom());
        }
    }
}
