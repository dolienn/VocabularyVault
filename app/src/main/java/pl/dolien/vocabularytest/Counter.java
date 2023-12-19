package pl.dolien.vocabularytest;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Counter {
    static int loadCounter(Context context) {
        Properties properties = new Properties();

        try (InputStream input = context.openFileInput("config.properties")) {
            properties.load(input);

            String counterValue = properties.getProperty("counter", "0");

            return Integer.parseInt(counterValue);
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    static void saveCounter(Context context, int counter) {
        Properties properties = new Properties();

        try (OutputStream output = context.openFileOutput("config.properties", Context.MODE_PRIVATE)) {
            properties.setProperty("counter", String.valueOf(counter));

            properties.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
