package com.example.family_bank_app;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class AccountWithTransactions {
    @Embedded public AccountEntity account;

    @Relation(
            parentColumn = "accountUid",
            entityColumn = "transactionUid"
    )
    public List<TransactionEntity> transactions;
}
