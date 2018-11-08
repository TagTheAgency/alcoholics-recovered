package com.tagtheagency.alcoholicsrecovered.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tagtheagency.alcoholicsrecovered.model.Charge;
import com.tagtheagency.alcoholicsrecovered.model.ProcessStep;

@Repository
public interface ProcessStepDAO extends JpaRepository<ProcessStep, Integer>{

}
