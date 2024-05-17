package com.freebills.gateways.entities;

import com.freebills.gateways.entities.enums.AccountType;
import com.freebills.gateways.entities.enums.BankType;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table(name = "account")
@EqualsAndHashCode(of = "id", exclude = {"transactions", "cards"})
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private BigDecimal amount = new BigDecimal(0);

    private String description;

    @Enumerated(STRING)
    private AccountType accountType;

    @Enumerated(STRING)
    private BankType bankType;

    @ManyToOne
    private UserEntity user;

    @OneToMany(mappedBy = "account", cascade = ALL, fetch = LAZY)
    private List<TransactionEntity> transactionEntities = new ArrayList<>();

    @OneToMany(mappedBy = "account", cascade = ALL, fetch = LAZY)
    private List<CreditCard> cards = new ArrayList<>();

    private boolean archived;

    private boolean dashboard;

}
