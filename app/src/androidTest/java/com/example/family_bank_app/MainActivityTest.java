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
    public String accountName = "test";
    public String accountBalance = "10";
    public String withdrawDepositAmount = "2";
    public String withdrawDepositNote = "p";

    // TESTS DO NOT RUN SEQUENTIALLY

    // Method for reading toasts text given a string param
    public void checkToast(String toastText) {
        onView(withText(toastText))
                .inRoot(withDecorView(not(mActivityRuleMain.getActivity()
                        .getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    // Check transaction for UI details and data
    public void checkTransaction() throws InterruptedException {

    }

    // Checks initial screen UI
    @Test
    public void hasTextOnScreen() throws InterruptedException {
        Thread.sleep(3000);
        onView(withId(R.id.MainAccount)).check(matches(withText(R.string.accounts)));
    }

    // Tests all features of the app
    @Test
    public void fullFeatureTest() throws InterruptedException {
        createNewAccount(accountName, accountBalance);

        // Check initial account data
        checkAccount(accountName, accountBalance);
        Thread.sleep(2000);
        onView(withId(R.id.Btn_AccountBack)).perform(click()); // Click back
        Thread.sleep(2000);

        // Click created recycler card
        onView(new RecyclerViewMatcher(R.id.AccountRecycler)
                .atPositionOnView(0, R.id.card_view))
                .perform(click());
        Thread.sleep(2000);

        // Deposit amount
        withdrawOrDepositTransaction("DEPOSIT", withdrawDepositAmount, withdrawDepositNote); // Extract later once finalized
        checkAccount(accountName, "8"); // Later calculate change
        Thread.sleep(2000);

        // Check transaction details
//        checkTransaction(); // @@@ HERE enter params and also determine where to click back
//        onView(withId(R.id.Btn_AccountBack)).perform(click());
//
//        // Delete deposit transaction
//        deleteTransaction();
//        checkAccount(accountName, accountBalance);
//
//        // Withdraw amount
//        withdrawOrDepositTransaction("WITHDRAW", withdrawDepositAmount, withdrawDepositNote); // Extract later once finalized
//        checkAccount(accountName, accountBalance);
//        Thread.sleep(2000);
//
//        // Delete withdraw transaction
//        deleteTransaction();
//        checkAccount(accountName, accountBalance);
//        deleteAccount();
    }


    // Create account
    // Check account created
    // Test dialog toasts
//    @Test
    public void createNewAccount(String accountName, String accountBalance) throws InterruptedException {

        Thread.sleep(5000);

        onView(withId(R.id.CreateAcct)).perform(click());
        Thread.sleep(3000);

        // Enter no form data
        onView(withText(R.string.cancel)).inRoot(isDialog()).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.CreateAcct)).perform(click());
        Thread.sleep(2000);
        onView(withText(R.string.create)).inRoot(isDialog()).perform(click());

        checkToast("Please enter a name and value"); // Extract string later once finalized
        Thread.sleep(2000);

        // Enter valid form data
        onView(withId(R.id.CreateAcct)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.edit_account_name)).perform(typeText(accountName));
        Thread.sleep(2000);
        onView(withId(R.id.edit_account_balance)).perform(typeText(accountBalance), closeSoftKeyboard());
        Thread.sleep(2000);

        // Click dialog
        onView(withText(R.string.create)).inRoot(isDialog()).perform(click());
        Thread.sleep(3000);

        // Click created recycler card
        onView(new RecyclerViewMatcher(R.id.AccountRecycler)
                .atPositionOnView(0, R.id.card_view))
                .perform(click());

        Thread.sleep(5000);
    }

    // Check account name and balance
    // TODO: calculate account balances instead of hard coded strings
    public void checkAccount(String accountName, String accountBalance) {
        onView(withId(R.id.NameOfAccount)).check(matches(withText(accountName)));
        onView(withId(R.id.balance)).check(matches(withText("Balance: $" + accountBalance + ".00")));
    }

    public void clickBack() {
        onView(withId(R.id.Btn_AccountBack)).perform(click());
    }

    // Interact with the deposit feature
    public void withdrawOrDepositTransaction(String withdrawDeposit, String withdrawDepositAmount, String withdrawDepositNote) throws InterruptedException {

        onView(withText(withdrawDeposit)).perform(click());
        onView(withText(R.string.cancel)).inRoot(isDialog()).perform(click());
        Thread.sleep(2000);

        // Enter empty form
        onView(withText(withdrawDeposit)).perform(click());
        onView(withText(withdrawDeposit)).inRoot(isDialog()).perform(click());
        checkToast("Please enter a value and note"); // Extract later
        Thread.sleep(2000);

        // Enter amount and note
        onView(withText(withdrawDeposit)).perform(click());
        onView(withId(R.id.deposit_dialog)).perform(typeText(withdrawDepositAmount));
        Thread.sleep(1000);
        onView(withId(R.id.depwith_memo_dialog)).perform(typeText(withdrawDepositNote), closeSoftKeyboard());
        Thread.sleep(2000);
        onView(withText(withdrawDeposit)).inRoot(isDialog()).perform(click());
        Thread.sleep(2000);
    }

    public void deleteTransaction() throws InterruptedException {

        onView(new RecyclerViewMatcher(R.id.TransactionRecycler)
                .atPositionOnView(0, R.id.card_view))
                .perform(click());
        Thread.sleep(2000);


    }

    public void checkTransaction(String transactionDate, String transactionBalance, String transactionNote, String transactionStatus) {

        onView(withId(R.id.transactionActivityDate)).check(matches(withText(transactionDate)));
        onView(withId(R.id.transactionActivityBal)).check(matches(withText("Balance: $" + transactionBalance + ".00")));
//        onView(withId(R.id.transactionActivityAmt)).check(matches(withText("Amount: $" + transactionAmount + ".00"))); // Amount?
        onView(withId(R.id.transactionActivityMessage)).check(matches(withText("Note: " + transactionNote)));
        onView(withId(R.id.transactionActivityStatus)).check(matches(withText("Status: " + transactionStatus)));
    }
}

