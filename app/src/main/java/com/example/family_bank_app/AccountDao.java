package com.example.family_bank_app;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.lifecycle.LiveData;

import java.util.List;

@Dao
public interface AccountDao {

    // Query all transaction data associated with an account uid
    @Transaction // Like batching
    @Query("SELECT * FROM AccountEntity WHERE accountUid = :accountUid")
    public List<AccountWithTransactions> getAccountsWithTransactions(int accountUid);

    // Query for all accounts in db
    @Query("SELECT * FROM AccountEntity")
    LiveData<List<AccountEntity>> getAllAccounts();

    // Insert new account data to db
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAccount(AccountEntity... accountEntity);

    // Insert new transaction data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTransaction(TransactionEntity... transactionEntity);

    // Can build out update/delete functionality later.
}
