package com.freebills.usecases;

import com.freebills.domains.Account;
import com.freebills.domains.Transaction;
import com.freebills.domains.enums.TransactionCategory;
import com.freebills.domains.enums.TransactionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public final class ReajustAccount {

    private static final String DESCRIPTION = "Reajuste";

    private final FindAccount findAccount;
    private final UpdateAccount updateAccount;
    private final CreateTransaction createTransaction;

    public void reajust(final Long id, final BigDecimal value, final String type) {
        final Account account = findAccount.byId(id);

        if (type.equals("true")) {
            if (value.equals(BigDecimal.ZERO)) {
                if (account.getAmount().compareTo(BigDecimal.ZERO) != 0) {
                    final BigDecimal difference = account.getAmount().multiply(new BigDecimal(-1));
                    account.setAmount(account.getAmount().add(difference));

                    updateAccount.update(account);
                    createTransaction.create(new Transaction(difference.multiply(new BigDecimal(-1)), LocalDate.now(), DESCRIPTION, TransactionType.EXPENSE, TransactionCategory.REAJUST, true, account));
                }
            } else {
                if (value.compareTo(account.getAmount()) > 0) {
                    final BigDecimal diff = value.subtract(account.getAmount());
                    account.setAmount(account.getAmount().add(diff));
                    updateAccount.update(account);
                    createTransaction.create(new Transaction(diff, LocalDate.now(), DESCRIPTION, TransactionType.REVENUE, TransactionCategory.REAJUST, true, account));
                } else {
                    final BigDecimal diff = account.getAmount().subtract(value);
                    account.setAmount(account.getAmount().subtract(diff));
                    updateAccount.update(account);
                    createTransaction.create(new Transaction(diff, LocalDate.now(), DESCRIPTION, TransactionType.EXPENSE, TransactionCategory.REAJUST, true, account));
                }
            }
        } else {
            account.setAmount(value);
            updateAccount.update(account);
        }
    }
}
