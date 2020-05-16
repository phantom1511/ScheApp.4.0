package com.dastan.scheapp4_0.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.dastan.scheapp4_0.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ImageView imageProfile;
    private TextView textName, textGroup;
    private String name, group;
    public static String ADMIN = "admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        boolean isRegistered = preferences.getBoolean("isRegistered", false);
        name = preferences.getString("getName", "Dastan Tulokulov");
        group = preferences.getString("getGroup", "AIN-1-16");


//        if (!isRegistered){
//            Intent intent = new Intent(this, ProfileActivity.class);
//            startActivity(intent);
//            finish();
//            return;
//        }

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent intent = new Intent(this, PhoneActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        String editName = getIntent().getStringExtra("info1");
        String editGroup = getIntent().getStringExtra("info2");
        Log.e("ron", editName + " " + editGroup);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, AddGroupActivity.class);
//                startActivity(intent);
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View header = navigationView.getHeaderView(0);
//        header.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
//                startActivityForResult(intent, 101);
//            }
//        });

        textName = header.findViewById(R.id.tvNameHeader);
        textGroup = header.findViewById(R.id.tvGroupHeader);
        imageProfile = header.findViewById(R.id.imgProfileHeader);

        textName.setText(name);
        textGroup.setText(group);
        setImageProfile();


        if (group.equals(ADMIN)) {
            Menu navMenu = navigationView.getMenu();
            navMenu.findItem(R.id.navMondayUser).setVisible(false);
            navMenu.findItem(R.id.navTuesdayUser).setVisible(false);
            navMenu.findItem(R.id.navWednesdayUser).setVisible(false);
            navMenu.findItem(R.id.navThursdayUser).setVisible(false);
            navMenu.findItem(R.id.navFridayUser).setVisible(false);
            navMenu.findItem(R.id.navSaturdayUser).setVisible(false);
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navMonday, R.id.navTuesday, R.id.navWednesday, R.id.navThursday,
                    R.id.navFriday, R.id.navSaturday, R.id.navProfile, R.id.navAbout)
                    .setDrawerLayout(drawer)
                    .build();
            Log.e("ron", "groups");
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
            NavGraph navGraph = navController.getGraph();
            navGraph.setStartDestination(R.id.navMonday);
            navController.setGraph(navGraph);

        } else {
            Menu navMenu = navigationView.getMenu();
            navMenu.findItem(R.id.navMonday).setVisible(false);
            navMenu.findItem(R.id.navTuesday).setVisible(false);
            navMenu.findItem(R.id.navWednesday).setVisible(false);
            navMenu.findItem(R.id.navThursday).setVisible(false);
            navMenu.findItem(R.id.navFriday).setVisible(false);
            navMenu.findItem(R.id.navSaturday).setVisible(false);
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navMondayUser, R.id.navTuesdayUser, R.id.navWednesdayUser, R.id.navThursdayUser,
                    R.id.navFridayUser, R.id.navSaturdayUser, R.id.navProfile, R.id.navAbout)
                    .setDrawerLayout(drawer)
                    .build();
            Log.e("ron", "days");
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
            NavGraph navGraph = navController.getGraph();
            navGraph.setStartDestination(R.id.navMondayUser);
            navController.setGraph(navGraph);
        }
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_signOut) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Are you sure to sign out?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth.getInstance().signOut();
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "your action was canceled", Toast.LENGTH_SHORT).show();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 101 && data != null) {
            name = data.getStringExtra("getName");
            group = data.getStringExtra("getGroup");

            textName.setText(name);
            textGroup.setText(group);
            setImageProfile();
            if (name != null && group != null) {

            }
        }
    }

    private void setImageProfile() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        String userId = FirebaseAuth.getInstance().getUid();
        storageReference.child("image/*" + userId).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(MainActivity.this).load(uri).circleCrop().into(imageProfile);
            }
        });
    }

    @Override
    public void onBackPressed() {
        NavHostFragment navHost = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHost.getNavController();

        NavInflater navInflater = navController.getNavInflater();
        NavGraph graph = navInflater.inflate(R.navigation.mobile_navigation);
        Log.e("ron", R.id.navMondayDay + "/" + navController.getCurrentDestination().getId());
        if (group.equals(ADMIN)) {
            if (R.id.navMondayDay == navController.getCurrentDestination().getId()) {
                graph.setStartDestination(R.id.navMonday);
                navController.setGraph(graph);
            } else if (R.id.navTuesdayDay == navController.getCurrentDestination().getId()) {
                graph.setStartDestination(R.id.navTuesday);
                navController.setGraph(graph);
            } else if (R.id.navWednesdayDay == navController.getCurrentDestination().getId()) {
                graph.setStartDestination(R.id.navWednesday);
                navController.setGraph(graph);
            } else if (R.id.navThursdayDay == navController.getCurrentDestination().getId()) {
                graph.setStartDestination(R.id.navThursday);
                navController.setGraph(graph);
            } else if (R.id.navFridayDay == navController.getCurrentDestination().getId()) {
                graph.setStartDestination(R.id.navFriday);
                navController.setGraph(graph);
            } else if (R.id.navSaturdayDay == navController.getCurrentDestination().getId()) {
                graph.setStartDestination(R.id.navSaturday);
                navController.setGraph(graph);
            }
        } else {
            super.onBackPressed();
        }
    }
}
