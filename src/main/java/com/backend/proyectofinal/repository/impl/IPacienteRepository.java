package com.backend.proyectofinal.repository.impl;



import com.backend.proyectofinal.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IPacienteRepository extends JpaRepository<Paciente, Long> {
    @Query("SELECT p FROM Paciente p WHERE p.dni = ?1")
    Paciente findPacienteByDNI(Integer dni);

    @Query("SELECT p FROM Paciente p WHERE p.nombre = ?1 AND p.apellido = ?2")
    Paciente findPacienteByFullname(String nombre, String apellido);

}

