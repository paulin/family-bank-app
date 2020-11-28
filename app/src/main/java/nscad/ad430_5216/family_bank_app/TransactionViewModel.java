package nscad.ad430_5216.family_bank_app;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class TransactionViewModel extends ViewModel{

    public static LiveData<TransactionEntity> getTransaction(Context context, long transactionId) {
        AppDatabase db = AppDatabaseSingleton.getDatabase(context);
        return db.accountDao().getTransaction(transactionId);
    }

    public static LiveData<List<TransactionEntity>> getAllTransactions(Context context, long AccountUID) {
        AppDatabase db = AppDatabaseSingleton.getDatabase(context);
        return db.accountDao().getAllTransactions(AccountUID);
    }

    // Also updates account
    public static void createTransaction(Context context, final TransactionEntity newTransaction) {
        AppDatabase db = AppDatabaseSingleton.getDatabase(context);
        db.getTransactionExecutor().execute(() -> {
            db.accountDao().insertTransaction(newTransaction);
        });
    }

    public static LiveData<TransactionEntity> getLastOkTransaction(Context context, String transactionStatus) {
        AppDatabase db = AppDatabaseSingleton.getDatabase(context);
        return db.accountDao().getLastOkTransaction(transactionStatus);
    }

    // Ask matt
//    public static void deleteTransaction(Context context, TransactionEntity transaction) {
//        AppDatabase db = AppDatabaseSingleton.getDatabase(context);
//        db.getTransactionExecutor().execute(() -> {
//            db.getTransactionExecutor().execute(() -> {
//                db.accountDao().deleteTransaction(transaction);
//            });
//
//        });
//    }
}
