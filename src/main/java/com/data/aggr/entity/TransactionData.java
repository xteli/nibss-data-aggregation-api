/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.data.aggr.entity;

import com.data.aggr.util.DateJsonUtil;
import com.data.aggr.util.Enum.AccountClass;
import com.data.aggr.util.Enum.AccountDesignation;
import com.data.aggr.util.Enum.AccountType;
import com.data.aggr.util.Enum.Channel;
import com.data.aggr.util.Enum.Currency;
import com.data.aggr.util.Enum.InstitutionType;
import com.data.aggr.util.Enum.PaymentType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author chineduojiteli
 */
@Entity
@Table(name = "transactiondata")
@Getter
@Setter
public class TransactionData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private long id;
    @Column(name = "RequestID", nullable = false)
    private String requestID;
    @Column(name = "TransactionID", nullable = false)
    private String transactionID;

    //sender institution information
    @Column(name = "SenderAccountNumber", nullable = false)
    private String senderAccountNumber;
    @Column(name = "SenderInstitutionCode", nullable = false)
    private String senderInstCode;
    @Column(name = "SenderInstitutionBranchCode", nullable = false)
    private String senderInstBranchCode;
    @Column(name = "SenderInstitutionType", nullable = false)
    private InstitutionType senderInstitutionType;
    @Column(name = "SenderInstitutionUniqueID", nullable = false)
    private String senderInstitutionUniqueID;

    //receiving institution information
    @Column(name = "ReceivingAccountNumber", nullable = false)
    private String receivingAccountNumber;
    @Column(name = "ReceivingInstitutionCode", nullable = false)
    private String receivingInstCode;
    @Column(name = "ReceivingInstitutionBranchCode", nullable = false)
    private String receivingInstBranchCode;
    @Column(name = "ReceivingInstitutionType", nullable = false)
    private InstitutionType receivingInstitutionType;
    @Column(name = "ReceivingInstitutionUniqueID", nullable = false)
    private String receivingInstitutionUniqueID;

    //other parameters
    @Column(name = "PaymentType", nullable = false)
    private PaymentType paymentType;
    @Column(name = "TransactionDate", nullable = false, columnDefinition = "DATETIME")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @JsonSerialize(using = DateJsonUtil.class)
    private Date transactionDate;
    @Column(name = "TransactionAmount", nullable = false)
    private BigDecimal transactionAmount;
    @Column(name = "TransactionFee", nullable = true)
    private BigDecimal transactionFee;
    @Column(name = "TransactionVAT", nullable = true)
    private BigDecimal transactionVAT;
    @Column(name = "ActualBankIncome", nullable = true)
    private BigDecimal actualBankIncome;
    @Column(name = "AccountType", nullable = false)
    private AccountType accountType;
    @Column(name = "AccountClass", nullable = false)
    private AccountClass accountClass;
    @Column(name = "SharedPSSPParty", nullable = false)
    private String sharedPSSPParty;
    @Column(name = "AccountDesgination", nullable = false)
    private AccountDesignation accountDesignation;
    @Column(name = "Currency", nullable = false)
    private Currency currency;
    @Column(name = "Channel", nullable = false)
    private Channel channel;

    //system date
    @Column(name = "SystemDate", nullable = false, columnDefinition = "DATETIME")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @JsonSerialize(using = DateJsonUtil.class)
    private Date systemDate;
}
