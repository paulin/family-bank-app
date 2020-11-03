package com.example.family_bank_app;

import android.view.View;
import android.widget.Toast;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;


import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRuleMain = new ActivityTestRule<>(MainActivity.class, true);

    private static final String TAG = MainActivityTest.class.getSimpleName();

    @Test
    public void hasTextOnScreen() {
        onView(withId(R.id.MainAccount)).check(matches(withText(R.string.accounts)));
    }

    @Test
    public void clickCreateNewAccount() throws InterruptedException{
        onView(withId(R.id.CreateAcct)).perform(click());
        Thread.sleep(3000);

        // Enter form data
        onView(withId(R.id.edit_account_name)).perform(typeText("test"));
        onView(withId(R.id.edit_account_balance)).perform(typeText("1"), closeSoftKeyboard());

        // Click dialog
        onView(withText(R.string.create)).inRoot(isDialog()).perform(click());
        Thread.sleep(1000);

        // Check toast
//        onView(withText("Account ID: 0Account Name: Demo AccountAccount Balance: 9999.99"))
//                .inRoot(withDecorView(not(mActivityRuleMain.getActivity()
//                        .getWindow().getDecorView()))).check(matches(isDisplayed()));

//        Thread.sleep(3000);
        onView(withId(R.id.CreateAcct)).perform(click());
        Thread.sleep(3000);

        onView(withText(R.string.cancel)).perform(click());
    }

//    public void createNewAccount() throws InterruptedException {
//        onView(withId(R.id.CreateAcct)).perform(click());
//        Thread.sleep(3000);
//
//        // Enter form data
//        onView(withId(R.id.edit_account_name)).perform(typeText("t"));
//        onView(withId(R.id.edit_account_balance)).perform(typeText("1"), closeSoftKeyboard());
//    }

//    // Click Withrdaw
//    public void clickCreateWithdraw() throws InterruptedException {
//        onView(new RecyclerViewMatcher(R.id.AccountRecycler)
//                .atPositionOnView(0, R.string.jerry_law))
//                .perform(click());
//        Thread.sleep(3000);
//
//        onView(withText(R.string.withdraw)).inRoot(isDialog()).perform(click());
//
//        Thread.sleep(3000);
//
//        onView(withId(R.id.deposit_dialog)).perform(typeText("t"));
//        onView(withId(R.id.depwith_memo_dialog)).perform(typeText("1"), closeSoftKeyboard());
//
//        Thread.sleep(3000);
//
//        onView(withText(R.string.confirm)).inRoot(isDialog()).perform(click());
//
//    }

    // Click Deposit
//    public void clickCreateDeposit() throws InterruptedException {
//        onView(new RecyclerViewMatcher(R.id.AccountRecycler)
//                .atPositionOnView(0, R.id.CreateAcct))
//                .perform(click());
//        Thread.sleep(3000);
//
//        onView(withText(R.string.deposit)).inRoot(isDialog()).perform(click());
//
//        Thread.sleep(3000);
//
//        onView(withId(R.id.deposit_dialog)).perform(typeText("t"));
//        onView(withId(R.id.depwith_memo_dialog)).perform(typeText("1"), closeSoftKeyboard());
//
//        Thread.sleep(3000);
//
//        onView(withText(R.string.confirm)).inRoot(isDialog()).perform(click());
//    }

//    @Test
//    public void createAndClickAccount() throws InterruptedException {
////        createNewAccount();
//        clickCreateWithdraw();
////        clickCreateDeposit();
//    }
}
