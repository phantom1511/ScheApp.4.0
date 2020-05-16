package com.dastan.scheapp4_0.ui.friday;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dastan.scheapp4_0.R;
import com.dastan.scheapp4_0.Schedule;
import com.dastan.scheapp4_0.interfaces.OnItemClickListeners;

import java.util.List;

public class FridayUserAdapter extends RecyclerView.Adapter<FridayUserAdapter.FridayUserViewHolder> {

    private List<Schedule> fridayList;
    private OnItemClickListeners onItemClickListeners;

    public FridayUserAdapter(List<Schedule> fridayList) {
        this.fridayList = fridayList;
    }

    public void setOnItemClickListeners(OnItemClickListeners onItemClickListeners) {
        this.onItemClickListeners = onItemClickListeners;
    }

    @NonNull
    @Override
    public FridayUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_friday, parent, false);
        return new FridayUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FridayUserViewHolder holder, int position) {
        holder.bind(fridayList.get(position));
    }

    @Override
    public int getItemCount() {
        return fridayList.size();
    }


    public class FridayUserViewHolder extends RecyclerView.ViewHolder {

        private TextView textTime;
        private TextView textLesson;
        private TextView textType;
        private TextView textRoom;

        public FridayUserViewHolder(@NonNull View itemView) {
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
