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

        // Tutaj możesz umieścić dodatkowy kod związany z widokiem, jeśli to konieczne

        // Tworzenie intencji do uruchomienia nowej aktywności
        Intent playActivityIntent = new Intent(getActivity(), DictionariesActivity.class);

        // Uruchamianie nowej aktywności
        startActivity(playActivityIntent);

        // Opcjonalnie możesz dodać finish(), ale upewnij się, że jest to wymagane
        // finish();

        // Zwracanie widoku fragmentu
        return view;
    }
}