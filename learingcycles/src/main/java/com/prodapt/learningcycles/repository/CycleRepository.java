package com.prodapt.learningcycles.repository;


import org.springframework.data.repository.CrudRepository;

import com.prodapt.learningcycles.entity.Cycles;


public interface CycleRepository extends CrudRepository<Cycles, Integer>{
	
}
