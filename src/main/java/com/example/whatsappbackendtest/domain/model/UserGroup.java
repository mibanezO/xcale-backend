package com.example.whatsappbackendtest.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "grupo_usuario")
public class UserGroup {
    @EmbeddedId
    private UserGroupId id;

    @ManyToOne
    @MapsId("userNumber")
    @JoinColumn(name = "usuario_numero")
    private User user;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "grupo_id")
    private Group group;
}
