package com.example.demo_postgresql.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "config")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Config {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String set;

    @Column(nullable = false)
    private String configId;

    @Column(nullable = false)
    private double size;

    @Column(nullable = false)
    private double multiply;

    @OneToOne
    @JoinColumn(name = "document_entity_id", nullable = false)
    @ToString.Exclude
    private DocumentEntity documentEntity;
}