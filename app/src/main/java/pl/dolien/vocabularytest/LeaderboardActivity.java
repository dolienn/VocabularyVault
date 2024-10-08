package pl.dolien.vocabularytest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private ListView listView;
    private ArrayList<String> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        ImageButton cancelButton = findViewById(R.id.cancelButton);

        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        listView = findViewById(R.id.leaderboard);
        userList = new ArrayList<>();

        showLeaderboard();

        cancelButton.setOnClickListener(v -> {
            Intent playActivityIntent = new Intent(LeaderboardActivity.this, NavbarActivity.class);
            startActivity(playActivityIntent);
            finish();
        });
    }

    void showLeaderboard() {
        Query recentPostsQuery = mDatabase.orderByChild("userBestScore");
        recentPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();

                List<DataSnapshot> userSnapshots = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    userSnapshots.add(userSnapshot);
                }

                Collections.reverse(userSnapshots);

                for (DataSnapshot reversedSnapshot : userSnapshots) {
                    String userNameData = reversedSnapshot.child("userName").getValue(String.class);
                    Integer userBestScoreData = reversedSnapshot.child("userBestScore_all_topics").getValue(Integer.class);
                    userList.add(userNameData + "       " + userBestScoreData);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(LeaderboardActivity.this, android.R.layout.simple_list_item_1, userList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                Toast.makeText(LeaderboardActivity.this, "Failed to read value", Toast.LENGTH_SHORT).show();
            }
        });
    }
}