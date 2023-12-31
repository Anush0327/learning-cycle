package com.prodapt.learningcycles.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Cycles {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int id;
  @Column
  private String company;
  @Column
  private int count;
}