package com.example.aol_se;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.aol_se.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth auth;
    DatabaseReference reference;
    ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        auth = FirebaseAuth.getInstance();

        binding.loginBtn.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        });

        binding.registerBtn.setOnClickListener(view -> {
            String str_username = binding.nameEdt.getEditText().getText().toString();
            String str_email = binding.emailEdt.getEditText().getText().toString();
            String str_password = binding.passEdt.getEditText().getText().toString();
            String str_confirmPassword = binding.confPassEdt.getEditText().getText().toString();
            String number = binding.numberEdt.getEditText().getText().toString();

            if (!str_username.equals("") && !str_email.equals("") && !str_password.equals("") && !str_confirmPassword.equals("") && !number.equals("")){
                if (str_username.length()<5){
                    Toast.makeText(this, "Name must be more than 5 character!", Toast.LENGTH_SHORT).show();
                } else if (!str_email.endsWith(".com")) {
                    Toast.makeText(this, "Email must end with .com!", Toast.LENGTH_SHORT).show();
                } else if (!str_password.matches("[A-Za-z0-9]+")) {
                    Toast.makeText(this, "Password must be alphanumeric!", Toast.LENGTH_SHORT).show();
                } else if (!str_password.endsWith(str_confirmPassword)) {
                    Toast.makeText(this, "Please enter the same password", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
                    register(str_username, str_email, str_password, Integer.parseInt(number));
                }
            }else{
                Toast.makeText(this, "All Field must be filled!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void register(String name, String email, String password,
                          int number) {

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userId = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

                            String timestamp = String.valueOf(System.currentTimeMillis());

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("id", userId);
                            hashMap.put("name", name.toLowerCase());
                            hashMap.put("email", email.toLowerCase());
                            hashMap.put("password", password);
                            hashMap.put("number", (int) number);

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
//                                    progressPopDialog.dismiss();
                                    Toast.makeText(RegisterActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            });
                        } else {
//                            progressPopDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}