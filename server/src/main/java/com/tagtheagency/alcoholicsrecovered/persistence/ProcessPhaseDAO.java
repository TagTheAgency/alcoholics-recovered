package com.tagtheagency.alcoholicsrecovered.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tagtheagency.alcoholicsrecovered.model.ProcessPhase;

@Repository
public interface ProcessPhaseDAO extends JpaRepository<ProcessPhase, Integer>{

	
	@Query(value="SELECT count(*) FROM ProcessStep s WHERE (s.phase) = (:phaseId)", nativeQuery=true)
    public int getStepCount(@Param("phaseId") int phaseId);
}
