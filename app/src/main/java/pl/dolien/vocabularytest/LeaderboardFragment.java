package pl.dolien.vocabularytest;

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

public class LeaderboardFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        ImageView imageView = view.findViewById(R.id.gifImageView);

        String gifUrl = "https://media1.tenor.com/m/dKj-01-GggoAAAAC/network-connecting.gif";

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(this)
                .asGif()
                .load(gifUrl)
                .apply(options)
                .into(imageView);

        Intent intent = new Intent(getActivity(), LeaderboardActivity.class);
        startActivity(intent);

        return view;
    }

}