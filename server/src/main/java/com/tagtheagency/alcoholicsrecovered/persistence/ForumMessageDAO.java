package com.tagtheagency.alcoholicsrecovered.persistence;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tagtheagency.alcoholicsrecovered.model.ForumMessage;

@Repository
public interface ForumMessageDAO extends PagingAndSortingRepository<ForumMessage, Integer>{

}
