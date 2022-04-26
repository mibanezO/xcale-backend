package com.example.whatsappbackendtest.domain.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class ConversationId implements Serializable {
    @Column(name = "grupo_id")
    @Type(type = "uuid-char")
    private UUID groupId;
    @Column(name = "mensaje_id")
    @Type(type = "uuid-char")
    private UUID messageId;
}
