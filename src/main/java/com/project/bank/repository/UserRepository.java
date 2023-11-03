package com.project.bank.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.bank.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	Optional<User> findByUsername(String username);
	boolean existsByUsername(String username);
	
	@Query(value = "SELECT * FROM users u WHERE u.role = 'NOTUSER'", nativeQuery = true)
	List<User> findByRole();	
	

}
