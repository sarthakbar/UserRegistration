package com.at.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="city_master")
public class CityEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer cityId;
	private String cityName;
	
//	@ManyToOne
//	@JoinColumn(name="state_id")
	private Integer stateId;
	
}
