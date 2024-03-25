package pl.dolien.vocabularytest;
import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.android.material.bottomnavigation.BottomNavigationView;

@RunWith(AndroidJUnit4.class)
public class NavbarActivityTest {

    @Rule
    public ActivityScenarioRule<NavbarActivity> activityRule =
            new ActivityScenarioRule<>(NavbarActivity.class);

    @Test
    public void testOnCreate() {
        activityRule.getScenario().onActivity(activity -> {
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.frameLayout);
            assertTrue(fragment instanceof PlayFragment);
        });
    }

    // TODO: Add more tests for other fragments and navigation items
}

