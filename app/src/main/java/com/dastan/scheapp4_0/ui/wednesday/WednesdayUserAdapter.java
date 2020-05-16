package com.dastan.scheapp4_0.ui.wednesday;

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

public class WednesdayUserAdapter extends RecyclerView.Adapter<WednesdayUserAdapter.WednesdayUserViewHolder> {

    private List<Schedule> wednesdayList;
    private OnItemClickListeners onItemClickListeners;

    public WednesdayUserAdapter(List<Schedule> wednesdayList) {
        this.wednesdayList = wednesdayList;
    }

    public void setOnItemClickListeners(OnItemClickListeners onItemClickListeners) {
        this.onItemClickListeners = onItemClickListeners;
    }

    @NonNull
    @Override
    public WednesdayUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_wednesday, parent, false);
        return new WednesdayUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WednesdayUserViewHolder holder, int position) {
        holder.bind(wednesdayList.get(position));
    }

    @Override
    public int getItemCount() {
        return wednesdayList.size();
    }


    public class WednesdayUserViewHolder extends RecyclerView.ViewHolder {

        private TextView textTime;
        private TextView textLesson;
        private TextView textType;
        private TextView textRoom;

        public WednesdayUserViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(View view) {
            textTime = view.findViewById(R.id.tvTimeWed);
            textLesson = view.findViewById(R.id.tvLessonWed);
            textType = view.findViewById(R.id.tvTypeWed);
            textRoom = view.findViewById(R.id.tvRoomWed);
        }

        public void bind(Schedule schedule) {
            textTime.setText(schedule.getTime());
            textLesson.setText(schedule.getLesson());
            textType.setText(schedule.getType());
            textRoom.setText(schedule.getRoom());
        }
    }
}
