package com.at.repo;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.at.entity.CityEntity;

public interface CityRepository extends JpaRepository<CityEntity,Integer>{

	//@Query(value="select * from city_master where state_id=:stateId", nativeQuery=true)
	public List<CityEntity>findByStateId(Integer stateId);
	
}
