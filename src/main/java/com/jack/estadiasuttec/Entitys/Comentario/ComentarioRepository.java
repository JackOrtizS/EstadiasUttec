package com.jack.estadiasuttec.Entitys.Comentario;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

}
