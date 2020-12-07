package com.example.family_bank_app;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class AccountViewModel extends ViewModel {

    public LiveData<List<AccountEntity>> loadAllByIds(Context context) {
        AppDatabase db = AppDatabaseSingleton.getDatabase(context);
        return db.accountDao().getAllAccounts();
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

    public static LiveData<AccountEntity> getAccount(Context context, long accountUID) {
        AppDatabase db = AppDatabaseSingleton.getDatabase(context);
        return db.accountDao().getAccount(accountUID);
    }

}
