//package com.dastan.scheapp4_0.ui.friday;
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
//public class FridayActivity extends AppCompatActivity {
//
//    private RecyclerView fridayRecyclerView;
//    private static FridayAdapter fridayAdapter;
//    private static List<Schedule> fridayList;
//    private FloatingActionButton fridayFab;
//    private int mondayRVPosition;
//    private String groupName;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_friday);
//
//        groupName = getIntent().getStringExtra("fridaySchedule");
//        //Log.e("ron", groupName);
//
//        initViews();
//        initList();
//        initListeners();
//    }
//
//    private void initViews() {
//        fridayRecyclerView = findViewById(R.id.rvFriday);
//        fridayFab = findViewById(R.id.fabFriday);
//    }
//
//    private void initListeners(){
//        fridayFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(FridayActivity.this, AddScheduleActivity.class);
//                intent.putExtra("groupName", groupName);
//                intent.putExtra("days", "friday");
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void initList() {
//        fridayList = new ArrayList<>();
//        fridayRecyclerView.setLayoutManager(new LinearLayoutManager(FridayActivity.this));
//        fridayRecyclerView.addItemDecoration(new DividerItemDecoration(FridayActivity.this,
//                DividerItemDecoration.VERTICAL));
//        fridayAdapter = new FridayAdapter(fridayList);
//        fridayRecyclerView.setAdapter(fridayAdapter);
//        fridayAdapter.setOnItemClickListeners(new OnItemClickListeners() {
//            @Override
//            public void onClick(int position) {
//
//            }
//
//            @Override
//            public void onLongClick(final int position) {
//                //Toast.makeText(getContext(), "long click pos = " + position, Toast.LENGTH_SHORT).show();
//                AlertDialog.Builder builder = new AlertDialog.Builder(FridayActivity.this);
//                builder.setTitle("Are you sure to delete?");
//                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        App.getDatabase().scheduleDao().delete(fridayList.get(position));
//                        FirebaseFirestore.getInstance()
//                                .collection("friday")
//                                .document(fridayList.get(position).getId())
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
//                        Intent intent = new Intent(FridayActivity.this, AddScheduleActivity.class);
//                        intent.putExtra("fridaySchedule", fridayList.get(position));
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
//                fridayList.clear();
//                for (Schedule schedule: schedules){
//                    if (schedule.getDays().equals("friday")){
//                        if (schedule.getGroup().equals(groupName))
//                            fridayList.add(schedule);
//                    }
//                }
//                fridayAdapter.notifyDataSetChanged();
//            }
//        });
//
//    }
//}
//
