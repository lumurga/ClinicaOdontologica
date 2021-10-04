package com.backend.proyectofinal.repository.impl;




import com.backend.proyectofinal.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IOdontologoRepository extends JpaRepository<Odontologo, Long> {
    @Query("SELECT o FROM Odontologo o WHERE o.matricula = ?1")
    Odontologo findOdontologoByMatricula(Integer matricula);

    @Query("SELECT o FROM Odontologo o WHERE o.nombre = ?1 AND o.apellido = ?2")
    Odontologo findOdontologoByFullname(String nombre, String apellido);


}
