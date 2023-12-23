package pl.dolien.vocabularytest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Properties;

public class TryAgainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try_again);

        Button tryagainButton = findViewById(R.id.tryagainButton);

        Intent intent = getIntent();

        ImageButton cancelButton = findViewById(R.id.cancelButton);

        if (intent != null && intent.hasExtra("Correct_Answer") && intent.hasExtra("Score") && intent.hasExtra("Best_Score")) {
            String message = intent.getStringExtra("Correct_Answer");
            String score = intent.getStringExtra("Score");
            String bestScoreEnd = intent.getStringExtra("Best_Score");


            TextView textView = findViewById(R.id.correctAnswerText);
            TextView scoreEndText = findViewById(R.id.scoreEndText);
            TextView bestScoreEndText = findViewById(R.id.bestScoreEndText);
            textView.setText(message);
            scoreEndText.setText(score);
            bestScoreEndText.setText(bestScoreEnd);
        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playActivityIntent = new Intent(TryAgainActivity.this, NavbarActivity.class);
                startActivity(playActivityIntent);
                finish();
            }
        });

        tryagainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameActivityIntent = new Intent(TryAgainActivity.this, GameActivity.class);
                startActivity(gameActivityIntent);
                finish();
            }
        });
    }
}