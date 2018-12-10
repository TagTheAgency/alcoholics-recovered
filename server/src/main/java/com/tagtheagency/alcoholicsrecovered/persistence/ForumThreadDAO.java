package com.tagtheagency.alcoholicsrecovered.persistence;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tagtheagency.alcoholicsrecovered.model.ForumThread;

@Repository
public interface ForumThreadDAO extends PagingAndSortingRepository<ForumThread, Integer>{

}
