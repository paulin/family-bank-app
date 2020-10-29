package com.example.family_bank_app;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Store the transactions related to each account, one to many with account Entity
//https://developer.android.com/training/data-storage/room/relationships
@Entity
public class TransactionEntity {

    // Fields
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long transactionUid;

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

    // Add field for 'new total' AccountEntity - newTotal. Do we want this in the DB or calculated on runtime?
    //Aggregate query of transactions would be most accurate, is there a way to cache that calculation?


    // Accessors/Mutators
    @NonNull
    public long getTransactionUid() {
        return transactionUid;
    }

    public void setTransactionUid(@NonNull long transactionUid) {
        this.transactionUid = transactionUid;
    }

    @NonNull
    public String getTransactionDate() { return transactionDate; }

    public void setTransactionDate( String transactionDate ) { this.transactionDate = transactionDate; }

    @NonNull
    public String getTransactionTitle() { return transactionTitle; }

    public void setTransactionTitle( String transactionTitle ) { this.transactionTitle = transactionTitle; }

    @NonNull
    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

}
