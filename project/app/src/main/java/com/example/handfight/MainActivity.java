package com.example.handfight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private String returnDatabase;

    private EditText Login;
    private EditText Password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Login = findViewById(R.id.TXT_Login);
        Password = findViewById(R.id.TXT_Password);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void signInWithEmailAndPassword(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Connexion échouée",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        Toast.makeText(MainActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();

        String key = (Login.getText().toString()).replaceAll("[@.]*", "");
        String mail = Login.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString("key", key);
        bundle.putString("mail", mail);

        Intent rulesActivity = new Intent(MainActivity.this, Rules.class);
        rulesActivity.putExtras(bundle);
        startActivity(rulesActivity);
    }

    public void Connect(View view) {
        if(Login.getText().toString().matches("") || Password.getText().toString().matches("")) { }
        else {
            signInWithEmailAndPassword(Login.getText().toString(), Password.getText().toString());
        }
    }

    public void Subscribe(View view) {
        Intent subscribeActivity = new Intent(MainActivity.this, Subscribe.class);
        startActivity(subscribeActivity);
    }

    public void ReadInformation(String ref) {
        myRef = database.getReference(ref);
        //returnDatabase = "NULL";
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                returnDatabase = value;
                Login.setText(returnDatabase);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
