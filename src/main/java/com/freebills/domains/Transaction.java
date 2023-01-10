package com.freebills.domains;


import com.freebills.domains.enums.TransactionCategory;
import com.freebills.domains.enums.TransactionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    @NotNull
    private LocalDate date;

    @NotBlank
    @Size(max = 50)
    private String description;

    @Size(max = 50)
    private String barCode;

    private Boolean bankSlip;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private TransactionType transactionType;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private TransactionCategory transactionCategory;

    private boolean paid;

    private Long fromAccount;
    private Long toAccount;
    private boolean transactionChange;
    private BigDecimal previousAmount;

    @ManyToOne
    private Account account;

    public Transaction(BigDecimal amount,
                       LocalDate date,
                       String description,
                       TransactionType transactionType,
                       TransactionCategory transactionCategory,
                       boolean paid,
                       Account account) {
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.transactionType = transactionType;
        this.transactionCategory = transactionCategory;
        this.paid = paid;
        this.account = account;
    }
}
