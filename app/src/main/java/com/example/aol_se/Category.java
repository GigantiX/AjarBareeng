package com.example.aol_se;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Category extends AppCompatActivity {

    Button nextBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);
        nextBtn = findViewById(R.id.button4);
        nextBtn.setOnClickListener(view -> startActivity(new Intent(this, NavbarActivity.class)));
    }
}