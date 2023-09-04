package com.prodapt.learningcycles.repository;

import org.springframework.data.repository.CrudRepository;

import com.prodapt.learningcycles.entity.User;

public interface UserRepository extends CrudRepository<User,Integer>{

    User findByName(String name);

}
