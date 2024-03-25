package pl.dolien.vocabularytest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;

@RunWith(RobolectricTestRunner.class)
public class DictionariesActivityTest {

    private DictionariesActivity dictionariesActivity;

    @Before
    public void setUp() {
        dictionariesActivity = Robolectric.buildActivity(DictionariesActivity.class).create().resume().get();
    }

    @Test
    public void testCancelButtonOnClick() {
        ImageButton cancelButton = dictionariesActivity.findViewById(R.id.cancelButton);
        cancelButton.performClick();
        Intent expectedIntent = new Intent(dictionariesActivity, NavbarActivity.class);
        Intent actual = Shadows.shadowOf(dictionariesActivity).getNextStartedActivity();
        assertNotNull(actual);
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
}