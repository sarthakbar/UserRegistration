package com.at.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="country_master")
@Data
public class CountryEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer countryId;
	private String countryName;
}
