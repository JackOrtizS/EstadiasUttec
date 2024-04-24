package com.jack.estadiasuttec.Entitys.Empresa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empresa")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long idEmpresa;

    @Basic
    @Column(name = "nombre",nullable = false, length = 255)
    private String nombre;

    @Column(name = "direccion",nullable = false, length = 255)
    private String direccion;
    @Enumerated(EnumType.STRING)
    Sector sector;
}
