package com.dastan.scheapp4_0.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dastan.scheapp4_0.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hamza.slidingsquaresloaderview.SlidingSquareLoaderView;

import java.util.concurrent.TimeUnit;

public class PhoneActivity extends AppCompatActivity {

    private EditText editPhone;
    private Button btnSend;
    private EditText editCode;
    private Button btnConfirm;
    private String codeSend;
    private boolean isCodeSent;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private SlidingSquareLoaderView phoneProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        initViews();
        phoneProgress.setVisibility(View.INVISIBLE);
        initListeners();
    }

    private void initViews() {
        editPhone = findViewById(R.id.etPhoneNumber);
        btnSend = findViewById(R.id.btnSend);
        editCode = findViewById(R.id.etCodeNumber);
        btnConfirm = findViewById(R.id.btnConfirm);
        phoneProgress = findViewById(R.id.phoneProgressBar);

        editCode.setVisibility(View.INVISIBLE);
        btnConfirm.setVisibility(View.INVISIBLE);
    }

    private void initListeners() {
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.e("ron", "onVerificationCompleted");

                if (isCodeSent) {
                    phoneAuthCredential.getSmsCode();
                    phoneProgress.stop();
                    phoneProgress.setVisibility(View.INVISIBLE);
                } else {
                    signIn(phoneAuthCredential);
                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("ron", "onVerificationFailed " + e.getMessage());
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                codeSend = s;
                isCodeSent = true;
            }
        };
    }

    private void signIn(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(PhoneActivity.this, "Succeed", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(PhoneActivity.this, ProfileActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.e("ron", "Error " + task.getException().getMessage());
                            Toast.makeText(PhoneActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void onCodeSend(View view) {
        phoneProgress.start();
        phoneProgress.setVisibility(View.VISIBLE);
        String phone = editPhone.getText().toString().trim();
        if (phone.isEmpty()) {
            editPhone.setError("Please input your number");
            editPhone.requestFocus();
            return;
        }

        if (phone.length() < 13) {
            editPhone.setError("Incorrect phone number");
            editPhone.requestFocus();
            return;
        }
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone, 60, TimeUnit.SECONDS, this, callbacks);
        editPhone.setVisibility(View.GONE);
        btnSend.setVisibility(View.GONE);
        editCode.setVisibility(View.VISIBLE);
        btnConfirm.setVisibility(View.VISIBLE);
    }

    public void onConfirm(View view) {
        String code = editCode.getText().toString().trim();
        if (code.isEmpty()) {
            editCode.setError("Please input sms code");
            editCode.requestFocus();
            return;
        }

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSend, code);
        signIn(credential);
    }
}
