package pl.dolien.vocabularytest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Intent;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;

@RunWith(RobolectricTestRunner.class)
public class LeaderboardActivityTest {

    private LeaderboardActivity leaderboardActivity;

    @Before
    public void setUp() {
        FirebaseApp.initializeApp(RuntimeEnvironment.application);
        // Other setup code
        leaderboardActivity = Robolectric.buildActivity(LeaderboardActivity.class).create().resume().get();
    }

    @Test
    public void testCancelButtonNotNull() {
        ImageButton cancelButton = leaderboardActivity.findViewById(R.id.cancelButton);
        assertNotNull(cancelButton);
    }

    @Test
    public void testListViewNotNull() {
        ListView listView = leaderboardActivity.findViewById(R.id.leaderboard);
        assertNotNull(listView);
    }

    @Test
    public void testCancelButtonOnClick() {
        ImageButton cancelButton = leaderboardActivity.findViewById(R.id.cancelButton);
        cancelButton.performClick();
        Intent expectedIntent = new Intent(leaderboardActivity, NavbarActivity.class);
        Intent actual = Shadows.shadowOf(leaderboardActivity).getNextStartedActivity();
        assertNotNull(actual);
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
}
