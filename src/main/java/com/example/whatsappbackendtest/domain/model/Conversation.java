package com.example.whatsappbackendtest.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "conversacion")
public class Conversation {
    @EmbeddedId
    private ConversationId id;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "grupo_id")
    private Group group;

    @ManyToOne
    @MapsId("messageId")
    @JoinColumn(name = "mensaje_id")
    private Message message;
}
