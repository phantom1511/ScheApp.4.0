package com.dastan.scheapp4_0.ui.monday;

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

public class MondayAdapter extends RecyclerView.Adapter<MondayAdapter.MondayViewHolder> {

    private List<Schedule> mondayList;
    private OnItemClickListeners onItemClickListeners;

    public MondayAdapter(List<Schedule> mondayList) {
        this.mondayList = mondayList;
    }

    public void setOnItemClickListeners(OnItemClickListeners onItemClickListeners) {
        this.onItemClickListeners = onItemClickListeners;
    }

    @NonNull
    @Override
    public MondayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_monday, parent, false);
        return new MondayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MondayViewHolder holder, int position) {
        holder.bind(mondayList.get(position));
    }

    @Override
    public int getItemCount() {
        return mondayList.size();
    }


    public class MondayViewHolder extends RecyclerView.ViewHolder {

        private TextView textTime;
        private TextView textLesson;
        private TextView textType;
        private TextView textRoom;

        public MondayViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);
            initListeners();
        }

        private void initViews(View view) {
            textTime = view.findViewById(R.id.tvTimeMon);
            textLesson = view.findViewById(R.id.tvLessonMon);
            textType = view.findViewById(R.id.tvTypeMon);
            textRoom = view.findViewById(R.id.tvRoomMon);
        }

        private void initListeners() {
            textTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListeners.onClick(getAdapterPosition());
                }
            });

            textLesson.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListeners.onClick(getAdapterPosition());
                }
            });
            textType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListeners.onClick(getAdapterPosition());
                }
            });
            textRoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListeners.onClick(getAdapterPosition());
                }
            });

            textTime.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListeners.onLongClick(getAdapterPosition());
                    return true;
                }
            });

            textLesson.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListeners.onLongClick(getAdapterPosition());
                    return true;
                }
            });

            textType.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListeners.onLongClick(getAdapterPosition());
                    return true;
                }
            });

            textRoom.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListeners.onLongClick(getAdapterPosition());
                    return true;
                }
            });
        }

        public void bind(Schedule schedule) {
            textTime.setText(schedule.getTime());
            textLesson.setText(schedule.getLesson());
            textType.setText(schedule.getType());
            textRoom.setText(schedule.getRoom());
        }
    }
}
