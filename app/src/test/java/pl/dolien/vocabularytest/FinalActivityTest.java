package pl.dolien.vocabularytest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Intent;
import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowApplication;

@RunWith(RobolectricTestRunner.class)
public class FinalActivityTest {

    private FinalActivity finalActivity;

    @Before
    public void setUp() {
        finalActivity = Robolectric.buildActivity(FinalActivity.class).create().resume().get();
    }

    @Test
    public void testBackToMenuButtonNotNull() {
        Button backToMenuButton = finalActivity.findViewById(R.id.backToMenuButton);
        assertNotNull(backToMenuButton);
    }

    @Test
    public void testBackToMenuButtonOnClick() {
        Button backToMenuButton = finalActivity.findViewById(R.id.backToMenuButton);
        backToMenuButton.performClick();
        Intent expectedIntent = new Intent(finalActivity, NavbarActivity.class);
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();
        assertNotNull(actual);
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
}
