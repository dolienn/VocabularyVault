package pl.dolien.vocabularytest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DictionariesActivity extends AppCompatActivity {
    final String[] fileNames = {"all_topics.json", "eighth_grade.json", "human.json", "home.json", "education.json", "job.json",
                                "private_life.json", "nutrition.json", "shopping_and_services.json", "travel_and_tourism", "culture.json",
                                "sport.json", "health.json", "science_and_technology.json", "world_of_adventure.json", "state_and_society.json"};

    private final String[] names = {"All topics", "Eighth Grade", "Human", "home.json", "education.json", "job.json",
            "private_life.json", "nutrition.json", "shopping_and_services.json", "travel_and_tourism", "culture.json",
            "sport.json", "health.json", "science_and_technology.json", "world_of_adventure.json", "state_and_society.json"};
    private final int dictionariesNumber = fileNames.length;
    MyJsonReader myJsonReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionaries);

        ImageButton cancelButton = findViewById(R.id.cancelButton);

        myJsonReader = new MyJsonReader();
        ListView listView = findViewById(R.id.listView);
        MyAdapter adapter = new MyAdapter(fileNames);
        listView.setAdapter(adapter);

        cancelButton.setOnClickListener(v -> {
            Intent playActivityIntent = new Intent(DictionariesActivity.this, NavbarActivity.class);
            startActivity(playActivityIntent);
            finish();
        });
    }

    class MyAdapter extends BaseAdapter {
        private final String[] fileNames;

        public MyAdapter(String[] fileNames) {
            this.fileNames = fileNames;
        }

        @Override
        public int getCount() {
            return  dictionariesNumber;
        }

        @Override
        public Object getItem(int position) {
            return fileNames[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                view = inflater.inflate(R.layout.list_item_layout, parent, false);
            }

            TextView textView = view.findViewById(R.id.textView);
            Button button = view.findViewById(R.id.button);

            textView.setText(getItem(position).toString());

            button.setOnClickListener(v -> {
                String fileName = fileNames[position];
                myJsonReader.setFileName(getApplicationContext(), fileName);
                Toast.makeText(getApplicationContext(), myJsonReader.getFileName(), Toast.LENGTH_SHORT).show();

                notifyDataSetChanged();
            });

            return view;
        }
    }
}