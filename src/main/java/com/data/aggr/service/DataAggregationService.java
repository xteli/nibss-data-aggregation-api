/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.data.aggr.service;

import com.data.aggr.dto.request.DataRequest;
import com.data.aggr.dto.response.DataResponse;
import com.data.aggr.entity.TransactionData;
import com.data.aggr.repository.DataAggregationRepository;
import com.data.aggr.util.Enum.AccountClass;
import com.data.aggr.util.Enum.AccountDesignation;
import com.data.aggr.util.Enum.AccountType;
import com.data.aggr.util.Enum.Channel;
import com.data.aggr.util.Enum.Currency;
import com.data.aggr.util.Enum.InstitutionType;
import com.data.aggr.util.Enum.PaymentType;
import com.data.aggr.util.StatusCode;
import com.data.aggr.util.Util;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author chineduojiteli
 */
@Service
public class DataAggregationService {

    StringBuilder builder = null;
    Logger logger = LoggerFactory.getLogger(DataAggregationService.class);
    DataAggregationRepository dataAggrRepository;
    Util util;
    String errorMessage;

    @Autowired
    public DataAggregationService(DataAggregationRepository dataAggrRepository, Util util) {
        this.dataAggrRepository = dataAggrRepository;
        this.util = util;
    }

    public DataResponse sendData(@RequestBody DataRequest dataRequest, HttpHeaders headers) {
        logger.info(".. inside sendData()");
        DataResponse dataResponse = new DataResponse();
        String status = "", message = "";
        String requestId = "";
        builder = new StringBuilder();
        try {
            //perform header validation
            logger.info("About validating header items");
            List<String> errors = util.doHeaderValidation(headers);
            if (errors.isEmpty()) {
                if (dataRequest != null) {

                    if (dataRequest.getTransID() == null) {
                        status = StatusCode.MISSING_PARAMETERS;
                        message = "MISSING TRANSACTION ID";
                    } else if (dataRequest.getDestAcctNo() == null) {
                        status = StatusCode.MISSING_PARAMETERS;
                        message = "MISSING DESTINATION ACCOUNT NUMBER";
                    } else if (dataRequest.getDestInstCode() == null) {
                        status = StatusCode.MISSING_PARAMETERS;
                        message = "MISSING DESTINATION INSTITUTION CODE";
                    } else if (dataRequest.getDestInstBranchCode() == null) {
                        status = StatusCode.MISSING_PARAMETERS;
                        message = "MISSING DESTINATION INSTITUTION BRANCH CODE";
                    } else if (dataRequest.getDestInstType() > 0 && InstitutionType.toEnum(dataRequest.getDestInstType()) == null) {
                        status = StatusCode.MISSING_PARAMETERS;
                        message = "INVALID DESTINATION INSTITUTION TYPE : 1 - INDIVIDUAL, 2 - CORPORATE, 3  - MOBILE WALLET";
                    } else if (dataRequest.getDestInstUniqueID() == null) {
                        status = StatusCode.MISSING_PARAMETERS;
                        message = "MISSING DESTINATION INSTITUTION UNIQUE ID";
                    } else if (dataRequest.getSrcAcctNo() == null) {
                        status = StatusCode.MISSING_PARAMETERS;
                        message = "MISSING SENDER ACCOUNT NUMBER";
                    } else if (dataRequest.getSrcInstCode() == null) {
                        status = StatusCode.MISSING_PARAMETERS;
                        message = "MISSING SENDER INSTITUTION CODE";
                    } else if (dataRequest.getSrcInstBranchCode() == null) {
                        status = StatusCode.MISSING_PARAMETERS;
                        message = "MISSING SENDER INSTITUTION BRANCH CODE";
                    } else if (dataRequest.getSrcInstType() > 0 && InstitutionType.toEnum(dataRequest.getSrcInstType()) == null) {
                        status = StatusCode.MISSING_PARAMETERS;
                        message = "INVALID SENDER INSTITUTION TYPE : 1 - INDIVIDUAL, 2 - CORPORATE, 3  - MOBILE WALLET";
                    } else if (dataRequest.getSrcInstUniqueID() == null) {
                        status = StatusCode.MISSING_PARAMETERS;
                        message = "MISSING SENDER INSTITUTION UNIQUE ID";
                    } else if (dataRequest.getAccountType() > 0 && AccountType.toEnum(dataRequest.getAccountType()) == null) {
                        status = StatusCode.MISSING_PARAMETERS;
                        message = "INVALID ACCOUNT TYPE";
                    } else if (dataRequest.getPaymentType() > 0 && AccountType.toEnum(dataRequest.getPaymentType()) == null) {
                        status = StatusCode.MISSING_PARAMETERS;
                        message = "INVALID PAYMENT TYPE";
                    } else if (dataRequest.getAccountClass() > 0 && AccountClass.toEnum(dataRequest.getAccountClass()) == null) {
                        status = StatusCode.MISSING_PARAMETERS;
                        message = "INVALID ACCOUNT CLASS";
                    }else if (dataRequest.getAmount() == null) {
                        status = StatusCode.MISSING_PARAMETERS;
                        message = "MISSING AMOUNT";
                    } else {
                        TransactionData transData = new TransactionData();
                        transData.setSystemDate(new Date());
                        requestId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
                        transData.setRequestID(requestId);
                        transData.setSharedPSSPParty(dataRequest.getPsspParty());
                        transData.setAccountClass(AccountClass.toEnum(dataRequest.getAccountClass()));
                        transData.setAccountDesignation(AccountDesignation.toEnum(dataRequest.getAccountDesignation()));
                        transData.setAccountType(AccountType.toEnum(dataRequest.getAccountType()));
                        transData.setChannel(Channel.toEnum(dataRequest.getChannel()));
                        transData.setCurrency(Currency.toEnum(dataRequest.getCurrency()));
                        transData.setPaymentType(PaymentType.toEnum(dataRequest.getPaymentType()));
                        transData.setActualBankIncome(BigDecimal.valueOf(new Double(dataRequest.getBankIncome())));
                        transData.setTransactionAmount(BigDecimal.valueOf(new Double(dataRequest.getAmount())));
                        transData.setTransactionFee(BigDecimal.valueOf(new Double(dataRequest.getFee())));
                        transData.setTransactionVAT(BigDecimal.valueOf(new Double(dataRequest.getVat())));
                        transData.setReceivingAccountNumber(dataRequest.getDestAcctNo());
                        transData.setReceivingInstBranchCode(dataRequest.getDestInstBranchCode());
                        transData.setReceivingInstCode(dataRequest.getDestInstCode());
                        transData.setReceivingInstitutionType(InstitutionType.toEnum(dataRequest.getDestInstType()));
                        transData.setReceivingInstitutionUniqueID(dataRequest.getDestInstUniqueID());
                        transData.setSenderAccountNumber(dataRequest.getSrcAcctNo());
                        transData.setSenderInstBranchCode(dataRequest.getSrcInstBranchCode());
                        transData.setSenderInstCode(dataRequest.getSrcInstCode());
                        transData.setSenderInstitutionType(InstitutionType.toEnum(dataRequest.getSrcInstType()));
                        transData.setSenderInstitutionUniqueID(dataRequest.getSrcInstUniqueID());

                        TransactionData dataSaved = dataAggrRepository.save(transData);
                        if (dataSaved != null) {
                            status = StatusCode.SUCCESSFUL;
                            message = "SUCCESSFUL";
                        } else {
                            status = StatusCode.DATABASE_EXCEPTION;
                            message = "FAILED TO SAVE IN DATABASE";
                        }
                    }
                } else {
                    status = StatusCode.EMPTY_REQUEST;
                    message = "EMPTY REQUEST";
                }
            } else {
                builder = new StringBuilder();
                errors.stream().forEach(error -> {
                    builder.append(error).append("\n");
                });
                status = StatusCode.SECURITY_VIOLATION;
                message = builder.toString();
                logger.info("Header Validation Errors : \n" + message);
            }

        } catch (Exception ex) {
            status = StatusCode.GENERAL_EXCEPTION;
            message = ex.getMessage();
            ex.printStackTrace();
        } finally {
            dataResponse.setStatus(status);
            dataResponse.setMessage(message);
            if (status.equals(StatusCode.SUCCESSFUL)) {
                dataResponse.setRequestId(requestId);
            }
        }
        logger.info(".. leaving sendData()");
        return dataResponse;
    }

}
