package pl.dolien.vocabularytest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Intent;
import android.os.Build;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
public class TryAgainActivityTest {

    private TryAgainActivity tryAgainActivity;

    @Before
    public void setUp() {
        tryAgainActivity = Robolectric.buildActivity(TryAgainActivity.class).create().resume().get();
    }

    @Test
    public void testCancelButtonNotNull() {
        ImageButton cancelButton = tryAgainActivity.findViewById(R.id.cancelButton);
        assertNotNull(cancelButton);
    }

    @Test
    public void testTryAgainButtonNotNull() {
        Button tryAgainButton = tryAgainActivity.findViewById(R.id.tryagainButton);
        assertNotNull(tryAgainButton);
    }

    @Test
    public void testIntentExtras() {
        Intent intent = new Intent();
        intent.putExtra("Correct_Answer", "Test Correct Answer");
        intent.putExtra("Score", "Test Score");
        intent.putExtra("Best_Score", "Test Best Score");

        tryAgainActivity.setIntent(intent);

        TextView textView = tryAgainActivity.findViewById(R.id.correctAnswerText);
        TextView scoreEndText = tryAgainActivity.findViewById(R.id.scoreEndText);
        TextView bestScoreEndText = tryAgainActivity.findViewById(R.id.bestScoreEndText);

        assertNotNull(textView.getText());
        assertNotNull(scoreEndText.getText());
        assertNotNull(bestScoreEndText.getText());
    }
}