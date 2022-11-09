package com.example.handfight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ComputableLiveData;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Game extends AppCompatActivity {

    public int difficulty = 0;
    public int manche = 1;
    public int score = 0;
    public int moment = 0;
    public String key = "";

    public TextView ComputerPlays;
    public TextView ComputerResults;
    public TextView NbScore;
    public TextView NbManche;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ComputerPlays = findViewById(R.id.LBL_ComputerPlays);
        ComputerResults = findViewById(R.id.LBL_ComputerResult);
        NbScore = findViewById(R.id.LBL_NbScore);
        NbManche = findViewById(R.id.LBL_NbManche);

        ComputerPlays.setText("");
        ComputerResults.setText("");

        Intent intent = getIntent();
        if (intent.hasExtra("difficulty")){
            difficulty = intent.getIntExtra("difficulty", 0);
        }
        if (intent.hasExtra("key")){
            key = intent.getStringExtra("key");
        }
    }

    public void Cancel(View view) {
        Intent connexionActivity = new Intent(Game.this, MainActivity.class);
        startActivity(connexionActivity);
    }

    public void spock_selection(View view) {
        PlaySelection("Spock");
    }

    public void pierre_selection(View view) {
        PlaySelection("Pierre");
    }

    public void papier_selection(View view) {
        PlaySelection("Papier");
    }

    public void ciseaux_selection(View view) {
        PlaySelection("Ciseaux");
    }

    public void lezard_selection(View view) {
        PlaySelection("Lezard");
    }

    public void PlaySelection(String selection) {
        if(moment == 0) {
            moment = 1;

            Random r = new Random();
            int valeur = 1 + r.nextInt(100);
            r = new Random();
            int choix = 1 + r.nextInt(2);
            Boolean win = true;
            String selectedPlay = "";

            if(difficulty == 1 && valeur > 75) {
                win = false;
            }
            else if(difficulty == 2 && valeur > 50) {
                win = false;
            }
            else if(difficulty == 3 && valeur > 25) {
                win = false;
            }

            if(selection == "Ciseaux") {
                selectedPlay = PlayCiseaux(win, choix);
            }
            else if(selection == "Pierre") {
                selectedPlay = PlayPierre(win, choix);
            }
            else if(selection == "Papier") {
                selectedPlay = PlayPapier(win, choix);
            }
            else if(selection == "Lezard") {
                selectedPlay = PlayLezard(win, choix);
            }
            else if(selection == "Spock") {
                selectedPlay = PlaySpock(win, choix);
            }

            ComputerPlays.setText("L'ordinateur a joué : " + selectedPlay);
            if(win) {
                ComputerResults.setText("Vous perdez cette étape !");
            }
            else{
                ComputerResults.setText("Vous gagnez cette étape !");
                score++;
                NbScore.setText("Score : " + score);
            }
        }
    }

    public String PlayCiseaux(Boolean win, int choix) {
        String choice;
        if(win) {
            if(choix == 1) {
                choice = "Papier";
            }
            else {
                choice = "Lezard";
            }
        }
        else {
            if(choix == 1) {
                choice = "Spock";
            }
            else {
                choice = "Pierre";
            }
        }
        return choice;
    }
    public String PlayPierre(Boolean win, int choix) {
        String choice;
        if(win) {
            if(choix == 1) {
                choice = "Lezard";
            }
            else {
                choice = "Ciseaux";
            }
        }
        else {
            if(choix == 1) {
                choice = "Papier";
            }
            else {
                choice = "Spock";
            }
        }
        return choice;
    }
    public String PlayPapier(Boolean win, int choix) {
        String choice;
        if(win) {
            if(choix == 1) {
                choice = "Pierre";
            }
            else {
                choice = "Spock";
            }
        }
        else {
            if(choix == 1) {
                choice = "Ciseaux";
            }
            else {
                choice = "Lezard";
            }
        }
        return choice;
    }
    public String PlayLezard(Boolean win, int choix) {
        String choice;
        if(win) {
            if(choix == 1) {
                choice = "Spock";
            }
            else {
                choice = "Papier";
            }
        }
        else {
            if(choix == 1) {
                choice = "Pierre";
            }
            else {
                choice = "Ciseaux";
            }
        }
        return choice;
    }
    public String PlaySpock(Boolean win, int choix) {
        String choice;
        if(win) {
            if(choix == 1) {
                choice = "Ciseaux";
            }
            else {
                choice = "Pierre";
            }
        }
        else {
            if(choix == 1) {
                choice = "Lezard";
            }
            else {
                choice = "Papier";
            }
        }
        return choice;
    }

    public void Next(View view) {
        if(moment == 1) {
            moment = 0;
            manche++;
            NbManche.setText("Manche : " + manche);
            ComputerPlays.setText("");
            ComputerResults.setText("");
            if(manche > 5) {
                Bundle bundle = new Bundle();
                bundle.putString("key", key);
                bundle.putInt("score", score);
                if(score > 2) {
                    bundle.putBoolean("win", true);
                }
                else {
                    bundle.putBoolean("win", false);
                }

                Intent finishedActivity = new Intent(Game.this, Finished.class);
                finishedActivity.putExtras(bundle);
                startActivity(finishedActivity);
            }
        }
    }
}
