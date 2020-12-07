package com.example.family_bank_app;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface AccountDao {

    //Account ---------------------

    @Transaction // Like batching
    @Query("SELECT * FROM Accounts WHERE accountUid = :accountUid")
    List<AccountWithTransactions> getAccountsWithTransactions(long accountUid);

    // @@@This should be changed to livedata
    //Query all Accounts with an account uid
    @Query("SELECT * FROM Accounts WHERE accountUid = :accountUid")
//    AccountEntity getAccount(long accountUid);
    LiveData<AccountEntity> getAccount(long accountUid);

    // Query for all accounts in db
    @Query("SELECT * FROM Accounts")
    LiveData<List<AccountEntity>> getAllAccounts();

    // Insert new account data to db
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAccount(AccountEntity... accountEntity);

    // @@@Should rename to deleteAccount
    // Delete specific account
    @Delete
    void delete(AccountEntity account);


    // Transaction ---------------------

    // Query for specific transaction data
    @Query("SELECT * FROM Transactions WHERE transactionUid = :transactionUid")
    LiveData<TransactionEntity> getTransaction(long transactionUid);

    //Query for all transaction data

    @Query("SELECT * FROM transactions WHERE accountMainUid = :accountUID")
    LiveData<List<TransactionEntity>> getAllTransactions(long accountUID);

    // Insert new transaction data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTransaction(TransactionEntity... transactionEntity);

    @Delete
    void deleteTransaction(TransactionEntity transaction);
}