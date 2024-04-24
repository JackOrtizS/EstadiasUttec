package com.jack.estadiasuttec.Entitys.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByMatricula(String matricula);


}
