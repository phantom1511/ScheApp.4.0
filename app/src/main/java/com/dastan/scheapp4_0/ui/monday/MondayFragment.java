package com.dastan.scheapp4_0.ui.monday;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dastan.scheapp4_0.App;
import com.dastan.scheapp4_0.R;
import com.dastan.scheapp4_0.Schedule;
import com.dastan.scheapp4_0.add.AddActivity;
import com.dastan.scheapp4_0.interfaces.OnItemClickListeners;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MondayFragment extends Fragment {

    private MondayViewModel mondayViewModel;
    private RecyclerView mondayRecyclerView;
    private static MondayAdapter mondayAdapter;
    private static List<Schedule> mondayList;
    private int mondayRVPosition;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mondayViewModel =
                ViewModelProviders.of(this).get(MondayViewModel.class);
        View root = inflater.inflate(R.layout.fragment_monday, container, false);

        initViews(root);
        initList();

        return root;
    }

    private void initViews(View view) {
        mondayRecyclerView = view.findViewById(R.id.rvMonday);
        FloatingActionButton fab = view.findViewById(R.id.fab);

    }

    private void initList() {
        mondayList = new ArrayList<>();
        mondayRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mondayRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        mondayAdapter = new MondayAdapter(mondayList);
        mondayRecyclerView.setAdapter(mondayAdapter);
        mondayAdapter.setOnItemClickListeners(new OnItemClickListeners() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getContext(), AddActivity.class);
                intent.putExtra("mondaySchedule", mondayList.get(position));
                startActivity(intent);
                mondayRVPosition = position;
            }

            @Override
            public void onLongClick(final int position) {
                //Toast.makeText(getContext(), "long click pos = " + position, Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Are you sure to delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        App.getDatabase().scheduleDao().delete(mondayList.get(position));
                        FirebaseFirestore.getInstance()
                                .collection("monday")
                                .document(mondayList.get(position).getId())
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
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
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
                mondayList.clear();
                mondayList.addAll(schedules);
                mondayAdapter.notifyDataSetChanged();
            }
        });
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK && requestCode == 100){
//            Schedule schedule = (Schedule) data.getSerializableExtra("mondaySchedule");
//            mondayList.add(schedule);
//            mondayAdapter.notifyDataSetChanged();
//        } else if (resultCode == Activity.RESULT_OK && requestCode == 200){
//            Schedule schedule = (Schedule) data.getSerializableExtra("mondaySchedule");
//            mondayList.set(mondayRVPosition, schedule);
//            mondayAdapter.notifyDataSetChanged();
//        }
//    }
}
