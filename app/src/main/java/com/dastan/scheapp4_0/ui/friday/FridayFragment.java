package com.dastan.scheapp4_0.ui.friday;

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

public class FridayFragment extends Fragment {

    private FridayViewModel fridayViewModel;
    private RecyclerView fridayRecyclerView;
    private FridayAdapter fridayAdapter;
    private List<Schedule> fridayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fridayViewModel =
                ViewModelProviders.of(this).get(FridayViewModel.class);
        View root = inflater.inflate(R.layout.fragment_friday, container, false);

        initViews(root);
        initList();

        return root;
    }

    private void initViews(View view) {
        fridayRecyclerView = view.findViewById(R.id.rvFriday);
    }

    private void initList(){
        fridayList = new ArrayList<>();
        fridayList.add(new Schedule("14:00", "Deutsch, Aida Dzheenalieva", "pra", "505"));
        fridayList.add(new Schedule("15:30", "Deutsch, Aida Dzheenalieva", "pra", "505"));
        fridayList.add(new Schedule("14:00", "Deutsch, Aida Dzheenalieva", "pra", "505"));
        fridayList.add(new Schedule("14:00", "Deutsch, Aida Dzheenalieva", "pra", "505"));
        fridayRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fridayRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        fridayAdapter = new FridayAdapter(fridayList);
        fridayRecyclerView.setAdapter(fridayAdapter);
    }
}
