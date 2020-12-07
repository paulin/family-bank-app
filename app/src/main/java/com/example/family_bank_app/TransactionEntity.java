package com.example.family_bank_app;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

// Store the transactions related to each account, one to many with account Entity
//https://developer.android.com/training/data-storage/room/relationships
@Entity (tableName = "Transactions")
public class TransactionEntity {

    // Fields
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "transactionUid")
    private long transactionUid;

    @NonNull
    @ColumnInfo(name = "accountMainUid")
    private long accountMainUid;


    // Will need to convert between date and string.
    @NonNull
    @ColumnInfo(name = "transactionDate")
    private String transactionDate = "";


    @NonNull
    @ColumnInfo(name = "transactionTitle")
    private String transactionTitle = "";

    @NonNull
    @ColumnInfo(name = "transactionAmount")
    private double transactionAmount = 0;

    @NonNull
    @ColumnInfo(name = "transactionCurrentBal")
    private double transactionCurrentBal = 0;



    // Status field,: empty string = normal, deleted = deleted...
    @NonNull
    @ColumnInfo(name = "transactionStatus")
    private String transactionStatus = "ok";

    // Accessors/Mutators
    @NonNull
    public long getTransactionUid() { return transactionUid; }

    public void setTransactionUid(@NonNull long transactionUid) { this.transactionUid = transactionUid; }

    @NonNull
    public long getAccountMainUid() { return accountMainUid; }

    public void setAccountMainUid(@NonNull long accountMainUid) { this.accountMainUid = accountMainUid; }
    @NonNull
    public String getTransactionDate() { return transactionDate; }

    public void setTransactionDate( String transactionDate ) { this.transactionDate = transactionDate; }

    @NonNull
    public String getTransactionTitle() { return transactionTitle; }

    public void setTransactionTitle( String transactionTitle ) { this.transactionTitle = transactionTitle; }

    @NonNull
    public double getTransactionAmount() { return transactionAmount; }

    public void setTransactionAmount( double transactionAmount ) { this.transactionAmount = transactionAmount; }

    @NonNull
    public double getTransactionCurrentBal() { return transactionCurrentBal; }

    public void setTransactionCurrentBal( double transactionCurrentBal ) { this.transactionCurrentBal = transactionCurrentBal; }

    @NonNull
    public String getTransactionStatus() { return transactionStatus; }

    public void setTransactionStatus( String transactionStatus ) { this.transactionStatus = transactionStatus; }


}
