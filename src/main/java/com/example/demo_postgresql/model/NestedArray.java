package com.example.demo_postgresql.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "nested_array")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NestedArray {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String value;

    @ManyToOne
    @JoinColumn(name = "document_entity_id", nullable = false)
    @ToString.Exclude
    private DocumentEntity documentEntity;
}