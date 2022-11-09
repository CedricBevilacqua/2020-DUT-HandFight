package com.example.handfight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Finished extends AppCompatActivity {

    private static final String TAG = "Finished";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;


    private String key = ("");
    private int score = 0;
    private Boolean win = true;
    private Boolean totalScoreUpdated = false;

    private TextView InGameScore;
    private TextView TotalScore;
    private TextView ResultAnnouncer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished);

        InGameScore = findViewById(R.id.LBL_InGameScore);
        TotalScore = findViewById(R.id.LBL_TotalScore);
        ResultAnnouncer = findViewById(R.id.LBL_ResultAnnouncer);

        Intent intent = getIntent();
        if (intent.hasExtra("key")){
            key = intent.getStringExtra("key");
        }
        if (intent.hasExtra("score")){
            score = intent.getIntExtra("score", 0);
        }
        if (intent.hasExtra("win")){
            win = intent.getBooleanExtra("win", false);
        }

        if(win) {
            ResultAnnouncer.setText("GAGNE");
        }
        else {
            ResultAnnouncer.setText("PERDU");
        }

        InGameScore.setText("Score de la partie : " + score);

        myRef = database.getReference(key + "SCORE");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                int value = dataSnapshot.getValue(Integer.class);
                Log.d(TAG, "Value is: " + String.valueOf(value));
                TotalScore.setText("Score total : "  + String.valueOf(value + score));
                if(totalScoreUpdated == false) {
                    myRef.setValue(value + score);
                    totalScoreUpdated = true;
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void Finish(View view) {
            Intent returnToMainActivity = new Intent(Finished.this, MainActivity.class);
            startActivity(returnToMainActivity);
    }

    public void Replay(View view) {
            Bundle bundle = new Bundle();
            bundle.putString("key", key);
            Intent replayActivity = new Intent(Finished.this, Rules.class);
            replayActivity.putExtras(bundle);
            startActivity(replayActivity);
    }
}
