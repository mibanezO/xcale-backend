package com.example.whatsappbackendtest.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class User {

    @Id
    @Column(name = "numero",nullable = false)
    private BigInteger number;
    @Column(name = "nombre_grupo")
    private String name;
    @Lob
    @Column(name = "foto", columnDefinition = "BLOB")
    private byte[] photo;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<UserGroup> userGroups;

}
