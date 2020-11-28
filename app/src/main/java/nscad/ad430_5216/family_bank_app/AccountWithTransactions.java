package nscad.ad430_5216.family_bank_app;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class AccountWithTransactions {
    @Embedded public AccountEntity account;

    @Relation(
            parentColumn = "accountUid",
            entityColumn = "accountMainUid"
    )
    public List<TransactionEntity> transactions;
}
