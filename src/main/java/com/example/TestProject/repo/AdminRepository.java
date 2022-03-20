package com.example.TestProject.repo;

import com.example.TestProject.entity.Blog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends CrudRepository<Blog, Long> {

}
