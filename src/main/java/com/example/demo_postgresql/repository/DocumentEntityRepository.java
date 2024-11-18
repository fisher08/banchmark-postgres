package com.example.demo_postgresql.repository;


import com.example.demo_postgresql.model.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentEntityRepository extends JpaRepository<DocumentEntity, Long> {

}
