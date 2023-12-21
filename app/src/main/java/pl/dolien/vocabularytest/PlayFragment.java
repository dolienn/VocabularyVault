package pl.dolien.vocabularytest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Objects;

public class PlayFragment extends Fragment {
    private TextView usernameText;
    private String username;
    private String email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, container, false);

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

        return view;
    }
}
