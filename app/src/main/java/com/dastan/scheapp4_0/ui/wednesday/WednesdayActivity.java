//package com.dastan.scheapp4_0.ui.wednesday;
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
//import com.dastan.scheapp4_0.ui.tuesday.TuesdayAdapter;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class WednesdayActivity extends AppCompatActivity {
//
//    private RecyclerView wednesdayRecyclerView;
//    private static WednesdayAdapter wednesdayAdapter;
//    private static List<Schedule> wednesdayList;
//    private FloatingActionButton wednesdayFab;
//    private int mondayRVPosition;
//    private String groupName;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_wednesday);
//
//        groupName = getIntent().getStringExtra("wednesdaySchedule");
//        //Log.e("ron", groupName);
//
//        initViews();
//        initList();
//        initListeners();
//    }
//
//    private void initViews() {
//        wednesdayRecyclerView = findViewById(R.id.rvWednesday);
//        wednesdayFab = findViewById(R.id.fabWednesday);
//    }
//
//    private void initListeners(){
//        wednesdayFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(WednesdayActivity.this, AddScheduleActivity.class);
//                intent.putExtra("groupName", groupName);
//                intent.putExtra("days", "wednesday");
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void initList() {
//        wednesdayList = new ArrayList<>();
//        wednesdayRecyclerView.setLayoutManager(new LinearLayoutManager(WednesdayActivity.this));
//        wednesdayRecyclerView.addItemDecoration(new DividerItemDecoration(WednesdayActivity.this,
//                DividerItemDecoration.VERTICAL));
//        wednesdayAdapter = new WednesdayAdapter(wednesdayList);
//        wednesdayRecyclerView.setAdapter(wednesdayAdapter);
//        wednesdayAdapter.setOnItemClickListeners(new OnItemClickListeners() {
//            @Override
//            public void onClick(int position) {
//
//            }
//
//            @Override
//            public void onLongClick(final int position) {
//                //Toast.makeText(getContext(), "long click pos = " + position, Toast.LENGTH_SHORT).show();
//                AlertDialog.Builder builder = new AlertDialog.Builder(WednesdayActivity.this);
//                builder.setTitle("Are you sure to delete?");
//                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        App.getDatabase().scheduleDao().delete(wednesdayList.get(position));
//                        FirebaseFirestore.getInstance()
//                                .collection("wednesday")
//                                .document(wednesdayList.get(position).getId())
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
//                        Intent intent = new Intent(WednesdayActivity.this, AddScheduleActivity.class);
//                        intent.putExtra("wednesdaySchedule", wednesdayList.get(position));
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
//                wednesdayList.clear();
//                for (Schedule schedule: schedules){
//                    if (schedule.getDays().equals("wednesday")){
//                        if (schedule.getGroup().equals(groupName))
//                            wednesdayList.add(schedule);
//                    }
//                }
//                wednesdayAdapter.notifyDataSetChanged();
//            }
//        });
//
//    }
//
////    @Override
////    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////        if (resultCode == RESULT_OK && requestCode == 202){
////            Schedule schedule = (Schedule) data.getSerializableExtra("tuesdaySchedule");
////            tuesdayList.add(schedule);
////            tuesdayAdapter.notifyDataSetChanged();
////        }
////    }
//}
//
