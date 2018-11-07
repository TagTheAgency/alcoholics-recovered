package com.tagtheagency.alcoholicsrecovered.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tagtheagency.alcoholicsrecovered.model.Charge;

@Repository
public interface ChargeDAO extends JpaRepository<Charge, Long>{

}
