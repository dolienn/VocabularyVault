package pl.dolien.vocabularytest;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class MyJsonReader {
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(Context context, String fileName) {
        this.fileName = fileName;

        SharedPreferences preferences = context.getSharedPreferences("PREFS_NAME", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("FILENAME", fileName);
        editor.apply();
    }

    public List<Word> readJsonFile(Context context, String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        AssetManager assetManager = context.getAssets();

        try {
            InputStream inputStream = assetManager.open(fileName);
            Word[] wordsArray = objectMapper.readValue(inputStream, Word[].class);
            return Arrays.asList(wordsArray);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception appropriately
            return null;
        }
    }
}
