package com.example.handfight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Subscribe extends AppCompatActivity {

    private static final String TAG = "Subscribe";
    private FirebaseAuth mAuth;
    private EditText Mail;
    private EditText Password;
    private EditText Name;
    private EditText Surname;
    private EditText Birthday;
    private RadioButton Man;
    private RadioButton Woman;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);

        Mail = findViewById(R.id.TXT_Mail);
        Password = findViewById(R.id.TXT_Password);
        Name = findViewById(R.id.TXT_Name);
        Surname = findViewById(R.id.TXT_Surname);
        Birthday = findViewById(R.id.TXT_Birthday);
        Man = findViewById(R.id.RB_ManSex);
        Woman = findViewById(R.id.RB_WomanSex);

        mAuth = FirebaseAuth.getInstance();
    }

    public void Return(View view) {
        Intent returnToMainActivity = new Intent(Subscribe.this, MainActivity.class);
        startActivity(returnToMainActivity);
    }

    public void subscribe(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Subscribe.this, "Une erreur s'est produite, vérifiez votre connexion, l'adresse mail et la longueur du mot de passe entré !",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(Object o) {
        String key;
        String value;

        key = (Mail.getText().toString() + "NAME").replaceAll("[@.]*", "");
        value = Name.getText().toString();
        myRef = database.getReference(key);
        myRef.setValue(value);

        key = (Mail.getText().toString() + "SURNAME").replaceAll("[@.]*", "");
        value = Surname.getText().toString();
        myRef = database.getReference(key);
        myRef.setValue(value);

        key = (Mail.getText().toString() + "BIRTHDATE").replaceAll("[@.]*", "");
        value = Birthday.getText().toString();
        myRef = database.getReference(key);
        myRef.setValue(value);

        key = (Mail.getText().toString() + "SEX").replaceAll("[@.]*", "");
        myRef = database.getReference(key);
        if(Man.isChecked()) {
            myRef.setValue("Homme");
        }
        else if(Woman.isChecked()) {
            myRef.setValue("Femme");
        }

        key = (Mail.getText().toString() + "SCORE").replaceAll("[@.]*", "");
        myRef = database.getReference(key);
        myRef.setValue(0);

        Toast.makeText(Subscribe.this, "Inscription réussie", Toast.LENGTH_SHORT).show();

        Intent returnToMainActivity = new Intent(Subscribe.this, MainActivity.class);
        startActivity(returnToMainActivity);
    }

    public void Validate(View view) {

        if(Mail.getText().toString().matches("") || Password.getText().toString().matches("") || Name.getText().toString().matches("") || Surname.getText().toString().matches("") || Birthday.getText().toString().matches("") || (!Man.isChecked() && !Woman.isChecked())) { }
        else {
            subscribe(Mail.getText().toString(), Password.getText().toString());
        }
    }
}
