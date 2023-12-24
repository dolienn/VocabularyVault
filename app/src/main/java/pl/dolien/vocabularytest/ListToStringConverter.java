package pl.dolien.vocabularytest;

import java.util.List;

public class ListToStringConverter {

    public static String listListToString(List<List<String>> listOfLists) {
        StringBuilder stringBuilder = new StringBuilder();

        for (List<String> list : listOfLists) {
            String innerListString = listToString(list);
            stringBuilder.append(innerListString).append(" | ");
        }

        if (stringBuilder.length() > 0) {
            // Remove the trailing " | "
            stringBuilder.setLength(stringBuilder.length() - 3);
        }

        return stringBuilder.toString();
    }

    private static String listToString(List<String> list) {
        StringBuilder innerStringBuilder = new StringBuilder();

        for (String element : list) {
            innerStringBuilder.append(element).append(", ");
        }

        if (innerStringBuilder.length() > 0) {
            // Remove the trailing ", "
            innerStringBuilder.setLength(innerStringBuilder.length() - 2);
        }

        return innerStringBuilder.toString();
    }
}

