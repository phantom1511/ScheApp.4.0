//package com.dastan.scheapp4_0.ui.saturday;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.lifecycle.Observer;
//import androidx.recyclerview.widget.DividerItemDecoration;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Toast;
//
//import com.dastan.scheapp4_0.App;
//import com.dastan.scheapp4_0.R;
//import com.dastan.scheapp4_0.Schedule;
//import com.dastan.scheapp4_0.add.AddScheduleActivity;
//import com.dastan.scheapp4_0.interfaces.OnItemClickListeners;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class SaturdayActivity extends AppCompatActivity {
//
//    private RecyclerView saturdayRecyclerView;
//    private static SaturdayAdapter saturdayAdapter;
//    private static List<Schedule> saturdayList;
//    private FloatingActionButton saturdayFab;
//    private int mondayRVPosition;
//    private String groupName;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_saturday);
//
//        groupName = getIntent().getStringExtra("saturdaySchedule");
//        //Log.e("ron", groupName);
//
//        initViews();
//        initList();
//        initListeners();
//    }
//
//    private void initViews() {
//        saturdayRecyclerView = findViewById(R.id.rvSaturday);
//        saturdayFab = findViewById(R.id.fabSaturday);
//    }
//
//    private void initListeners(){
//        saturdayFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(SaturdayActivity.this, AddScheduleActivity.class);
//                intent.putExtra("groupName", groupName);
//                intent.putExtra("days", "saturday");
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void initList() {
//        saturdayList = new ArrayList<>();
//        saturdayRecyclerView.setLayoutManager(new LinearLayoutManager(SaturdayActivity.this));
//        saturdayRecyclerView.addItemDecoration(new DividerItemDecoration(SaturdayActivity.this,
//                DividerItemDecoration.VERTICAL));
//        saturdayAdapter = new SaturdayAdapter(saturdayList);
//        saturdayRecyclerView.setAdapter(saturdayAdapter);
//        saturdayAdapter.setOnItemClickListeners(new OnItemClickListeners() {
//            @Override
//            public void onClick(int position) {
//
//            }
//
//            @Override
//            public void onLongClick(final int position) {
//                //Toast.makeText(getContext(), "long click pos = " + position, Toast.LENGTH_SHORT).show();
//                AlertDialog.Builder builder = new AlertDialog.Builder(SaturdayActivity.this);
//                builder.setTitle("Are you sure to delete?");
//                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        App.getDatabase().scheduleDao().delete(saturdayList.get(position));
//                        FirebaseFirestore.getInstance()
//                                .collection("saturday")
//                                .document(saturdayList.get(position).getId())
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
//                        Intent intent = new Intent(SaturdayActivity.this, AddScheduleActivity.class);
//                        intent.putExtra("saturdaySchedule", saturdayList.get(position));
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
//        App.getDatabase().scheduleDao().getAll().observe(this, new Observer<List<Schedule>>() {
//            @Override
//            public void onChanged(List<Schedule> schedules) {
//                saturdayList.clear();
//                for (Schedule schedule: schedules){
//                    if (schedule.getDays().equals("saturday")){
//                        if (schedule.getGroup().equals(groupName))
//                            saturdayList.add(schedule);
//                    }
//                }
//                saturdayAdapter.notifyDataSetChanged();
//            }
//        });
//
//    }
//}
//
