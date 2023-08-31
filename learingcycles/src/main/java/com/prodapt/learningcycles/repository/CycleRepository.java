package com.prodapt.learningcycles.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.prodapt.learningcycles.entity.Cycles;


public interface CycleRepository extends CrudRepository<Cycles, Integer>{
	Optional<Cycles> findByName(String name);
	
	@Transactional
	@Modifying
	@Query(value="update Cycles set taken=?1 where id=?2")
	void setTakenById(boolean flag,Integer id);

}
