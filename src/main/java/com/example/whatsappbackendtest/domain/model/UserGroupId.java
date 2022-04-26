package com.example.whatsappbackendtest.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class UserGroupId implements Serializable {
    @Column(name = "usuario_numero")
    private BigInteger userNumber;
    @Column(name = "grupo_id")
    @Type(type = "uuid-char")
    private UUID groupId;
}
