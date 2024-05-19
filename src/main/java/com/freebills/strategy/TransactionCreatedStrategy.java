package com.freebills.strategy;

import com.freebills.domain.Event;
import com.freebills.gateways.entities.enums.EventType;
import com.freebills.gateways.entities.enums.TransactionType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static java.lang.Boolean.TRUE;

@Component(value = "TRANSACTION_CREATED")
public class TransactionCreatedStrategy implements BalanceUpdateStrategy  {

    @Override
    public BigDecimal updateBalance(BigDecimal currentBalance, Event event) {
        if (event.getEventType() == EventType.TRANSACTION_CREATED && TRUE.equals(event.getTransactionData().getPaid())) {
            if (event.getTransactionData().getTransactionType() == TransactionType.EXPENSE) {
                return currentBalance.subtract(event.getTransactionData().getAmount());
            } else if (event.getTransactionData().getTransactionType() == TransactionType.REVENUE) {
                return currentBalance.add(event.getTransactionData().getAmount());
            }
        }

        return currentBalance;
    }
}
