package com.example.whatsappbackendtest.repository;

import com.example.whatsappbackendtest.domain.model.Conversation;
import com.example.whatsappbackendtest.domain.model.ConversationId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, ConversationId> {
    @Query("select c from Conversation c where c.group.id = :groupId")
    Page<Conversation> paginateByGroupId(@Param("groupId") UUID groupId, Pageable pageable);
}
