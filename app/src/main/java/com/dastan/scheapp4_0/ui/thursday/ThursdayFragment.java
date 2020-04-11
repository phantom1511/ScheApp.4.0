package com.dastan.scheapp4_0.ui.thursday;

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

import java.util.ArrayList;
import java.util.List;

public class ThursdayFragment extends Fragment {

    private ThursdayViewModel thursdayViewModel;
    private RecyclerView thursdayRecyclerView;
    private ThursdayAdapter thursdayAdapter;
    private List<Schedule> thursdayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        thursdayViewModel =
                ViewModelProviders.of(this).get(ThursdayViewModel.class);
        View root = inflater.inflate(R.layout.fragment_thursday, container, false);

        initViews(root);
        initList();

        return root;
    }

    private void initViews(View view) {
        thursdayRecyclerView = view.findViewById(R.id.rvThursday);
    }

    private void initList(){
        thursdayList = new ArrayList<>();
        thursdayList.add(new Schedule("8:00", "English, Aisuluu Imanalieva", "pra", "502"));
        thursdayList.add(new Schedule("8:00", "English, Aisuluu Imanalieva", "pra", "502"));
        thursdayList.add(new Schedule("8:00", "English, Aisuluu Imanalieva", "pra", "502"));
        thursdayRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        thursdayRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        thursdayAdapter = new ThursdayAdapter(thursdayList);
        thursdayRecyclerView.setAdapter(thursdayAdapter);
    }
}
