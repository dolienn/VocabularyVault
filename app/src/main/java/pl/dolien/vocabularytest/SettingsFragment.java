package pl.dolien.vocabularytest;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;


public class SettingsFragment extends Fragment {
    private Button btn;
    private ImageView profileImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        btn = view.findViewById(R.id.logoutButton);
        profileImageView = view.findViewById(R.id.profilePicture); // Make sure to replace with the actual ImageView ID


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), SignInActivity.class);


                startActivity(intent);
            }
        });

        return view;
    }
}