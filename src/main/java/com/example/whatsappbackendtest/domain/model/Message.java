package com.example.whatsappbackendtest.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "message")
public class Message {

    @Id
    @Column(name = "id",nullable = false)
    @Type(type = "uuid-char")
    private UUID id;
    @Column(name = "fecha_envio")
    private LocalDateTime date;
    @Column(name = "contenido")
    private String message;
    @Column(name = "usuario")
    private BigInteger userNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "message")
    private Set<Conversation> conversations;

}
