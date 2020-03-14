/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.data.aggr.dto.response;

import com.data.aggr.dto.ServiceResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author chineduojiteli
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ResetResponse extends ServiceResponse{

    private String iv;
    private String key;
}
