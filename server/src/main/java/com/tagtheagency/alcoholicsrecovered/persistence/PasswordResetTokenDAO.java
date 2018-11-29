package com.tagtheagency.alcoholicsrecovered.persistence;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tagtheagency.alcoholicsrecovered.model.PasswordResetToken;
import com.tagtheagency.alcoholicsrecovered.model.User;

@Repository
public interface PasswordResetTokenDAO extends JpaRepository<PasswordResetToken, Long>{

	PasswordResetToken findByToken(String token);
	
    @Transactional
    @Modifying
    @Query("delete from PasswordResetToken p where p.user=:u")
    public void deleteByUser(@Param("u") User user); 

}
