package pl.dolien.vocabularytest;

import android.content.Context;
import android.content.res.AssetManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class MyJsonReader {
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
