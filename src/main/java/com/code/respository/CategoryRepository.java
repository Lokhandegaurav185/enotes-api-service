package com.code.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.code.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
