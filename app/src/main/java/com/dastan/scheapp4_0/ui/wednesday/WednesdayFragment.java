package com.dastan.scheapp4_0.ui.wednesday;

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

public class WednesdayFragment extends Fragment {

    private WednesdayViewModel wednesdayViewModel;
    private RecyclerView wednesdayRecyclerView;
    private WednesdayAdapter wednesdayAdapter;
    private List<Schedule> wednesdayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        wednesdayViewModel =
                ViewModelProviders.of(this).get(WednesdayViewModel.class);
        View root = inflater.inflate(R.layout.fragment_wednesday, container, false);

        initViews(root);
        initList();

        return root;
    }

    private void initViews(View view){
        wednesdayRecyclerView = view.findViewById(R.id.rvWednesday);
    }

    private void initList(){
        wednesdayList = new ArrayList<>();
        wednesdayList.add(new Schedule("9:30", "History", "prac", "502"));
        wednesdayRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        wednesdayRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        wednesdayAdapter = new WednesdayAdapter(wednesdayList);
        wednesdayRecyclerView.setAdapter(wednesdayAdapter);
    }
}
