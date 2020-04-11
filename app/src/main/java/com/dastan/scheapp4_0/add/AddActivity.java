package com.dastan.scheapp4_0.add;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dastan.scheapp4_0.App;
import com.dastan.scheapp4_0.R;
import com.dastan.scheapp4_0.Schedule;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddActivity extends AppCompatActivity {

    private EditText editTime;
    private EditText editLesson;
    private EditText editType;
    private EditText editRoom;
    private Schedule mSchedule;
    private ProgressBar addProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initViews();
    }

    private void initViews() {
        editTime = findViewById(R.id.etTimeMon);
        editLesson = findViewById(R.id.etLessonMon);
        editType = findViewById(R.id.etTypeMon);
        editRoom = findViewById(R.id.etRoomMon);
        addProgress = findViewById(R.id.addProgressBar);

        mSchedule = (Schedule) getIntent().getSerializableExtra("mondaySchedule");
        if (mSchedule != null) {
            editTime.setText(mSchedule.getTime(), TextView.BufferType.EDITABLE);
            editLesson.setText(mSchedule.getLesson(), TextView.BufferType.EDITABLE);
            editType.setText(mSchedule.getType(), TextView.BufferType.EDITABLE);
            editRoom.setText(mSchedule.getRoom(), TextView.BufferType.EDITABLE);
        }
    }


    public void onItemAdd(View view) {
        addProgress.setVisibility(View.VISIBLE);
        String time = editTime.getText().toString().trim();
        String lesson = editLesson.getText().toString().trim();
        String type = editType.getText().toString().trim();
        String room = editRoom.getText().toString().trim();

        if (mSchedule != null) {
            mSchedule.setTime(time);
            mSchedule.setLesson(lesson);
            mSchedule.setType(type);
            mSchedule.setRoom(room);
            updateInFirestore();
        } else {
            mSchedule = new Schedule(time, lesson, type, room);
            saveToFirestore();
        }
        finish();
    }

    private void saveToFirestore() {
        FirebaseFirestore.getInstance()
                .collection("monday")
                .add(mSchedule)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            mSchedule.setId(task.getResult().getId());
                            App.getDatabase().scheduleDao().insert(mSchedule);
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
                .collection("monday")
                .document(mSchedule.getId())
                .set(mSchedule)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            App.getDatabase().scheduleDao().update(mSchedule);
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
