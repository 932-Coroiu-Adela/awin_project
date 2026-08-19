package com.awin.awin_project.domain;

import com.awin.awin_project.exception.TransactionAlreadyDecidedException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal saleAmount;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal commissionAmount;

    @Version
    private Long version;

    protected Transaction() {
        // required by JPA
    }

    private Transaction(
            BigDecimal saleAmount,
            BigDecimal commissionAmount
    ) {
        this.status = TransactionStatus.PENDING;
        this.saleAmount = saleAmount;
        this.commissionAmount = commissionAmount;
    }

    public static Transaction create(
            BigDecimal saleAmount,
            BigDecimal commissionAmount
    ) {
        Objects.requireNonNull(saleAmount, "Sale amount must not be null");
        Objects.requireNonNull(
                commissionAmount,
                "Commission amount must not be null"
        );

        if (saleAmount.signum() <= 0) {
            throw new IllegalArgumentException(
                    "Sale amount must be greater than zero"
            );
        }

        if (commissionAmount.signum() <= 0) {
            throw new IllegalArgumentException(
                    "Commission amount must be greater than zero"
            );
        }

        return new Transaction(saleAmount, commissionAmount);
    }

    public void approve() {
        ensurePending();
        status = TransactionStatus.APPROVED;
    }

    public void decline() {
        ensurePending();
        status = TransactionStatus.DECLINED;
    }

    private void ensurePending() {
        if (status != TransactionStatus.PENDING) {
            throw new TransactionAlreadyDecidedException(id);
        }
    }

    public Long getId() {
        return id;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    public BigDecimal getCommissionAmount() {
        return commissionAmount;
    }
}