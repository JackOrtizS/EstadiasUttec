package com.jack.estadiasuttec.Entitys.Comentario;

import com.jack.estadiasuttec.Entitys.Empresa.Empresa;
import com.jack.estadiasuttec.Entitys.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comentarios")
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComentario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEmpresa", nullable = false)
    private Empresa empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser", nullable = false)
    private User user;

    @Column(name = "calificacion", nullable = false)
    private double calificacion;
    @Column(name = "comentario", nullable = false, length = 200)
    private String comentario;
    @Column(name = "fecha_comentario", nullable = false)
    private Date fecha_comentario;
    @Enumerated(EnumType.STRING)
    Tipo tipo_comentario;
}
