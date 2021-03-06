//package com.dastan.scheapp4_0.ui.monday;
//
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.DividerItemDecoration;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.dastan.scheapp4_0.App;
//import com.dastan.scheapp4_0.R;
//import com.dastan.scheapp4_0.Schedule;
//import com.dastan.scheapp4_0.add.AddScheduleActivity;
//import com.dastan.scheapp4_0.interfaces.OnItemClickListeners;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QuerySnapshot;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MondayActivity extends AppCompatActivity {
//
//    private RecyclerView mondayRecyclerView;
//    private static MondayAdapter mondayAdapter;
//    private static List<Schedule> mondayList;
//    private FloatingActionButton mondayFab;
//    private int mondayRVPosition;
//    private String groupName;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_monday);
//
//        groupName = getIntent().getStringExtra("mondaySchedule");
//
//        initViews();
//        initList();
//        initListeners();
//        loadData();
//    }
//
//    private void initViews() {
//        mondayRecyclerView = findViewById(R.id.rvMonday);
//        mondayFab = findViewById(R.id.fabMonday);
//    }
//
//    private void initListeners(){
//        mondayFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MondayActivity.this, AddScheduleActivity.class);
//                intent.putExtra("groupName", groupName);
//                intent.putExtra("days", "monday");
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void initList() {
//        mondayList = new ArrayList<>();
//        mondayRecyclerView.setLayoutManager(new LinearLayoutManager(MondayActivity.this));
//        mondayRecyclerView.addItemDecoration(new DividerItemDecoration(MondayActivity.this,
//                DividerItemDecoration.VERTICAL));
//        mondayAdapter = new MondayAdapter(mondayList);
//        mondayRecyclerView.setAdapter(mondayAdapter);
//        mondayAdapter.setOnItemClickListeners(new OnItemClickListeners() {
//            @Override
//            public void onClick(int position) {
//
//            }
//
//            @Override
//            public void onLongClick(final int position) {
//                //Toast.makeText(getContext(), "long click pos = " + position, Toast.LENGTH_SHORT).show();
//                AlertDialog.Builder builder = new AlertDialog.Builder(MondayActivity.this);
//                builder.setTitle("Are you sure to delete?");
//                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        App.getDatabase().scheduleDao().delete(mondayList.get(position));
//                        FirebaseFirestore.getInstance()
//                                .collection("monday")
//                                .document(mondayList.get(position).getId())
//                                .delete()
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()) {
//                                            Toast.makeText(App.instance.getBaseContext(), "Deleted", Toast.LENGTH_SHORT).show();
//                                        } else {
//                                            Toast.makeText(App.instance.getBaseContext(), "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
//                    }
//                });
//                builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(MondayActivity.this, AddScheduleActivity.class);
//                        intent.putExtra("mondaySchedule", mondayList.get(position));
//                        startActivity(intent);
//                        mondayRVPosition = position;
//                    }
//                });
//                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(App.instance.getBaseContext(), "your action was canceled", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
//            }
//        });
////        App.getDatabase().scheduleDao().getAll().observe(this, new Observer<List<Schedule>>() {
////            @Override
////            public void onChanged(List<Schedule> schedules) {
////                mondayList.clear();
////                for (Schedule schedule: schedules){
////                    if (schedule.getDays().equals("monday")){
////                        if (schedule.getGroup().equals(groupName))
////                        mondayList.add(schedule);
////                    }
////                }
////                mondayAdapter.notifyDataSetChanged();
////            }
////        });
//
////        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
////        FirebaseFirestore.getInstance()
////                .collection("days")
////                .whereEqualTo("group", preferences.getString("getGroup", "AIN-1-16"))
////                .get()
////                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
////                    @Override
////                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
////                        if (task.isSuccessful()) {
////                            for (QueryDocumentSnapshot document : task.getResult()) {
////                                Log.d("ron", document.getId() + " => " + document.getData());
////                            }
////                        } else {
////                            Log.d("ron", "Error getting documents: ", task.getException());
////                        }
////                    }
////                });
//
//    }
//
//    private void loadData() {
//        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
//        FirebaseFirestore.getInstance()
//                .collection("days")
//                .whereEqualTo("group", groupName)
//                .whereEqualTo("days", "monday")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            mondayList.clear();
//                            for (DocumentSnapshot document : task.getResult().getDocuments()) {
//                                Schedule schedule = document.toObject(Schedule.class);
//                                mondayList.add(schedule);
//                                Log.d("ron", document.getId() + " => " + document.getData());
//                            }
//                            mondayAdapter.notifyDataSetChanged();
//                        } else {
//                            Log.d("ron", "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
//    }
//
//}
