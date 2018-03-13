package com.example.android.basketballgame;

        import android.graphics.Color;
        import android.support.v7.app.ActionBar;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;
        import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    static final String STATE_SCORETEAMA = "scoreTeamA";
    static final String STATE_SCORETEAMB = "scoreTeamB";
    int scoreTeamA =0;
    int scoreTeamB =0;

    EditText otherA;
    EditText otherB;
    EditText dealA;
    EditText dealB;

    CheckBox checkBoxDealA;
    CheckBox checkBoxDealB;
    CheckBox checkBox40A;
    CheckBox checkBox40B;
    CheckBox checkBox60A;
    CheckBox checkBox60B;
    CheckBox checkBox80A;
    CheckBox checkBox80B ;
    CheckBox checkBox100A;
    CheckBox checkBox100B;

    TextView scoreViewA;
    TextView scoreViewB;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        otherA = findViewById(R.id.otherA);
        otherB = findViewById(R.id.otherB);
        dealA = findViewById(R.id.dealA);
        dealB = findViewById(R.id.dealB);

        checkBoxDealA = findViewById(R.id.checkBoxDealA);
        checkBoxDealB = findViewById(R.id.checkBoxDealB);
        checkBox40A = findViewById(R.id.checkBox40A);
        checkBox40B = findViewById(R.id.checkBox40B);
        checkBox60A = findViewById(R.id.checkBox60A);
        checkBox60B = findViewById(R.id.checkBox60B);
        checkBox80A = findViewById(R.id.checkBox80A);
        checkBox80B = findViewById(R.id.checkBox80B);
        checkBox100A = findViewById(R.id.checkBox100A);
        checkBox100B = findViewById(R.id.checkBox100B);

        scoreViewA = findViewById(R.id.points_A);
        scoreViewB = findViewById(R.id.points_B);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current score state
        savedInstanceState.putInt(STATE_SCORETEAMA, scoreTeamA);
        savedInstanceState.putInt(STATE_SCORETEAMB, scoreTeamB);

        // Always deal the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always deal the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        scoreTeamA = savedInstanceState.getInt(STATE_SCORETEAMA);
        scoreTeamB = savedInstanceState.getInt(STATE_SCORETEAMB);

        DisplayScoreForTeamA(scoreTeamA);
        DisplayScoreForTeamB(scoreTeamB);
    }

    public void Reset (View view) {
        ResetView();
        scoreTeamB=0;
        DisplayScoreForTeamB(scoreTeamB);
        scoreTeamA=0;
        DisplayScoreForTeamA(scoreTeamA);     
    }

    public void AddPoints (View view) {
        int pointsA=0;
        int pointsB=0;
        int dealPointsA = 0;
        int dealPointsB = 0;

        //validation
        if (checkBoxDealA.isChecked() && checkBoxDealB.isChecked()) {
            Toast.makeText(this, "Only one player have to take a deal", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!checkBoxDealA.isChecked() && !checkBoxDealB.isChecked()) {
            Toast.makeText(this, "Someone have to take a deal", Toast.LENGTH_SHORT).show();
            return;
        }

        if (checkBoxDealA.isChecked()) {
            try {
                dealPointsA = Integer.parseInt(dealA.getText().toString());
                if (dealPointsA < 100) {
                    Toast.makeText(this, "Deal can't be less than 100", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException n) {
                Toast.makeText(this, "enter the correct value:\ndeal Player A", Toast.LENGTH_SHORT).show();
                dealA.setText("");
                return;
            }
        }

        if (checkBoxDealB.isChecked()) {
            try {
                dealPointsB = Integer.parseInt(dealB.getText().toString());
                if (dealPointsB < 100) {
                    Toast.makeText(this, "Deal can't be less than 100", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException n) {
                Toast.makeText(this, "enter the correct value:\ndeal Player B", Toast.LENGTH_SHORT).show();
                dealB.setText("");
                return;
            }
        }

        if ((checkBox40A.isChecked() && checkBox40B.isChecked()) || checkBox60A.isChecked() && checkBox60B.isChecked() || (checkBox80A.isChecked() && checkBox80B.isChecked()) || (checkBox100A.isChecked() && checkBox100B.isChecked())) {
            Toast.makeText(this, "Only one player can declare the same color", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!otherA.getText().toString().equals("")) {
            try {
                pointsA = Integer.parseInt(otherA.getText().toString());
            } catch (NumberFormatException n) {
                Toast.makeText(this, "enter the correct value:\nother Points Player A", Toast.LENGTH_SHORT).show();
                otherA.setText("");
                return;
            }
        }

        if(!otherB.getText().toString().equals("")) {
            try {
                pointsB = Integer.parseInt(otherB.getText().toString());
            } catch (NumberFormatException n) {
                Toast.makeText(this, "enter the correct value:\nother Points Player B", Toast.LENGTH_SHORT).show();
                otherB.setText("");
                return;
            }
        }
        //sum points player A
        if (checkBox40A.isChecked())
            pointsA += 40;

        if (checkBox60A.isChecked())
            pointsA += 60;

        if (checkBox80A.isChecked())
            pointsA += 80;

        if (checkBox100A.isChecked())
            pointsA += 100;

        //check if player set a Deal
        if (checkBoxDealA.isChecked()) {
            if (pointsA >= dealPointsA)
                scoreTeamA += pointsA;
            else scoreTeamA -= dealPointsA;
        } else {
            scoreTeamA += pointsA;
        }

        DisplayScoreForTeamA(scoreTeamA);

        //sum points player B
        if (checkBox40B.isChecked())
            pointsB += 40;

        if (checkBox60B.isChecked())
            pointsB += 60;

        if (checkBox80B.isChecked())
            pointsB += 80;

        if (checkBox100B.isChecked())
            pointsB += 100;

        //check if player set a Deal
        if (checkBoxDealB.isChecked()) {
            if (pointsB >= dealPointsB)
                scoreTeamB += pointsB;
            else scoreTeamB -= dealPointsB;
        } else {
            scoreTeamB += pointsB;
        }

        DisplayScoreForTeamB(scoreTeamB);

        button = findViewById(R.id.buttonAddPoints);

        //show the winner
        if (scoreTeamA >= 1000 || scoreTeamB >= 1000) {
            if (scoreTeamA > scoreTeamB) {
                Toast.makeText(this, "Player A is the winner!", Toast.LENGTH_SHORT).show();
            } else if (scoreTeamA < scoreTeamB) {
                Toast.makeText(this, "Player B is the winner!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "THE TIE", Toast.LENGTH_SHORT).show();
            }
            button.setEnabled(false);
        } else {
            //reset
            ResetView();
        }
    }

    public void DisplayScoreForTeamA (int scoreTeamA) {
     scoreViewA.setText(String.valueOf(scoreTeamA));
    }

    public void DisplayScoreForTeamB (int scoreTeamB) {
     scoreViewB.setText(String.valueOf(scoreTeamB));
    }

    public void ResetView () {
       checkBoxDealA.setChecked(false);
       checkBoxDealB.setChecked(false);
       checkBox40A.setChecked(false);
       checkBox40B.setChecked(false);
       checkBox60A.setChecked(false);
       checkBox60B.setChecked(false);
       checkBox80A.setChecked(false);
       checkBox80B.setChecked(false);
       checkBox100A.setChecked(false);
       checkBox100B.setChecked(false);
       button.setEnabled(true);
       otherA.setText("");
       otherB.setText("");
       dealA.setText("");
       dealB.setText("");
    }
}


