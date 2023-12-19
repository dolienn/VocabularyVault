package pl.dolien.vocabularytest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavbarActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navbar);

        bottomNavigationView = findViewById(R.id.bottomNavView);
        frameLayout = findViewById(R.id.frameLayout);


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("Email")){
            email = intent.getStringExtra("Email");

            changeFragmentWithUserData(email);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if(itemId == R.id.navPlay){
                    loadFragment(new PlayFragment(), false);
                } else if (itemId == R.id.navDictionaries) {
                    loadFragment(new DictionariesFragment(), false);
                } else if (itemId == R.id.navLeaderboard) {
                    loadFragment(new LeaderboardFragment(), false);
                } else { //Settings
                    loadFragment(new SettingsFragment(), false);
                }

                return true;
            }
        });

        loadFragment(new PlayFragment(), true);
    }

    private void loadFragment(Fragment fragment, boolean isAppInitialized){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(isAppInitialized){
            fragmentTransaction.add(R.id.frameLayout, fragment);
        } else {
            fragmentTransaction.replace(R.id.frameLayout, fragment);
        }

        fragmentTransaction.commit();
    }

    public void changeFragmentWithUserData(String email) {
        PlayFragment playFragment = new PlayFragment();

        Bundle bundle = new Bundle();
        bundle.putString("Email", email);
        playFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, playFragment)
                .addToBackStack(null)
                .commit();
    }

    public String getUserEmail() {
        return email;
    }
}