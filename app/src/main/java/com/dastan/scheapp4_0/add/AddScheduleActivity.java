package com.dastan.scheapp4_0.add;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.dastan.scheapp4_0.App;
import com.dastan.scheapp4_0.R;
import com.dastan.scheapp4_0.Schedule;
import com.dastan.scheapp4_0.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddScheduleActivity extends AppCompatActivity {

    private EditText editTime;
    private EditText editLesson;
    private EditText editType;
    private EditText editRoom;
    private String groupName;
    private String days;
    private int scheduleId;
    private Schedule mSchedule;
    private ProgressBar addProgress;
    private NotificationHelper mNotificationHelper;
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        groupName = getIntent().getStringExtra("groupName");
        days = getIntent().getStringExtra("days");

        notificationManager = NotificationManagerCompat.from(this);
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

        mNotificationHelper = new NotificationHelper(this);
    }


    public void onItemAdd(View view) {
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
            mSchedule = new Schedule(time, lesson, type, room, groupName, days);
            saveToFirestore();
        }
        finish();
        addProgress.setVisibility(View.VISIBLE);
        startNotification("Schedule has been changed",
                mSchedule.getTime() + "\n" + mSchedule.getLesson() + "\n" +
                        mSchedule.getType() + "\n" + mSchedule.getRoom());
    }

    private void saveToFirestore() {
        FirebaseFirestore.getInstance()
                .collection("days")
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
                .collection("days")
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

    private void startNotification(String title, String message) {
        Notification nb = mNotificationHelper.getChannelNotification(title, message);
        mNotificationHelper.getManager().notify(1, nb);
    }
}
