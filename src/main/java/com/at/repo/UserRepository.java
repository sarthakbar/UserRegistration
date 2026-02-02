package com.at.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.at.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
	
	public boolean existsByEmail(String email);
	
	public UserEntity findByEmail(String email);
	
	public UserEntity findByEmailAndPassword(String email, String password);
}
