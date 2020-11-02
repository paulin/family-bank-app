package com.example.family_bank_app;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class TransactionViewModel extends ViewModel{

    public LiveData<TransactionEntity> getTransaction(Context context, long transactionId) {
        AppDatabase db = AppDatabaseSingleton.getDatabase(context);
        return db.accountDao().getTransaction(transactionId);
    }

    // Also updates account
    public void createTransaction(Context context, final TransactionEntity newTransaction) {
        AppDatabase db = AppDatabaseSingleton.getDatabase(context);
        db.getTransactionExecutor().execute(() -> {
            db.accountDao().insertTransaction(newTransaction);
        });
    }

    public void deleteTransaction(Context context, TransactionEntity transaction) {
        AppDatabase db = AppDatabaseSingleton.getDatabase(context);
        db.getTransactionExecutor().execute(() -> {
            db.getTransactionExecutor().execute(() -> {
                db.accountDao().deleteTransaction(transaction);
            });

        });
    }
}
