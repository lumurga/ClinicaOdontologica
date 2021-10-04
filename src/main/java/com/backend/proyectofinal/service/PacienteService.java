package com.backend.proyectofinal.service;



import com.backend.proyectofinal.entity.Paciente;
import com.backend.proyectofinal.repository.impl.IPacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class PacienteService implements IEntidadService<Paciente> {

    /* Atributos */
    private IPacienteRepository pacienteRepository;
    java.util.logging.Logger logger = Logger.getLogger(String.valueOf(PacienteService.class));

    /* Constructor */
    @Autowired
    public PacienteService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    /* Getters y Setters */

    public IPacienteRepository getPacienteRepository() {
        return pacienteRepository;
    }

    public void setPacienteRepository(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    /* Métodos */
    public Paciente save(Paciente paciente) {
        paciente.setFechaIngreso(LocalDate.now());
        return pacienteRepository.save(paciente);
    }

    public Optional<Paciente> findById(Long id) {
        return pacienteRepository.findById(id);
    }

    public List<Paciente> findAll() {
        return pacienteRepository.findAll();
    }

    public Paciente update(Paciente pacienteNew) {
        Paciente pac = pacienteRepository.findById(pacienteNew.getId()).get();
        pac.setNombre(pacienteNew.getNombre());
        pac.setApellido(pacienteNew.getApellido());
        pac.setDni(pacienteNew.getDni());
        pac.setFechaIngreso(pacienteNew.getFechaIngreso());
        pac.setDomicilio(pacienteNew.getDomicilio());
        pacienteRepository.save(pac);
        logger.info("Ha sido actualizado el registro correspondiente al ID: "+ pac.getId());
        return pac;
    }

    public void delete(Long id) {
        if (pacienteRepository.findById(id).isPresent()) {
            pacienteRepository.deleteById(id);
            logger.info("Registro eliminado de la entidad Pacientes correctamente");
            System.out.println("Eliminado con exito!");
        } else {
            logger.info("No ha sido posible eliminar el registro de la entidad Pacientes: ID no encontrado");
            System.out.println("Paciente no encontrado!");
        }
    }

    public Paciente findPacienteByDni(Integer dni) {
        logger.info("Búsqueda en la entidad Pacientes filtrada por DNI");
        return pacienteRepository.findPacienteByDNI(dni);
    }

    public Paciente findPacienteByFullname(String nombre, String apellido){
        logger.info("Búsqueda en la entidad Pacientes filtrada por Nombre y Apellido");
        return pacienteRepository.findPacienteByFullname(nombre, apellido);
    }


}
