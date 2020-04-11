package com.dastan.scheapp4_0.ui.tuesday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dastan.scheapp4_0.R;
import com.dastan.scheapp4_0.Schedule;
import com.dastan.scheapp4_0.ui.thursday.ThursdayViewModel;

import java.util.ArrayList;
import java.util.List;


public class TuesdayFragment extends Fragment {

    private TuesdayViewModel tuesdayViewModel;
    private RecyclerView tuesdayRecyclerView;
    private TuesdayAdapter tuesdayAdapter;
    private List<Schedule> tuesdayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        tuesdayViewModel =
                ViewModelProviders.of(this).get(TuesdayViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tuesday, container, false);

        initViews(root);
        initList();

        return root;
    }

    private void initViews(View view){
        tuesdayRecyclerView = view.findViewById(R.id.rvTuesday);
    }

    private void initList(){
        tuesdayList = new ArrayList<>();
        tuesdayList.add(new Schedule("8:00", "Informatics", "theory", "501"));
        tuesdayRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tuesdayRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        tuesdayAdapter = new TuesdayAdapter(tuesdayList);
        tuesdayRecyclerView.setAdapter(tuesdayAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 100){
            Schedule schedule = (Schedule) data.getSerializableExtra("tuesdaySchedule");
            tuesdayList.add(schedule);
            tuesdayAdapter.notifyDataSetChanged();
        }
    }
}
