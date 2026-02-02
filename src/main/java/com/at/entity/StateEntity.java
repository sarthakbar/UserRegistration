package com.at.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="state_master")
@Data
public class StateEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer stateId;
	private String stateName;
	   
//	@ManyToOne
//	@JoinColumn(name="country_Id")
//	private CountryEntity country;
	
	private Integer countryId;
	
}
