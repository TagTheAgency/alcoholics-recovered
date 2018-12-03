package com.tagtheagency.alcoholicsrecovered.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tagtheagency.alcoholicsrecovered.model.FileLink;

@Repository
public interface FileLinkDAO extends JpaRepository<FileLink, Integer>{
	
}
