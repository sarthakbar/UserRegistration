package com.at.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.at.entity.CountryEntity;

public interface CountryRepository extends JpaRepository<CountryEntity,Integer> {

}
