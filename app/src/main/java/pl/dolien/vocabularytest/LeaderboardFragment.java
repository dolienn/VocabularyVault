package pl.dolien.vocabularytest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LeaderboardFragment extends Fragment {
    private DatabaseReference mDatabase;
    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        ImageView imageView = view.findViewById(R.id.gifImageView);

        // Ścieżka do animowanego GIF-a
        String gifUrl = "https://media1.tenor.com/m/dKj-01-GggoAAAAC/network-connecting.gif";

        // Ustaw opcje dla Glide
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        // Załaduj GIF przy użyciu Glide
        Glide.with(this)
                .asGif()
                .load(gifUrl)
                .apply(options)
                .into(imageView);

        Intent intent = new Intent(getActivity(), LeaderboardActivity.class);
        startActivity(intent);
        getActivity().finish();

        return view;
    }

}