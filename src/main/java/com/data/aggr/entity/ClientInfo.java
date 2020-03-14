/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.data.aggr.entity;

import com.data.aggr.util.Enum.LifespanType;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author chineduojiteli
 */
@Entity
@Table(name = "clientinfo")
@Getter
@Setter
@NamedQueries({
    @NamedQuery(name = "ClientInfo.findByUsername", query = "SELECT client FROM ClientInfo client where client.username=?1")
})
public class ClientInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private long id;
    @Column(name = "Username", nullable = false)
    private String username;
    @Column(name = "Email", nullable = true)
    private String email;
    @Column(name = "ClientName", nullable = true)
    private String clientName;
    @Column(name = "Active", nullable = false)
    private boolean active = true;
   // @JsonProperty("iv")
    @Column(name = "Password", nullable = true)
    private String password;
   // @JsonProperty("key")
    @Column(name = "Token", nullable = true)
    private String token;
    @Column(name = "Lifespan", nullable = false)
    private int lifespan;
    @Column(name = "LifespanType", nullable = false)
    private LifespanType lifespanType;
    @Column(name = "DateCreated", nullable = false, columnDefinition = "DATETIME")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Column(name = "DateModified", nullable = true, columnDefinition = "DATETIME")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateModified;

   
}
