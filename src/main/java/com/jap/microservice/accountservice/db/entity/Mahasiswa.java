package com.jap.microservice.accountservice.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mahasiswa")
public class Mahasiswa {
    @Id
    @GeneratedValue
    private String id;
    @Column(nullable = false)
    private String nama;
}
