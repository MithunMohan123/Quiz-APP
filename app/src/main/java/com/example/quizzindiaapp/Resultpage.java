package com.example.quizzindiaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Resultpage extends AppCompatActivity {

    TextView result, score, scoreview, usernameTxt;
    Button logoutBtn, shareBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultpage);

        result = findViewById(R.id.result);
        score = findViewById(R.id.score);
        scoreview = findViewById(R.id.scoreview);
        logoutBtn = findViewById(R.id.logoutBtn);
        usernameTxt = findViewById(R.id.usernameTxt);
        shareBtn = findViewById(R.id.shareBtn);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int userScore = extras.getInt("user_score");
            scoreview.setText(String.valueOf(userScore));
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String username = user.getDisplayName();
            if (username != null) {
                usernameTxt.setText("User Name: " + username);
            } else {
                // The user does not have a username set
            }
        } else {
            // The user is not signed in
        }

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareData(usernameTxt.getText().toString(), Integer.parseInt(scoreview.getText().toString()));
            }
        });
    }

    public void shareData(String username, int userScore){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");

        i.putExtra(Intent.EXTRA_SUBJECT, username + " completed the quiz!");
        i.putExtra(Intent.EXTRA_TEXT, " " + username + "\nScore: " + userScore + " points");

        startActivity(Intent.createChooser(i, "Share Result"));
    }
}
