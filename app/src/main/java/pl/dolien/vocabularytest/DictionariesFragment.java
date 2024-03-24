package pl.dolien.vocabularytest;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DictionariesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dictionaries, container, false);

        Intent playActivityIntent = new Intent(getActivity(), DictionariesActivity.class);
        startActivity(playActivityIntent);

        return view;
    }
}