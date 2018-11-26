package com.tagtheagency.alcoholicsrecovered.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tagtheagency.alcoholicsrecovered.model.ProcessPhase;

@Repository
public interface ProcessPhaseDAO extends JpaRepository<ProcessPhase, Integer>{

	
	@Query(value="SELECT count(*) FROM process_step s WHERE phase_id = (:phaseId)", nativeQuery=true)
    public int getStepCount(@Param("phaseId") int phaseId);
	
	List<ProcessPhase> findByPhaseNumber(int phaseNumber);

}
