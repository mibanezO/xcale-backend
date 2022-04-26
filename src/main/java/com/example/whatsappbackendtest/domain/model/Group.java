package com.example.whatsappbackendtest.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "grupo")
public class Group {

    @Id
    @Column(name = "id",nullable = false)
    @Type(type = "uuid-char")
    private UUID id;
    @Column(name = "nombre_grupo")
    private String name;
    @Lob
    @Column(name = "foto", columnDefinition = "BLOB")
    private byte[] photo;

    @JsonIgnore
    @OneToMany(mappedBy = "group")
    private Set<UserGroup> userGroups;

    @JsonIgnore
    @OneToMany(mappedBy = "group")
    private Set<Conversation> conversations;
}
