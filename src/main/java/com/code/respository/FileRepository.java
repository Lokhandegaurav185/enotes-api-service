package com.code.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.code.entity.FileDetails;

public interface FileRepository extends JpaRepository<FileDetails, Integer>{

}
