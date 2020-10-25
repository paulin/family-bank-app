package com.example.family_bank_app;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AccountEntity {

    // Fields for table with default values
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int accountUid;

    @NonNull
    @ColumnInfo(name = "Name")
    private String accountName = "";

    // Will need to convert between int and currency for accuracy
    @NonNull
    @ColumnInfo(name = "Balance")
    private double accountBalance = 0;

    // Accessors/Mutators

    @NonNull
    public int getAccountUid() {
        return accountUid;
    }

    public void setAccountUid(int id) {
        this.accountUid = id;
    }

    @NonNull
    public String getAccountName() { return accountName; }

    public void setAccountName(String name) { this.accountName = name; }

    @NonNull
    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double balance) {
        this.accountBalance = balance;
    }
}
