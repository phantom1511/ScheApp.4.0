package com.dastan.scheapp4_0.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.dastan.scheapp4_0.App;
import com.dastan.scheapp4_0.Group;
import com.dastan.scheapp4_0.R;
import com.dastan.scheapp4_0.Schedule;
import com.dastan.scheapp4_0.ui.monday.group.GroupMondayAdapter;
import com.dastan.scheapp4_0.ui.monday.group.GroupMondayFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hamza.slidingsquaresloaderview.SlidingSquareLoaderView;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private EditText editName, editGroup;
    private ImageView imageProfile, imgProfileHeader;
    private final int Pick_image = 1;
    private SharedPreferences sharedPreferences;
    private SlidingSquareLoaderView imgProgress;
    private SlidingSquareLoaderView profileProgress;
    private Spinner groupSpinner;
    private List<Group> groupList;
    private ArrayAdapter<Group> adapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initViews();
        imgProgress.start();
        profileProgress.start();
        initListeners();
        loadData();
        setSpinner();
        loadGroupName();

    }

    private void setSpinner() {
        adapter = new ArrayAdapter<Group>(this, android.R.layout.simple_spinner_item, groupList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        groupSpinner.setAdapter(adapter);
        groupSpinner.setPrompt("Groups");
        groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), "pos " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadData() {
        String userId = FirebaseAuth.getInstance().getUid();
        FirebaseFirestore.getInstance()
                .collection("users")
                .document(userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            String name = task.getResult().getString("name");
                            String group = task.getResult().getString("group");
                            editName.setText(name);
                            editGroup.setText(group);
                            profileProgress.stop();
                            profileProgress.setVisibility(View.INVISIBLE);
                        }
                    }
                });

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        storageReference.child("image/*" + userId).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(ProfileActivity.this)
                        .load(uri)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(50)))
                        .into(imageProfile);
                imgProgress.stop();
                imgProgress.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void initViews() {
        editName = findViewById(R.id.etName);
        editGroup = findViewById(R.id.etGroup);
        imageProfile = findViewById(R.id.imgProfile);
        imgProfileHeader = findViewById(R.id.imgProfileHeader);
        imgProgress = findViewById(R.id.imgProgressBar);
        profileProgress = findViewById(R.id.profileProgressBar);
        groupSpinner = findViewById(R.id.spGroup);
    }

    private void initListeners() {
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, Pick_image);
            }
        });
    }


    public void onLogIn(View view) {
        String name = editName.getText().toString().trim();
        String group = editGroup.getText().toString().trim();
        if (name.isEmpty() && group.isEmpty()) {
            editName.setError("Please input your name");
            editName.requestFocus();
            return;
        }

        if (group.isEmpty()) {
            editGroup.setError("Please input your group");
            editGroup.requestFocus();
            return;
        }
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("group", group);
        String userId = FirebaseAuth.getInstance().getUid();
        FirebaseFirestore.getInstance()
                .collection("users")
                .document(userId)
                .set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfileActivity.this, "Succeed", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProfileActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        sharedPreferences = getApplicationContext().getSharedPreferences("settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("getName", name);
        editor.putString("getGroup", group);
        editor.apply();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("getName", name);
        intent.putExtra("getGroup", group);
        setResult(RESULT_OK, intent);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Pick_image && data != null){
            Uri uri = data.getData();
            imageProfile.setImageURI(uri);
            upload(uri);
        }
    }

    private void upload(Uri uri){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        String userId = FirebaseAuth.getInstance().getUid();
        StorageReference reference = storage.getReference().child("image/*" + userId);
        UploadTask uploadTask = reference.putFile(uri);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    Toast.makeText(App.instance.getBaseContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                    imgProgress.stop();
                    imgProgress.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void loadGroupName(){
        FirebaseFirestore.getInstance()
                .collection("group")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            groupList.clear();
                            for (DocumentSnapshot document : task.getResult().getDocuments()){
                                Group group = document.toObject(Group.class);
                                groupList.add(group);
                            }
                            adapter = new ArrayAdapter<Group>(ProfileActivity.this, android.R.layout.simple_spinner_item, groupList);
                            groupSpinner.setAdapter(adapter);
                        }
                    }
                });
    }
}
