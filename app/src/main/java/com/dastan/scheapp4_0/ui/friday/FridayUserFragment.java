package com.dastan.scheapp4_0.ui.friday;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dastan.scheapp4_0.App;
import com.dastan.scheapp4_0.R;
import com.dastan.scheapp4_0.Schedule;
import com.dastan.scheapp4_0.add.AddScheduleActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hamza.slidingsquaresloaderview.SlidingSquareLoaderView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.dastan.scheapp4_0.main.MainActivity.ADMIN;

public class FridayUserFragment extends Fragment {
    private RecyclerView fridayRecyclerView;
    private static FridayUserAdapter fridayAdapter;
    private static List<Schedule> fridayList;
    private FloatingActionButton fridayFab;
    private int fridayRVPosition;
    private String groupName;
    private SharedPreferences preferences;
    private SlidingSquareLoaderView slidingSquareLoaderView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_friday_user, container, false);

        preferences = root.getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        groupName = preferences.getString("getGroup", "");

        if (groupName.equals(ADMIN)) {
            groupName = preferences.getString("click", "");
        }

        initViews(root);
        slidingSquareLoaderView.start();
        initList();
        loadData();
        return root;
    }

    private void initViews(View view) {
        fridayRecyclerView = view.findViewById(R.id.rvFriday);
        fridayFab = view.findViewById(R.id.fabFriday);
        slidingSquareLoaderView = view.findViewById(R.id.pbFri);
    }

    private void initList() {
        fridayList = new ArrayList<>();
        fridayRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fridayRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        fridayAdapter = new FridayUserAdapter(fridayList);
        fridayRecyclerView.setAdapter(fridayAdapter);
    }

    private void loadData() {
        //SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        FirebaseFirestore.getInstance()
                .collection("days")
                .whereEqualTo("group", groupName)
                .whereEqualTo("days", "friday")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            fridayList.clear();
                            for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                Schedule schedule = document.toObject(Schedule.class);
                                fridayList.add(schedule);
                                slidingSquareLoaderView.stop();
                                slidingSquareLoaderView.setVisibility(View.INVISIBLE);
                                Log.d("ron", document.getId() + " => " + document.getData());
                            }
                            Collections.sort(fridayList, new Comparator<Schedule>() {
                                @Override
                                public int compare(Schedule o1, Schedule o2) {
                                    return o1.getTime().compareTo(o2.getTime());
                                }
                            });
                            fridayAdapter.notifyDataSetChanged();
                        } else {
                            Log.d("ron", "Error getting documents: ", task.getException());
                        }
                    }
                });

        App.getDatabase().scheduleDao().getAll().observe(this, new Observer<List<Schedule>>() {
            @Override
            public void onChanged(List<Schedule> schedules) {
                fridayList.clear();
                for (Schedule schedule : schedules) {
                    if (schedule.getDays().equals("friday")) {
                        if (schedule.getGroup().equals(groupName))
                            fridayList.add(schedule);
                    }
                }
                fridayAdapter.notifyDataSetChanged();
            }
        });
    }

}
