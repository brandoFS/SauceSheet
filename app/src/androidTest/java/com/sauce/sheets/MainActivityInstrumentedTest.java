package com.sauce.sheets;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.sauce.sheets.ui.activity.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.sauce.sheet", appContext.getPackageName());
    }

    @Test
    public void changeText_sameActivity() {
        // Select first cell and verify text can be typed into it

        //Verify Editcell is disabled by itself
        onView(withId(R.id.main_cell_edit_area)).check(matches(not(isEnabled())));

        //Select first cell in table
        onView(withId(R.id.main_recycler_view))
                .perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.main_cell_edit_area)).perform(click());

        //Verify Editcell is now enabled
        onView(withId(R.id.main_cell_edit_area)).check(matches(isEnabled()));
        //Type text into edit cell
        onView(withId(R.id.main_cell_edit_area)).perform(typeTextIntoFocusedView("Form"));
        //Check that input shows in spreadsheet cell
        onView(withRecyclerView(R.id.main_recycler_view).atPosition(0))
                .check(matches(hasDescendant(withText("Form"))));

    }
}

