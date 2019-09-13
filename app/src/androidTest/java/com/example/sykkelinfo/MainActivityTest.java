package com.example.sykkelinfo;


import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import com.example.sykkelinfo.Map.Kart_aktivitet;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertNotNull;


@RunWith(AndroidJUnit4.class)

//Tester tittel og knapp til kart-aktivitet
public class MainActivityTest {

@Rule
   public ActivityTestRule<MainActivity> mainActivityActivityTestRule=
        new ActivityTestRule<>(MainActivity.class);

private MainActivity mActivity = null;

Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(Kart_aktivitet.class.getName(), null, false);

@Before
public void setUp()throws Exception{
 mActivity = mainActivityActivityTestRule.getActivity();
}
@Test
    public  void validateTitleAndButton(){
        String tittel = "BYSYKKEL OSLO";

        Espresso.onView(withText(tittel)).check(matches(isDisplayed()));

        assertNotNull(mActivity.findViewById(R.id.map_button));

        Espresso.onView(withId(R.id.map_button)).perform(click());

        Activity secondActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);

        assertNotNull(secondActivity);

        secondActivity.finish();
}
@After
    public void tearDown() throws Exception{
    mActivity = null;
}

}