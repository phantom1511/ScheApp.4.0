package com.dastan.scheapp4_0.ui.thursday.group;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dastan.scheapp4_0.Group;
import com.dastan.scheapp4_0.R;
import com.dastan.scheapp4_0.interfaces.OnItemClickListeners;

import java.util.List;

public class GroupThursdayAdapter extends RecyclerView.Adapter<GroupThursdayAdapter.GroupThursdayViewHolder> {

    private List<Group> groupList;
    private OnItemClickListeners onItemClickListeners;

    public GroupThursdayAdapter(List<Group> groupList) {
        this.groupList = groupList;
    }

    public void setOnItemClickListeners(OnItemClickListeners onItemClickListeners) {
        this.onItemClickListeners = onItemClickListeners;
    }

    @NonNull
    @Override
    public GroupThursdayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_group, parent, false);
        return new GroupThursdayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupThursdayViewHolder holder, int position) {
        holder.bind(groupList.get(position));
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public class GroupThursdayViewHolder extends RecyclerView.ViewHolder {

        private TextView groupName;

        public GroupThursdayViewHolder(@NonNull View itemView) {
            super(itemView);

            initViews(itemView);
            initListeners();
        }

        private void initViews(View view){
            groupName = view.findViewById(R.id.tvGroup);
        }

        private void initListeners(){
            groupName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListeners.onClick(getAdapterPosition());
                }
            });

            groupName.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListeners.onLongClick(getAdapterPosition());
                    return true;
                }
            });
        }

        public void bind(Group group) {
            groupName.setText(group.getGroupName());
        }
    }
}
