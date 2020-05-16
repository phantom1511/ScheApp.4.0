//package com.dastan.scheapp4_0.ui.thursday;
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
//public class ThursdayActivity extends AppCompatActivity {
//
//    private RecyclerView thursdayRecyclerView;
//    private static ThursdayAdapter thursdayAdapter;
//    private static List<Schedule> thursdayList;
//    private FloatingActionButton thursdayFab;
//    private int mondayRVPosition;
//    private String groupName;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_thursday);
//
//        groupName = getIntent().getStringExtra("thursdaySchedule");
//        //Log.e("ron", groupName);
//
//        initViews();
//        initList();
//        initListeners();
//    }
//
//    private void initViews() {
//        thursdayRecyclerView = findViewById(R.id.rvThursday);
//        thursdayFab = findViewById(R.id.fabThursday);
//    }
//
//    private void initListeners(){
//        thursdayFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ThursdayActivity.this, AddScheduleActivity.class);
//                intent.putExtra("groupName", groupName);
//                intent.putExtra("days", "thursday");
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void initList() {
//        thursdayList = new ArrayList<>();
//        thursdayRecyclerView.setLayoutManager(new LinearLayoutManager(ThursdayActivity.this));
//        thursdayRecyclerView.addItemDecoration(new DividerItemDecoration(ThursdayActivity.this,
//                DividerItemDecoration.VERTICAL));
//        thursdayAdapter = new ThursdayAdapter(thursdayList);
//        thursdayRecyclerView.setAdapter(thursdayAdapter);
//        thursdayAdapter.setOnItemClickListeners(new OnItemClickListeners() {
//            @Override
//            public void onClick(int position) {
//
//            }
//
//            @Override
//            public void onLongClick(final int position) {
//                //Toast.makeText(getContext(), "long click pos = " + position, Toast.LENGTH_SHORT).show();
//                AlertDialog.Builder builder = new AlertDialog.Builder(ThursdayActivity.this);
//                builder.setTitle("Are you sure to delete?");
//                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        App.getDatabase().scheduleDao().delete(thursdayList.get(position));
//                        FirebaseFirestore.getInstance()
//                                .collection("thursday")
//                                .document(thursdayList.get(position).getId())
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
//                        Intent intent = new Intent(ThursdayActivity.this, AddScheduleActivity.class);
//                        intent.putExtra("thursdaySchedule", thursdayList.get(position));
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
//                thursdayList.clear();
//                for (Schedule schedule: schedules){
//                    if (schedule.getDays().equals("thursday")){
//                        if (schedule.getGroup().equals(groupName))
//                            thursdayList.add(schedule);
//                    }
//                }
//                thursdayAdapter.notifyDataSetChanged();
//            }
//        });
//
//    }
//}
