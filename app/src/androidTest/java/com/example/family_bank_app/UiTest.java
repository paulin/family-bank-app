package com.example.family_bank_app;

import android.view.View;


import androidx.test.espresso.action.ViewActions;


import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.app.PendingIntent.getActivity;
import static android.service.autofill.Validators.not;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;






@RunWith(AndroidJUnit4.class)
public class UiTest {



    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);



    @Test
    public void hasTextOnScreen() {
        onView(withId(R.id.MainAccount)).check(matches(withText(R.string.accounts)));
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.MainLogo)).check(matches(isDisplayed()));
        onView(withId(R.id.CreateAcct)).check(matches(isDisplayed()));
        onView(withId(R.id.AccountRecycler)).check(matches(isDisplayed()));


    }

    @Test
    public void hasAccountActivity() {



    }






}
