package com.at.repo;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.at.entity.StateEntity;

public interface StateRepository extends JpaRepository<StateEntity, Integer>{

	//@Query(value="select * from state_master where country_Id=:countryId" ,nativeQuery=true )
	public List<StateEntity> findByCountryId(Integer countryId);
}
