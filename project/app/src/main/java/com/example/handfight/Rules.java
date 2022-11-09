package com.example.handfight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Rules extends AppCompatActivity {

    private static final String TAG = "Rules";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    private String key;

    private TextView Mail;
    private TextView Name;
    private TextView Surname;
    private TextView Birthday;
    private TextView Sex;
    private RadioButton Easy;
    private RadioButton Middle;
    private RadioButton Hard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        Mail = findViewById(R.id.LBL_Mail);
        Name = findViewById(R.id.LBL_Name);
        Surname = findViewById(R.id.LBL_Surname);
        Birthday = findViewById(R.id.LBL_Birthday);
        Sex = findViewById(R.id.LBL_Sex);
        Easy = findViewById(R.id.RB_Easy);
        Middle = findViewById(R.id.RB_Middle);
        Hard = findViewById(R.id.RB_Hard);

        Intent intent = getIntent();
        if (intent.hasExtra("key")){
            key = intent.getStringExtra("key");
        }
        if (intent.hasExtra("mail")){
            Mail.setText(intent.getStringExtra("mail"));
        }

        myRef = database.getReference(key + "NAME");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                Name.setText(value);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        myRef = database.getReference(key + "SURNAME");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                Surname.setText(value);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        myRef = database.getReference(key + "BIRTHDATE");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                Birthday.setText(value);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        myRef = database.getReference(key + "SEX");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                Sex.setText(value);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void Start(View view) {
        if(Easy.isChecked() || Middle.isChecked() || Hard.isChecked()) {
            int difficulty = 0;
            if(Easy.isChecked()) {
                difficulty = 1;
            }
            else if(Middle.isChecked()) {
                difficulty = 2;
            }
            else if(Hard.isChecked()) {
                difficulty = 3;
            }

            Bundle bundle = new Bundle();
            bundle.putInt("difficulty", difficulty);
            bundle.putString("key", key);

            Intent gameActivity = new Intent(Rules.this, Game.class);
            gameActivity.putExtras(bundle);
            startActivity(gameActivity);
        }
    }
}
