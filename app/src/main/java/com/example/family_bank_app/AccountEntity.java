package com.example.family_bank_app;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Accounts")
public class AccountEntity {

    // Fields for table with default values
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "accountUid")
    private long accountUid;

    @ColumnInfo(name = "name")
    private String accountName;

    @ColumnInfo(name = "balance")
    private double accountBalance;

    // Accessors/Mutator
    @NonNull
    public long getAccountUid() {
        return accountUid;
    }

    @NonNull
    public void setAccountUid(long id) {
        this.accountUid = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String name) {
        this.accountName = name;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double balance) {
        this.accountBalance = balance;
    }
}
