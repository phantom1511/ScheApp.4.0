package com.dastan.scheapp4_0.add;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dastan.scheapp4_0.App;
import com.dastan.scheapp4_0.Group;
import com.dastan.scheapp4_0.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddGroupActivity extends AppCompatActivity {

    private EditText editGroup;
    private Group mGroup;
    private String name;
    private ProgressBar addProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        initViews();
    }

    private void initViews() {
        editGroup = findViewById(R.id.etMondayGroupName);
        addProgress = findViewById(R.id.addGroupMondayProgressBar);

        mGroup = (Group) getIntent().getSerializableExtra("groupSchedule");
        if (mGroup != null) {
            editGroup.setText(mGroup.getGroupName(), TextView.BufferType.EDITABLE);
        }
    }


    public void onGroupMondayAdd(View view) {
        addProgress.setVisibility(View.VISIBLE);
        name = editGroup.getText().toString().trim();

        if (mGroup != null) {
            mGroup.setGroupName(name);
            updateInFirestore();
        } else {
            mGroup = new Group(name);
            saveToFirestore();
        }
        finish();
    }

    private void saveToFirestore() {
        FirebaseFirestore.getInstance()
                .collection("groupMonday")
                .add(mGroup)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            mGroup.setId(task.getResult().getId());
                            App.getDatabase().groupDao().insert(mGroup);
                            Toast.makeText(App.instance.getBaseContext(), "Added", Toast.LENGTH_SHORT).show();
                            addProgress.setVisibility(View.INVISIBLE);
                            finish();
                        } else {
                            Toast.makeText(App.instance.getBaseContext(), "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateInFirestore() {
        FirebaseFirestore.getInstance()
                .collection("groupMonday")
                .document(mGroup.getId())
                .set(mGroup)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            App.getDatabase().groupDao().update(mGroup);
                            Toast.makeText(App.instance.getBaseContext(), "Updated", Toast.LENGTH_SHORT).show();
                            addProgress.setVisibility(View.INVISIBLE);
                            finish();
                        } else {
                            Toast.makeText(App.instance.getBaseContext(), "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