//        onView(new RecyclerViewMatcher(R.id.AccountRecycler)
//                .atPositionOnView(0, R.id.card_view))
//                .perform(click());
//
//        Thread.sleep(2000);
//        onView(withId(R.id.Btn_Withdraw)).perform(click());
//        Thread.sleep(2000);
//        onView(withText(R.string.cancel)).inRoot(isDialog()).perform(click());
//
//        Thread.sleep(2000);
//
//        // Withdraw
//
//        onView(withId(R.id.Btn_Withdraw)).perform(click());
//        Thread.sleep(2000);
//        onView(withId(R.id.deposit_dialog)).perform(typeText("1"));
//        Thread.sleep(2000);
//        onView(withId(R.id.depwith_memo_dialog)).perform(typeText("p"), closeSoftKeyboard());
//
//        Thread.sleep(2000);
//        onView(withText(R.string.withdraw_test)).inRoot(isDialog()).perform(click());
//        Thread.sleep(2000);
////        checkToast("-1.0 p");
//        Thread.sleep(2000);
//
//        onView(withId(R.id.Btn_Deposit)).perform(click());
//        Thread.sleep(2000);
//        onView(withText(R.string.cancel)).inRoot(isDialog()).perform(click());
//
//        Thread.sleep(2000);
//
//        // Deposit
//
//        onView(withId(R.id.Btn_Deposit)).perform(click());
//        Thread.sleep(2000);
//        onView(withId(R.id.deposit_dialog)).perform(typeText("1"));
//        Thread.sleep(1000);
//        onView(withId(R.id.depwith_memo_dialog)).perform(typeText("p"), closeSoftKeyboard());
//        Thread.sleep(2000);
//        onView(withText(R.string.deposit_test)).inRoot(isDialog()).perform(click());
//        Thread.sleep(2000);
////        checkToast("1.0 p");
//        Thread.sleep(2000);
//
//        // Delete transaction
//        onView(withId(R.id.deleteTransactionButton)).perform(click());
//
//    }