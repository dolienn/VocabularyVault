package pl.dolien.vocabularytest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GameActivity extends AppCompatActivity {
    private final Random random = new Random();
    private final Set<TranslationPair> usedTranslations = new HashSet<>();
    TextView wordText;
    EditText userAnswer;
    Button acceptButton;
    private TextView correct;
    private TextView answers;
    private TextView scoreText;
    private TextView bestScoreText;
    private String fileName;
    private int bestScore = 0;
    FirebaseAuth auth;
    FirebaseDatabase database;
    List<List<String>> correctTranslations;
    private int score = 0;
    private boolean gameOrCheck = true;
    private boolean multiAnswer = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        MyJsonReader myJsonReader = new MyJsonReader();
        SharedPreferences preferences = getSharedPreferences("PREFS_NAME", Context.MODE_PRIVATE);
        String defaultValue = "all_topics.json";
        fileName = preferences.getString("FILENAME", defaultValue);
        List<Word> words = myJsonReader.readJsonFile(this, fileName);

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
                        checkAnswers();
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
        acceptButton.setText(R.string.game_settext_accept);
        correct.setText("");
        multiAnswer=true;


        if(usedTranslations.size() < words.size()*2){
            List<List<String>> wordSynonyms;
            int randomIndex;
            do {
                //translationDirection = random.nextInt(2);
                translationDirection = 0;

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
            Intent tryAgainIntent = new Intent(GameActivity.this, FinalActivity.class);
            startActivity(tryAgainIntent);
        }
    }

    public void checkAnswer() {
        boolean lose = false;
        for (List<String> translation : correctTranslations) {
            if (translation.contains(userAnswer.getText().toString().trim())) {
                correct.setText(R.string.game_settext_correct_answer);
                acceptButton.setText(R.string.game_setext_generate_button);
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

    public void checkAnswers(){
        boolean lose = false;
        if (!correctTranslations.isEmpty()) {
            for (List<String> translation : correctTranslations) {
                if (translation.contains(userAnswer.getText().toString().trim())) {
                    correct.setText(R.string.game_settext_correct_answer_AM);
                    acceptButton.setText(R.string.game_settext_accept);
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
                String scoreTopicName = "userBestScore_" + fileName.substring(0, fileName.length() - 5);

                userRef.child(scoreTopicName).setValue(bestScore);
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
                    String scoreTopicName = "userBestScore_" + fileName.substring(0, fileName.length() - 5);
                    Integer bestScoreData = dataSnapshot.child(scoreTopicName).getValue(Integer.class);

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