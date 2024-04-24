package com.collage.collageerp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer adminId;

  private String adminName;

  @Column(unique = true)
  private String email;

  private String password;

  private String role;
}
