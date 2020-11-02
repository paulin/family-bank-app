package com.example.family_bank_app;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class AccountViewModel extends ViewModel {

    // Grab all accounts and associated transactions
    public LiveData<List<AccountWithTransactions>> getAccountWithTransactions(Context context) {
        AppDatabase db = AppDatabaseSingleton.getDatabase(context);
        return db.accountDao().getAccountWithTransactions();
    }

    // @@@ create and update are the same, probably only need one.
    public static void createAccount(Context context, final AccountEntity newAccount) {
        AppDatabase db = AppDatabaseSingleton.getDatabase(context);
        db.getTransactionExecutor().execute(() -> {
            db.accountDao().insertAccount(newAccount);
        });
    }

    public static void updateAccount(Context context, final AccountEntity account) {
        AppDatabase db = AppDatabaseSingleton.getDatabase(context);
        db.getTransactionExecutor().execute(() -> {
            db.accountDao().insertAccount(account);
        });
    }

    public static void deleteAccount(Context context, AccountEntity account) {
        AppDatabase db = AppDatabaseSingleton.getDatabase(context);
        db.getTransactionExecutor().execute(() -> {
            db.getTransactionExecutor().execute(() -> {
                db.accountDao().delete(account);
            });

        });
    }

    public static AccountEntity getAccount(Context context, long accountUID) {
        AppDatabase db = AppDatabaseSingleton.getDatabase(context);
        return db.accountDao().getAccount(accountUID);

    }

}
