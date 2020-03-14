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
import com.data.aggr.util.StatusCode;
import com.data.aggr.util.Util;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

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

    @Autowired
    public DataAggregationService(DataAggregationRepository dataAggrRepository) {
        this.dataAggrRepository = dataAggrRepository;
    }

    @Autowired
    public DataAggregationService(DataAggregationRepository dataAggrRepository, Util util) {
        this.dataAggrRepository = dataAggrRepository;
        this.util = util;
    }

    public DataResponse sendData(DataRequest dataRequest, HttpHeaders headers) {
        logger.info(".. inside sendData()");
        DataResponse dataResponse = new DataResponse();
        String status = "", message = "";
        String requestId = "";
        try {
            //perform header validation
            logger.info("About validating header items");
            List<String> errors = util.doHeaderValidation(headers);
            if (errors.isEmpty()) {
                if (dataRequest != null) {

                    TransactionData transData = new TransactionData();
                    transData.setSystemDate(new Date());
                    transData.setRequestID(requestId);
                    dataAggrRepository.save(transData);
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
