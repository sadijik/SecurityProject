package com.example.TestProject.repo;


import com.example.TestProject.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
	Users findByUsername(String username);

}
