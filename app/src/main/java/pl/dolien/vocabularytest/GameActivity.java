package pl.dolien.vocabularytest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
    List<String> word;
    TextView counterText;
    TextView bestStreakText;

    public int bestStreak;


    List<String> correctTranslation;

    int counter = 0;
    int randomIndex;

    List<String> originalCorrectTranslation;

    boolean gameOrCheck = true;
    boolean multiAnswer = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        MyJsonReader myJsonReader = new MyJsonReader();
        List<Word> words = myJsonReader.readJsonFile(this, "vocabularyList.json");

        if(words != null && !words.isEmpty()) {
            wordText = findViewById(R.id.wordText);
            userAnswer = findViewById(R.id.userAnswer);
            acceptButton = findViewById(R.id.acceptButton);
            correct = findViewById(R.id.correct);
            counterText = findViewById(R.id.counterText);
            bestStreakText = findViewById(R.id.bestStreakText);
            bestStreak = Counter.loadCounter(this);


            bestStreakText.setText(String.valueOf(bestStreak));
            counterText.setText(String.valueOf(counter));


            game(words);

            originalCorrectTranslation = new ArrayList<>(correctTranslation);

            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!gameOrCheck) {
                        game(words);
                        originalCorrectTranslation = new ArrayList<>(correctTranslation);
                        gameOrCheck = true;
                    } else {
                        if (multiAnswer && originalCorrectTranslation.size() > 1) {
                            multiplayCorrectTranslation();
                        } else {
                            checkAnswer();
                            gameOrCheck = false;
                        }
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
                    word = words.get(randomIndex).getEnglish();
                } else {
                    word = words.get(randomIndex).getPolish();
                }
            } while (usedTranslations.contains(new TranslationPair(words.get(randomIndex).getEnglish(), words.get(randomIndex).getPolish())) || usedTranslations.contains(new TranslationPair(words.get(randomIndex).getPolish(), words.get(randomIndex).getEnglish())));

            usedTranslations.add(new TranslationPair(words.get(randomIndex).getEnglish(), words.get(randomIndex).getPolish()));
            usedTranslations.add(new TranslationPair(words.get(randomIndex).getPolish(), words.get(randomIndex).getEnglish()));

            if (translationDirection == 0) {
                correctTranslation = words.get(randomIndex).getPolish();
            } else {
                correctTranslation = words.get(randomIndex).getEnglish();
            }

            wordText.setText(ListToString(word));
        } else {
            correct.setText("Congratulations");
        }
    }

    public void checkAnswer() {
        if (correctTranslation.contains(userAnswer.getText().toString().trim())) {
            correct.setText("Poprawna odpowiedz");
            acceptButton.setText("Generate");
            counter++;
            counterText.setText(String.valueOf(counter));
            bestOrNot();
        } else {
            tryagain();
        }
        userAnswer.setText("");
        userAnswer.setVisibility(View.INVISIBLE);
    }

    public void multiplayCorrectTranslation(){
        if(!originalCorrectTranslation.isEmpty()){
            if (originalCorrectTranslation.contains(userAnswer.getText().toString().trim())) {
                correct.setText("Poprawna odpowiedz. Napisz alternatywe");
                acceptButton.setText("Accept");
                counter++;
                counterText.setText(String.valueOf(counter));
                bestOrNot();
                originalCorrectTranslation.remove(userAnswer.getText().toString().trim());
            } else {
                tryagain();
            }
        } else {
            multiAnswer=false;
        }
        userAnswer.setText("");
    }

    public void tryagain() {
        bestOrNot();
        Intent tryAgainIntent = new Intent(GameActivity.this, TryAgainActivity.class);
        tryAgainIntent.putExtra("Correct_Answer", ListToString(originalCorrectTranslation));
        tryAgainIntent.putExtra("Counter", String.valueOf(counter));
        tryAgainIntent.putExtra("Best_Streak", String.valueOf(bestStreak));
        startActivity(tryAgainIntent);
        finish();
    }

    public String ListToString(List<String> list){
        StringBuilder stringBuilder = new StringBuilder();
        for (String element : list) {
            stringBuilder.append(element).append(", ");
        }

        if (stringBuilder.length() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 2);
        }

        return stringBuilder.toString();
    }

    public void bestOrNot(){
        if(counter > bestStreak) {
            bestStreak = counter;
            bestStreakText.setText(String.valueOf(counter));
            Counter.saveCounter(this, counter);
        }
    }
}