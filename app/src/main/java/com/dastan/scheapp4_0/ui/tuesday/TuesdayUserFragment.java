package com.dastan.scheapp4_0.ui.tuesday;

import android.content.Context;
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

public class TuesdayUserFragment extends Fragment {

    private RecyclerView tuesdayRecyclerView;
    private static TuesdayUserAdapter tuesdayAdapter;
    private static List<Schedule> tuesdayList;
    private FloatingActionButton tuesdayFab;
    private int tuesdayRVPosition;
    private String groupName;
    private SharedPreferences preferences;
    private SlidingSquareLoaderView slidingSquareLoaderView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tuesday_user, container, false);

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
        tuesdayRecyclerView = view.findViewById(R.id.rvTuesday);
        tuesdayFab = view.findViewById(R.id.fabTuesday);
        slidingSquareLoaderView = view.findViewById(R.id.pbTue);
    }

    private void initList() {
        tuesdayList = new ArrayList<>();
        tuesdayRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tuesdayRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        tuesdayAdapter = new TuesdayUserAdapter(tuesdayList);
        tuesdayRecyclerView.setAdapter(tuesdayAdapter);
    }

    private void loadData() {
        //SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        FirebaseFirestore.getInstance()
                .collection("days")
                .whereEqualTo("group", groupName)
                .whereEqualTo("days", "tuesday")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            tuesdayList.clear();
                            for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                slidingSquareLoaderView.stop();
                                slidingSquareLoaderView.setVisibility(View.INVISIBLE);
                                Schedule schedule = document.toObject(Schedule.class);
                                tuesdayList.add(schedule);
                                Log.d("ron", document.getId() + " => " + document.getData());
                            }
                            Collections.sort(tuesdayList, new Comparator<Schedule>() {
                                @Override
                                public int compare(Schedule o1, Schedule o2) {
                                    return o1.getTime().compareTo(o2.getTime());
                                }
                            });
                            tuesdayAdapter.notifyDataSetChanged();
                        } else {
                            Log.e("ron", "Error getting documents: ", task.getException());
                        }
                        Log.e("ron", "data");
                        Log.e("ron", "Error getting documents: " + task.getException());
                    }
                });

        App.getDatabase().scheduleDao().getAll().observe(this, new Observer<List<Schedule>>() {
            @Override
            public void onChanged(List<Schedule> schedules) {
                tuesdayList.clear();
                for (Schedule schedule : schedules) {
                    if (schedule.getDays().equals("tuesday")) {
                        if (schedule.getGroup().equals(groupName))
                            tuesdayList.add(schedule);
                    }
                }
                tuesdayAdapter.notifyDataSetChanged();
            }
        });

    }
}
