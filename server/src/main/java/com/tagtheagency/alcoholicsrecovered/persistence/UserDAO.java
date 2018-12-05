package com.tagtheagency.alcoholicsrecovered.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tagtheagency.alcoholicsrecovered.model.User;

@Repository
public interface UserDAO extends JpaRepository<User, Long>{

	List<User> findByEmailIgnoreCase(String email);

	List<User> findByUsernameIgnoreCase(String username);
}
