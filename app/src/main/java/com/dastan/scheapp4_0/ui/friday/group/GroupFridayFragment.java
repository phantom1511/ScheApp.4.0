package com.dastan.scheapp4_0.ui.friday.group;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dastan.scheapp4_0.App;
import com.dastan.scheapp4_0.Group;
import com.dastan.scheapp4_0.R;
import com.dastan.scheapp4_0.Schedule;
import com.dastan.scheapp4_0.interfaces.OnItemClickListeners;
import com.dastan.scheapp4_0.ui.friday.FridayFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class GroupFridayFragment extends Fragment {

    private RecyclerView groupRecyclerView;
    private static GroupFridayAdapter groupAdapter;
    private static List<Group> groupList;
    private FloatingActionButton groupFridayFab;
    private int groupRVPosition;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_group, container, false);

        initViews(root);
        initList();
        //initListeners();

        return root;
    }

    private void initViews(View view) {
        groupRecyclerView = view.findViewById(R.id.rvGroup);

    }

    private void initList() {
        groupList = new ArrayList<>();
        groupRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        groupRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        groupAdapter = new GroupFridayAdapter(groupList);
        groupRecyclerView.setAdapter(groupAdapter);
        groupAdapter.setOnItemClickListeners(new OnItemClickListeners() {
            @Override
            public void onClick(int position) {
                getContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
                        .edit()
                        .putString("click", groupList.get(position).getGroupName())
                        .apply();
                NavHostFragment navHost = (NavHostFragment) getActivity()
                        .getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment);
                NavController navController = navHost.getNavController();

                NavInflater navInflater = navController.getNavInflater();
                NavGraph graph = navInflater.inflate(R.navigation.mobile_navigation);
                graph.setStartDestination(R.id.navFridayDay);
                navController.setGraph(graph);
//                FridayFragment fridayFragment = new FridayFragment();
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.nav_host_fragment, fridayFragment)
//                        .addToBackStack(null)
//                        .commit();
//                Intent intent = new Intent(getContext(), FridayActivity.class);
//                intent.putExtra("fridaySchedule", groupList.get(position).getGroupName());
//                startActivity(intent);
                groupRVPosition = position;
            }

            @Override
            public void onLongClick(final int position) {
                //Toast.makeText(getContext(), "long click pos = " + position, Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Are you sure to delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        App.getDatabase().groupDao().delete(groupList.get(position));
                        FirebaseFirestore.getInstance()
                                .collection("groupFriday")
                                .document(groupList.get(position).getId())
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
        App.getDatabase().groupDao().getAll().observe(this, new Observer<List<Group>>() {
            @Override
            public void onChanged(List<Group> groups) {
                groupList.clear();
                groupList.addAll(groups);
                groupAdapter.notifyDataSetChanged();
            }
        });
    }
}
