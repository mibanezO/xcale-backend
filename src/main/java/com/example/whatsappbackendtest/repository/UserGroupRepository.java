package com.example.whatsappbackendtest.repository;

import com.example.whatsappbackendtest.domain.model.UserGroup;
import com.example.whatsappbackendtest.domain.model.UserGroupId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, UserGroupId> {

    @Query("select g from UserGroup g where g.id.userNumber = :number")
    List<UserGroup> findAllByUserNumber(@Param("number") BigInteger number);
}
