package pl.dolien.vocabularytest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

public class GameActivity extends AppCompatActivity {
    Random random = new Random();

    Set<TranslationPair> usedTranslations = new HashSet<>();
    TextView wordText;
    EditText userAnswer;
    Button acceptButton;
    TextView correct;
    TextView answers;
    List<String> word;
    List<List<String>> wordSynonyms;
    TextView scoreText;
    TextView bestScoreText;

    public int bestScore = 0;

    FirebaseAuth auth;
    FirebaseDatabase database;


    List<List<String>> correctTranslations;

    int score = 0;
    int randomIndex;

    boolean gameOrCheck = true;
    boolean multiAnswer = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        MyJsonReader myJsonReader = new MyJsonReader();
        List<Word> words = myJsonReader.readJsonFile(this, "vocabularyList.json");

        if(words != null && !words.isEmpty()) {
            wordText = findViewById(R.id.wordText);
            userAnswer = findViewById(R.id.userAnswer);
            acceptButton = findViewById(R.id.acceptButton);
            correct = findViewById(R.id.correct);
            scoreText = findViewById(R.id.scoreText);
            bestScoreText = findViewById(R.id.bestScoreText);
            answers = findViewById(R.id.answers);

            if (auth.getCurrentUser() != null) {
                redirectToMainActivity(auth.getCurrentUser().getUid());
            }
            scoreText.setText(String.valueOf(score));

            game(words);

            acceptButton.setOnClickListener(v -> {
                if (!gameOrCheck) {
                    game(words);
                    gameOrCheck = true;
                } else {
                    if (multiAnswer && correctTranslations.size() > 1) {
                        multiplayCorrectTranslation();
                    } else {
                        checkAnswer();
                        gameOrCheck = false;
                    }
                }
            });
        }
    }

    public void game(List<Word> words) {
        int translationDirection;

        userAnswer.setText("");
        userAnswer.setVisibility(View.VISIBLE);
        acceptButton.setText("Accept");
        correct.setText("");
        multiAnswer=true;


        if(usedTranslations.size() < words.size()*2){
            do {
                translationDirection = random.nextInt(2);

                randomIndex = random.nextInt(words.size());

                if (translationDirection == 0) {
                    wordSynonyms = words.get(randomIndex).getEnglish();
                } else {
                    wordSynonyms = words.get(randomIndex).getPolish();
                }
            } while (usedTranslations.contains(new TranslationPair(words.get(randomIndex).getEnglish(), words.get(randomIndex).getPolish())) || usedTranslations.contains(new TranslationPair(words.get(randomIndex).getPolish(), words.get(randomIndex).getEnglish())));

            usedTranslations.add(new TranslationPair(words.get(randomIndex).getEnglish(), words.get(randomIndex).getPolish()));
            usedTranslations.add(new TranslationPair(words.get(randomIndex).getPolish(), words.get(randomIndex).getEnglish()));

            if (translationDirection == 0) {
                correctTranslations = words.get(randomIndex).getPolish();
            } else {
                correctTranslations = words.get(randomIndex).getEnglish();
            }

            wordText.setText(ListToStringConverter.listListToString(wordSynonyms));
        } else {
            correct.setText("Congratulations");
        }
    }

    public void checkAnswer() {
        boolean lose = false;
        for (List<String> translation : correctTranslations) {
            if (translation.contains(userAnswer.getText().toString().trim())) {
                correct.setText("Poprawna odpowiedz");
                acceptButton.setText("Generate");
                score++;
                scoreText.setText(String.valueOf(score));
                bestOrNot();
                lose = false;
                break;
            } else {
                lose = true;
            }
        }
        if(lose){
            tryagain();
        }
        userAnswer.setText("");
        userAnswer.setVisibility(View.INVISIBLE);
    }

    public void multiplayCorrectTranslation(){
        boolean lose = false;
        if (!correctTranslations.isEmpty()) {
            for (List<String> translation : correctTranslations) {
                if (translation.contains(userAnswer.getText().toString().trim())) {
                    correct.setText("Poprawna odpowiedz. Napisz alternatywe");
                    acceptButton.setText("Accept");
                    score++;
                    scoreText.setText(String.valueOf(score));
                    bestOrNot();
                    correctTranslations.remove(translation);
                    answers.setText(ListToStringConverter.listListToString(correctTranslations));

                    lose = false;
                    break;
                } else {
                    lose = true;
                }
            }

            if (lose) {
                tryagain();
            }
        } else {
            multiAnswer = false;
        }

        userAnswer.setText("");
    }

    public void tryagain() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
            userRef.child("userBestScore").setValue(bestScore);
        }

        Intent tryAgainIntent = new Intent(GameActivity.this, TryAgainActivity.class);
        tryAgainIntent.putExtra("Correct_Answer", ListToStringConverter.listListToString(correctTranslations));
        tryAgainIntent.putExtra("Score", String.valueOf(score));
        tryAgainIntent.putExtra("Best_Score", String.valueOf(bestScore));
        startActivity(tryAgainIntent);
        finish();
    }

    public void bestOrNot(){
        if(score > bestScore) {
            bestScore = score;
            bestScoreText.setText(String.valueOf(score));
        }
    }

    private void redirectToMainActivity(String userId) {
        DatabaseReference userReference = database.getReference().child("users").child(userId);

        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Integer bestScoreData = dataSnapshot.child("userBestScore").getValue(Integer.class);

                    if(bestScoreData != null){
                        bestScore = bestScoreData;
                    }
                    bestScoreText.setText(String.valueOf(bestScore));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                Toast.makeText(GameActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}