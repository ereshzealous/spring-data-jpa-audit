package com.postgres.jsonb.user.repository;

import com.postgres.jsonb.user.entity.UserDetails;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created on 11/November/2020 By Author Eresh, Gorantla
 **/
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

}