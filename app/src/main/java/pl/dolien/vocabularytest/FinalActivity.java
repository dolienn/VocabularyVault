package pl.dolien.vocabularytest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class FinalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_activity);

        Button backToMenu = findViewById(R.id.backToMenuButton);

        backToMenu.setOnClickListener(v -> {
            Intent playActivityIntent = new Intent(FinalActivity.this, NavbarActivity.class);
            startActivity(playActivityIntent);
            finish();
        });
    }
}