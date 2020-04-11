package com.dastan.scheapp4_0.ui.saturday;

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

public class SaturdayFragment extends Fragment {

    private SaturdayViewModel saturdayViewModel;
    private RecyclerView saturdayRecyclerView;
    private SaturdayAdapter saturdayAdapter;
    private List<Schedule> saturdayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        saturdayViewModel =
                ViewModelProviders.of(this).get(SaturdayViewModel.class);
        View root = inflater.inflate(R.layout.fragment_saturday, container, false);

        initViews(root);
        initList();

        return root;
    }

    private void initViews(View view) {
        saturdayRecyclerView = view.findViewById(R.id.rvSaturday);
    }

    private void initList(){
        saturdayList = new ArrayList<>();
        saturdayList.add(new Schedule("9:30", "Android, Prof.Muller", "theory", "505"));
        saturdayList.add(new Schedule("9:30", "Android, Prof.Muller", "theory", "505"));
        saturdayRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        saturdayRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        saturdayAdapter = new SaturdayAdapter(saturdayList);
        saturdayRecyclerView.setAdapter(saturdayAdapter);
    }
}
