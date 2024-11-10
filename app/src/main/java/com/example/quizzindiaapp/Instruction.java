package com.example.quizzindiaapp;

// InstructionActivity.java

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Instruction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        Button startQuizButton = findViewById(R.id.startQuizBtn);
        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuiz();
            }
        });
    }

    public void startQuiz() {
        // Start the quiz activity
        Intent intent = new Intent(this, Quiz.class);
        startActivity(intent);
        finish(); // Finish the instruction activity to prevent going back to it
    }
}


