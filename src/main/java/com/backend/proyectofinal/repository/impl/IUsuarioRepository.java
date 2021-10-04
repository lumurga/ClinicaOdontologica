package com.backend.proyectofinal.repository.impl;

import com.backend.proyectofinal.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u WHERE u.nombreUsuario = ?1")
    Optional<Usuario> findUsuarioByName(String nombreUsuario);

}
