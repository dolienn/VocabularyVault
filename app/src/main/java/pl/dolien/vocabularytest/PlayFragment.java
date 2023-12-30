package pl.dolien.vocabularytest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlayFragment extends Fragment {
    private TextView usernameText;
    private String username;
    private String email;
    private TextView userName;
    private CircleImageView userAvatar;
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, container, false);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        userName = view.findViewById(R.id.userName);
        userAvatar = view.findViewById(R.id.userAvatar);

        Button playButton = view.findViewById(R.id.playButton);
        usernameText = view.findViewById(R.id.usernameText);



        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent gameIntent = new Intent(getActivity(), GameActivity.class);
                    startActivity(gameIntent);
                    getActivity().finish();
            }
        });

        if (auth.getCurrentUser() != null) {
            redirectToMainActivity(auth.getCurrentUser().getUid());
        }


        return view;
    }

    private void redirectToMainActivity(String userId) {
        DatabaseReference userReference = database.getReference().child("users").child(userId);

        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String userNameData = dataSnapshot.child("userName").getValue(String.class);
                    String userProfileData = dataSnapshot.child("userProfile").getValue(String.class);

                    usernameText.setText(userNameData);
                    Picasso.get().load(userProfileData).into(userAvatar);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                Toast.makeText(getActivity(), "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
