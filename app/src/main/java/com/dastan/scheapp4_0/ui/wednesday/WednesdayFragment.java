package com.dastan.scheapp4_0.ui.wednesday;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.dastan.scheapp4_0.interfaces.OnItemClickListeners;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hamza.slidingsquaresloaderview.SlidingSquareLoaderView;

import java.util.ArrayList;
import java.util.List;

import static com.dastan.scheapp4_0.main.MainActivity.ADMIN;

public class WednesdayFragment extends Fragment {
    private RecyclerView wednesdayRecyclerView;
    private static WednesdayAdapter wednesdayAdapter;
    private static List<Schedule> wednesdayList;
    private FloatingActionButton wednesdayFab;
    private int wednesdayRVPosition;
    private String groupName;
    private SharedPreferences preferences;
    private SlidingSquareLoaderView slidingSquareLoaderView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_wednesday, container, false);

        preferences = root.getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        groupName = preferences.getString("getGroup", "");

        if (groupName.equals(ADMIN)) {
            groupName = preferences.getString("click", "");
        }

        initViews(root);
        initList();
        initListeners();
        //loadData();
        return root;
    }

    private void initViews(View view) {
        wednesdayRecyclerView = view.findViewById(R.id.rvWednesday);
        wednesdayFab = view.findViewById(R.id.fabWednesday);
    }

    private void initListeners() {
            wednesdayFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), AddScheduleActivity.class);
                    intent.putExtra("groupName", groupName);
                    intent.putExtra("days", "wednesday");
                    startActivity(intent);
                }
            });
    }

    private void initList() {
        wednesdayList = new ArrayList<>();
        wednesdayRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        wednesdayRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        wednesdayAdapter = new WednesdayAdapter(wednesdayList);
        wednesdayRecyclerView.setAdapter(wednesdayAdapter);
        wednesdayAdapter.setOnItemClickListeners(new OnItemClickListeners() {
            @Override
            public void onClick(int position) {
                Log.e("ron", "justClick");
            }

            @Override
            public void onLongClick(final int position) {
                    //Toast.makeText(getContext(), "long click pos = " + position, Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Are you sure to delete?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            App.getDatabase().scheduleDao().delete(wednesdayList.get(position));
                            FirebaseFirestore.getInstance()
                                    .collection("wednesday")
                                    .document(wednesdayList.get(position).getId())
                                    .delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(App.instance.getBaseContext(), "Deleted", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(App.instance.getBaseContext(), "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    });
                    builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getContext(), AddScheduleActivity.class);
                            intent.putExtra("mondaySchedule", wednesdayList.get(position));
                            startActivity(intent);
                            wednesdayRVPosition = position;
                        }
                    });
                    builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(App.instance.getBaseContext(), "your action was canceled", Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
            }
        });
        App.getDatabase().scheduleDao().getAll().observe(this, new Observer<List<Schedule>>() {
            @Override
            public void onChanged(List<Schedule> schedules) {
                wednesdayList.clear();
                for (Schedule schedule: schedules){
                    if (schedule.getDays().equals("wednesday")){
                        if (schedule.getGroup().equals(groupName))
                        wednesdayList.add(schedule);
                    }
                }
                wednesdayAdapter.notifyDataSetChanged();
            }
        });

//        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
//        FirebaseFirestore.getInstance()
//                .collection("days")
//                .whereEqualTo("group", preferences.getString("getGroup", "AIN-1-16"))
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d("ron", document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Log.d("ron", "Error getting documents: ", task.getException());
//                        }
//                    }
//                });

    }

//    private void loadData() {
//        //SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
//        FirebaseFirestore.getInstance()
//                .collection("days")
//                .whereEqualTo("group", groupName)
//                .whereEqualTo("days", "wednesday")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            wednesdayList.clear();
//                            for (DocumentSnapshot document : task.getResult().getDocuments()) {
//                                Schedule schedule = document.toObject(Schedule.class);
//                                wednesdayList.add(schedule);
//                                slidingSquareLoaderView.stop();
//                                slidingSquareLoaderView.setVisibility(View.INVISIBLE);
//                                Log.d("ron", document.getId() + " => " + document.getData());
//                            }
//                            wednesdayAdapter.notifyDataSetChanged();
//                        } else {
//                            Log.d("ron", "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
//    }

}
